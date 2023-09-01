package org.acestream.engine.client.example;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class APIManager {
    private String apiUrl = "https://kodispaintv.onrender.com/api/buscar/productos/60911fbf01f9ac001530be3c";
    private String consultaAPI(){
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            Log.v("aa","json");
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            String json = content.toString();

            in.close();
            con.disconnect();
            return json;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public AcestreamElement[] ParseAPI(){
        String json = consultaAPI();
        // Parsear el JSON
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        JsonArray resultsArray = jsonObject.getAsJsonArray("results");

        // Usar Gson para convertir el JSON en objetos Java
        Gson gson = new Gson();
        AcestreamElement[] resultados = gson.fromJson(resultsArray, AcestreamElement[].class);

        return resultados;

    }

}
