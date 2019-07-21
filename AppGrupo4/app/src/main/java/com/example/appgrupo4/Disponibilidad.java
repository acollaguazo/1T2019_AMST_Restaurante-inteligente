package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Disponibilidad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidad);
        llenarScrolling(Menu.registros);
    }

    /**
     * METODO SOBREESCRITO: Toma la acción del boton de regresar del telefono y se encarga de llamar al método para eliminar TextView y regresar al menu
     * @param keyCode variable que viene por override
     * @param event   evento que viene por override
     * @return retorno que viene por override
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            vaciarScrolling(Menu.registros.size());
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Elimina dinamicamente los LinearLayout que se crearon
     * @param cantidadMesas cantidad de mesas existentes
     */
    private void vaciarScrolling(int cantidadMesas){
        View linearLayout = findViewById(R.id.LinearScrollDisp);
        for(int i = 0; i < cantidadMesas; i++){
            ((LinearLayout) linearLayout).removeView((LinearLayout)findViewById(i));
        }
    }

    /**
     * llena el Scrolling
     * @param registros
     */
    private void llenarScrolling(HashMap<String,String[]> registros){
        View linearLayout = findViewById(R.id.LinearScrollDisp);
        for (Map.Entry<String, String[]> entry : registros.entrySet()) {
            String mesa = entry.getKey();
            String ubicacion = entry.getValue()[0];
            String capacidad = entry.getValue()[1];
            String estado = entry.getValue()[2];
            crearLinearLayoutHorizontalParaMesa(mesa,capacidad,estado,linearLayout);
        }
    }

    /**
     *
     * @param id
     * @param capacidad
     * @param estado
     * @param L
     */
    private void crearLinearLayoutHorizontalParaMesa(String id, String capacidad, String estado, View L){
        //setting  TextView TVid
        TextView TVid = new TextView(this);
        TVid.setText("MESA: "+id);
        TVid.setGravity(Gravity.CENTER);
        // Set the TextView font and font style
        TVid.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        TVid.setTextColor(Color.BLACK);
        TVid.setPadding(15,15,15,10);
        TVid.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20); //dp
        //setting  TextView capacidad
        TextView TVCapacidad = new TextView(this);
        TVCapacidad.setText("Capacidad: "+capacidad);
        TVCapacidad.setGravity(Gravity.CENTER);
        //setting  TextView estado
        TextView TVEstado = new TextView(this);
        TVEstado.setGravity(Gravity.CENTER);
        ImageView imgEstado = new ImageView(this);
        imgEstado.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        TVEstado.setText(estado);
        if(estado == null){
            TVEstado.setText("sin dato");
            imgEstado.setImageResource(R.drawable.advertencia);
        }
        else if(estado.equals("OC")) {
            TVEstado.setText("OCUPADA");
            TVEstado.setTextColor(Color.RED);
            imgEstado.setImageResource(R.drawable.llena);
        }
        else if(estado.equals("DE")) {
            TVEstado.setText("LIBRE");
            TVEstado.setTextColor(Color.GREEN);
            imgEstado.setImageResource(R.drawable.vacia);
        }
        //setting  LinearLayout vertical
        LinearLayout linearLayoutVertical = new LinearLayout(this);
        linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);
        ((LinearLayout) linearLayoutVertical).addView(TVid);
        ((LinearLayout) linearLayoutVertical).addView(TVCapacidad);
        ((LinearLayout) linearLayoutVertical).addView(TVEstado);
        //SETTING LINEARLAYOUT Horizontal
        LinearLayout layoutHorizontal = new LinearLayout(this);
        layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        layoutHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layoutHorizontal.setGravity(Gravity.CENTER);
        layoutHorizontal.setBackgroundColor(Color.WHITE);
        ((LinearLayout) layoutHorizontal).addView(imgEstado);
        ((LinearLayout) layoutHorizontal).addView(linearLayoutVertical);
        ((LinearLayout) L).addView(layoutHorizontal);
    }
}
