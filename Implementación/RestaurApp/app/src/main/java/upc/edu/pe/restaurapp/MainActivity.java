package upc.edu.pe.restaurapp;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

import upc.edu.pe.restaurapp.Adapter.RestauranteAdapter;
import upc.edu.pe.restaurapp.Entidades.Restaurante;


public class MainActivity extends ActionBarActivity {


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

        //Generales
        Button btnbuscar = (Button) findViewById(R.id.mainbtnfterbuscar);
        btnbuscar.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

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
                return true;
            case R.id.action_perfil:
                irUsuario(findViewById(R.id.action_perfil));
                return true;
            case R.id.action_contactos:
                irContactos(findViewById(R.id.action_contactos));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cambiarBuscar(View v){

        setContentView(R.layout.activity_main_buscar_distrito);
        Button btn = (Button) findViewById(R.id.mainbtnfterbuscar);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //TODO expandedList
    }
    public void cambiarBuscarTipoComida(View v){

        setContentView(R.layout.activity_main_buscar_tipocomida);
        Button btn = (Button) findViewById(R.id.mainbtnfterbuscar);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //TODO expandedList
    }
    public void cambiarCerca(View v){

        setContentView(R.layout.activity_main_cerca_lista);
        Button btn = (Button) findViewById(R.id.mainbtnftercerca);
        btn.setBackgroundColor(getResources().getColor(R.color.restaurapptheme_color));

        //Lista
        ListView lstVwRestaurantesCerca = (ListView) findViewById(R.id.listViewCerca);
        RestauranteAdapter restauranteAdapter = new RestauranteAdapter(ObtenerListaCerca(),this);
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
        final RestauranteAdapter restauranteAdapter = new RestauranteAdapter(ObtenerListaRecomPreferencias(),this);
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
    public void irContactos(View v){
        Intent intent = new Intent(this, ContactosActivity.class);
        startActivity(intent);
    }
    public  void irUsuario(View v){
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
    }


    private List<Restaurante> ObtenerListaCerca() {
        List<Restaurante> lst = new ArrayList<Restaurante>();

        //TODO cambiar esta funcion por la real de BD
        lst = generarDatosPrueba();

        return lst;
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









    //TODO: Borrar esta funcion de prueba
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


}
