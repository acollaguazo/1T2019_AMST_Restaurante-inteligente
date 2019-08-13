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
public class Disponibilidad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidad);
        refresh();
    }
    public void refresh(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                View linearLayout = findViewById(R.id.reciclador);
                //((LinearLayout) linearLayout).removeAllViews();
                llenarScrolling(Menu.registros);
                handler.postDelayed(this, 7000);

                //vaciarScrolling(Menu.registros.size());
                System.out.println("Actualizando Vista");
            }
        };
        runnable.run();
    }

    /**
     * llena el Scrolling
     * @param registros
     */
    @SuppressWarnings("unused")
    void llenarScrolling(HashMap<String, String[]> registros){

        List items = new ArrayList();

        for (Map.Entry<String, String[]> entry : registros.entrySet()) {
            String mesa = entry.getKey();
            String ubicacion = entry.getValue()[0];
            String capacidad = entry.getValue()[1];
            String estado = entry.getValue()[2];
            aggItem(items,mesa,capacidad,estado);
        }

        RecyclerView recycler = findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        RecyclerView.Adapter adapter = new MesaAdapter(items);
        recycler.setAdapter(adapter);
    }


    @SuppressLint("SetTextI18n")
    private void aggItem(List items, String mesa, String capacidad, String estado){
        if(estado == null){
            items.add(new Mesa(mesa,capacidad,"Sin Dato",R.drawable.advertencia));
        }
        else if(estado.equals("OC")) {
            items.add(new Mesa(mesa,capacidad,"OCUPADA",R.drawable.llena));
        }
        else if(estado.equals("DE")) {
            items.add(new Mesa(mesa,capacidad,"LIBRE",R.drawable.vacia));
        }
    }


}