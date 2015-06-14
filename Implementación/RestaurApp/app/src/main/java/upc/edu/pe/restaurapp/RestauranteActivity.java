package upc.edu.pe.restaurapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class RestauranteActivity extends ActionBarActivity {

    int IdRestaurante;
    String Nombre ;
    String Latitud;
    String Longitud;
    String Descripcion ;
    Integer Foto_id;
    Double PuntuacionTotal;
    String Distrito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante_ver);

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

        TextView txtNombre = (TextView) findViewById(R.id.restaurante_txt_nombre);
        TextView txtDescripcion = (TextView) findViewById(R.id.restaurante_txt_descripcion);
        TextView txtPuntaje = (TextView) findViewById(R.id.restaurante_txt_puntaje);

        txtNombre.setText(Nombre);
        txtDescripcion.setText(Descripcion);
        txtPuntaje.setText(PuntuacionTotal.toString());
    }

    public void AbrirCamara(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);

    }


}
