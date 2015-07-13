package upc.edu.pe.restaurapp.Servicios;

import com.loopj.android.http.*;

/**
 * Created by Roy on 08/06/2015.
 */
public class RestaurAppisClient {
    //52.25.159.62
    private static final String BASE_URL = "http://ec2-52-25-159-62.us-west-2.compute.amazonaws.com/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void patch(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.add("_method", "PATCH");
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
