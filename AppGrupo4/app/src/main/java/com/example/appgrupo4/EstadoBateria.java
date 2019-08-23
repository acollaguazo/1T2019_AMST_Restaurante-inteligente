package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase de Estado de bateria Permite obtener la informacion de la bateria para mostrarla.
 */
public class EstadoBateria extends AppCompatActivity {

    public static RequestQueue mQueue;
    private final BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private final IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    /**
     * METODO SOBREESCRITO: Permite almacenar y recuperar el estado de la actividad estado_bateria,
     * ademas, se actualizara cada 7 segundos con los valores actuales.
     *
     * @param savedInstanceState La actividad almacenada a recuperar.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_estado_bateria);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));
    }

    /**
     * Permite ejecutar al inicio cada vez que se reinicia el registro de la bateria.
     */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryReceiver, mIntentFilter);

    }

    /**
     * Se desregistrara la informacion de la bateria.
     */
    @Override
    protected void onPause() {
        unregisterReceiver(mBatteryReceiver);
        super.onPause();
    }





}
