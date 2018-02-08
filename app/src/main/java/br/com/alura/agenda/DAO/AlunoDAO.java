package br.com.alura.agenda.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 06/12/17.
 */

public class AlunoDAO  extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "";
        switch ( oldVersion)
        {
            case 1:
                sql = " ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT ; ";
                db.execSQL(sql);

        }


    }



    public void insere(Aluno aluno)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues dados = pegaDadosAluno(aluno);
        database.insert("Alunos",null,dados);


    }

    public List<Aluno> buscaAlunos() {
        SQLiteDatabase db = getWritableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM Alunos;", null);
                List<Aluno> buscaAlunos = new ArrayList<Aluno>();
        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            // Preencha todos os dados do aluno como no exemplo da linha abaixo
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            buscaAlunos.add(aluno);
        }
        c.close();
        return buscaAlunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};
        db.delete("Alunos","id= ?",params);
    }

    public void atualiza(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosAluno(aluno);
        String[] params = {aluno.getId().toString()};
        db.update("Alunos",dados,"id=?", params);

    }

    public ContentValues pegaDadosAluno(Aluno aluno)
    {
        ContentValues dados = new ContentValues();
        dados.put("nome",aluno.getNome());
        dados.put("endereco",aluno.getEndereco());
        dados.put("telefone",aluno.getTelefone());
        dados.put("site",aluno.getSite());
        dados.put("nota",aluno.getNota());
        dados.put("caminhoFoto",aluno.getCaminhoFoto());

        return dados;
    }

    public boolean ehAluno(String telefone)
    {
        Boolean status = false;
        if(telefone != null || !telefone.isEmpty())
        {
            SQLiteDatabase db = getReadableDatabase();
            String[] parametros = {telefone};
            Cursor cursor = db.rawQuery("SELECT * FROM Alunos WHERE telefone=?", parametros);
            status = cursor.getCount() > 0 ? true : false;
            cursor.close();
        }
        return status;
    }
}
