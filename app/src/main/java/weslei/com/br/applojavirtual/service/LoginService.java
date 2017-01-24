package weslei.com.br.applojavirtual.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import weslei.com.br.applojavirtual.MainActivity;
import weslei.com.br.applojavirtual.async.AsyncUsuario;
import weslei.com.br.applojavirtual.async.AsyncUsuarioHttpClient;
import weslei.com.br.applojavirtual.util.Constantes;
import weslei.com.br.applojavirtual.util.Util;
import weslei.com.br.applojavirtual.validation.LoginValidation;


/**
 * Created by Weslei on 09/11/2016.
 */

public class LoginService {


    public void validarCamposLogin(final LoginValidation validation){

        final Activity activity = validation.getActivity();
        Boolean resultado = true;
        if(validation.getLogin() == null || "".equals(validation.getLogin())){
            validation.getEdtLogin().setError("Campo Obrigatório!");
            resultado = false;
        }
        if(validation.getSenha() == null || "".equals(validation.getSenha())){
            validation.getEdtSenha().setError("Campo Obrigatório");
            resultado = false;
        }

        if(resultado){

            RequestParams params = new RequestParams();
            params.put("usuario", validation.getLogin());
            params.put("senha", validation.getSenha());

            AsyncUsuarioHttpClient.post("/usuario/login", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String resultado, Throwable throwable) {
                    Util.showMessageToast(activity, "Erro no login do usuário: " + throwable.getMessage());

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String resultado) {

                    if(Boolean.valueOf(resultado)){
                        SharedPreferences.Editor editor =  activity.getSharedPreferences("pref", Context.MODE_PRIVATE).edit();//Editando o preferences
                        editor.putString("login", validation.getLogin());
                        editor.putString("senha", validation.getSenha());
                        editor.commit();

                        Intent i = new Intent(activity, MainActivity.class);//Passamos por parâmetros onde estamos e para onde vamos. Quando informamo o .class significa que o objeto não foi criado
                        activity.startActivity(i);//cria uma nova activity
                        activity.finish();//Finaliza a activity  atual


                    }else{
                        Util.showMessageToast(activity,"Login/Senha inválidos");
                    }


                }
            });
            //new AsyncUsuario(validation).execute(Constantes.URL_WS_LOGIN);
        }

    }
    public void deslogar(){

    }
}
