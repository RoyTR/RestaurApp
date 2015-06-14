package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import upc.edu.pe.restaurapp.Adapter.RestauranteAdapter;
import upc.edu.pe.restaurapp.Entidades.Restaurante;


public class MainActivity extends ActionBarActivity {

    ProgressDialog prgDialog;
    public final List<Restaurante> lstRestCerca = new ArrayList<Restaurante>();
    final List<Restaurante> lstRestFavoritos = new ArrayList<Restaurante>();
    final List<Restaurante> lstRestPreferencias = new ArrayList<Restaurante>();
    final List<Restaurante> lstRestRecomendados = new ArrayList<Restaurante>();

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
            //Lista
            ListView lstVwDistritos = (ListView) findViewById(R.id.distrito_lst_distritos);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ObtenerDistritos());
            lstVwDistritos.setAdapter(adapter);
            //Listener
            lstVwDistritos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, ListaRestaurantesActivity.class);
                    startActivity(intent);
                }
            });

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
                irAbout(findViewById((R.id.action_about)));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cambiarBuscar(View v){

        setContentView(R.layout.activity_main_buscar_distrito);
        Button btn = (Button) findViewById(R.id.mainbtnfterbuscar);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ListView lstVwDistritos = (ListView) findViewById(R.id.distrito_lst_distritos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ObtenerDistritos());
        lstVwDistritos.setAdapter(adapter);

        //Listener
        lstVwDistritos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ListaRestaurantesActivity.class);
                startActivity(intent);
            }
        });

    }
    public void cambiarBuscarTipoComida(View v){

        setContentView(R.layout.activity_main_buscar_tipocomida);
        Button btn = (Button) findViewById(R.id.mainbtnfterbuscar);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ListView lstVwTipoComida = (ListView) findViewById(R.id.tipocomida_lst_tipocomida);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ObtenerTipoComida());
        lstVwTipoComida.setAdapter(adapter);

        //Listener
        lstVwTipoComida.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ListaRestaurantesActivity.class);
                startActivity(intent);
            }
        });

    }

    public void cambiarCerca(View v){

        setContentView(R.layout.activity_main_cerca_lista);
        Button btn = (Button) findViewById(R.id.mainbtnftercerca);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ObtenerListaCerca();

        ListView lstVwRestaurantesCerca = (ListView) findViewById(R.id.listViewCerca);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(lstRestCerca,this);
        lstVwRestaurantesCerca.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesCerca.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
        });

    }
    public void cambiarCercaMap(View v){

        setContentView(R.layout.activity_main_cerca_mapa);
        Button btn = (Button) findViewById(R.id.mainbtnftercerca);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
    }

    public void cambiarFavoritos(View v){

        setContentView(R.layout.activity_main_favoritos);
        Button btn = (Button) findViewById(R.id.mainbtnfterfavoritos);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ListView lstVwRestaurantesFavoritos = (ListView) findViewById(R.id.listViewFavoritos);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(ObtenerListaFavoritos(),this);
        lstVwRestaurantesFavoritos.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesFavoritos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
        });

    }

    public void cambiarRecomendaciones(View v){

        setContentView(R.layout.activity_main_recomendaciones_recomendados);
        Button btn = (Button) findViewById(R.id.mainbtnfterrecomendaciones);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ListView lstVwRestaurantesRecomRecomendados = (ListView) findViewById(R.id.listViewRecomendacionesRecomendados);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(ObtenerListaRecomRecomendados(),this);
        lstVwRestaurantesRecomRecomendados.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesRecomRecomendados.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
        });
    }
    public void cambiarRecomendacionesPreferencias(View v){

        setContentView(R.layout.activity_main_recomendaciones_preferencias);
        Button btn = (Button) findViewById(R.id.mainbtnfterrecomendaciones);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ListView lstVwRestaurantesRecomPreferencias = (ListView) findViewById(R.id.listViewRecomendacionesPreferencias);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(ObtenerListaRecomPreferencias(),this);
        lstVwRestaurantesRecomPreferencias.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesRecomPreferencias.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
        });
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




    //------------------------------OBTENER LISTAS DE RESTAURANTES-----------------------//
    private void ObtenerListaCerca() {

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
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
                            restaurante.setFoto_id(Integer.parseInt(jObj.getString("foto_id")));
                            restaurante.setPuntuacionTotal(Double.parseDouble(jObj.getString("puntuacion_total")));

                            restaurante.setTipoComida("PRube");
                            restaurante.setDistrito("PRube");

                            llenarListaCerca(restaurante);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "No se encontro el resource", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFinish(){
                prgDialog.hide();
            }
        });
        //--------FIN LOGICA DE LA LLAMADA A LA API-------//

    }
    public void llenarListaCerca(Restaurante rest){
        lstRestCerca.add(rest);
    }

    private List<Restaurante> ObtenerListaFavoritos() {
        List<Restaurante> lst = new ArrayList<Restaurante>();

        //TODO cambiar esta funcion por la real de BD
        lst = generarDatosPrueba();

        return lst;
    }

    private List<Restaurante> ObtenerListaRecomPreferencias() {
        List<Restaurante> lst = new ArrayList<Restaurante>();

        //TODO cambiar esta funcion por la real de BD
        lst = generarDatosPrueba();

        return lst;
    }

    private List<Restaurante> ObtenerListaRecomRecomendados() {
        List<Restaurante> lst = new ArrayList<Restaurante>();

        //TODO cambiar esta funcion por la real de BD
        lst = generarDatosPrueba();

        return lst;
    }

    //-------------------------OBTENER DISTRITOS--------------------//
    private List<String> ObtenerDistritos(){
        List<String> lst = new ArrayList<String>();

        //TODO cambiar esta funcion por la real de BD
        lst = generarDistritosPrueba();

        return lst;
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
        r1.setNombre("Ultima Cena");
        r1.setDistrito("Ate");
        r1.setTipoComida("Criolla");
        r1.setPuntuacionTotal(3.5);

        Restaurante r2 = new Restaurante();
        r2.setNombre("La tia Veneno");
        r2.setDistrito("Miraflores");
        r2.setTipoComida("Mariscos");
        r2.setPuntuacionTotal(7.3);

        Restaurante r3 = new Restaurante();
        r3.setNombre("Baigon");
        r3.setDistrito("Los Olivos");
        r3.setTipoComida("China");
        r3.setPuntuacionTotal(5.5);

        Restaurante r4 = new Restaurante();
        r4.setNombre("Cucarachita");
        r4.setDistrito("Lince");
        r4.setTipoComida("Pan");
        r4.setPuntuacionTotal(8.9);

        Restaurante r5 = new Restaurante();
        r5.setNombre("Pantalones Fritos");
        r5.setDistrito("San Miguel");
        r5.setTipoComida("Frituras");
        r5.setPuntuacionTotal(7.4);

        lstRest.add(r1);
        lstRest.add(r2);
        lstRest.add(r3);
        lstRest.add(r4);
        lstRest.add(r5);
        lstRest.add(r1);
        lstRest.add(r2);
        lstRest.add(r3);
        lstRest.add(r4);

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
