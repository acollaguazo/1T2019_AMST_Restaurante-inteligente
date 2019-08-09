package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("SameParameterValue")
public class Menu extends AppCompatActivity {

    private RequestQueue mQueue;
    private String token = "";
    private final String urlEstados =  "https://amstdb.herokuapp.com/db/registroEstadoMesa";
    private final String urlMesas =  "https://amstdb.herokuapp.com/db/mesa";
    public static final HashMap<String,String[]> registros = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mQueue = Volley.newRequestQueue(this);
        Intent login = getIntent();
        this.token = (String) Objects.requireNonNull(login.getExtras()).get("token");
        obtenerEstadoMesa(urlMesas,urlEstados);
    }

    /**
     * Se encarga de obtener datos de las mesas que se encuantran en la base de datos
     * @param urlMesa
     * @param urlEstado
     */
    private void obtenerEstadoMesa(String urlMesa, final String urlEstado) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, urlMesa, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        for(int i = 0; i<response.length(); i++){
                            try {
                                @SuppressWarnings("CStyleArrayDeclaration") String datos[] = new String[3];
                                datos[0] = response.getJSONObject(i).getString("ubicacion");
                                datos[1] = response.getJSONObject(i).getString("capacidad");
                                registros.put(response.getJSONObject(i).getString("id"),datos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        JsonArrayRequest request = new JsonArrayRequest(
                                Request.Method.GET, urlEstado, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response1) {
                                        for(int i = 0; i<response1.length(); i++){
                                            try {
                                                String estado = response1.getJSONObject(i).getString("estado");
                                                Objects.requireNonNull(registros.get(response1.getJSONObject(i).getString("mesa")))[2] = estado;
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast toast=Toast.makeText(getApplicationContext(),"Sin datos",Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            })
                            {
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> parametros = new HashMap<>();
                                    parametros.put("Authorization", "JWT " + token);
                                    return parametros;
                                }
                            };
                            mQueue.add(request);
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast=Toast.makeText(getApplicationContext(),"Sin datos",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Authorization", "JWT " + token);
                return parametros;
            }
        };
        mQueue.add(request);
    }

    /**
     * avanzar a la clase Disponibilidad donde se representara cada mesa de manera grafica con todos sus componentes
     * @param view
     */
    public void next_disponibilidad(View view){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                obtenerEstadoMesa(urlMesas,urlEstados);
                for (Map.Entry<String, String[]> entry : registros.entrySet()) {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue()[0]);
                    System.out.println(entry.getValue()[1]);
                    System.out.println(entry.getValue()[2]);}

                handler.postDelayed(this, 6000);
            }
        };
        runnable.run();

        Intent siguiente = new Intent(this, Disponibilidad.class);
        startActivity(siguiente);
    }

    /**
     * avanzar a la clase Estado donde se representara por medio de un grafico de pastel, el porcentaje de mesas ocupadas y disponibles
     * @param view
     */
    public void next_estado(View view){
        obtenerEstadoMesa(urlMesas,urlEstados);
        Intent siguiente = new Intent(this,Estado.class);
        startActivity(siguiente);
    }

    /**
     * Cierra la aplicacion por completo
     * @param view
     */
    public void next_salir(View view){
        Intent siguiente = new Intent(this,Login.class);
        startActivity(siguiente);
    }
    public void next_bateria(View view){
        Intent siguiente = new Intent(this,EstadoBateria.class);
        startActivity(siguiente);
    }
}
