package upc.edu.pe.restaurapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.restaurapp.Adapter.AgregarUsuarioAdapter;
import upc.edu.pe.restaurapp.Adapter.AmigoAdapter;
import upc.edu.pe.restaurapp.Adapter.GrupoAdapter;
import upc.edu.pe.restaurapp.Entidades.Grupo;
import upc.edu.pe.restaurapp.Entidades.Usuario;
import upc.edu.pe.restaurapp.Servicios.RestaurAppisClient;
import upc.edu.pe.restaurapp.Utilitario.Validar;


public class AmigosGruposActivity extends ActionBarActivity {

    ProgressDialog prgDialog;
    public final List<Grupo> lstAmigosGrupos = new ArrayList<Grupo>();
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES";
    public int idUsuarioLogueado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_amigosgrupos);

        SharedPreferences shared = getSharedPreferences(RESTAURAPP_PREFERENCES, MODE_PRIVATE);
        idUsuarioLogueado = shared.getInt("USUARIO_ACTUAL_ID", 0);

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

        ObtenerAmigosGrupos_SW(idUsuarioLogueado);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contactos, menu);
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
            case R.id.action_about:
                irAbout(findViewById((R.id.action_about)));
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void ObtenerAmigosGrupos_SW(Integer idUsuarioLogueado)
    {
        this.lstAmigosGrupos.clear();

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();
        client.get("http://"+getApplicationContext().getResources().getString(R.string.aws_elastic_ip)+"/api/usuarios/"+idUsuarioLogueado.toString()+"/amigosgrupos", new AsyncHttpResponseHandler() {
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

                        for(int i=0;i<jArray.length();i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            Grupo grupo = new Grupo();
                            grupo.setId(jObj.getInt("id"));
                            grupo.setNombre(jObj.getString("nombre"));

                            llenarListaAmigosGrupos(grupo);
                        }
                    }
                    actualizarListaAmigosGrupos();
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

    public void llenarListaAmigosGrupos(Grupo grupo)
    {
        this.lstAmigosGrupos.add(grupo);
    }

    public void actualizarListaAmigosGrupos()
    {
        //Lista
        ListView lstVwAmigosRestaurantes = (ListView) findViewById(R.id.usuario_amigosgrupos_lstvw);
        GrupoAdapter grupoAdapter = new GrupoAdapter(this.lstAmigosGrupos,this);
        lstVwAmigosRestaurantes.setAdapter(grupoAdapter);

        //Listener
        lstVwAmigosRestaurantes.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Grupo grupo = (Grupo) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("GrupoNombre",grupo.getNombre());
                intent.putExtra("GrupoId",grupo.getId());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}
