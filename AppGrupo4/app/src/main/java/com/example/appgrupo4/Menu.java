package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
    public static int NUMMESAS =2;
    public static ArrayList<String> entries = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mQueue = Volley.newRequestQueue(this);
        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");
        revisarSensores();
    }

    private void revisarSensores() {
        String url_temp;
        for (int i = 0; i < NUMMESAS; i++) {
            url_temp = (String)("https://amstdb.herokuapp.com/db/mesa/"+(i+1));
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, url_temp, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("----------------------------------------");
                            System.out.println(response);
                            System.out.println("----------------------------------------");
                            try {
                                entries.add(response.getString("id")+"|"+response.getString("ubicacion")+"|"+response.getString("capacidad"));
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
            }) {
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
    }

    public void next_disponibilidad(View view){
        Intent next = new Intent(this, Disponibilidad.class);
        startActivity(next);
    }

    public void next_estado(View view){
        Intent next = new Intent(this,Estado.class);
        startActivity(next);
    }
}
