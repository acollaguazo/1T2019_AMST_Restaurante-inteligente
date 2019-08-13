package com.example.appgrupo4;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MesadosAdapter extends RecyclerView.Adapter<MesadosAdapter.MesadosViewHolder> {
    private final List<Mesados> itemsdos;

    public class MesadosViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        final TextView iddos;
        final TextView capacidaddos;
        final TextView estadodos;


        MesadosViewHolder(View v) {
            super(v);
            iddos = v.findViewById(R.id.mesaID);
            capacidaddos = v.findViewById(R.id.capacidad);
            estadodos = v.findViewById(R.id.estado);
        }
    }

    public MesadosAdapter(List<Mesados> items) {
        this.itemsdos = items;
    }

    @Override
    public int getItemCount() {
        return itemsdos.size();
    }

    @NonNull
    @Override
    public MesadosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mesa_carddos, viewGroup, false);
        return new MesadosViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MesadosViewHolder viewHolder, int i) {


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