package upc.edu.pe.restaurapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class PantallaInicioActivity extends Activity {
/*
    private TextView mTextDetails;
    private CallbackManager mcallbackManager;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if(profile != null)
                mTextDetails.setText("Bienvenido " + profile.getName());
        }
        @Override
        public void onCancel() {
        }
        @Override
        public void onError(FacebookException e) {
        }
    };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //mcallbackManager=CallbackManager.Factory.create();

    }

    //METODO PARA LOGIN FACEOOK
/*
    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(mcallbackManager,mCallback);

        return super.onCreateView(name, context, attrs);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
    public void Registro(View v){
        Intent intent = new Intent(this,IniciarSesionActivity.class);
        intent.putExtra("Destino","Registro");
        startActivity(intent);
    }
    public void Login(View v){
        Intent intent= new Intent(this,IniciarSesionActivity.class);
        intent.putExtra("Destino","Login");
        startActivity(intent);

    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mcallbackManager.onActivityResult(requestCode,resultCode,data);
    }
    */
}
