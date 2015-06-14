package upc.edu.pe.restaurapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


public class SplashScreenActivity extends Activity {
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES" ;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        //Averiguar si ya existe un usuario logueado

        sharedpreferences = getSharedPreferences(RESTAURAPP_PREFERENCES, Context.MODE_PRIVATE);
        Integer usuarioActualId = sharedpreferences.getInt("USUARIO_ACTUAL_ID",0);

        if(usuarioActualId == 0) {

            int delay = 3000;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, LoginMainActivity.class));
                    finish();
                }
            }, delay);
        }
        else{
            int delay = 3000;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "¡Bienvenido de vuelta a Restaurapp!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }
            }, delay);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
}
