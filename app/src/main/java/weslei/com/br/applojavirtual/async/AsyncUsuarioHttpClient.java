package weslei.com.br.applojavirtual.async;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import weslei.com.br.applojavirtual.MainActivity;
import weslei.com.br.applojavirtual.util.Constantes;
import weslei.com.br.applojavirtual.util.Util;
import weslei.com.br.applojavirtual.validation.LoginValidation;

/**
 * Created by weslei on 11/01/2017.
 */

//Classe respossável por fazer requisições assincronas no webservice
public class AsyncUsuarioHttpClient {


    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Constantes.URL_WS_BASE + relativeUrl;
    }
}
