package upc.edu.pe.restaurapp.Servicios;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.*;

import java.nio.charset.StandardCharsets;

public class RestaurAppis {

    public JSONObject obj = null;
    String Error;

    public JSONObject Request(RequestParams params, String URL, String MetodoREST) {

        switch (MetodoREST){
            case "post":case "POST":
                RequestPost(params,URL);
                break;
            case "get":case "GET":
                RequestGet(params,URL);
                break;
            case "delete":case "DELETE":
                RequestDelete(params,URL);
                break;
        }

        return this.obj;
    }


    private void RequestPost(RequestParams params, String URL){

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL ,params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responsebody) {
                String response = new String(responsebody);
                try {
                    JSONObject JSONobj = new JSONObject(response);
                    SetJSONobj(JSONobj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responsebody, Throwable error) {
                String response = new String(responsebody);

                if(statusCode == 404){
                    SetError("No se encontro el resource");
                }
                else if(statusCode == 500){
                    SetError("Hubo un error en el servidor");
                }
                else{
                    SetError("Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]");
                }
            }
        });
    }

    private void RequestGet(RequestParams params, String URL){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL ,params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responsebody) {
                String response = new String(responsebody);
                try {
                    JSONObject JSONobj = new JSONObject(response);
                    SetJSONobj(JSONobj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responsebody, Throwable error) {
                String response = new String(responsebody);

                if(statusCode == 404){
                    SetError("No se encontro el resource");
                }
                else if(statusCode == 500){
                    SetError("Hubo un error en el servidor");
                }
                else{
                    SetError("Ocurrio un Error Inesperado [Puede que el dispositivo no esté conectado al Internet o que el servidor remoto no este funcionando]");
                }
            }
        });
    }

    private void RequestDelete(RequestParams params, String URL){}

    private void SetJSONobj(JSONObject JSONobj){
        this.obj = JSONobj;
    }

    private void SetError(String Error){
        this.Error = Error;
    }




}
