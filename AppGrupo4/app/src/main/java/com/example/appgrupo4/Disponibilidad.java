package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
/**
 * La clase Disponibilidad permite obetner el estado de cada mesa y muestra si esta disponible
 * u ocupada. Ademas de validar si no se posee dato de una mesa, mostrando una advertencia.
 */
public class Disponibilidad extends AppCompatActivity {
    /**
     *METODO SOBREESCRITO: Permite almacenar y recuperar el estado de la actividad disponibilidad.
     *
     * @param savedInstanceState La actividad almacenada a recuperar.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidad);
        refresh();
    }

    /**
     * Permite la actualizacion de la aplicacion de manera automatica cada 7 segundos.
     */
    public void refresh(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                View linearLayout = findViewById(R.id.reciclador);
                llenarScrolling(Menu.registros);
                handler.postDelayed(this, 7000);
            }
        };
        runnable.run();
    }

    /**
     * Permite llenar la ventana de desplazamiento con los estados de las mesas.
     *
     * @param registros HashMap que contiene la informacion de las mesas
     */
    @SuppressWarnings("unused")
    void llenarScrolling(HashMap<String, String[]> registros){

        List items = new ArrayList();
        /**
         * Se Recorre el HashMap para separar los datos de las mesas y luego se los agrega a la
         * lista separandolos por sus estados.
         */
        for (Map.Entry<String, String[]> entry : registros.entrySet()) {
            String mesa = entry.getKey();
            String ubicacion = entry.getValue()[0];
            String capacidad = entry.getValue()[1];
            String estado = entry.getValue()[2];
            aggItem(items,mesa,capacidad,estado);
        }

        // Crea uan lista de tipo RecyclerView
        RecyclerView recycler = findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        RecyclerView.Adapter adapter = new MesaAdapter(items);
        recycler.setAdapter(adapter);
    }

    /**
     * Permite agregar a la lista de items los estados de las mesas.
     *
     * @param items     Lista de las mesas.
     * @param mesa      El numero de la mesa.
     * @param capacidad Numero de personas que se pueden sentar en la mesa.
     * @param estado    El estado actual de la mesa.
     */
    @SuppressLint("SetTextI18n")
    private void aggItem(List items, String mesa, String capacidad, String estado){
        if(estado == null){
            // Si el estado esta vacio se le agrega la imagen de advertencia.
            items.add(new Mesa(mesa,capacidad,"Sin Dato",R.drawable.advertencia));
        }
        else if(estado.equals("OC")) {
            // Si el estado es OC se agrega la imagen de mesa llena.
            items.add(new Mesa(mesa,capacidad,"OCUPADA",R.drawable.llena));
        }
        else if(estado.equals("DE")) {
            // Si el estado es DE se agrega la imagen de mesa vacia.
            items.add(new Mesa(mesa,capacidad,"LIBRE",R.drawable.vacia));
        }
    }
}