package com.thiagopeixoto.testpratico.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thiagopeixoto.testpratico.R;
import com.thiagopeixoto.testpratico.models.Generos;

import java.util.List;

public class GenerosAdpter extends RecyclerView.Adapter<GenerosAdpter.ViewHolder> {

    /* Exibição de generos */
    Context context;
    List<Generos> generosList;

    public GenerosAdpter(Context context, List<Generos> generosList) {
        this.context = context;
        this.generosList = generosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("GenerosAdpter","onCreateViewHolder");
        View generoView = LayoutInflater.from(context).inflate(R.layout.activity_generos_adpter, parent, false);
        return new ViewHolder(generoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder" + position);
        Generos generos = generosList.get(position);
        holder.bind(generos);
    }

    @Override
    public int getItemCount() {
        return generosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomeGenero;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           nomeGenero = itemView.findViewById(R.id.textGenero);
        }

        public void bind(Generos generos) {

            nomeGenero.setText(generos.getGenero());
        }
        }
    }

