package com.example.appgrupo4;

import android.app.Activity;
import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;

public class LogoInicial extends Activity {

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