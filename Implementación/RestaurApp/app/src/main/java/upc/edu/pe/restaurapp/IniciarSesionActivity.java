package upc.edu.pe.restaurapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import upc.edu.pe.restaurapp.Servicios.RestaurAppisClient;
import upc.edu.pe.restaurapp.Utilitario.Validar;


public class IniciarSesionActivity extends ActionBarActivity {

    public static final String RESTAURAPP_PREFERENCES = "RESTAURAPP_PREFERENCES" ;
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.getStringExtra("Destino").equals("Registro") ){
            setContentView(R.layout.activity_registro);
        }else{
            setContentView(R.layout.activity_iniciar_sesion);
        }


        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        ColorDrawable color = new ColorDrawable();
        color.setColor(Color.parseColor(String.valueOf("#00ccff")));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Procesando...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iniciar_sesion, menu);
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

    public void CambiarRegistrarse(View v){
        setContentView(R.layout.activity_registro);
    }

    public void CambiarIniciarSesion(View v) {

        setContentView(R.layout.activity_iniciar_sesion);
    }
    public void CambiarIniciarSesion() {

        setContentView(R.layout.activity_iniciar_sesion);
    }

    public void IniciarSesion(View v) {

        FormEditText etUsuario = (FormEditText)findViewById(R.id.inisesTextNombre);
        String usuario = String.valueOf(etUsuario.getText());
        FormEditText etContrasena = (FormEditText)findViewById(R.id.inisesTextPassword);
        FormEditText[] allFields    = { etUsuario, etContrasena };
        String pwd = String.valueOf(etContrasena.getText());

        if (Validar.validarEditTexts(allFields)) {
            RequestParams params = new RequestParams();
            params.put("username", usuario);
            params.put("password", pwd);
            prgDialog.setMessage("Iniciando Sesion...");
            prgDialog.show();
            RestaurAppisClient.post("usuarios/verificar", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    prgDialog.hide();
                    String response = new String(responseBody);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (response.contains("error")) {
                            Toast.makeText(getApplicationContext(), obj.getJSONObject("data").getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "Te Has Identificado Correctamente", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = getSharedPreferences(RESTAURAPP_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt("USUARIO_ACTUAL_ID",obj.getJSONObject("data").getInt("id"));
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "¡Bienvenido a Restaurapp, "+obj.getJSONObject("data").getString("nombres")+"!", Toast.LENGTH_SHORT).show();
                            IrMainIniciarSesion();
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
        } else {
            // EditText are going to appear with an exclamation mark and an explicative message.
        }

        //INGRESAR CUANDO INSTANCIA APAGADA
        //IrMainIniciarSesion();
    }

    public void Registrarse(View v){

        FormEditText txtnombre = (FormEditText)findViewById(R.id.regTextNombre);
        FormEditText txtapellido = (FormEditText)findViewById(R.id.regTextApellido);
        FormEditText txtemail = (FormEditText)findViewById(R.id.regTextEmail);
        FormEditText txtusuario = (FormEditText)findViewById(R.id.regTextUserName);
        FormEditText txtpwd = (FormEditText)findViewById(R.id.regTextPassword);
        FormEditText txtpwd2 = (FormEditText)findViewById(R.id.regTextConfirmPassword);

        FormEditText[] allFields = {txtnombre, txtapellido, txtemail, txtusuario, txtpwd, txtpwd2};
        if(Validar.validarEditTexts(allFields)) {
            String nombres = String.valueOf(txtnombre.getText());
            String apellidos = String.valueOf(txtapellido.getText());
            String email = String.valueOf(txtemail.getText());
            String usuario = String.valueOf(txtusuario.getText());
            String pwd = String.valueOf(txtpwd.getText());
            String pwd2 = String.valueOf(txtpwd2.getText());
            //  Ver si las contraseñas hacen match
            if(pwd.equals(pwd2)) {
                RequestParams params = new RequestParams();
                params.put("nombres", nombres);
                params.put("apellidos", apellidos);
                params.put("username", usuario);
                params.put("password", pwd);
                params.put("email", email);
                params.put("facebook_id", "1");
                params.put("is_admin", "No");
                params.put("created_by", "1");

                prgDialog.setMessage("Registrandote...");
                prgDialog.show();
                RestaurAppisClient.post("usuarios/create", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        prgDialog.hide();
                        String response = new String(responseBody);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (response.contains("error")) {
                                if(obj.getJSONObject("data").getJSONObject("message").has("username"))
                                {
                                    Toast.makeText(getApplicationContext(), "El Usuario ingresado ya fue tomado", Toast.LENGTH_SHORT).show();
                                }

                                if(obj.getJSONObject("data").getJSONObject("message").has("email"))
                                {
                                    Toast.makeText(getApplicationContext(), "El Correo ingresado ya fue tomado", Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(getApplicationContext(), obj.getJSONObject("data").getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Te Has Registrado Correctamente, por favor Accede", Toast.LENGTH_SHORT).show();
                                CambiarIniciarSesion();
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
            } else {
                txtpwd.setError("Contraseñas no conciden");
                txtpwd2.setError("Contraseñas no conciden");
            }
        } else {
            // EditText are going to appear with an exclamation mark and an explicative message.
        }

    }

    public void IrMainIniciarSesion(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
