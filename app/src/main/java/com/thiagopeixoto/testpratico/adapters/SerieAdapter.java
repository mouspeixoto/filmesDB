package com.thiagopeixoto.testpratico.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thiagopeixoto.testpratico.R;

import java.util.List;

import com.thiagopeixoto.testpratico.models.Series;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.ViewHolder>{

    /* Adapter principal - exebição */
    Context context;
    List<Series> series;

    public SerieAdapter(Context context, List<Series> series) {
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.adapter_series_new, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder" + position);
        Series series = this.series.get(position);
        holder.bind(series);

    }
    @Override
    public int getItemCount() {
        return series.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tituloSerie, serieAno;
        ImageView banner;
        double voteRange;
        RingProgressBar circuloRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloSerie = itemView.findViewById(R.id.serie_titulo);
            serieAno = itemView.findViewById(R.id.serie_ano);
            banner = itemView.findViewById(R.id.post_img);
            circuloRating = itemView.findViewById(R.id.progress_bar_1);
        }

        public void bind(Series series) {
            tituloSerie.setText(series.getTitle());
            voteRange = series.getVotosAverage();
            double trans = voteRange * 10;
            int resultadoMedia = (int)trans;

            String test = series.getLancamento();
            String retornoData[] = test.split("-");
            String ano = retornoData[0];//dia 23
            String mes = retornoData[1];//mes 01
            String dia = retornoData[2];//ano 2018
            String anoEscolido= ano;
            if (anoEscolido != null){
                serieAno.setText(ano);
            }

            /*  Faz com que mude o ProgressBar mude de cor a partir de uma determinada avaliação  */
            if (resultadoMedia > 90) {
                circuloRating.setProgress(resultadoMedia);
                circuloRating.setRingProgressColor(Color.BLUE);
            }else if ( resultadoMedia > 60){
                circuloRating.setProgress(resultadoMedia);
                circuloRating.setRingProgressColor(Color.GREEN);
            }else if ( resultadoMedia > 30){
                circuloRating.setProgress(resultadoMedia);
                circuloRating.setRingProgressColor(Color.YELLOW);
            }else if (resultadoMedia < 30){
                circuloRating.setProgress(resultadoMedia);
                circuloRating.setRingProgressColor(Color.RED); }

            /*  Biblioteca Glide   */
            String  imageUrl;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = series.getBackdropPath();
            } else {
                imageUrl = series.getPosterPath();
            }
            Glide.with(context).load(imageUrl).into(banner);
        }
    }
}
