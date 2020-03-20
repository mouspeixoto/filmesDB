package com.thiagopeixoto.testpratico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.thiagopeixoto.testpratico.adapters.GenerosAdpter;
import com.thiagopeixoto.testpratico.adapters.SemelhantesAdapter;
import com.thiagopeixoto.testpratico.models.Generos;
import com.thiagopeixoto.testpratico.models.Semelhantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import okhttp3.Headers;

public class DetalhesActivity extends AppCompatActivity {

    public static final String TAG="Resultado: ";
    private String idSerie;
    private String desc, titulo, ano, linkCapa;
    private String keySerieEscolida, keySemelhantes;
    private int voteRange;
    private TextView title, sipnose, anoDetalhe;
    private ImageView capa;
    private RingProgressBar range;
    private RecyclerView recySemelhantes, recyGeneros;
    private List<Generos> listaGenero = new ArrayList<>();
    private List<Semelhantes> semelhantes  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        recySemelhantes = findViewById(R.id.recyRecomend);
        recyGeneros = findViewById(R.id.linearLayoutCompat);

        /*  Recupera dados da Activity anterior   */
        Bundle bundle = getIntent().getExtras();
        if( bundle != null ){
            idSerie = bundle.getString("videos");
            desc = bundle.getString("desc");
            titulo = bundle.getString("titulo");
            ano = bundle.getString("ano");
            linkCapa = bundle.getString("capa");
            voteRange = bundle.getInt("voteRange");

            /*  Monta URL utilizadas pela API  */
            montagem();
            /*  Recupera dados da serie selecionada para utilizar no recyclerView onde mostra as series semelhantes  */
            recuperaSeries();
            /*  Utiliza dados da Activity anterior e da URL para dados sobre a serie selecionada */
            montaDetalhes();
            /*  Verifica a list de generos da serie e monta usando um novo model para mostrar os generos da serie  */
            montaGeneros();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciarComponentes();

    }

    private void montaGeneros(){

        final GenerosAdpter generosAdpter = new GenerosAdpter(this, listaGenero);
        recyGeneros.setAdapter(generosAdpter);
        recyGeneros.setLayoutManager(new GridLayoutManager( this, 2));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(keySerieEscolida, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.e(TAG, " Generos onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("genres");
                    Log.i(TAG, "WEB " +results.toString());
                    listaGenero.addAll(Generos.fromJSONArray(results));
                    generosAdpter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception",e);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) { } });
    }

    private void montaDetalhes(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(keySerieEscolida, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                title.setText(titulo);
                sipnose.setText(desc);
                String test = ano;
                String retornoData[] = test.split("-");
                String ano = retornoData[0];//dia 23
                String mes = retornoData[1];//mes 01
                String dia = retornoData[2];//ano 2018
                String anoEscolido = ano;
                if (anoEscolido != null){
                    anoDetalhe.setText(ano);
                }
                Log.i("linkCapa", ": " + keySerieEscolida);
                Glide.with(DetalhesActivity.this).load(linkCapa).into(capa);

                /*  Faz com que mude o ProgressBar mude de cor a partir de uma determinada avaliação  */
                if (voteRange > 90) {
                    range.setProgress(voteRange);
                    range.setRingProgressColor(Color.BLUE);
                }else if ( voteRange > 60){
                    range.setProgress(voteRange);
                    range.setRingProgressColor(Color.GREEN);
                }else if ( voteRange > 30){
                    range.setProgress(voteRange);
                    range.setRingProgressColor(Color.YELLOW);
                }else if (voteRange < 30){
                    range.setProgress(voteRange);
                    range.setRingProgressColor(Color.RED);
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

        Log.i("WEB", ": " + keySerieEscolida);
    }

    private void montagem(){

        keySerieEscolida = "https://api.themoviedb.org/3/tv/" +  idSerie + "?api_key=2f116e00cdbdd973618c9f25183932ef&language=pt-BR";
        keySemelhantes = "https://api.themoviedb.org/3/tv/" + idSerie + "/similar?" + "api_key=2f116e00cdbdd973618c9f25183932ef&language=pt-BR&page=1";
    }

    private void iniciarComponentes(){

        title = findViewById(R.id.serie_tituloDetalhe);
        sipnose = findViewById(R.id.sipnose);
        anoDetalhe = findViewById(R.id.serie_anoDetalhe);
        capa = findViewById(R.id.post_imgDetalhe);
        range = findViewById(R.id.progress_bar_2);

    }

    private void recuperaSeries(){

        /*  URL da Serie selecionada para busca de seres semelhantes */

        String url = keySemelhantes;

        /*        Cria e Configura Adapter         */
        final SemelhantesAdapter semelhantesAdapter = new SemelhantesAdapter(this, semelhantes);
        recySemelhantes.setAdapter(semelhantesAdapter);
        recySemelhantes.setLayoutManager(new GridLayoutManager( this, 2)); //Duas Colunas

        AsyncHttpClient clientDois = new AsyncHttpClient();
        clientDois.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.e(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "keyDois" +results.toString());
                    semelhantesAdapter.notifyDataSetChanged();
                    semelhantes.addAll(Semelhantes.fromJSONArray(results));
                    Log.i(TAG,"keyDois: "+ semelhantes.size());

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception",e);
                    e.printStackTrace(); } }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure"); }
        });
    }

}
