package com.example.alex.recupalejandrogarcia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String URL="http://192.168.31.12/ciudades/city.list.json";

    Button btnBuscar, btnMostrar, btnGuardar, btnCargar;
    EditText etLocalidad;
    TextView tvLatitud, tvLongitud, tvCodigoPais;

    ArrayList<Localidad> listaLocalidades;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnMostrar = findViewById(R.id.btnMostrar);
        etLocalidad = findViewById(R.id.etLocalidad);
        tvLatitud=findViewById(R.id.tvLatitud);
        tvLongitud=findViewById(R.id.tvLongitud);
        tvCodigoPais=findViewById(R.id.tvCodigoPais);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCargar= findViewById(R.id.btnCargar);

        listaLocalidades =  new ArrayList<>();

        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONArray jsonArrayPrincipal = new JSONArray(response.toString(0));
                    // System.out.println(jsonArrayPrincipal.toString());
                    for(int i=0;i<jsonArrayPrincipal.length();i++){
                        JSONObject elemento = jsonArrayPrincipal.getJSONObject(i);
                       // System.out.println(elemento.toString());

                        Localidad localidad = new Localidad();
                        //System.out.println(elemento.get("name").toString());
                        localidad.setLocalidad(elemento.get("name").toString());

                        System.out.println(elemento.getJSONObject("coord").get("lat").toString()+" "+elemento.getJSONObject("coord").get("lon").toString());
                        localidad.setLatitud(elemento.getJSONObject("coord").get("lat").toString());
                        localidad.setLongitud(elemento.getJSONObject("coord").get("lon").toString());

                        localidad.setCodigoPais(elemento.get("country").toString());

                        listaLocalidades.add(localidad);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO CREA JSON");
                Toast.makeText(MainActivity.this, "No se han podido recuperar los datos de Internet.", Toast.LENGTH_SHORT).show();
            }
        });

        request.add(jsonArrayRequest);



    }

    public void buscar (View v){
        String local = etLocalidad.getText().toString();
        boolean encontrado=false;

        for(int i=0;i<listaLocalidades.size();i++){
            if(listaLocalidades.get(i).getLocalidad().equals(local)){
                Toast.makeText(this, "Localidad encontrada. Actualizando campos.", Toast.LENGTH_SHORT).show();
                tvLongitud.setText(listaLocalidades.get(i).getLongitud());
                tvLatitud.setText(listaLocalidades.get(i).getLatitud());
                tvCodigoPais.setText(listaLocalidades.get(i).getCodigoPais());
                btnMostrar.setEnabled(true);
                encontrado=true;
                break;
            }
        }
        if(encontrado==false) {
            Toast.makeText(this, "Localidad no existe.", Toast.LENGTH_SHORT).show();
            btnMostrar.setEnabled(false);
            tvLatitud.setText("Latitud:");
            tvLongitud.setText("Longitud:");
            tvCodigoPais.setText("Codigo de pais:");
        }
    }

    public void mostrar(View v){
        Intent e = new Intent(getApplicationContext(), ActivityMap.class);
        e.putExtra("localidad", etLocalidad.getText().toString());
        e.putExtra("latitud",tvLatitud.getText().toString());
        e.putExtra("longitud",tvLongitud.getText().toString());
        e.putExtra("codPais",tvCodigoPais.getText().toString());
        startActivity(e);
    }

    public void guardarLocalidad(View v){
        SharedPreferences preferences=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("localidad",etLocalidad.getText().toString());
        editor.commit();
        Toast.makeText(this, "Se ha guardado la localidad", Toast.LENGTH_SHORT).show();
    }

    public void cargarLocalidad (View v){
        SharedPreferences preferences=getSharedPreferences("datos", Context.MODE_PRIVATE);
        etLocalidad.setText(preferences.getString("localidad", ""));
        Toast.makeText(this, "Se ha cargado la localidad.", Toast.LENGTH_SHORT).show();
        tvLatitud.setText("Latitud:");
        tvLongitud.setText("Longitud:");
        tvCodigoPais.setText("Codigo de pais:");
        btnMostrar.setEnabled(false);
    }
}
