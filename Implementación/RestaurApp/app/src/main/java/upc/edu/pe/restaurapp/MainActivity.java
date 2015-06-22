package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.restaurapp.Adapter.CategoriaAdapter;
import upc.edu.pe.restaurapp.Adapter.DistritoAdapter;
import upc.edu.pe.restaurapp.Adapter.RestauranteAdapter;
import upc.edu.pe.restaurapp.Entidades.Categoria;
import upc.edu.pe.restaurapp.Entidades.Distrito;
import upc.edu.pe.restaurapp.Entidades.Restaurante;


public class MainActivity extends ActionBarActivity {

    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES" ;
    private SharedPreferences sharedpreferences;

    ProgressDialog prgDialog;
    public final List<Restaurante> lstRestCerca = new ArrayList<Restaurante>();
    public final List<Distrito> lstDistritos = new ArrayList<Distrito>();
    public final List<Categoria> lstCategorias = new ArrayList<Categoria>();
    final List<Restaurante> lstRestFavoritos = new ArrayList<Restaurante>();
    final List<Restaurante> lstRestPreferencias = new ArrayList<Restaurante>();
    final List<Restaurante> lstRestRecomendados = new ArrayList<Restaurante>();
    GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_buscar_distrito);

        //Action Bar personalizado
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //Dialog
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);


        //Generales
        Button btn = (Button) findViewById(R.id.mainbtnfterbuscar);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        ObtenerDistritos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_perfil:
                irUsuario(findViewById(R.id.action_perfil));
                return true;
            case R.id.action_contactos:
                irContactos(findViewById(R.id.action_contactos));
                return true;
            case R.id.action_about:
                irAbout(findViewById(R.id.action_about));
                return true;
            case R.id.action_cerrarsesion:
                cerrarSesion(findViewById(R.id.action_cerrarsesion));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cambiarBuscar(View v){

        setContentView(R.layout.activity_main_buscar_distrito);
        Button btn = (Button) findViewById(R.id.mainbtnfterbuscar);
        btn.setClickable(false);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        //ListView lstVwDistritos = (ListView) findViewById(R.id.distrito_lst_distritos);
        ObtenerDistritos();
        //DistritoAdapter distritosAdapter = new DistritoAdapter(lstDistritos,this);
        //lstVwDistritos.setAdapter(distritosAdapter);

        /*//Listener
        lstVwDistritos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ListaRestaurantesActivity.class);
                startActivity(intent);
            }
        });*/

    }
    public void cambiarBuscarTipoComida(View v){

        setContentView(R.layout.activity_main_buscar_tipocomida);
        Button btn = (Button) findViewById(R.id.mainbtnfterbuscar);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        btn.setClickable(false);
        //llamada a categorias
        ObtenerCategorias();
    }

    public void cambiarCerca(View v){

        setContentView(R.layout.activity_main_cerca_lista);
        Button btn = (Button) findViewById(R.id.mainbtnftercerca);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ObtenerListaCerca();

        //ListView lstVwRestaurantesCerca = (ListView) findViewById(R.id.listViewCerca);
        //RestauranteAdapter restauranteAdapter = new RestauranteAdapter(lstRestCerca,this);
        //lstVwRestaurantesCerca.setAdapter(restauranteAdapter);

        //Listener
        /*lstVwRestaurantesCerca.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurante restaurante = (Restaurante) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, RestauranteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Nombre",restaurante.getNombre());
                bundle.putString("Distrito",restaurante.getDistrito());
                bundle.putString("TipoComida",restaurante.getTipoComida());
                bundle.putDouble("Puntaje",restaurante.getPuntuacionTotal());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });*/

    }
    public void cambiarCercaMap(View v){

        setContentView(R.layout.activity_main_cerca_mapa);
        Button btn = (Button) findViewById(R.id.mainbtnftercerca);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        SupportMapFragment spmap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapa = spmap.getMap();

        //Establecer tipos de mapa
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapa.setMyLocationEnabled(true);

        for (int i=0; i<lstRestCerca.size(); i++) {
            mapa.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lstRestCerca.get(i).getLatitud()), Double.parseDouble(lstRestCerca.get(i).getLongitud()))).title(lstRestCerca.get(i).getNombre()));
        }

        //mapa.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lstRestCerca.get(1).getLatitud()), Double.parseDouble(lstRestCerca.get(1).getLongitud()))).title(lstRestCerca.get(1).getNombre()));

    }

    public void cambiarFavoritos(View v){

        setContentView(R.layout.activity_main_favoritos);
        Button btn = (Button) findViewById(R.id.mainbtnfterfavoritos);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        ObtenerListaFavoritos();
    }

    public void cambiarRecomendaciones(View v){

        setContentView(R.layout.activity_main_recomendaciones_recomendados);
        Button btn = (Button) findViewById(R.id.mainbtnfterrecomendaciones);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        ObtenerListaRecomRecomendados();

    }
    public void cambiarRecomendacionesPreferencias(View v){

        setContentView(R.layout.activity_main_recomendaciones_preferencias);
        Button btn = (Button) findViewById(R.id.mainbtnfterrecomendaciones);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        ObtenerListaRecomPreferencias();
    }

    public void irRestaurante(View v){
        Intent intent = new Intent(this, RestauranteActivity.class);
        startActivity(intent);
    }
    //-----------------------------------MENU BAR----------------------------------//
    public void irContactos(View v){
        Intent intent = new Intent(this, ContactosActivity.class);
        startActivity(intent);
    }
    public  void irUsuario(View v){
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
    }
    public  void irSettings(View v){
    }
    public  void irAbout(View v){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    public void cerrarSesion(View v){
        SharedPreferences.Editor editor = getSharedPreferences(RESTAURAPP_PREFERENCES, MODE_PRIVATE).edit();
        editor.putInt("USUARIO_ACTUAL_ID",0);
        editor.commit();

        Intent intent = new Intent(this, IniciarSesionActivity.class);
        intent.putExtra("Destino","Login");
        startActivity(intent);

        finish();

    }




    //------------------------------OBTENER LISTAS DE RESTAURANTES-----------------------//
    private void ObtenerListaCerca() {

        this.lstRestCerca.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();
        client.get("http://52.25.159.62/api/restaurantes", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    //obtenemos el JSON
                    JSONObject obj = new JSONObject(response);
                    //Obtenemos el Array de restaurantes
                    JSONArray jArray = obj.getJSONArray("data");


                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        //Convertir JsonAray ---> List<Restaurante>
                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            Restaurante restaurante = new Restaurante();
                            restaurante.setIdRestaurante((Integer) jObj.getInt("id"));
                            restaurante.setNombre(jObj.getString("nombre"));
                            restaurante.setLatitud(jObj.getString("latitud"));
                            restaurante.setLongitud(jObj.getString("longitud"));
                            restaurante.setDescripcion(jObj.getString("descripcion"));
                            if(jObj.getString("foto_id") != null)
                                restaurante.setFoto_id(Integer.parseInt(jObj.getString("foto_id")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "El id de la foto es nulo", Toast.LENGTH_LONG).show();
                            }
                            restaurante.setDistrito(jObj.getString("distrito_id"));
                            if(jObj.getString("puntuacion_total") != null)
                                restaurante.setPuntuacionTotal(Double.parseDouble(jObj.getString("puntuacion_total")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "La puntuacion es nula", Toast.LENGTH_LONG).show();
                            }

                            llenarListaCerca(restaurante);
                        }

                    }
                    actualizarListaConAdapterCerca();
                    prgDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_LONG).show();
                }
            }

        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//

    }
    private void actualizarListaConAdapterCerca(){

        ListView lstVwRestaurantesCerca = (ListView) findViewById(R.id.listViewCerca);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(lstRestCerca,this);
        lstVwRestaurantesCerca.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesCerca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurante restaurante = (Restaurante) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, RestauranteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("IdRestaurante", restaurante.getIdRestaurante());
                bundle.putString("Nombre", restaurante.getNombre());
                bundle.putString("Latitud", restaurante.getLatitud());
                bundle.putString("Longitud", restaurante.getLongitud());
                bundle.putString("Descripcion", restaurante.getDescripcion());
                bundle.putInt("Foto_id", restaurante.getFoto_id());
                bundle.putString("DistritoId", restaurante.getDistrito());
                bundle.putDouble("PuntuacionTotal", restaurante.getPuntuacionTotal());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
    public void llenarListaCerca(Restaurante rest){
        lstRestCerca.add(rest);
    }


    private void ObtenerListaFavoritos() {

        this.lstRestFavoritos.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();

        //Obtener Id del usuario
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);

        client.get("http://52.25.159.62/api/usuarios/"+usuarioActualId+"/favoritos", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    //obtenemos el JSON
                    JSONObject obj = new JSONObject(response);
                    //Obtenemos el Array de restaurantes
                    JSONArray jArray = obj.getJSONArray("data");


                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        //Convertir JsonAray ---> List<Restaurante>
                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            Restaurante restaurante = new Restaurante();
                            restaurante.setIdRestaurante( jObj.getInt("id"));
                            restaurante.setNombre(jObj.getString("nombre"));
                            restaurante.setLatitud(jObj.getString("latitud"));
                            restaurante.setLongitud(jObj.getString("longitud"));
                            restaurante.setDescripcion(jObj.getString("descripcion"));
                            if(jObj.getString("foto_id") != null)
                                restaurante.setFoto_id(Integer.parseInt(jObj.getString("foto_id")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "El id de la foto es nulo", Toast.LENGTH_LONG).show();
                            }
                            restaurante.setDistrito(jObj.getString("distrito_id"));
                            if(jObj.getString("puntuacion_total") != null)
                                restaurante.setPuntuacionTotal(Double.parseDouble(jObj.getString("puntuacion_total")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "La puntuacion es nula", Toast.LENGTH_LONG).show();
                            }

                            llenarListaFavoritos(restaurante);
                        }

                    }
                    actualizarListaConAdapterFavoritos();
                    prgDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_LONG).show();
                }
            }

        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//

    }
    private void actualizarListaConAdapterFavoritos(){
        //Lista
        ListView lstVwRestaurantesFavoritos = (ListView) findViewById(R.id.listViewFavoritos);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(lstRestFavoritos,this);
        lstVwRestaurantesFavoritos.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesFavoritos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurante restaurante = (Restaurante) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, RestauranteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("IdRestaurante",restaurante.getIdRestaurante());
                bundle.putString("Nombre",restaurante.getNombre());
                bundle.putString("Latitud",restaurante.getLatitud());
                bundle.putString("Longitud",restaurante.getLongitud());
                bundle.putString("Descripcion",restaurante.getDescripcion());
                bundle.putInt("Foto_id",restaurante.getFoto_id());
                bundle.putString("DistritoId",restaurante.getDistrito());
                bundle.putDouble("PuntuacionTotal",restaurante.getPuntuacionTotal());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
    public void llenarListaFavoritos(Restaurante rest){
        lstRestFavoritos.add(rest);
    }

    //Ahora es Mas Recomendados
    private void ObtenerListaRecomPreferencias() {

        this.lstRestPreferencias.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();

        client.get("http://52.25.159.62/api/restaurantes/toprecomendados", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    //obtenemos el JSON
                    JSONObject obj = new JSONObject(response);
                    //Obtenemos el Array de restaurantes
                    JSONArray jArray = obj.getJSONArray("data");


                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        //Convertir JsonAray ---> List<Restaurante>
                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            Restaurante restaurante = new Restaurante();
                            restaurante.setIdRestaurante( jObj.getInt("id"));
                            restaurante.setNombre(jObj.getString("nombre"));
                            restaurante.setLatitud(jObj.getString("latitud"));
                            restaurante.setLongitud(jObj.getString("longitud"));
                            restaurante.setDescripcion(jObj.getString("descripcion"));
                            if(jObj.getString("foto_id") != null)
                                restaurante.setFoto_id(Integer.parseInt(jObj.getString("foto_id")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "El id de la foto es nulo", Toast.LENGTH_LONG).show();
                            }
                            restaurante.setDistrito(jObj.getString("distrito_id"));
                            if(jObj.getString("puntuacion_total") != null)
                                restaurante.setPuntuacionTotal(Double.parseDouble(jObj.getString("puntuacion_total")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "La puntuacion es nula", Toast.LENGTH_LONG).show();
                            }

                            llenarListaPreferencias(restaurante);
                        }

                    }
                    actualizarListaConAdapterPreferencias();
                    prgDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_LONG).show();
                }
            }

        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//

    }
    private void actualizarListaConAdapterPreferencias(){
        //Lista
        ListView lstVwRestaurantesRecomPreferencias = (ListView) findViewById(R.id.listViewRecomendacionesPreferencias);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(lstRestPreferencias,this);
        lstVwRestaurantesRecomPreferencias.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesRecomPreferencias.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurante restaurante = (Restaurante) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, RestauranteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("IdRestaurante",restaurante.getIdRestaurante());
                bundle.putString("Nombre",restaurante.getNombre());
                bundle.putString("Latitud",restaurante.getLatitud());
                bundle.putString("Longitud",restaurante.getLongitud());
                bundle.putString("Descripcion",restaurante.getDescripcion());
                bundle.putInt("Foto_id",restaurante.getFoto_id());
                bundle.putString("DistritoId",restaurante.getDistrito());
                bundle.putDouble("PuntuacionTotal",restaurante.getPuntuacionTotal());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
    public void llenarListaPreferencias(Restaurante rest){
        lstRestPreferencias.add(rest);
    }

    //Ahora es Mejor Puntuados
    private void ObtenerListaRecomRecomendados() {

        this.lstRestRecomendados.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();

        client.get("http://52.25.159.62/api/restaurantes/toppuntuados", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    //obtenemos el JSON
                    JSONObject obj = new JSONObject(response);
                    //Obtenemos el Array de restaurantes
                    JSONArray jArray = obj.getJSONArray("data");


                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        //Convertir JsonAray ---> List<Restaurante>
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            Restaurante restaurante = new Restaurante();
                            restaurante.setIdRestaurante(jObj.getInt("id"));
                            restaurante.setNombre(jObj.getString("nombre"));
                            restaurante.setLatitud(jObj.getString("latitud"));
                            restaurante.setLongitud(jObj.getString("longitud"));
                            restaurante.setDescripcion(jObj.getString("descripcion"));
                            if (jObj.getString("foto_id") != null)
                                restaurante.setFoto_id(Integer.parseInt(jObj.getString("foto_id")));
                            else {
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "El id de la foto es nulo", Toast.LENGTH_LONG).show();
                            }
                            restaurante.setDistrito(jObj.getString("distrito_id"));
                            if (jObj.getString("puntuacion_total") != null)
                                restaurante.setPuntuacionTotal(Double.parseDouble(jObj.getString("puntuacion_total")));
                            else {
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "La puntuacion es nula", Toast.LENGTH_LONG).show();
                            }

                            llenarListaRecomendados(restaurante);
                        }

                    }
                    actualizarListaConAdapterRecomendados();
                    prgDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_LONG).show();
                }
            }

        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//

    }
    private void actualizarListaConAdapterRecomendados(){
        //Lista
        ListView lstVwRestaurantesRecomRecomendados = (ListView) findViewById(R.id.listViewRecomendacionesRecomendados);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(lstRestRecomendados,this);
        lstVwRestaurantesRecomRecomendados.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesRecomRecomendados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurante restaurante = (Restaurante) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, RestauranteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("IdRestaurante", restaurante.getIdRestaurante());
                bundle.putString("Nombre", restaurante.getNombre());
                bundle.putString("Latitud", restaurante.getLatitud());
                bundle.putString("Longitud", restaurante.getLongitud());
                bundle.putString("Descripcion", restaurante.getDescripcion());
                bundle.putInt("Foto_id", restaurante.getFoto_id());
                bundle.putString("DistritoId", restaurante.getDistrito());
                bundle.putDouble("PuntuacionTotal", restaurante.getPuntuacionTotal());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
    public void llenarListaRecomendados(Restaurante rest){
        lstRestRecomendados.add(rest);
    }

    private void ObtenerCategorias()
    {
        this.lstCategorias.clear();
        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();
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

                            llenarListaCategorias(categoria);
                        }
                    }
                    actualizarListaConAdapterCategorias();
                    prgDialog.hide();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_LONG).show();
                }

            }
        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//
    }
    public void llenarListaCategorias(Categoria categoria)
    {
        this.lstCategorias.add(categoria);
    }
    private void actualizarListaConAdapterCategorias() {
        //Lista
        ListView lstVwTipoComida = (ListView) findViewById(R.id.tipocomida_lst_tipocomida);
        CategoriaAdapter adapter = new CategoriaAdapter(this.lstCategorias,this);
        lstVwTipoComida.setAdapter(adapter);

        //Listener
        lstVwTipoComida.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ListaRestaurantesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("categoriaId", categoria.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //-------------------------OBTENER DISTRITOS--------------------//
    private void ObtenerDistritos(){

        this.lstDistritos.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();
        client.get("http://52.25.159.62/api/distritos", new AsyncHttpResponseHandler() {
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
                        //Convertir JsonAray ---> List<Distrito>
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            Distrito distrito = new Distrito();
                            distrito.setId((Integer) jObj.getInt("id"));
                            distrito.setNombre(jObj.getString("nombre"));

                            llenarListaDistritos(distrito);
                        }
                    }
                    actualizarListaConAdapterDistritos();
                    prgDialog.hide();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_LONG).show();
                }

            }
        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//
    }
    private void actualizarListaConAdapterDistritos()    {
        //Lista
        ListView lstVwDistritos = (ListView) findViewById(R.id.distrito_lst_distritos);
        DistritoAdapter distritosAdapter = new DistritoAdapter(this.lstDistritos,this);
        lstVwDistritos.setAdapter(distritosAdapter);

        //Listener
        lstVwDistritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Distrito distrito = (Distrito) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ListaRestaurantesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("distritoId", distrito.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void llenarListaDistritos(Distrito distrito)
    {
        lstDistritos.add(distrito);
    }

    //--------------------OBTENER TIPOS DE COMIDA-------------------//
    private List<String> ObtenerTipoComida(){
        List<String> lst = new ArrayList<String>();

        //TODO cambiar esta funcion por la real de BD
        lst = generarTipoComidaPrueba();

        return lst;
    }







    //TODO: Borrar estas funciones de prueba
    private List<Restaurante> generarDatosPrueba() {
        List<Restaurante> lstRest = new ArrayList<Restaurante>();

        Restaurante r1 = new Restaurante();
        r1.setIdRestaurante(1);
        r1.setNombre("Prueba1");
        r1.setLatitud("");
        r1.setLongitud("");
        r1.setDescripcion("desc1");
        r1.setFoto_id(32);
        r1.setPuntuacionTotal(3.5);
        r1.setDistrito("2");

        Restaurante r2 = new Restaurante();
        r2.setIdRestaurante(2);
        r2.setNombre("Preueba2");
        r2.setLatitud("");
        r2.setLongitud("");
        r2.setDescripcion("desc2");
        r2.setFoto_id(33);
        r2.setPuntuacionTotal(5.5);
        r2.setDistrito("1");

        lstRest.add(r1);
        lstRest.add(r2);
        lstRest.add(r1);
        lstRest.add(r2);
        lstRest.add(r1);
        lstRest.add(r2);
        lstRest.add(r1);
        lstRest.add(r2);
        lstRest.add(r1);
        lstRest.add(r2);
        lstRest.add(r1);
        lstRest.add(r2);

        return lstRest;
    }

    private List<String> generarDistritosPrueba(){
        List<String> distritos = new ArrayList<String>();

        distritos.add("Los Olivos");
        distritos.add("El Callao");
        distritos.add("San Isidro");
        distritos.add("Monterrico");
        distritos.add("Ate");
        distritos.add("La Victoria");
        distritos.add("La Molina");
        distritos.add("San Martin");

        return  distritos;
    }
    private List<String> generarTipoComidaPrueba(){
        List<String> comida = new ArrayList<String>();

        comida.add("Criolla");
        comida.add("China");
        comida.add("Picante");
        comida.add("Marina");
        comida.add("Japones");
        comida.add("Italiana");
        comida.add("Pastas");
        comida.add("Frituras");

        return  comida;
    }


}
