package com.example.appgrupo4;

import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MesadosAdapter extends RecyclerView.Adapter<MesadosAdapter.MesadosViewHolder> {
    private List<Mesados> itemsdos;

    public class MesadosViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView iddos;
        public TextView capacidaddos;
        public TextView estadodos;


        public MesadosViewHolder(View v) {
            super(v);
            iddos = (TextView) v.findViewById(R.id.mesaID);
            capacidaddos = (TextView) v.findViewById(R.id.capacidad);
            estadodos = (TextView) v.findViewById(R.id.estado);
        }
    }

    public MesadosAdapter(List<Mesados> items) {
        this.itemsdos = items;
    }

    @Override
    public int getItemCount() {
        return itemsdos.size();
    }

    @Override
    public MesadosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mesa_carddos, viewGroup, false);
        return new MesadosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MesadosViewHolder viewHolder, int i) {


        if(Estado.orientacion.equals("portrait")) {
            viewHolder.iddos.getLayoutParams().width = Estado.width / 4;
            viewHolder.capacidaddos.getLayoutParams().width = Estado.width / 4;
            viewHolder.estadodos.getLayoutParams().width = Estado.width / 3;
        }
        else if(Estado.orientacion.equals("landscape")) {
            viewHolder.iddos.getLayoutParams().width = Estado.width / 7;
            viewHolder.capacidaddos.getLayoutParams().width = Estado.width / 7;
            viewHolder.estadodos.getLayoutParams().width = Estado.width / 6;
        }
        viewHolder.iddos.setGravity(Gravity.CENTER);
        viewHolder.estadodos.setGravity(Gravity.CENTER);
        viewHolder.capacidaddos.setGravity(Gravity.CENTER);
        viewHolder.iddos.setText("MESA: "+itemsdos.get(i).getIDdos());
        viewHolder.capacidaddos.setText("CAP: "+itemsdos.get(i).getCapacidaddos());
        viewHolder.estadodos.setText("EST: "+itemsdos.get(i).getEstadodos());
    }
}