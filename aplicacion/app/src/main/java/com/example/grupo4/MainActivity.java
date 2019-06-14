package com.example.grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button InicioB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InicioB = (Button) findViewById(R.id.bntInicio);
    }
    public void LogA(View view) {
        Intent sesionIn = new Intent(this, login.class);
        startActivity(sesionIn);
    }
}
