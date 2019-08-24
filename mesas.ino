// Importacion de librerias
#include <WISOL.h>
#include <Wire.h>
#include <math.h>
// Conexion con sigfox
Isigfox *Isigfox = new WISOL();
// Constructor para convertir en bytes
typedef union {
  uint16_t number;
  uint8_t bytes[2];
} UINT16_t;

// Declaracion de variables
int pin0, pin1, voltajeAlfombra, bateriaEnviar;
float bateria, porcentajeBateria, voltajeMedido;
unsigned long tiempoActual;
long tiempoAnterior, intervalo;
String mesa;
/*
  Fuencion de inicio del programa que se
  ejecuta una sola vez
*/
void setup() {
  // Inicializacion de variables
  pin0 = 0;
  pin1 = 1;
  tiempoAnterior = 0;
  intervalo = 600000;
  mesa = "";
  bateriaEnviar = 100;
  //Inicio de la la comunicacion I2C
  Wire.begin();
  Wire.setClock(100000);
  // Velocidad del puerto serial
  Serial.begin(9600);
  // Inicializacion de la comunicacion con sigfox
  Isigfox->initSigfox();
  Isigfox->testComms();
}
/*
  Funcion que se ejecutara continuamente durante el arduino
  se encuentre activo.
*/
void loop() {
  // Funcion de lectura de todos los sensores
  ValoresSensados();
  // Funcion que permite obtener el ultimo valor de la bateria
  BateriaMenor((int)porcentajeBateria);
  /*
    Condicion que solo enviara a sigfox si existe un cmabio de 
    estado en la mesa.
  */
  if (voltajeMedido > 2.90) {
    if (mesa != "DE") {
      Serial.println("desocupada");
      Serial.println(bateriaEnviar);
      mesa = ObtenerEstadoDeMesa(voltajeMedido);
      EnviarSigfox(mesa, bateriaEnviar);
      delay(10000);
    }
  }
  else {
    if (mesa != "OC") {
      Serial.println("Ocupada");
      Serial.println(bateriaEnviar);
      mesa = ObtenerEstadoDeMesa(voltajeMedido);
      EnviarSigfox(mesa, bateriaEnviar);
      delay(10000);
    }
  }
  /*  
    Envia a sigfox el valor de la bateria en porcentaje
    a sigfox cada 10 minutos.
  */
  tiempoActual = millis();
  if (tiempoActual - tiempoAnterior > intervalo) {
    tiempoAnterior = tiempoActual;
    Serial.println(bateriaEnviar);
    EnviarSigfox(mesa, (int)porcentajeBateria);
    delay(10000);
  }
}

/*  
  La funcion EnviarSigfox recive como parametros el estado de la mesa y la batera
  agregandolas en el contructor como bytes y luego enviando a sigfox 
*/
void EnviarSigfox(String mesa, int bateria) {
  byte *stringt_byte2 = (byte *)&mesa;
  const uint8_t payloadSize = 4;
  uint8_t buf_stri[payloadSize];
  buf_stri[0] = mesa.charAt(0);
  buf_stri[1] = mesa.charAt(1);
  buf_stri[2] = bateria;
  uint8_t *sendData = buf_stri;
  int len = 4;
  recvMsg *RecvMsg;
  RecvMsg = (recvMsg *)malloc(sizeof(recvMsg));
  Isigfox->sendPayload(sendData, len, 0, RecvMsg);
  for (int i = 0; i < RecvMsg->len; i++) {
    Serial.print(RecvMsg->inData[i]);
  }
  Serial.println("");
  free(RecvMsg);
}

/* 
  La funcion ObtenerEstadoDeMesa obtiene como parametro el valor del sensor en 
  voltios y luego retorna DE su es mayor a 2.90 y OC si es menor a dicho valor
*/
String ObtenerEstadoDeMesa(float voltajeMedido) {
  if (voltajeMedido > 2.90) {
    return "DE";
  } else {
    return "OC";
  }
}

/*  
  Se obtiene todos los valores de los sensores y se les asigna a una variable
  los valores censados son la alfombra y la bateria.
*/
void ValoresSensados() {
  voltajeAlfombra = analogRead(pin0);
  voltajeMedido = (((float)voltajeAlfombra) * 5.0) / 1023.0;
  Serial.println(voltajeMedido);
  bateria = analogRead(pin1);
  porcentajeBateria = (((bateria) * 5.0) / 1023.0) * (100 / 4.5);
}
/*
  La funcion BateriaMenor recibe el valor de la bateria actual y si es menor a 
  la anterior actualiza la variable caso contrario no hace nada.
*/
void BateriaMenor(int bateriaActual) {
  if ((bateriaActual < bateriaEnviar)) {
    bateriaEnviar = bateriaActual;
  }
}
