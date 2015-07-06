package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.restaurapp.Entidades.Foto;
import upc.edu.pe.restaurapp.Servicios.RestaurAppisClient;


public class RecomendacionVerActivity extends ActionBarActivity {

    String Nombre ;
    String Descripcion ;
    Double PuntuacionTotal;
    ProgressDialog prgDialog;
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES";
    public Integer idUsuarioLogueado;
    ImageView ImageView1;
    ImageView ImageView2;
    ImageView ImageView3;
    public final List<Foto> lstFotos = new ArrayList<Foto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante_recomendacion_ver);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        SharedPreferences shared = getSharedPreferences(RESTAURAPP_PREFERENCES, MODE_PRIVATE);
        idUsuarioLogueado = shared.getInt("USUARIO_ACTUAL_ID", 0);

        //Action Bar personalizado
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        Intent intent = getIntent();

        Nombre = intent.getStringExtra("Nombre");
        Descripcion = intent.getStringExtra("Descripcion");
        PuntuacionTotal = intent.getDoubleExtra("PuntuacionTotal", 0.0);

        TextView txtNombre = (TextView) findViewById(R.id.restaurante_recomendacion_ver_txt_nombre);
        TextView txtDescripcion = (TextView) findViewById(R.id.restaurante_recomendacion_ver_txt_descripcion);
        TextView txtPuntaje = (TextView) findViewById(R.id.restaurante_recomendacion_ver_txt_puntaje);

        txtNombre.setText(Nombre);
        txtDescripcion.setText(Descripcion);
        txtPuntaje.setText(PuntuacionTotal.toString());

        Integer usuario_id_comentario = intent.getIntExtra("id", 0);
        String usuario_comentario = intent.getStringExtra("comentario");
        String usuario_nombre = intent.getStringExtra("nomusuario");
        String usuario_fecha = intent.getStringExtra("fecha");
        String usuario_puntuacion = intent.getStringExtra("puntuacion");
        TextView txtUNombre = (TextView) findViewById(R.id.restaurante_recomendacion_ver_txt_usuario);
        TextView txtUFecha = (TextView) findViewById(R.id.restaurante_recomendacion_ver_txt_fecha);
        TextView txtUPuntuacion = (TextView) findViewById(R.id.restaurante_recomendacion_ver_txt_puntuacion_usuario);
        TextView txtUComentario = (TextView) findViewById(R.id.restaurante_recomendacion_ver_txt_comentario_usuario);
        txtUNombre.setText(usuario_nombre);
        txtUFecha.setText(usuario_fecha);
        txtUPuntuacion.setText(usuario_puntuacion);
        txtUComentario.setText(usuario_comentario);

        ImageView1 = (ImageView) findViewById(R.id.restaurante_recomendacion_ver_img1);
        ImageView1.setClickable(true);
        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecomendacionVerActivity.this, ImageFotoActivity.class);
                intent.putExtra("url", getListaFotos(0));
                startActivity(intent);
            }
        });
        ImageView2 = (ImageView) findViewById(R.id.restaurante_recomendacion_ver_img2);
        ImageView2.setClickable(true);
        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecomendacionVerActivity.this, ImageFotoActivity.class);
                intent.putExtra("url", getListaFotos(1));
                startActivity(intent);
            }
        });
        ImageView3 = (ImageView) findViewById(R.id.restaurante_recomendacion_ver_img3);
        ImageView3.setClickable(true);
        ImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecomendacionVerActivity.this, ImageFotoActivity.class);
                intent.putExtra("url", getListaFotos(2));
                startActivity(intent);
            }
        });

        if(usuario_id_comentario != 0) {
            prgDialog.show();
            lstFotos.clear();
            RequestParams params = new RequestParams();
            params.put("recomendacion_id", usuario_id_comentario);
            RestaurAppisClient.post("fotos/recomendacion", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    prgDialog.hide();
                    String response = new String(responseBody);
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jArray = obj.getJSONArray("data");

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jFoto = jArray.getJSONObject(i);
                            Foto foto = new Foto();
                            foto.setId(jFoto.getInt("id"));
                            foto.setNombre(jFoto.getString("nombre"));
                            foto.setUrl(jFoto.getString("url"));
                            foto.setUrl_min(jFoto.getString("url_min"));
                            llenarListaDeFotos(foto);
                        }
                        //actualizarFotos();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }

                @Override
                public void onFinish() {
                    actualizarFotos(null);
                }
            });
        }
    }

    public void llenarListaDeFotos(Foto foto) {
        this.lstFotos.add(foto);
    }

    public String getListaFotos(int i) {
        return this.lstFotos.get(i).getUrl();
    }

    public void actualizarFotos(View v) {
        if(this.lstFotos.size() == 1){
            ImageLoader imageLoader = ImageLoader.getInstance();
            ImageView1.setVisibility(View.VISIBLE);
            imageLoader.displayImage(this.lstFotos.get(0).getUrl_min(), ImageView1);
            ImageView2.setVisibility(View.GONE);
            ImageView3.setVisibility(View.GONE);
        }
        if(this.lstFotos.size() == 2){
            ImageLoader imageLoader = ImageLoader.getInstance();
            ImageView1.setVisibility(View.VISIBLE);
            imageLoader.displayImage(this.lstFotos.get(0).getUrl_min(), ImageView1);
            ImageView2.setVisibility(View.VISIBLE);
            imageLoader.displayImage(this.lstFotos.get(1).getUrl_min(), ImageView2);
            ImageView3.setVisibility(View.GONE);
        }
        if(this.lstFotos.size() == 3){
            ImageLoader imageLoader = ImageLoader.getInstance();
            ImageView1.setVisibility(View.VISIBLE);
            imageLoader.displayImage(this.lstFotos.get(0).getUrl_min(), ImageView1);
            ImageView2.setVisibility(View.VISIBLE);
            imageLoader.displayImage(this.lstFotos.get(1).getUrl_min(), ImageView2);
            ImageView3.setVisibility(View.VISIBLE);
            imageLoader.displayImage(this.lstFotos.get(2).getUrl_min(), ImageView3);
        }
    }

    public void mostrarFoto(int i, ImageView imageView){
        if(this.lstFotos.size() > i) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(this.lstFotos.get(i).getUrl(), imageView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurante, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
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
    public  void irAbout(View v) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /*
    public void dismissListener(View view){

    }
    */
}
