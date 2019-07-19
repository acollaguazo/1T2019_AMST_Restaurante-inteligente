package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Disponibilidad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidad);
        llenarScrolling(Menu.entries.size());
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
            //vaciarScrolling(Menu.entries.size());
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void vaciarScrolling(int numeroDeMesas){
        View linearLayout = findViewById(R.id.LinearScrollDisp);
        for(int i = 0; i < numeroDeMesas; i++){
            ((LinearLayout) linearLayout).removeView((LinearLayout)findViewById(i));
        }
    }


    private void llenarScrolling(int numeroDeMesas){
        View linearLayout = findViewById(R.id.LinearScrollDisp);
        for (int i = 0; i < numeroDeMesas; i++) {
            crearLinearLayoutHorizontalParaMesa(i,"10","LLENA",linearLayout);
        }
    }

    /**
     *
     * @param id
     * @param capacidad
     * @param estado
     */
    private void crearLinearLayoutHorizontalParaMesa(int id, String capacidad, String estado, View L){
        //setting  TextView ID
        TextView newTxVwTablaID = new TextView(this);
        newTxVwTablaID.setText("Mesa: "+id);
        //setting  TextView capacidad
        TextView newTxVwCapacidad = new TextView(this);
        newTxVwCapacidad.setText("Capacidad: "+capacidad);
        //setting  TextView estado
        TextView newTxVwEstado = new TextView(this);
        newTxVwEstado.setText(estado);
        //setting  LinearLayout vertical
        LinearLayout linearLayoutVertical = new LinearLayout(this);
        linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);
        ((LinearLayout) linearLayoutVertical).addView(newTxVwTablaID);
        ((LinearLayout) linearLayoutVertical).addView(newTxVwCapacidad);
        ((LinearLayout) linearLayoutVertical).addView(newTxVwEstado);
        // setting imageView Estado
       // ImageView imgEstado = new ImageView(this);
       // if(estado.equals("LIBRE"))
       //     imgEstado.setImageResource(R.drawable.vacia);
       // if(estado.equals("OCUPADA"))
       //     imgEstado.setImageResource(R.drawable.llena);
       // imgEstado.getLayoutParams().height = 50;
       // imgEstado.getLayoutParams().width = 50;

        //setting LinearLayout Horizontal
        LinearLayout linearLayoutHorizontal = new LinearLayout(this);
        linearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        //((LinearLayout) linearLayoutHorizontal).addView(imgEstado);
        ((LinearLayout) linearLayoutHorizontal).addView(linearLayoutVertical);

        ((LinearLayout) L).addView(linearLayoutHorizontal);
    }
}
