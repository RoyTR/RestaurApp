package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import upc.edu.pe.restaurapp.Adapter.ComentarioAdapter;
import upc.edu.pe.restaurapp.Entidades.Comentario;
import upc.edu.pe.restaurapp.Entidades.Restaurante;
import upc.edu.pe.restaurapp.Servicios.RestaurAppisClient;
import upc.edu.pe.restaurapp.Utilitario.Validar;


public class RestauranteActivity extends ActionBarActivity {

    int IdRestaurante;
    int recomendacion_grupoId;
    String Nombre ;
    String Latitud;
    String Longitud;
    String Descripcion ;
    Integer Foto_id;
    Double PuntuacionTotal;
    String Distrito;
    ProgressDialog prgDialog;
    List<Comentario> listaComentarios = new ArrayList<Comentario>();
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES";
    public Integer idUsuarioLogueado;
    ImageView ImageView1;
    ImageView ImageView2;
    ImageView ImageView3;
    Button Cancel1;
    Button Cancel2;
    Button Cancel3;
    int CAMERA_PIC_REQUEST = 1337;
    int aux;
    public Uri fileUri1;
    public Uri fileUri2;
    public Uri fileUri3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante_ver);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        prgDialog = new ProgressDialog(this);
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

        Bundle bundle = getIntent().getExtras();

        IdRestaurante = bundle.getInt("IdRestaurante");
        Nombre = bundle.getString("Nombre");
        Latitud = bundle.getString("Latitud");
        Longitud = bundle.getString("Longitud");
        Descripcion = bundle.getString("Descripcion");
        Foto_id = bundle.getInt("Foto_id");
        Distrito = bundle.getString("DistritoId");
        PuntuacionTotal = bundle.getDouble("PuntuacionTotal");


        TextView txtNombre = (TextView) findViewById(R.id.restaurante_txt_nombre);
        TextView txtDescripcion = (TextView) findViewById(R.id.restaurante_txt_descripcion);
        TextView txtPuntaje = (TextView) findViewById(R.id.restaurante_txt_puntaje);

        txtNombre.setText(Nombre);
        txtDescripcion.setText(Descripcion);
        txtPuntaje.setText(PuntuacionTotal.toString());

        llenarListaRecomendaciones();
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
    public  void irAbout(View v){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void irRecomendar(View v){
        setContentView(R.layout.activity_restaurante_recomendar);
        this.recomendacion_grupoId = 0;

        Button btnRecomendar = (Button) findViewById(R.id.restaurante_btn_enviar_recomend);
        btnRecomendar.setClickable(true);

        TextView txtNombre = (TextView) findViewById(R.id.restaurante_txt_nombre);
        TextView txtDescripcion = (TextView) findViewById(R.id.restaurante_txt_descripcion);
        TextView txtPuntaje = (TextView) findViewById(R.id.restaurante_txt_puntaje);

        txtNombre.setText(Nombre);
        txtDescripcion.setText(Descripcion);
        txtPuntaje.setText(PuntuacionTotal.toString());

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView seekBarValue = (TextView)findViewById(R.id.restaurante_txtvw_seekbar_valor);
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // something
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // something
            }
        });

        ImageView1 =(ImageView) findViewById(R.id.restaurante_recomendacion_foto_img1);
        ImageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                aux = 1;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri1 = getOutputMediaFileUri();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri1);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
        Cancel1 = (Button) findViewById(R.id.restaurante_recomendacion_foto_btn1);
        Cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView1.setImageResource(R.drawable.plus);
                EliminarFile(fileUri1);
                fileUri1 = null;
            }
        });

        ImageView2 =(ImageView) findViewById(R.id.restaurante_recomendacion_foto_img2);
        ImageView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                aux = 2;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri2 = getOutputMediaFileUri();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri2);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
        Cancel2 = (Button) findViewById(R.id.restaurante_recomendacion_foto_btn2);
        Cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView2.setImageResource(R.drawable.plus);
                EliminarFile(fileUri2);
                fileUri2 = null;
            }
        });

        ImageView3 =(ImageView) findViewById(R.id.restaurante_recomendacion_foto_img3);
        ImageView3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                aux = 3;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri3 = getOutputMediaFileUri();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri3);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
        Cancel3 = (Button) findViewById(R.id.restaurante_recomendacion_foto_btn3);
        Cancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView3.setImageResource(R.drawable.plus);
                EliminarFile(fileUri3);
                fileUri3 = null;
            }
        });
    }

    public void AbrirCamara(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);

    }

    //aqui va la funcion que envia al usuario a elegir un grupo o amigo
    public void irAgregarAmigoGrupo(View v)
    {
        Intent intent = new Intent(this, AmigosGruposActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode == RESULT_OK)
        {
            if( requestCode == CAMERA_PIC_REQUEST) {
                if (aux == 1) {
                    ImageView1.setImageBitmap(getThumbnailBitmap(fileUri1.getPath(), 50));
                }
                if (aux == 2) {
                    ImageView2.setImageBitmap(getThumbnailBitmap(fileUri2.getPath(), 50));
                }
                if (aux == 3) {
                    ImageView3.setImageBitmap(getThumbnailBitmap(fileUri3.getPath(), 50));
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

        if(data == null)
            return;

        switch (requestCode)
        {
            case 1:
                TextView dest_resultado = (TextView)findViewById(R.id.restaurante_txtvw_dest_resultado);
                dest_resultado.setText(data.getStringExtra("GrupoNombre"));
                this.recomendacion_grupoId = data.getIntExtra("GrupoId",0);
                break;
        }
    }

    public void recomendarRestaurante(View v)
    {
        final File file1;
        final File file2;
        final File file3;
        final int auxIdUsuario = this.idUsuarioLogueado;
        if(this.fileUri1 != null) {
            file1 = new File(this.fileUri1.getPath());
        } else {
            file1 = null;
        }
        if(this.fileUri2 != null) {
            file2 = new File(this.fileUri2.getPath());
        } else {
            file2 = null;
        }
        if(this.fileUri3 != null) {
            file3 = new File(this.fileUri3.getPath());
        } else {
            file3 = null;
        }

        TextView seekBarValue = (TextView)findViewById(R.id.restaurante_txtvw_seekbar_valor);
        FormEditText  comentarioET = (FormEditText) findViewById(R.id.restaurante_rec_comentarios);
        FormEditText[] allFields = {comentarioET};
        if (Validar.validarEditTexts(allFields)) {

            if(this.recomendacion_grupoId == 0){
                Toast.makeText(getApplicationContext(), "Seleccione un Amigo o Grupo", Toast.LENGTH_LONG).show();
                return;
            }

            Integer puntuacion = Integer.parseInt(seekBarValue.getText().toString());
            String comentario = comentarioET.getText().toString();

            //--------LOGICA DE LA LLAMADA A LA API----------//
            AsyncHttpClient client = new AsyncHttpClient();
            prgDialog.setMessage("Please wait...");
            prgDialog.show();

            RequestParams params = new RequestParams();
            params.put("comentario", comentario);
            params.put("puntuacion", (int)puntuacion);
            params.put("usuario_id", idUsuarioLogueado);
            params.put("restaurante_id", this.IdRestaurante);
            params.put("grupo_id", this.recomendacion_grupoId);
            params.put("created_by", idUsuarioLogueado);

            client.post("http://52.25.159.62/api/recomendaciones/create", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    try {
                        JSONObject obj = new JSONObject(response);

                        //TODO: revisar manejo del error
                        if (response.contains("error")) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        } else {
                            int idRecomendacion = obj.getJSONObject("data").getInt("id");
                            RequestParams requestParams = new RequestParams();
                            try {
                                if(file1 != null) {
                                    requestParams.put("file1", file1);
                                }
                                if(file2 != null) {
                                    requestParams.put("file2", file2);
                                }
                                if(file3 != null) {
                                    requestParams.put("file3", file3);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            requestParams.put("recomendacion_id", idRecomendacion);
                            requestParams.put("created_by", auxIdUsuario);

                            prgDialog.hide();
                            prgDialog.show();
                            RestaurAppisClient.post("recomendaciones/fotos/crearAgregarFotos", requestParams, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    Toast.makeText(getApplicationContext(), "Recomendacion Enviada!", Toast.LENGTH_LONG).show();
                                    Button btnRecomendar = (Button) findViewById(R.id.restaurante_btn_enviar_recomend);
                                    btnRecomendar.setClickable(false);
                                    prgDialog.hide();
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(getApplicationContext(), "Recomendacion Enviada!", Toast.LENGTH_LONG).show();
                                    Button btnRecomendar = (Button) findViewById(R.id.restaurante_btn_enviar_recomend);
                                    btnRecomendar.setClickable(false);
                                    prgDialog.hide();
                                }
                            });
                        }
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
    }

    public void llenarListaRecomendaciones(){

        //--------LOGICA DE LA LLAMADA A LA API----------//
        AsyncHttpClient client = new AsyncHttpClient();
        prgDialog.setMessage("Please wait...");
        prgDialog.show();

        client.get("http://52.25.159.62/api/restaurantes/"+IdRestaurante+"/recomendaciones?include=emisor", new AsyncHttpResponseHandler() {
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
                            JSONObject jobjUsu = jObj.getJSONObject("emisor");
                            JSONObject jobjUsuData = jobjUsu.getJSONObject("data");

                            Comentario comentario = new Comentario();

                            comentario.setId(jObj.getInt("id"));
                            comentario.setComentario(jObj.getString("comentario"));
                            comentario.setNomusuario(jobjUsuData.getString("nombres") + " " + jobjUsuData.getString("apellidos"));
                            comentario.setFecha(jObj.getString("created_at"));
                            comentario.setPuntuacion(jObj.getString("puntuacion"));
                            comentario.setRestaurante_id(jObj.getInt("restaurante_id"));
                            comentario.setGrupo_id(jObj.getInt("grupo_id"));

                            llenarListaComentarios(comentario);
                        }
                    }
                    actualizarListaConAdapterComentarios();
                    prgDialog.hide();
                    if(listaComentarios.isEmpty())
                        Toast.makeText(getApplicationContext(),"Este restaurante aún no tiene comentarios",Toast.LENGTH_LONG).show();
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
    public void llenarListaComentarios(Comentario comentario){
        listaComentarios.add(comentario);
    }
    public void actualizarListaConAdapterComentarios(){
        //Lista
        ListView lstVwComentarios = (ListView) findViewById(R.id.restaurante_lista_comentarios);
        ComentarioAdapter comentarioAdapter = new ComentarioAdapter(listaComentarios,this);
        lstVwComentarios.setAdapter(comentarioAdapter);
        lstVwComentarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comentario comentario = (Comentario)parent.getItemAtPosition(position);
                Intent intent = new Intent(RestauranteActivity.this, RecomendacionVerActivity.class);
                intent.putExtra("Nombre", Nombre);
                intent.putExtra("Descripcion", Descripcion);
                intent.putExtra("PuntuacionTotal", PuntuacionTotal);
                intent.putExtra("id", comentario.getId());
                intent.putExtra("comentario", comentario.getComentario());
                intent.putExtra("nomusuario", comentario.getNomusuario());
                intent.putExtra("fecha", comentario.getFecha());
                intent.putExtra("puntuacion", comentario.getPuntuacion());
                intent.putExtra("grupo_id", comentario.getGrupo_id());
                startActivity(intent);
            }
        });

    }

    private Bitmap getThumbnailBitmap(String path, int thumbnailSize) {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        return BitmapFactory.decodeFile(path, opts);
    }

    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "RestaurApp");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("RestaurApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".png");

        return mediaFile;
    }

    private void EliminarFile(Uri uriFile){
        if(uriFile != null) {
            File file = new File(uriFile.getPath());
            file.delete();
        }
    }
}
