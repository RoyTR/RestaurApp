package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.restaurapp.Adapter.RestauranteAdapter;
import upc.edu.pe.restaurapp.Entidades.Distrito;
import upc.edu.pe.restaurapp.Entidades.Restaurante;


public class ListaRestaurantesActivity extends ActionBarActivity {

    ProgressDialog prgDialog;
    public final List<Restaurante> lstRestaurantes = new ArrayList<Restaurante>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurantes);

        prgDialog = new ProgressDialog(this);

        //Action Bar personalizado
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if(bundle.containsKey("distritoId"))
            LlenarRestaurantesPorDistrito(bundle.getInt("distritoId"));
        if(bundle.containsKey("categoriaId"))
            ObtenerListaRestaurantesTipoComida(bundle.getInt("categoriaId"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_restaurantes, menu);
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
    //------------------------------------MENU BAR------------------------------------------//
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



    private void ObtenerListaRestaurantesPorDistrito_SW(Integer distritoId) {

        this.lstRestaurantes.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();
        client.get("http://52.25.159.62/api/distritos/"+distritoId.toString()+"/restaurantes?include=main_category", new AsyncHttpResponseHandler() {
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
                        //Convertir JsonAray ---> List<Restaurante>

                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            JSONObject jobjcat = jObj.getJSONObject("main_category");
                            JSONObject jobjcatdata = jobjcat.getJSONObject("data");

                            Restaurante restaurante = new Restaurante();
                            restaurante.setCategoria(jobjcatdata.getInt("id"));
                            restaurante.setIdRestaurante(jObj.getInt("id"));
                            restaurante.setNombre(jObj.getString("nombre"));
                            restaurante.setLatitud(jObj.getString("latitud"));
                            restaurante.setLongitud(jObj.getString("longitud"));
                            restaurante.setDescripcion(jObj.getString("descripcion"));
                            restaurante.setFoto_id(Integer.parseInt(jObj.getString("foto_id")));
                            restaurante.setDistrito(jObj.getString("distrito_id"));
                            restaurante.setPuntuacionTotal(Double.parseDouble(jObj.getString("puntuacion_total")));

                            llenarListaRestaurantes(restaurante);
                        }
                    }
                    actualizarListaDeRestaurantesPorDistrito();
                    Bundle bundle = getIntent().getExtras();
                    bundle.remove("distritoId");
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

    private void ObtenerListaRestaurantesPorCategoria_SW(Integer categoriaId) {

        this.lstRestaurantes.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();
        client.get("http://52.25.159.62/api/categorias/"+categoriaId.toString()+"/restaurantes?include=main_category", new AsyncHttpResponseHandler() {
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
                        //Convertir JsonAray ---> List<Restaurante>

                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            JSONObject jobjcat = jObj.getJSONObject("main_category");
                            JSONObject jobjcatdata = jobjcat.getJSONObject("data");

                            Restaurante restaurante = new Restaurante();
                            restaurante.setCategoria(jobjcatdata.getInt("id"));
                            restaurante.setIdRestaurante(jObj.getInt("id"));
                            restaurante.setNombre(jObj.getString("nombre"));
                            restaurante.setLatitud(jObj.getString("latitud"));
                            restaurante.setLongitud(jObj.getString("longitud"));
                            restaurante.setDescripcion(jObj.getString("descripcion"));
                            restaurante.setFoto_id(Integer.parseInt(jObj.getString("foto_id")));
                            restaurante.setDistrito(jObj.getString("distrito_id"));
                            restaurante.setPuntuacionTotal(Double.parseDouble(jObj.getString("puntuacion_total")));

                            llenarListaRestaurantes(restaurante);
                        }
                    }
                    actualizarListaDeRestaurantesPorCategoria();
                    Bundle bundle = getIntent().getExtras();
                    bundle.remove("categoriaId");
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

    private void ObtenerListaRestaurantesTipoComida(Integer idCategoria)
    {
        ObtenerListaRestaurantesPorCategoria_SW(idCategoria);
    }

    private void LlenarRestaurantesPorDistrito(Integer idDistrito)
    {
        ObtenerListaRestaurantesPorDistrito_SW(idDistrito);
    }

    private void llenarListaRestaurantes(Restaurante restaurante)
    {
        this.lstRestaurantes.add(restaurante);
    }

    private void actualizarListaDeRestaurantesPorDistrito()
    {
        //Lista
        ListView lstVwRestaurantesPorDistrito = (ListView) findViewById(R.id.lst_restaurantes_list_view);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(this.lstRestaurantes,this);
        lstVwRestaurantesPorDistrito.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesPorDistrito.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurante restaurante = (Restaurante) parent.getItemAtPosition(position);

                Intent intent = new Intent(ListaRestaurantesActivity.this, RestauranteActivity.class);
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

    private void actualizarListaDeRestaurantesPorCategoria()
    {
        //Lista
        ListView lstVwRestaurantesPorDistrito = (ListView) findViewById(R.id.lst_restaurantes_list_view);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(this.lstRestaurantes,this);
        lstVwRestaurantesPorDistrito.setAdapter(restauranteAdapter);

        //Listener
        lstVwRestaurantesPorDistrito.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurante restaurante = (Restaurante) parent.getItemAtPosition(position);

                Intent intent = new Intent(ListaRestaurantesActivity.this, RestauranteActivity.class);
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









    //TODO: Borrar esta funcion de prueba
    private List<Restaurante> generarDatosPrueba() {
        List<Restaurante> lstRest = new ArrayList<Restaurante>();

        Restaurante r1 = new Restaurante();
        r1.setIdRestaurante(1);
        r1.setNombre("Ultima Cena");
        r1.setLatitud("");
        r1.setLongitud("");
        r1.setDescripcion("desc1");
        r1.setFoto_id(32);
        r1.setPuntuacionTotal(3.5);
        r1.setDistrito("2");

        Restaurante r2 = new Restaurante();
        r2.setIdRestaurante(2);
        r2.setNombre("Primera Cena");
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
}
