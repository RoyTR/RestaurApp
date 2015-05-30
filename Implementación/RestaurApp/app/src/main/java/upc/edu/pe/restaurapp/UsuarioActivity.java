package upc.edu.pe.restaurapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class UsuarioActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);
        Button btnPerfil = (Button)findViewById(R.id.footerusuariobtnperfil);
        btnPerfil.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

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

    public void GuardarCambiosPerfil(View v) {

    }
    public void GuardarCambiosPreferencia(View v) {

    }
    public void cambiarPerfil(View v) {
        setContentView(R.layout.activity_usuario_perfil);
        Button btnPerfil = (Button)findViewById(R.id.footerusuariobtnperfil);
        btnPerfil.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
    }
    public void cambiarPreferencia(View v) {
        setContentView(R.layout.activity_usuario_preferencias);
        Button btnPref = (Button)findViewById(R.id.footerusuariobtnpreferencia);
        btnPref.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));
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
