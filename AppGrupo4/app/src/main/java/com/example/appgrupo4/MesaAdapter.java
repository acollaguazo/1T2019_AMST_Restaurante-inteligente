package com.example.appgrupo4;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.MesaViewHolder> {
    private List<Mesa> items;

    public class MesaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView id;
        public TextView capacidad;
        public TextView estado;


        public MesaViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            id = (TextView) v.findViewById(R.id.mesaID);
            capacidad = (TextView) v.findViewById(R.id.capacidad);
            estado = (TextView) v.findViewById(R.id.estado);
        }
    }

    public MesaAdapter(List<Mesa> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MesaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mesa_card, viewGroup, false);
        return new MesaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MesaViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.id.setText("     Mesa: "+items.get(i).getID());
        viewHolder.capacidad.setText("       Capacidad :"+String.valueOf(items.get(i).getCapacidad()));
        if(items.get(i).getEstado().equals("OCUPADA")){
            viewHolder.estado.setTextColor(Color.rgb(255,50,50));
        } else {
            viewHolder.estado.setTextColor(Color.rgb(50,255,50));
        }
        viewHolder.estado.setText("        "+String.valueOf(items.get(i).getEstado()));
    }
}