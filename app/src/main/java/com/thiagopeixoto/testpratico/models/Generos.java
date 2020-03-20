package com.thiagopeixoto.testpratico.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Generos {

    /* Modelo de generos */

   public String genero;
   public String idGenero;

    public Generos(JSONObject jsonObject) throws JSONException {
        genero = jsonObject.getString("name");
        idGenero = jsonObject.getString("id");
    }

    public static List<Generos> fromJSONArray(JSONArray generoJsonArray) throws JSONException {
        List<Generos> generos = new ArrayList<>();
        for (int i = 0;i < generoJsonArray.length(); i++){
            generos.add(new Generos(generoJsonArray.getJSONObject(i)));
        }
        return generos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(String idGenero) {
        this.idGenero = idGenero;
    }

}
