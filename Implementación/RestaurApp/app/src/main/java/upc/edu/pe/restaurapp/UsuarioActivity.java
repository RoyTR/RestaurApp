package upc.edu.pe.restaurapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import upc.edu.pe.restaurapp.Servicios.RestaurAppis;

public class UsuarioActivity extends ActionBarActivity {

    private SharedPreferences sharedpreferences;
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);
        Button btnPerfil = (Button)findViewById(R.id.footerusuariobtnperfil);
        btnPerfil.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Action Bar personalizado
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        /*--------------------------Seteando los datos del usuario actual-----------------------*/
        //Obteniendo el Usuario Actual
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);

        //Llamada a nuestro servicio
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://52.25.159.62/api/usuarios/"+usuarioActualId.toString(), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("data").getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        EditText nombres = (EditText) findViewById(R.id.perfTextNombre);
                        EditText apellidos = (EditText) findViewById(R.id.perfTextApellido);
                        EditText email = (EditText) findViewById(R.id.perfTextEmail);
                        EditText username = (EditText) findViewById(R.id.perfTextUserName);

                        nombres.setText(obj.getJSONObject("data").getString("nombres"));
                        apellidos.setText(obj.getJSONObject("data").getString("apellidos"));
                        email.setText(obj.getJSONObject("data").getString("email"));
                        username.setText(obj.getJSONObject("data").getString("username"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_SHORT).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                irSettings(findViewById(R.id.action_settings));
            case R.id.action_contactos:
                irContactos(findViewById(R.id.action_contactos));
                return true;
            case R.id.action_about:
                irAbout(findViewById((R.id.action_about)));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GuardarCambiosPerfil(View v) {

        EditText nombres = (EditText)  findViewById(R.id.perfTextNombre);
        EditText apellidos = (EditText)  findViewById(R.id.perfTextApellido);
        //EditText email = (EditText)  findViewById(R.id.perfTextEmail);
        //EditText username = (EditText)  findViewById(R.id.perfTextUserName);
        EditText password = (EditText) findViewById(R.id.perfTextPassword);

        /*--------Obteniendo el Usuario Actual----------*/
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);

        /*-------Seteando parametros-----------*/
        RequestParams params = new RequestParams();
        params.put("nombres", nombres.getText().toString());
        params.put("apellidos", apellidos.getText().toString());
        params.put("password", password.getText().toString());
        params.put("updated_by", usuarioActualId);
        params.put("_method", "PATCH");

        /*----------Llamada a nuestro servicio---------*/
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://52.25.159.62/api/usuarios/"+usuarioActualId.toString(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("data").getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"¡Cambios Guardados!",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_SHORT).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void GuardarCambiosPreferencia(View v) {

    }

    public void cambiarPerfil(View v) {
        setContentView(R.layout.activity_usuario_perfil);
        Button btnPerfil = (Button)findViewById(R.id.footerusuariobtnperfil);
        btnPerfil.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
    }
    public void cambiarPreferencia(View v) {
        setContentView(R.layout.activity_usuario_preferencias);
        Button btnPref = (Button)findViewById(R.id.footerusuariobtnpreferencia);
        btnPref.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
    }


    public void irContactos(View v){
        Intent intent = new Intent(this, ContactosActivity.class);
        startActivity(intent);
    }
    public  void irSettings(View v){
    }
    public  void irAbout(View v){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}
