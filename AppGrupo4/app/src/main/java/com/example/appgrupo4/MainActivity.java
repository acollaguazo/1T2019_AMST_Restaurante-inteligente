package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void siguienteLogin(View view){
        Intent toLogin = new Intent(this, Login.class);
        startActivity(toLogin);
    }
}
