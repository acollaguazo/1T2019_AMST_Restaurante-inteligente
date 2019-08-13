package com.example.appgrupo4;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.MesaViewHolder> {
    private final List<Mesa> items;

    public class MesaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        final ImageView imagen;
        final TextView id;
        final TextView capacidad;
        final TextView estado;


        MesaViewHolder(View v) {
            super(v);
            imagen = v.findViewById(R.id.imagen);
            id = v.findViewById(R.id.mesaID);
            capacidad = v.findViewById(R.id.capacidad);
            estado = v.findViewById(R.id.estado);
        }
    }

    public MesaAdapter(List<Mesa> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public MesaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mesa_card, viewGroup, false);
        return new MesaViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MesaViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.id.setText("     Mesa: "+items.get(i).getID());
        viewHolder.capacidad.setText("       Capacidad :"+ items.get(i).getCapacidad());
        if(items.get(i).getEstado().equals("OCUPADA")){
            viewHolder.estado.setTextColor(Color.rgb(255,50,50));
        } else {
            viewHolder.estado.setTextColor(Color.rgb(50,255,50));
        }
        viewHolder.estado.setText("        "+ items.get(i).getEstado());
    }
}