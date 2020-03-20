package com.thiagopeixoto.testpratico.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Semelhantes {

    /* Modelo de Series Semelhantes */
    String capa;
    String title;

    public Semelhantes(JSONObject jsonObject) throws JSONException {

        capa = jsonObject.getString("poster_path");
        title = jsonObject.getString("name");

    }

    public static List<Semelhantes> fromJSONArray(JSONArray semelhantesJsonArray) throws JSONException {
        List<Semelhantes> semelhantes = new ArrayList<>();
        for (int i = 0;i < semelhantesJsonArray.length(); i++){
            semelhantes.add(new Semelhantes(semelhantesJsonArray.getJSONObject(i)));
        }
        return semelhantes;
    }

    public String getCapa() {
        /* Formata url das capas */
        return String.format("https://image.tmdb.org/t/p/w342/%s", capa);
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
