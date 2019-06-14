package com.example.grupo4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText usuario = (EditText) findViewById(R.id.txtUsuario);
    EditText password = (EditText) findViewById(R.id.txtPasswd);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void LogB(View v) {
        iniciarSesion(usuario.getText().toString(),password.getText().toString());
    }

    void iniciarSesion(String usuario, String password){
        Map<String, String> params = new HashMap();
        params.put("username", usuario);
        params.put("password", password);
        // la verificacion con la base de datos va aqui


        //
        Intent Menu_Principal = new Intent(this, MesasDisponibles.class);
        startActivity(Menu_Principal);
    }
}
