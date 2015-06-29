package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import upc.edu.pe.restaurapp.Adapter.CategoriaAdapter;
import upc.edu.pe.restaurapp.Adapter.CategoriaPreferenciaAdapter;
import upc.edu.pe.restaurapp.Entidades.Categoria;

public class UsuarioActivity extends ActionBarActivity {

    private SharedPreferences sharedpreferences;
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES" ;
    ProgressDialog prgDialog;

    List<Categoria> lstCategoriasUsuario = new ArrayList<Categoria>();
    List<Categoria> lstCategoriasTotales = new ArrayList<Categoria>();



    ListView lstVwCategPref;

    String errorhttp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);
        Button btnPerfil = (Button)findViewById(R.id.footerusuariobtnperfil);
        btnPerfil.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        this.prgDialog = new ProgressDialog(this);

        //Action Bar personalizado
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //Cargar de antemano la lista de categrias Completa
        cargarPreferenciasCompletas();


        //Cargar y mostrar el perfil
        this.cambiarPerfil(btnPerfil);
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


    public void cargarPreferenciasCompletas(){

        /*--------------------------LLamar a las categorias totales-----------------------*/

        //Llamada a nuestro servicio
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://52.25.159.62/api/categorias", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jArray = obj.getJSONArray("data");

                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        //Convertir JsonAray ---> List<Categoria>
                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            Categoria categoria = new Categoria();
                            categoria.setId(jObj.getInt("id"));
                            categoria.setNombre(jObj.getString("nombre"));
                            categoria.setDescripcion(jObj.getString("descripcion"));

                            llenarListaCategoriasCompletas(categoria);
                        }
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
    public void llenarListaCategoriasCompletas(Categoria categoria){
        this.lstCategoriasTotales.add(categoria);
    }

    public void GuardarCambiosPerfil(View v) {

        EditText nombres = (EditText)  findViewById(R.id.perfTextNombre);
        EditText apellidos = (EditText)  findViewById(R.id.perfTextApellido);
        EditText email = (EditText)  findViewById(R.id.perfTextEmail);
        //EditText username = (EditText)  findViewById(R.id.perfTextUserName);
        EditText password = (EditText) findViewById(R.id.perfTextPassword);

        /*--------Obteniendo el Usuario Actual----------*/
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);

        /*-------Seteando parametros-----------*/
        RequestParams params = new RequestParams();
        params.put("nombres", nombres.getText().toString());
        params.put("apellidos", apellidos.getText().toString());
        params.put("email", email.getText().toString());
        if(!(password.getText().toString().isEmpty()))//if the user added a password
            params.put("password", password.getText().toString());
        params.put("updated_by", usuarioActualId);
        params.put("_method", "PATCH");

        prgDialog.setCancelable(false);
        prgDialog.setMessage("Guardando...");
        prgDialog.show();

        /*----------Llamada a nuestro servicio---------*/
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://52.25.159.62/api/usuarios/"+usuarioActualId.toString(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("data").getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences(RESTAURAPP_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt("USUARIO_ACTUAL_ID",obj.getJSONObject("data").getInt("id"));
                        editor.putString("USUARIO_ACTUAL_NOMBRES",obj.getJSONObject("data").getString("nombres"));
                        editor.putString("USUARIO_ACTUAL_APELLIDOS",obj.getJSONObject("data").getString("apellidos"));
                        editor.putString("USUARIO_ACTUAL_EMAIL",obj.getJSONObject("data").getString("email"));
                        editor.putString("USUARIO_ACTUAL_USERNAME",obj.getJSONObject("data").getString("username"));
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"¡Cambios Guardados!",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
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

         /*--------Obteniendo el Usuario Actual----------*/
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);


        //-------Guardando o borrando las preferencias--------/
        for(int i=0;i< lstVwCategPref.getAdapter().getCount();i++){
            Categoria categoria = (Categoria)lstVwCategPref.getItemAtPosition(i);
            if(categoria.isPref()) {
                lstCategoriasTotales.get(i).setPref(true);
                AgregarPreferencia(usuarioActualId, categoria.getId());
            }else {
                lstCategoriasTotales.get(i).setPref(false);
                EliminarPreferencia(usuarioActualId, categoria.getId());
            }
        }
    }
    public void AgregarPreferencia(int usuario_id, int categoria_id){
        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();

        //parametros a agregar
        RequestParams params = new RequestParams();
        params.put("usuario_id", usuario_id);
        params.put("categoria_id", categoria_id);
        //api
        prgDialog.setMessage("Guardando..");
        prgDialog.show();
        client.post("http://52.25.159.62/api/preferencias/agregar", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    errorhttp = "No se encontro el resource";
                } else if (statusCode == 500) {
                    errorhttp = "Hubo un error en el servidor";
                } else {
                    errorhttp = "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]";
                }

            }
        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//

    }
    public void EliminarPreferencia(int usuario_id, int categoria_id){

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();

        //api
        prgDialog.setMessage("Guardando..");
        prgDialog.show();
        client.post("http://52.25.159.62/api/preferencias/eliminar?usuario_id="+usuario_id+"&categoria_id="+categoria_id,  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    errorhttp = "No se encontro el resource";
                } else if (statusCode == 500) {
                    errorhttp = "Hubo un error en el servidor";
                } else {
                    errorhttp="Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]";
                }
            }
        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//

    }

    public void cambiarPerfil(View v) {
        setContentView(R.layout.activity_usuario_perfil);
        Button btnPerfil = (Button)findViewById(R.id.footerusuariobtnperfil);
        btnPerfil.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        btnPerfil.setClickable(false);

        /*--------------------------Seteando los datos del usuario actual-----------------------*/
        //Obteniendo el Usuario Actual
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);
        String usuarioActualNombres = sharedpreferences.getString("USUARIO_ACTUAL_NOMBRES", "");
        String usuarioActualApellidos = sharedpreferences.getString("USUARIO_ACTUAL_APELLIDOS", "");
        String usuarioActualEmail = sharedpreferences.getString("USUARIO_ACTUAL_EMAIL", "");
        String usuarioActualUsername = sharedpreferences.getString("USUARIO_ACTUAL_USERNAME", "");

        EditText nombres = (EditText) findViewById(R.id.perfTextNombre);
        EditText apellidos = (EditText) findViewById(R.id.perfTextApellido);
        EditText email = (EditText) findViewById(R.id.perfTextEmail);
        EditText username = (EditText) findViewById(R.id.perfTextUserName);
        EditText password = (EditText) findViewById(R.id.perfTextPassword);

        nombres.setText(usuarioActualNombres);
        apellidos.setText(usuarioActualApellidos);
        email.setText(usuarioActualEmail);
        username.setText(usuarioActualUsername);
        password.setText("");
    }

    public void cambiarPreferencia(View v) {
        setContentView(R.layout.activity_usuario_preferencias);
        Button btnPref = (Button)findViewById(R.id.footerusuariobtnpreferencia);
        btnPref.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        btnPref.setClickable(false);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Cargando Preferencias de Usuario");
        prgDialog.setCancelable(false);
        prgDialog.show();

        lstCategoriasUsuario.clear();

        /*--------------------------LLamar a las preferecnias del usuario-----------------------*/
        //Obteniendo el Usuario Actual
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);

        //Llamada a nuestro servicio
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://52.25.159.62/api/usuarios/"+usuarioActualId.toString()+"/preferencias?include=detail", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    //obtenemos el JSON
                    JSONObject obj = new JSONObject(response);
                    //Obtenemos el Array de restaurantes
                    JSONArray jArray = obj.getJSONArray("data");

                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("data").getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        //Convertir JsonAray ---> List<Categoria>
                        for(int i=0;i<jArray.length();i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            Categoria categoria = new Categoria();

                            categoria.setId(jObj.getJSONObject("detail").getJSONObject("data").getInt("id"));
                            categoria.setNombre(jObj.getJSONObject("detail").getJSONObject("data").getString("nombre"));
                            categoria.setDescripcion(jObj.getJSONObject("detail").getJSONObject("data").getString("descripcion"));

                            llenarListaCategoriasUsuario(categoria);
                        }
                        actualizarListaConAdapterCategorias();
                        prgDialog.hide();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
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
    public void llenarListaCategoriasUsuario(Categoria categoria){
        this.lstCategoriasUsuario.add(categoria);
    }
    private void actualizarListaConAdapterCategorias() {

        //En la lista total seteamos como favoritos las categorias de la lista de usuario
        for(int i=0;i<lstCategoriasTotales.size();i++){
            for(int j=0;j<lstCategoriasUsuario.size();j++){
                if(lstCategoriasTotales.get(i).getId() == lstCategoriasUsuario.get(j).getId()){
                    lstCategoriasTotales.get(i).setPref(true);
                    break;
                }
                else
                    lstCategoriasTotales.get(i).setPref(false);
            }
        }

        //Lista
        lstVwCategPref = (ListView) findViewById(R.id.usuario_pref_lstvw);
        CategoriaPreferenciaAdapter adapter = new CategoriaPreferenciaAdapter(this.lstCategoriasTotales,this);
        lstVwCategPref.setAdapter(adapter);
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
