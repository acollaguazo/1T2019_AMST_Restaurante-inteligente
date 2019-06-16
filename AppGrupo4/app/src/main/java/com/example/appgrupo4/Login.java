package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Siguiente_menu(View view){
        Intent siguiente = new Intent( this, Menu.class);
        startActivity(siguiente);
    }
}
