package com.example.downloadmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adaptador.ArchivosAdapter;
import modelo.Archivos;

public class MainActivity extends AppCompatActivity {

    List<Archivos> listaArchivos;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        evento();
    }

    private void ponerAdaptador() {
        ArchivosAdapter adapter = new ArchivosAdapter(listaArchivos, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerArchivos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void evento() {
        String url = "https://my-json-server.typicode.com/VivianZamora/JsonDocumentos/documentos";
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaArchivos = new ArrayList<Archivos>();

                        int tamanio = response.length();
                        if (tamanio > 0) {
                            for (int i = 0; i < tamanio; i++) {
                                try {
                                    JSONObject json = new JSONObject(response.get(i).toString());
                                    Archivos Archivos = new Archivos(json.getString("descripcion"),
                                            json.getString("fecha"), json.getString("link"));
                                    listaArchivos.add(Archivos);
                                } catch (JSONException ex) {
                                    Archivos Archivos = new Archivos("Sin Fecha",
                                            "Sin Fecha", "");
                                    listaArchivos.add(Archivos);
                                    System.out.println(ex.toString());
                                }
                            }
                        } else {
                            Archivos Archivos = new Archivos("Sin nombre",
                                    "Sin Fecha", "");
                            listaArchivos.add(Archivos);
                        }
                        ponerAdaptador();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError ex) {
                Archivos Archivos = new Archivos("Sin Fecha",
                        "Sin Fecha", "");
                listaArchivos.add(Archivos);
                System.out.println(ex.toString());
            }
        });
        requestQueue.add(jsonRequest);

    }
}