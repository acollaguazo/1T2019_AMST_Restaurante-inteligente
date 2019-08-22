package com.example.appgrupo4;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class BatteryReceiver extends BroadcastReceiver {

    public int valorB=Menu.valorB;


    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {

        TextView statusLabel = ((EstadoBateria)context).findViewById(R.id.textEstadoActualBateria);
        TextView percentageLabel = ((EstadoBateria)context).findViewById(R.id.textViewPorcentajeBateria);
        ImageView batteryImage = ((EstadoBateria)context).findViewById(R.id.imageEstadoActualBateria);

        //String action = intent.getAction();

        if (valorB != 0) {


            statusLabel.setText("Estado Actual");


            // Percentage
            int percentage = valorB;
            percentageLabel.setText(percentage + "%");



            // Image
            Resources res = context.getResources();

            if (percentage >= 90) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b100));

            } else if (percentage >= 65) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b75));

            } else if (percentage >= 40) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b50));

            } else if (percentage >= 15) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b25));
            } else {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b0));

            }

        }
    }

}