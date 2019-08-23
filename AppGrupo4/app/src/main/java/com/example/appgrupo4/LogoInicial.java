package com.example.appgrupo4;

import android.app.Activity;
import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;

/**
 * La clase LogoInicial permite mostar el logo de la empresa al iniciar la aplicacion y luego de 15
 * milisegundos ir a la ventan de inicio de sesion.
 */
public class LogoInicial extends Activity {
    /**
     * METODO SOBREESCRITO: Permite almacenar y recuperar el estado de la actividad logoinicial,
     * ademas, se actualizara cada cierto tiempo con los valores actuales.
     *
     * @param savedInstanceState La actividad almacenada a recuperar.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoinicial);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent siguiente = new Intent(LogoInicial.this, Login.class);
                startActivity(siguiente);
            }
        }, 1500);
    }
}