package weslei.com.br.applojavirtual.async;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

import weslei.com.br.applojavirtual.LoginActivity;
import weslei.com.br.applojavirtual.MainActivity;
import weslei.com.br.applojavirtual.util.Util;
import weslei.com.br.applojavirtual.validation.LoginValidation;

/**
 * Created by weslei on 11/01/2017.
 */

//Classe respossável por fazer requisições assincronas no webservice
public class AsyncUsuario  extends AsyncTask<String, String, String>{

    private LoginValidation loginValidation;

    private Activity activity;

    public AsyncUsuario(LoginValidation loginValidation){
        this.loginValidation = loginValidation;
        this.activity = loginValidation.getActivity();
    }



    @Override
    protected String doInBackground(String... url) {

        StringBuilder resultado = new StringBuilder("");

        try {

            String path = url[0];
            path += "?usuario="+loginValidation.getLogin()+"&senha="+loginValidation.getSenha();

            URL urlNet = new URL(path);
            HttpURLConnection con =(HttpURLConnection) urlNet.openConnection();
            //Informa que a conexão será do tipo GET
            con.setRequestMethod("POST");
            //Informa que vai aceitar entrada de dados
            con.setDoInput(true);
            con.connect();

            //Este Objeto armazena o resultado da requisição
            InputStream inp = con.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inp));

            String linha = "";

            while((linha = bufferedReader.readLine())!= null){
                resultado.append(linha);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado.toString();
    }

    @Override
    protected void onPostExecute(String  resultado) {
        if(Boolean.valueOf(resultado)){
            SharedPreferences.Editor editor = activity.getSharedPreferences("pref", Context.MODE_PRIVATE).edit();//Editando o preferences
            editor.putString("login", loginValidation.getLogin());
            editor.putString("senha", loginValidation.getSenha());
            editor.commit();

            Intent i = new Intent(activity, MainActivity.class);//Passamos por parâmetros onde estamos e para onde vamos. Quando informamo o .class significa que o objeto não foi criado
            activity.startActivity(i);//cria uma nova activity
            activity.finish();//Finaliza a activity  atual


        }else{
            Util.showMessageToast(activity,"Login/Senha inválidos");
        }
    }
}
