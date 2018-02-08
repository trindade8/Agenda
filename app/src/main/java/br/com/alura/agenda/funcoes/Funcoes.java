package br.com.alura.agenda.funcoes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by wesley on 14/12/17.
 */

public final class Funcoes {


    public  static boolean possuiConexaoInternet(Context context)
    {
        ConnectivityManager cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public static  void ExibeAlerta(Context context, String titulo, String mensagem)
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setCancelable(true);
        alerta.show();
    }

    public static boolean VerificaConexaoComInternet(Context context)
    {
        boolean possuiConexao = possuiConexaoInternet(context);

        if(possuiConexao)
            return true;
        else
            ExibeAlerta(context,"Atenção","O recurso que você está querendo acessar precisa de uma conexão a internet.\n Seu device não possui conexão ativa com a internet.");

            return false;

    }
}
