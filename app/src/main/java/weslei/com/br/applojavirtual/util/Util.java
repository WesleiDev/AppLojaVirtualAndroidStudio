package weslei.com.br.applojavirtual.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import weslei.com.br.applojavirtual.R;


/**
 * Created by Suporte on 23/09/2016.
 */

public class Util {

    public static void showMessageToast(Activity activity, String texto){
        //Acessando layout de outra activity para customizar meu Toast
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.meu_template, (ViewGroup) activity.findViewById(R.id.layoutToast));
        TextView txtToast =(TextView)layout.findViewById(R.id.txtToast);
        txtToast.setText(texto);

        Toast toast = new Toast(activity);
        toast.setView(layout);
        toast.show();

    }

    public static void showMsgSimpleToast(Activity activity, String texto){
         Toast.makeText(activity, texto, Toast.LENGTH_SHORT).show();
    }

    public static void showMesgConfirm(final Activity activity, String titulo, String texto, DialogInterface.OnClickListener listener){

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(texto);


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", listener);

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();

    }

    public static void showMessageAlertDialog(final Activity activity, String titulo, String texto){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(texto);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cacenlar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Util.showMessageToast(this, "Clicou no cancelar");
            }
        });
        alertDialog.show();
    }
}
