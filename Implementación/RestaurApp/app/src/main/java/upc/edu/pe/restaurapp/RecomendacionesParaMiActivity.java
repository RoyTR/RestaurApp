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

import upc.edu.pe.restaurapp.Adapter.RecomendacionesParaMiAdapter;
import upc.edu.pe.restaurapp.Entidades.Recomendaciones;
import upc.edu.pe.restaurapp.Entidades.Restaurante;


public class RecomendacionesParaMiActivity extends ActionBarActivity {

    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES" ;
    private SharedPreferences sharedpreferences;
    Integer usuarioActualId;

    ProgressDialog prgDialog;


    final List<Recomendaciones> lstRecomendaciones = new ArrayList<Recomendaciones>();
    final List<Restaurante> lstrestaurantes = new ArrayList<Restaurante>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones_para_mi);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);

        //Action Bar personalizado
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        ObtenerListaRecomendaciones();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recomendaciones_para_mi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void ObtenerListaRecomendaciones() {

        this.lstRecomendaciones.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();

        //Obtener Id del usuario
        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);

        client.get("http://"+getApplicationContext().getResources().getString(R.string.aws_elastic_ip)+"/api/usuarios/"+usuarioActualId+"/recomendacionesparami?include=restaurante,emisor", new AsyncHttpResponseHandler() {
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
                        //Convertir JsonAray ---> List<Recomendaciones>
                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObjRec = jArray.getJSONObject(i);
                            JSONObject jobjrest = jObjRec.getJSONObject("restaurante");
                            JSONObject jobjrestdata = jobjrest.getJSONObject("data");
                            JSONObject jobjemis = jObjRec.getJSONObject("emisor");
                            JSONObject jobjemistdata = jobjemis.getJSONObject("data");

                            //RESTAURANTE
                            Restaurante restaurante = new Restaurante();
                            restaurante.setCategoria(jobjrestdata.getInt("id"));
                            restaurante.setIdRestaurante( jobjrestdata.getInt("id"));
                            restaurante.setNombre(jobjrestdata.getString("nombre"));
                            restaurante.setLatitud(jobjrestdata.getString("latitud"));
                            restaurante.setLongitud(jobjrestdata.getString("longitud"));
                            restaurante.setDescripcion(jobjrestdata.getString("descripcion"));
                            if(jobjrestdata.getString("foto_id") != null)
                                restaurante.setFoto_id(Integer.parseInt(jobjrestdata.getString("foto_id")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "El id de la foto es nulo", Toast.LENGTH_LONG).show();
                            }
                            restaurante.setDistrito(jobjrestdata.getString("distrito_id"));
                            if(jobjrestdata.getString("puntuacion_total") != null)
                                restaurante.setPuntuacionTotal(Double.parseDouble(jobjrestdata.getString("puntuacion_total")));
                            else{
                                prgDialog.hide();
                                Toast.makeText(getApplicationContext(), "La puntuacion es nula", Toast.LENGTH_LONG).show();
                            }
                            //RECOMENDACION
                            Recomendaciones recomendacion = new Recomendaciones();
                            recomendacion.setComentario(jObjRec.getString("comentario"));
                            recomendacion.setNombreRest(jobjrestdata.getString("nombre"));
                            recomendacion.setNombreUsuario(jobjemistdata.getString("nombres"));
                            recomendacion.setPuntuacion(jObjRec.getString("puntuacion"));
                            recomendacion.setRestId(jobjrestdata.getInt("id"));
                            recomendacion.setUsuaId(jobjemistdata.getInt("id"));

                            llenarListaRecomendaciones(recomendacion, restaurante);
                        }
                    }
                    actualizarListaConAdapterRecomendaciones();
                    prgDialog.hide();
                    if(lstRecomendaciones.isEmpty() || lstrestaurantes.isEmpty())
                        Toast.makeText(getApplicationContext(),"Aún no ha recibido ninguna recomendación",Toast.LENGTH_LONG).show();
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
    private void actualizarListaConAdapterRecomendaciones(){
        //Lista
        ListView lstVwRecomendaciones = (ListView) findViewById(R.id.lst_recomend_parami_list_view);
        RecomendacionesParaMiAdapter recomendacionesAdapter = new RecomendacionesParaMiAdapter(lstRecomendaciones,this);
        lstVwRecomendaciones.setAdapter(recomendacionesAdapter);

        //Listener
        lstVwRecomendaciones.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recomendaciones recomendacion = (Recomendaciones) parent.getItemAtPosition(position);
                Restaurante restaurante = new Restaurante();
                for(int i=0;i<lstRecomendaciones.size();i++)
                {
                    if(recomendacion.getRestId() == lstRecomendaciones.get(i).getRestId()) {
                        restaurante = lstrestaurantes.get(i);
                        break;
                    }
                }


                Intent intent = new Intent(RecomendacionesParaMiActivity.this, RestauranteActivity.class);
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
    public void llenarListaRecomendaciones(Recomendaciones recom,Restaurante restaurante){
        lstRecomendaciones.add(recom);
        lstrestaurantes.add(restaurante);
    }
}
