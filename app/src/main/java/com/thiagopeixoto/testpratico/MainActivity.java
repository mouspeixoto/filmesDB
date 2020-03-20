package com.thiagopeixoto.testpratico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.thiagopeixoto.testpratico.adapters.SerieAdapter;
import com.thiagopeixoto.testpratico.helpers.RecyclerItemClickListener;
import com.thiagopeixoto.testpratico.models.Generos;
import com.thiagopeixoto.testpratico.models.Series;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

public static final String TELEVISAO_URL = "https://api.themoviedb.org/3/tv/popular?api_key=2f116e00cdbdd973618c9f25183932ef&language=pt-BR&page=1";

    List<Series> series = new ArrayList<>();
    RecyclerView rvSeries;
    double voteRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSeries = findViewById(R.id.rvMovies);
        recuperaSeries();
    }

    private void recuperaSeries(){

        // Chama adapter das series
        final SerieAdapter serieAdapter = new SerieAdapter(this, series);
        rvSeries.setAdapter(serieAdapter);
        rvSeries.setLayoutManager(new LinearLayoutManager(this));

        //Consulta banco de dados
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(TELEVISAO_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.e("Sucesso", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    series.addAll(Series.fromJSONArray(results));
                    serieAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("JSONException", "Hit json exception",e);
                    e.printStackTrace();
                }

                //Configura evento de clique
                rvSeries.addOnItemTouchListener(
                        new RecyclerItemClickListener(
                                MainActivity.this,
                                rvSeries,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Series serie =  series.get(position);
                                        Intent a = new Intent(MainActivity.this, DetalhesActivity.class);
                                        a.putExtra("videos", serie.getId());
                                        a.putExtra("desc", serie.getOverview());
                                        a.putExtra("titulo", serie.getTitle());
                                        a.putExtra("ano", serie.getLancamento());
                                        a.putExtra("capa", serie.getPosterPath());
                                        voteRange = serie.getVotosAverage();
                                        double trans = voteRange * 10;
                                        int resultadoMedia = (int)trans;
                                        a.putExtra("voteRange", resultadoMedia);
                                        startActivity(a);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    }
                                }
                        )
                );
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("Falhou", "onFailure");

            }
        });
    }

}
