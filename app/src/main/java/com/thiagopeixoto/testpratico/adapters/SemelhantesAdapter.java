package com.thiagopeixoto.testpratico.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thiagopeixoto.testpratico.R;
import com.thiagopeixoto.testpratico.models.Semelhantes;

import java.util.List;

public class SemelhantesAdapter extends RecyclerView.Adapter<SemelhantesAdapter.ViewHolder>{

    /* Exibição de series semelhantes */
    Context context;
    List<Semelhantes> semelhantes;


    public SemelhantesAdapter(Context context, List<Semelhantes> semelhantes) {
        this.context = context;
        this.semelhantes = semelhantes;
    }

    @NonNull
    @Override
    public SemelhantesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.activity_semelhantes_adapter, parent, false);
        return new SemelhantesAdapter.ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull SemelhantesAdapter.ViewHolder holder, int position) {
        Semelhantes semelhante = semelhantes.get(position);
        holder.bind(semelhante);
    }

    @Override
    public int getItemCount() {
        return semelhantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  titulo;
        ImageView capa;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            capa = itemView.findViewById(R.id.semelhantesCapa);
            titulo = itemView.findViewById(R.id.textTituloSemelhante);
        }

        public void bind(Semelhantes semelhantes) {

            titulo.setText(semelhantes.getTitle());

            String  imageUrl;
            // if phone is an landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // the imageUrl = back drop image
                imageUrl = semelhantes.getCapa();
            } else {
                // else imageUrl = poster image
                imageUrl = semelhantes.getCapa();
            }
            Glide.with(context).load(imageUrl).into(capa);
            }
    }
}
