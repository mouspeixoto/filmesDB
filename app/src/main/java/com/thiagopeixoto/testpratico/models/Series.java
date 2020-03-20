package com.thiagopeixoto.testpratico.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Series {

    /* Modelo principal - Responsavel pelo formar uma serie e mandar dados sobre a mesma para outras activities */

    String id;
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String lancamento;
    String generos;
    int votos;
    double votosAverage;
    int test;



    public Series(JSONObject jsonObject) throws JSONException {
        //Capas
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        //
        title = jsonObject.getString("name");
        overview = jsonObject.getString("overview");
        lancamento = jsonObject.getString("first_air_date");
        votos = jsonObject.getInt("vote_count");
        votosAverage = jsonObject.getDouble("vote_average");
        test = jsonObject.getInt("vote_average");
        id = jsonObject.getString("id");
        generos = String.valueOf(jsonObject.getJSONArray("genre_ids"));

    }

    public static List<Series> fromJSONArray(JSONArray movieJsonArray) throws JSONException {
        List<Series> series = new ArrayList<>();
        for (int i = 0;i < movieJsonArray.length(); i++){
        series.add(new Series(movieJsonArray.getJSONObject(i)));
    }
        return series;
    }


    public String getPosterPath() {

        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public double getVotosAverage() {
        return votosAverage;
    }

    public void setVotosAverage(double votosAverage) {
        this.votosAverage = votosAverage;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }
}
