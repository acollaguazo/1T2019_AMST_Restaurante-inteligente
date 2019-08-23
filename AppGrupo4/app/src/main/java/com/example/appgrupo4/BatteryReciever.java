package com.example.appgrupo4;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("WeakerAccess")
class BatteryReceiver extends BroadcastReceiver {

    public final int valorB=Menu.valorB;


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