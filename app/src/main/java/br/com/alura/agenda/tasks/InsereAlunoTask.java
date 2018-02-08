package br.com.alura.agenda.tasks;

import android.os.AsyncTask;

import br.com.alura.agenda.WebClient;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 05/02/18.
 */

public class InsereAlunoTask  extends AsyncTask {

    private  final Aluno aluno;
    public InsereAlunoTask(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String json = new AlunoConverter().toJson(this.aluno);
        new WebClient().insere(json);
        return null;
    }
}
