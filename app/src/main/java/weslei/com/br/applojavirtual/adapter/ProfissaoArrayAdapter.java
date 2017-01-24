package weslei.com.br.applojavirtual.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import weslei.com.br.applojavirtual.R;
import weslei.com.br.applojavirtual.async.AsyncImageHelper;
import weslei.com.br.applojavirtual.entity.Profissao;
import weslei.com.br.applojavirtual.util.Constantes;

/**
 * Created by weslei on 18/01/2017.
 */

public class ProfissaoArrayAdapter extends ArrayAdapter<Profissao> {

    private List<Profissao> profissoes;
    private Context context;


    public ProfissaoArrayAdapter(Context context, int resource, List<Profissao> profissoes) {
        super(context, resource);
        this.profissoes = profissoes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return profissoes.size();
    }

    @Override
    public int getPosition(Profissao item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewAuxiliar(position, parent);

    }

    @NonNull
    private View getViewAuxiliar(int position, ViewGroup parent) {
        //Utilizamos o LayoutInflater para poder manipular elementes de outro arquivo xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linha = layoutInflater.inflate(R.layout.linha_profissao, parent, false);

        Profissao profissao = profissoes.get(position);

        TextView txtProfissao = (TextView) linha.findViewById(R.id.txtProfissao);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        ImageView imgProfissao= (ImageView) linha.findViewById(R.id.imgProfissao);

        new AsyncImageHelper(imgProfissao).execute(Constantes.URL_BASE_WEB +profissao.getUrlImage());

        txtProfissao.setText(profissao.getDescricao());
        txtDescricao.setText(profissao.getSubDescricao());


        return linha;
    }

    @Nullable
    @Override
    public Profissao getItem(int position) {
        return profissoes.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getViewAuxiliar(position, parent);
    }
}
