package com.example.appgrupo4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        TextView statusLabel = ((EstadoBateria)context).findViewById(R.id.textEstadoActualBateria);
        TextView percentageLabel = ((EstadoBateria)context).findViewById(R.id.textViewPorcentajeBateria);
        ImageView batteryImage = ((EstadoBateria)context).findViewById(R.id.imageEstadoActualBateria);

        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Status
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String message = "";

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "Carga Completa";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Cargando Batería";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Batería en Uso";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    message = "Batería en uso";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Estado Actual Desconocido";
                    break;
            }
            statusLabel.setText(message);


            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;
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