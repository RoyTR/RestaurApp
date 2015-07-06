package upc.edu.pe.restaurapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import upc.edu.pe.restaurapp.Servicios.RestaurAppisClient;

public class LoginMainActivity extends Activity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private String FacebookId;
    private String Nombre;
    private String Apellidos;
    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES" ;
    ProgressDialog prgDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login_main);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));

        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Procesando...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {

                if(currentProfile != null)
                {
                    FacebookId = currentProfile.getId();
                    Nombre = currentProfile.getFirstName();
                    Apellidos = currentProfile.getLastName();

                    iniciarSesionConFacebook();
                }
            }
        };

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //info.setText(
                //        "PRUEBA BORRAR LUEGO - User ID: "
                //                + loginResult.getAccessToken().getUserId()
                //);

                FacebookId = loginResult.getAccessToken().getUserId();
            }


            @Override
            public void onCancel() {
                info.setText("Problemas del servidor de Facebook.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Problemas del servidor de Facebook.");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

    public void iniciarSesionConFacebook()
    {
        RequestParams params = new RequestParams();
        params.put("nombres", this.Nombre);
        params.put("apellidos", this.Apellidos);
        params.put("facebook_id", this.FacebookId);

        prgDialog.setMessage("Iniciando Sesion con FB...");
        prgDialog.show();

        RestaurAppisClient.post("usuarios/iniciarSesionConFacebook", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (response.contains("error")) {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("data").getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences(RESTAURAPP_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt("USUARIO_ACTUAL_ID", obj.getJSONObject("data").getInt("id"));
                        editor.putString("USUARIO_ACTUAL_NOMBRES", obj.getJSONObject("data").getString("nombres"));
                        editor.putString("USUARIO_ACTUAL_APELLIDOS", obj.getJSONObject("data").getString("apellidos"));
                        editor.putString("USUARIO_ACTUAL_EMAIL", obj.getJSONObject("data").getString("email"));
                        editor.putString("USUARIO_ACTUAL_USERNAME", obj.getJSONObject("data").getString("username"));
                        editor.commit();

                        Toast.makeText(getApplicationContext(), "¡Bienvenido a Restaurapp, " + obj.getJSONObject("data").getString("nombres") + "!", Toast.LENGTH_SHORT).show();

                        IrMain();
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
    }

    public void IrMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}