package com.example.inmobiliarialucero.ui.inmuebles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliarialucero.R;
import com.example.inmobiliarialucero.modelo.Inmueble;
import com.example.inmobiliarialucero.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {

    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Inmueble inmueble);
    }

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater, OnItemClickListener listener) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.iteminmueble, parent, false);
        return new ViewHolderInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmueble = listaInmuebles.get(position);

        holder.direccion.setText(inmueble.getDireccion());
        holder.precio.setText(String.format("$ %,.2f", inmueble.getValor()));

        Glide.with(context)
                .load(ApiClient.BASE_URL + inmueble.getImagen())
                .placeholder(null)
                .error("error")
                .into(holder.portada);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(inmueble));
    }

    @Override
    public int getItemCount() {
        return listaInmuebles != null ? listaInmuebles.size() : 0;
    }

    public static class ViewHolderInmueble extends RecyclerView.ViewHolder {
        TextView direccion, precio;
        ImageView portada;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvValor);
            portada = itemView.findViewById(R.id.imgPortada);
        }
    }

    public void setInmuebles(List<Inmueble> nuevosInmuebles) {
        this.listaInmuebles.clear();
        this.listaInmuebles.addAll(nuevosInmuebles);
        notifyDataSetChanged();
    }
}
