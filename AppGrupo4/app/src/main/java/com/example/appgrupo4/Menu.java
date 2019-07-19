package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {

    private RequestQueue mQueue;
    private String token = "";
    public static int NUMMESAS = 5; // NUMERO DE MESAS QUE HAY EN LA BASE DE DATOS
    private String urlEstadoMesas =  "https://amstdb.herokuapp.com/db/registroEstadoMesa/";
    private String urlMesas =  "https://amstdb.herokuapp.com/db/mesa/";
    public static ArrayList<String> entries = new ArrayList<>(); // GUARDARA LA INFORMACION DE LA BASE DE DATOS EN FORMA DE QUE CADA MESA SE REPRESENTA COMO STRING


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mQueue = Volley.newRequestQueue(this);
        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");
        obtenerEstadoDeMesas(NUMMESAS);
    }

    public void obtenerEstadoDeMesas(int numeroDeMesas){
        for(int i = 1; i <= numeroDeMesas; i++){
            obtenerEstadoMesa(urlMesas,i);   ///////////// AQUI
        }
    }

    /**
     * Se encarga de obtener datos de una mesa en la base de datos
     * @param url Url de que representa el lugar donde esta la base de datos
     * @param numero numero que representa una mesa en la base de datos
     */
    private void obtenerEstadoMesa(String url,int numero) {
        String url_temp;
        url_temp = (String)(url+(numero));
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("----------------------------------------");  // ELIMINAR DESPUES
                        System.out.println(response);                                    // ELIMINAR DESPUES
                        System.out.println("----------------------------------------");  // ELIMINAR DESPUES
                        try {
                            entries.add("             id: "+response.getString("id")+"               ubicacion: "+response.getString("ubicacion")+"               capacidad: "+response.getString("capacidad"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR");
                        Toast toast=Toast.makeText(getApplicationContext(),"Sin datos",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT " + token);
                System.out.println(token);
                return params;
            }
        };
        mQueue.add(request);
    }

    /**
     * avanzar a la clase Disponibilidad donde se representara cada mesa de manera grafica con todos sus componentes
     * @param view
     */
    public void next_disponibilidad(View view){
        Intent next = new Intent(this, Disponibilidad.class);
        startActivity(next);
    }

    /**
     * avanzar a la clase Estado donde se representara por medio de un grafico de pastel, el porcentaje de mesas ocupadas y disponibles
     * @param view
     */
    public void next_estado(View view){
        Intent next = new Intent(this,Estado.class);
        startActivity(next);
    }

    public void next_salir(View view){
        finishAffinity();
        System.exit(0);
    }
}
