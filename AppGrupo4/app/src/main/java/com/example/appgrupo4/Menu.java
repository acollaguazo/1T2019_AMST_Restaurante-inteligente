package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void Siguiente_disponibilidad(View view){
        Intent siguiente = new Intent( this, Disponibilidad.class);
        startActivity(siguiente);
    }
}
