package br.com.alura.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 13/12/17.
 */

public class AlunoConverter {

    public String toJson(List<Aluno> lista)
    {
        JSONStringer js = new JSONStringer();
        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : lista)
            {
                js.object();
                js.key("id").value(aluno.getId());
                js.key("nome").value(aluno.getNome());
                js.key("endereco").value(aluno.getEndereco());
                js.key("telefone").value(aluno.getTelefone());
                js.key("site").value(aluno.getSite());
                js.key("nota").value(aluno.getNota());
                js.endObject();




            }
            js.endArray().endObject().endArray().endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  js.toString();
    }

    public  String toJson(Aluno aluno)
    {
        JSONStringer js = new JSONStringer();
        try {


                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("endereco").value(aluno.getEndereco());
                js.key("site").value(aluno.getSite());
                js.key("telefone").value(aluno.getTelefone());
                js.key("nota").value(aluno.getNota());
                js.endObject();


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  js.toString();
    }
}
