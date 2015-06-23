package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.protocol.RequestExpectContinue;
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


public class ContactosActivity extends ActionBarActivity {

    ProgressDialog prgDialog;
    public final List<Usuario> lstAmigos = new ArrayList<Usuario>();
    public final List<Grupo> lstGrupos = new ArrayList<Grupo>();
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES";
    public int idUsuarioLogueado;
    public final List<Integer> idsUsuariosParaNuevoGrupo = new ArrayList<Integer>();
    public final List<String> nombresUsuariosParaNuevoGrupo = new ArrayList<String>();
    public final List<String> apellidosUsuariosParaNuevoGrupo = new ArrayList<String>();
    public int redirigir;
    public final Grupo grupoBean = new Grupo();
    public String nombreGrupo;

    public final List<Integer> idsUsuariosAEliminar = new ArrayList<Integer>();
    public final List<Integer> idsGruposDeUsuariosAEliminar = new ArrayList<Integer>();
    public final List<Integer> idsUsuariosAAgregar = new ArrayList<Integer>();
    public final List<Integer> idsGruposDeUsuariosAAgregar = new ArrayList<Integer>();

    public final List<Integer> idsDeGrupos = new ArrayList<Integer>();
    public final List<Integer> idsAuxiliarDeUsuarios = new ArrayList<Integer>();
    public final List<Integer> idsAuxiliarDeGrupos = new ArrayList<Integer>();
    public final List<Integer> idsAAgregar = new ArrayList<Integer>();
    public final List<String> nombresAAgregar = new ArrayList<String>();
    public final List<String> apellidosAAgregar = new ArrayList<String>();
    public final List<Integer> idsAEliminar = new ArrayList<Integer>();
    public final List<Integer> idsGruposAEliminar = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_amigos);
        Button btnamigos = (Button) findViewById(R.id.footercontactobtnamigos);
        btnamigos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        SharedPreferences shared = getSharedPreferences(RESTAURAPP_PREFERENCES, MODE_PRIVATE);
        idUsuarioLogueado = shared.getInt("USUARIO_ACTUAL_ID", 0);
        nombreGrupo = "";
        //idsUsuariosParaNuevoGrupo = "";
        //TextView tvAgregarUsuarioEnCrearGrupo = (TextView) findViewById(R.id.contactos_crear_grupo_et_link_agregar);
        //tvAgregarUsuarioEnCrearGrupo.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        redirigir = 1;

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        llenarListaDeAmigos();

        //Action Bar personalizado
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
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


    public void cambiarAmigos(View v){
        setContentView(R.layout.activity_contactos_amigos);
        Button btnamigos = (Button) findViewById(R.id.footercontactobtnamigos);
        btnamigos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        llenarListaDeAmigos();
    }
    public void cambiarGrupos(View v){
        setContentView(R.layout.activity_contactos_grupos);
        Button btngrupos = (Button) findViewById(R.id.footercontactobtngrupos);
        btngrupos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        llenarListaDeGrupos();
    }

    public void AgregarAmigos(View v){
        setContentView(R.layout.activity_usuarios_agregar);
        Button btngrupos = (Button) findViewById(R.id.footercontactobtnamigos);
        btngrupos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        redirigir = 1;
        this.idsAuxiliarDeUsuarios.clear();
        this.idsAuxiliarDeGrupos.clear();
        for(int i = 0; i<this.idsUsuariosParaNuevoGrupo.size(); i++) {
            this.idsAuxiliarDeUsuarios.add(this.idsUsuariosParaNuevoGrupo.get(i));
            this.idsAuxiliarDeGrupos.add(this.idsDeGrupos.get(i));
        }
        llenarListaDeUsuariosAgregar();
    }
    public void AgregarGrupos(View v){
        setContentView(R.layout.activity_contactos_grupos_crear);
        Button btngrupos = (Button) findViewById(R.id.footercontactobtngrupos);
        btngrupos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        TextView tvTitulo = (TextView) findViewById(R.id.contactos_grupos_tv_titulo);
        tvTitulo.setText("Crear Nuevo Grupo:");
        EditText etNombre = (EditText) findViewById(R.id.contactos_crear_grupo_et_nombre_grupo);
        etNombre.setVisibility(View.VISIBLE);
        Button btnAgregar = (Button) findViewById(R.id.contactos_crear_grupo_et_link_agregar);
        btnAgregar.setVisibility(View.VISIBLE);
        Button btnCrearGrupo = (Button) findViewById(R.id.grupos_btn_Crear_grupo);
        btnCrearGrupo.setVisibility(View.VISIBLE);
        nombreGrupo = "";
        this.idsUsuariosParaNuevoGrupo.clear();
        this.nombresUsuariosParaNuevoGrupo.clear();
        this.apellidosUsuariosParaNuevoGrupo.clear();
        llenarListaDeCrearGrupos();
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

    private void llenarIdsAuxiliarDeUsuarios(int id) {
        this.idsAuxiliarDeUsuarios.add(id);
    }
    private void llenarListaAmigos(Usuario usuario)
    {
        this.lstAmigos.add(usuario);
    }
    private void llenarListaIdsUsuarios(Usuario usuario)
    {
        if(!this.idsUsuariosParaNuevoGrupo.contains(usuario.getId())) {
            this.idsUsuariosParaNuevoGrupo.add(usuario.getId());
            this.nombresUsuariosParaNuevoGrupo.add(usuario.getNombres());
            this.apellidosUsuariosParaNuevoGrupo.add(usuario.getApellidos());
            this.idsDeGrupos.add(usuario.getIdGupo());
        } else {
            this.idsUsuariosParaNuevoGrupo.remove(usuario.getId());
            this.nombresUsuariosParaNuevoGrupo.remove(usuario.getNombres());
            this.apellidosUsuariosParaNuevoGrupo.remove(usuario.getApellidos());
            this.idsDeGrupos.remove(usuario.getIdGupo());
        }
    }
    private void llenarListaGrupos(Grupo grupo)
    {
        this.lstGrupos.add(grupo);
    }
    private void llenarGrupoBean(Grupo grupo)
    {
        this.grupoBean.setId(grupo.getId());
        this.grupoBean.setNombre(grupo.getNombre());
    }



    public void llenarListaDeAmigos(){
        this.lstAmigos.clear();
        this.idsUsuariosParaNuevoGrupo.clear();
        this.nombresUsuariosParaNuevoGrupo.clear();
        this.apellidosUsuariosParaNuevoGrupo.clear();

        this.idsUsuariosAEliminar.clear();
        this.idsGruposDeUsuariosAEliminar.clear();
        this.idsUsuariosAAgregar.clear();
        this.idsGruposDeUsuariosAAgregar.clear();

        this.idsDeGrupos.clear();
        prgDialog.setMessage("Cargando Amigos...");
        prgDialog.show();
        RestaurAppisClient.get("usuarios/" + idUsuarioLogueado + "/amigos?include=usuarios", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jArray = obj.getJSONArray("data");

                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < jArray.length(); i++) {
                            // obtengo usuarios
                            JSONArray jUsuarioArray = jArray.getJSONObject(i).getJSONObject("usuarios").getJSONArray("data");
                            //recorreo arreglo nuevo
                            for(int j=0;j<jUsuarioArray.length();j++) {
                                JSONObject jUsuarioObject = jUsuarioArray.getJSONObject(j);
                                Usuario usuario = new Usuario();
                                usuario.setId(jUsuarioObject.getInt("id"));
                                usuario.setNombres(jUsuarioObject.getString("nombres"));
                                usuario.setApellidos(jUsuarioObject.getString("apellidos"));
                                usuario.setUsername(jUsuarioObject.getString("username"));
                                usuario.setEmail(jUsuarioObject.getString("email"));
                                usuario.setIdGupo(jArray.getJSONObject(i).getInt("id"));
                                llenarListaAmigos(usuario);
                                llenarListaIdsUsuarios(usuario);
                            }
                        }
                    }
                    actualizarListaDeAmigos();
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
    private void actualizarListaDeAmigos() {
        //Lista
        ListView lstVwAmigos = (ListView) findViewById(R.id.contactos_amigos_listview);
        AmigoAdapter amigoAdapter = new AmigoAdapter(this.lstAmigos,this);
        lstVwAmigos.setAdapter(amigoAdapter);

        //Listener
        lstVwAmigos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = (Usuario) parent.getItemAtPosition(position);
                Toast.makeText(ContactosActivity.this, usuario.getEmail(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void llenarListaDeGrupos(){
        this.lstGrupos.clear();
        prgDialog.setMessage("Cargando Grupos...");
        prgDialog.show();
        RestaurAppisClient.get("usuarios/" + idUsuarioLogueado + "/grupos", null, new AsyncHttpResponseHandler() {
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
                            llenarListaGrupos(grupo);
                        }
                    }
                    actualizarListaDeGrupos();
                    /*
                    Bundle bundle = getIntent().getExtras();
                    bundle.remove("amigosId");
                    */
                    prgDialog.hide();

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
    private void actualizarListaDeGrupos() {
        //Lista
        ListView lstVwGrupos = (ListView) findViewById(R.id.contactos_grupos_listview);
        GrupoAdapter grupoAdapter = new GrupoAdapter(lstGrupos,this);
        lstVwGrupos.setAdapter(grupoAdapter);

        //Listener
        lstVwGrupos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Grupo grupo = (Grupo) parent.getItemAtPosition(position);
                llenarGrupoBean(grupo);
                irAVisualizarGrupo();
            }
        });
    }

    public void llenarListaDeCrearGrupos(){
        this.lstAmigos.clear();
        prgDialog.setMessage("Cargando...");
        prgDialog.show();
        Usuario usuario;
        for(int i = 0;i < this.idsUsuariosParaNuevoGrupo.size(); i++) {
            usuario = new Usuario();
            usuario.setId(this.idsUsuariosParaNuevoGrupo.get(i));
            usuario.setNombres(this.nombresUsuariosParaNuevoGrupo.get(i));
            usuario.setApellidos(this.apellidosUsuariosParaNuevoGrupo.get(i));
            llenarListaAmigos(usuario);
        }
        actualizarListaDeCrearGrupos();
        prgDialog.hide();

    }
    private void actualizarListaDeCrearGrupos() {
        //Lista
        ListView lstVwCrearGrupoUsuarios = (ListView) findViewById(R.id.contactos_grupos_lv_lista_usuarios);
        AmigoAdapter amigoAdapter = new AmigoAdapter(lstAmigos,this);
        lstVwCrearGrupoUsuarios.setAdapter(amigoAdapter);
    }
    public void irAgregarUsuariosParaGrupo(View view) {
        FormEditText tvNombreGrupo = (FormEditText) findViewById(R.id.contactos_crear_grupo_et_nombre_grupo);
        nombreGrupo = tvNombreGrupo.getText().toString();
        setContentView(R.layout.activity_usuarios_agregar);
        Button btngrupos = (Button) findViewById(R.id.footercontactobtngrupos);
        btngrupos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
        redirigir = 2;
        llenarListaDeUsuariosAgregar();
    }
    public void llenarListaDeUsuariosAgregar() {
        this.lstAmigos.clear();
        prgDialog.setMessage("Cargando Usuarios...");
        prgDialog.show();
        RestaurAppisClient.get("usuarios", null, new AsyncHttpResponseHandler() {
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
                            Usuario usuario = new Usuario();
                            usuario.setId(jObj.getInt("id"));
                            usuario.setNombres(jObj.getString("nombres"));
                            usuario.setApellidos(jObj.getString("apellidos"));
                            llenarListaAmigos(usuario);
                        }
                    }
                    actualizarListaDeUsuariosAgregar();
                    /*
                    Bundle bundle = getIntent().getExtras();
                    bundle.remove("amigosId");
                    */
                    prgDialog.hide();

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
    private void actualizarListaDeUsuariosAgregar() {
        //Lista
        ListView lstVwUsuarios = (ListView) findViewById(R.id.contactos_agregar_lv_lista_usuarios);
        AgregarUsuarioAdapter agregarUsuarioAdapter = new AgregarUsuarioAdapter(this.lstAmigos,this.idsUsuariosParaNuevoGrupo,this);
        lstVwUsuarios.setAdapter(agregarUsuarioAdapter);

        //Listener
        lstVwUsuarios.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = (Usuario)parent.getItemAtPosition(position);
                llenarListaIdsUsuarios(usuario);
                actualizarListaDeUsuariosAgregar();
            }
        });
    }
    public void AgregarUsuariosParaCrear(View v) {
        if(redirigir == 1) {
            setContentView(R.layout.activity_contactos_amigos);
            Button btnamigos = (Button) findViewById(R.id.footercontactobtnamigos);
            btnamigos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
            CrearAmigosDelUsuario(v);
            //llenarListaDeAmigos();
        } else {
            setContentView(R.layout.activity_contactos_grupos_crear);
            FormEditText tvNombreGrupo = (FormEditText) findViewById(R.id.contactos_crear_grupo_et_nombre_grupo);
            tvNombreGrupo.setText(nombreGrupo);
            Button btnamigos = (Button) findViewById(R.id.footercontactobtnamigos);
            btnamigos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
            llenarListaDeCrearGrupos();
        }
    }
    public void CrearGrupo(final View v) {
        FormEditText txtNombreGrupo = (FormEditText)findViewById(R.id.contactos_crear_grupo_et_nombre_grupo);
        FormEditText[] allFields = {txtNombreGrupo};
        if (Validar.validarEditTexts(allFields)) {
            if(idsUsuariosParaNuevoGrupo.size() <= 1) {
                Toast.makeText(getApplicationContext(), "Añada al menos 2 personas al grupo", Toast.LENGTH_SHORT).show();
            } else {
                prgDialog.setMessage("Creando Grupo...");
                prgDialog.show();
                RequestParams requestParams = new RequestParams();
                requestParams.add("nombre", txtNombreGrupo.getText().toString());
                requestParams.add("created_by", String.valueOf(idUsuarioLogueado));
                RestaurAppisClient.post("grupos/create", requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject jNuevoGrupo = obj.getJSONObject("data");

                            //TODO: revisar manejo del error
                            if (response.contains("error")) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            } else {
                                Grupo grupo = new Grupo();
                                grupo.setId(jNuevoGrupo.getInt("id"));
                                grupo.setNombre(jNuevoGrupo.getString("nombre"));
                                llenarGrupoBean(grupo);
                            }
                            prgDialog.hide();
                            for(int i=0; i<idsUsuariosParaNuevoGrupo.size(); i++) {
                                RequestParams params = new RequestParams();
                                params.add("usuario_id", idsUsuariosParaNuevoGrupo.get(i).toString());
                                params.add("grupo_id", grupoBean.getId().toString());
                                if(i == idsUsuariosParaNuevoGrupo.size() - 1) {
                                    AgregarUsuarioAGrupoService(params, false, null);
                                } else {
                                    AgregarUsuarioAGrupoService(params, true, v);
                                }
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
                //Toast.makeText(getApplicationContext(), "Grupo " + nombreGrupo + " Creado", Toast.LENGTH_SHORT).show();
                //cambiarGrupos(v);
            }
        } else {
        }
    }

    public void AgregarUsuarioAGrupoService(RequestParams params, final boolean acabar, final View v) {
        prgDialog.setMessage("Guardando..");
        prgDialog.show();
        RestaurAppisClient.post("grupos/usuarios/agregar", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
                if(acabar) {
                    Toast.makeText(getApplicationContext(), "Grupo " + nombreGrupo + " Creado", Toast.LENGTH_SHORT).show();
                    cambiarGrupos(v);
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


    public void irAVisualizarGrupo() {
        setContentView(R.layout.activity_contactos_grupos_crear);
        Button btngrupos = (Button) findViewById(R.id.footercontactobtngrupos);
        btngrupos.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        TextView tvTitulo = (TextView) findViewById(R.id.contactos_grupos_tv_titulo);
        tvTitulo.setText("Grupo: " + this.grupoBean.getNombre());
        EditText etNombre = (EditText) findViewById(R.id.contactos_crear_grupo_et_nombre_grupo);
        etNombre.setVisibility(View.GONE);
        nombreGrupo = this.grupoBean.getNombre();
        Button btnAgregar = (Button) findViewById(R.id.contactos_crear_grupo_et_link_agregar);
        btnAgregar.setVisibility(View.GONE);
        Button btnCrearGrupo = (Button) findViewById(R.id.grupos_btn_Crear_grupo);
        btnCrearGrupo.setVisibility(View.GONE);
        this.idsUsuariosParaNuevoGrupo.clear();
        this.nombresUsuariosParaNuevoGrupo.clear();
        this.apellidosUsuariosParaNuevoGrupo.clear();
        this.lstAmigos.clear();

        prgDialog.setMessage("Cargando Integrantes del grupo...");
        prgDialog.show();
        RestaurAppisClient.get("grupos/"+this.grupoBean.getId().toString()+"?include=usuarios", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject jArray = obj.getJSONObject("data");

                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        JSONObject dataGrupo = jArray.getJSONObject("usuarios");
                        JSONArray integrantesGrupo = dataGrupo.getJSONArray("data");
                        for(int i=0;i<integrantesGrupo.length();i++){
                            JSONObject jObj = integrantesGrupo.getJSONObject(i);
                            Usuario usuario = new Usuario();
                            usuario.setId(jObj.getInt("id"));
                            usuario.setNombres(jObj.getString("nombres"));
                            usuario.setApellidos(jObj.getString("apellidos"));
                            llenarListaAmigos(usuario);
                        }
                        actualizarListaDeCrearGrupos();
                    }
                    /*
                    Bundle bundle = getIntent().getExtras();
                    bundle.remove("amigosId");
                    */
                } catch (JSONException e) {
                    e.printStackTrace();
                    e.getMessage();
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

    public void comprobarUsuariosParaAgregar() {
        this.idsAAgregar.clear();
        this.nombresAAgregar.clear();
        this.apellidosAAgregar.clear();
        this.idsAEliminar.clear();
        this.idsGruposAEliminar.clear();
        for(int i = 0 ;i <idsUsuariosParaNuevoGrupo.size();i++) {
            if(! idsAuxiliarDeUsuarios.contains(idsUsuariosParaNuevoGrupo.get(i))) {
                this.idsAAgregar.add(idsUsuariosParaNuevoGrupo.get(i));
                this.nombresAAgregar.add(nombresUsuariosParaNuevoGrupo.get(i));
                this.apellidosAAgregar.add(apellidosUsuariosParaNuevoGrupo.get(i));
            }
        }
        for(int i = 0 ;i <idsAuxiliarDeUsuarios.size();i++) {
            if(! idsUsuariosParaNuevoGrupo.contains(idsAuxiliarDeUsuarios.get(i))) {
                this.idsAEliminar.add(idsAuxiliarDeUsuarios.get(i));
                this.idsGruposAEliminar.add(idsAuxiliarDeGrupos.get(i));
            }
        }
    }

    public void CrearAmigosDelUsuario(View v) {
        comprobarUsuariosParaAgregar();
        prgDialog.setMessage("Creando...");
        if(idsAAgregar.size() != 0 && idsAEliminar.size() != 0) {
            for (int h = 0; h < idsAAgregar.size(); h++) {
                UsuarioAgregarAmigoService(nombresAAgregar.get(h), apellidosAAgregar.get(h), idsAAgregar.get(h), false, v);
            }
            for (int h = 0; h < this.idsAEliminar.size(); h++) {
                if(h == this.idsAEliminar.size() - 1) {
                    UsuarioEliminarAmigoService(this.idsAEliminar.get(h), this.idsGruposAEliminar.get(h), true, v);
                } else {
                    UsuarioEliminarAmigoService(this.idsAEliminar.get(h), this.idsGruposAEliminar.get(h), false, v);
                }
            }
        } else if(idsAAgregar.size() != 0) {
            for (int h = 0; h < idsAAgregar.size(); h++) {
                if(h == this.idsAAgregar.size() - 1) {
                    UsuarioAgregarAmigoService(nombresAAgregar.get(h), apellidosAAgregar.get(h), idsAAgregar.get(h), true, v);
                } else {
                    UsuarioAgregarAmigoService(nombresAAgregar.get(h), apellidosAAgregar.get(h), idsAAgregar.get(h), false, v);
                }
            }
        } else if(idsAEliminar.size() != 0) {
            for (int h = 0; h < this.idsAEliminar.size(); h++) {
                if(h == this.idsAEliminar.size() - 1) {
                    UsuarioEliminarAmigoService(this.idsAEliminar.get(h), this.idsGruposAEliminar.get(h), true, v);
                } else {
                    UsuarioEliminarAmigoService(this.idsAEliminar.get(h), this.idsGruposAEliminar.get(h), false, v);
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Amigos Agregados", Toast.LENGTH_SHORT).show();
            cambiarAmigos(v);
        }
    }

    public void UsuarioAgregarAmigoService(String nombre, String apellido, final Integer auxID, final boolean acabar, final View v){
        prgDialog.setMessage("Guardando...");
        prgDialog.show();
        RequestParams requestParams = new RequestParams();
        requestParams.add("nombre", nombre+" "+ apellido);
        requestParams.add("created_by", String.valueOf(idUsuarioLogueado));
        //final Integer auxID = idsAAgregar.get(h);
        RestaurAppisClient.post("grupos/create", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject jNuevoGrupo = obj.getJSONObject("data");

                    //TODO: revisar manejo del error
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        Grupo grupo = new Grupo();
                        grupo.setId(jNuevoGrupo.getInt("id"));
                        grupo.setNombre(jNuevoGrupo.getString("nombre"));
                        llenarGrupoBean(grupo);
                    }
                    prgDialog.hide();
                    RequestParams params = new RequestParams();
                    params.add("usuario_id", auxID.toString());
                    params.add("grupo_id", grupoBean.getId().toString());
                    prgDialog.show();
                    RestaurAppisClient.post("grupos/usuarios/agregar", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            prgDialog.hide();
                            if(acabar) {
                                Toast.makeText(getApplicationContext(), "Amigos Agregados", Toast.LENGTH_SHORT).show();
                                cambiarAmigos(v);
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

    public void UsuarioEliminarAmigoService(int idAEliminar, int idGrupoEliminar, final boolean acabar, final View v) {
        prgDialog.setMessage("Guardando...");
        prgDialog.show();
        RestaurAppisClient.post("grupos/usuarios/eliminar?usuario_id=" + idAEliminar + "&grupo_id=" + idGrupoEliminar, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
                if(acabar) {
                    Toast.makeText(getApplicationContext(), "Amigos Agregados", Toast.LENGTH_SHORT).show();
                    cambiarAmigos(v);
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
}
