package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private RequestQueue mQueue;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout constr =findViewById(R.id.root_layoutLogin);
        AnimationDrawable animacion= (AnimationDrawable) constr.getBackground();
        animacion.setEnterFadeDuration(2000);
        animacion.setExitFadeDuration(4000);
        animacion.start();
        mQueue = Volley.newRequestQueue(this);
    }



    public void irMenuPrincipal(View v){
        final EditText usr = (EditText) findViewById(R.id.txtUsuario);
        final EditText psswrd = (EditText) findViewById(R.id.txtPassword);
        String str_usr = usr.getText().toString();
        String str_psswd = psswrd.getText().toString();
        iniciarSesion(str_usr,str_psswd);
    }

    private void iniciarSesion(String usuario, String password){


        Map<String, String> params = new HashMap();
        params.put("username", usuario);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);
        String login_url = "https://amstdb.herokuapp.com/db/nuevo-jwt";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, login_url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            token = response.getString("token");
                            Intent toMenuPrincipal = new Intent(getBaseContext(), Menu.class);
                            toMenuPrincipal.putExtra("token", token);
                            startActivity(toMenuPrincipal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ConnectivityManager gestorDeConexion = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo infoDeRed = gestorDeConexion .getActiveNetworkInfo();
                        boolean connected = infoDeRed != null && infoDeRed.isAvailable() && infoDeRed.isConnected();
                        if(!connected) {
                            Toast advertencia = Toast.makeText(getApplicationContext(),"Sin acceso a internet",Toast.LENGTH_SHORT);
                            advertencia.show();
                        } else {
                            Toast advertencia = Toast.makeText(getApplicationContext(), "Datos incorrectos, intente nuevamente", Toast.LENGTH_SHORT);
                            advertencia.show();
                        }
                    }
                });
        mQueue.add(request);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 1500);
       //     super.onBackPressed();
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}




