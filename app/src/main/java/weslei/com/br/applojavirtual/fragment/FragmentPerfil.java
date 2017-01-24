package weslei.com.br.applojavirtual.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import weslei.com.br.applojavirtual.R;
import weslei.com.br.applojavirtual.adapter.ProfissaoArrayAdapter;
import weslei.com.br.applojavirtual.entity.Profissao;
import weslei.com.br.applojavirtual.entity.User;
import weslei.com.br.applojavirtual.util.Constantes;

/**
 * Created by weslei on 10/01/2017.
 */

public class FragmentPerfil extends Fragment {

    private Button btnCadastrar;
    private TextView txtNome, txtEmail, txtMiniBio;
    private TextInputLayout lytTxtNome;
    private Spinner spnProfissao;
    private List<Profissao> profissoes;
    private RadioGroup rbgSexo;
    private RadioButton rbtMasc, rbtFem;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        final RelativeLayout layoutLoading = (RelativeLayout) view.findViewById(R.id.layoutLoading);
        final Gson gson = new Gson();
        layoutLoading.setVisibility(View.VISIBLE);

        lytTxtNome = (TextInputLayout) view.findViewById(R.id.lytTxtNome);
        btnCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        txtNome = (TextView) view.findViewById(R.id.txtNome);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtMiniBio = (TextView) view.findViewById(R.id.txtMinibio);
        spnProfissao = (Spinner) view.findViewById(R.id.spnProfissao);
        rbgSexo = (RadioGroup) view.findViewById(R.id.rbgSexo);
        rbtMasc = (RadioButton) view.findViewById(R.id.rbtMasc);
        rbtFem = (RadioButton) view.findViewById(R.id.rbtFem);

        new AsyncHttpClient().get(Constantes.URL_WS_BASE+"/usuario/get_profissoes", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCOde, Header[] headers, JSONArray response){

                //Criando tipo de dado que queremos converter
                //Despois posso pegar a lista de json e passar direto para a lista
                //do tipo criado abixo
                if(response != null) {
                    Type type = new TypeToken<List<Profissao>>() {
                    }.getType();

                    profissoes = gson.fromJson(response.toString(), type);
                    spnProfissao.setAdapter(new ProfissaoArrayAdapter(getActivity(), R.layout.linha_profissao, profissoes));
                }else{
                    Toast.makeText(getActivity(), "Houve um erro no carregamento na lista de profissões", Toast.LENGTH_LONG);
                }

                layoutLoading.setVisibility(View.GONE);

            }
        });


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarCampos()){
                    return;
                }

                String json = gson.toJson(montarPessoa());

                try {
                    StringEntity stringEntity = new StringEntity(json, "UTF-8");
                    //onexão POST com o WebService
                    new AsyncHttpClient().post(null, Constantes.URL_WS_BASE+"/usuario/add",stringEntity,"application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                           Log.d("response", response.toString());
                           User u = gson.fromJson(response.toString(),User.class);
                            Log.d("Nome", u.getNome());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return view;

    }

    private boolean validarCampos(){

        if(txtNome.getText().toString().trim().isEmpty()){
            lytTxtNome.setError("Campo nome Obrigatório");
            return false;
        }else{
            lytTxtNome.setErrorEnabled(false);
        }
        return true;
    };

    private User montarPessoa(){

        User user = new User();

        user.setNome(txtNome.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setMiniBio(txtMiniBio.getText().toString());
        user.setSexo(rbtFem.isChecked() ?'M':'F');
        user.setProfissao((Profissao) spnProfissao.getSelectedItem());

        return user;
    };
}
