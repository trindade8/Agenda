package br.com.alura.agenda;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.DAO.AlunoDAO;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 13/12/17.
 */

public class EnviaAlunosTask extends AsyncTask<Void,Void,String>  {
    private Context context;
    private ProgressDialog alertDialog;

    public EnviaAlunosTask(Context context)
    {
        this.context = context;
    }
    @Override
    protected  String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(this.context);
        List<Aluno> lista = dao.buscaAlunos();
        dao.close();
        AlunoConverter converter = new AlunoConverter();
        String json = converter.toJson(lista);
        WebClient client = new WebClient();
        String resporta = client.Post(json);
        return resporta;
    }

    @Override
    protected void onPreExecute()
    {

        alertDialog = ProgressDialog.show(context,"Aguarde" , "Enviando para o servidor ...", true, true);
        alertDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String resposta)
    {
        this.alertDialog.dismiss();
        Toast.makeText(this.context, resposta, Toast.LENGTH_SHORT).show();
    }
}
