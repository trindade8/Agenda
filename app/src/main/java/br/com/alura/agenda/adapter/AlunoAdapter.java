package br.com.alura.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.helper.ItemListaHelper;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 13/12/17.
 */

public class AlunoAdapter extends BaseAdapter {


    private final Context context;
    private final List<Aluno> listaAluno;

    public AlunoAdapter(Context context, List<Aluno> lista)
    {
        this.context = context;
        this.listaAluno = lista;
    }
    @Override
    public int getCount() {
        return this.listaAluno.size();
    }

    @Override
    public Object getItem(int i) {
        return this.listaAluno.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.listaAluno.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno _aluno = this.listaAluno.get(position);
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = convertView;
        if(view == null)
        {
            view = inflater.inflate(R.layout.list_item, parent,false);
        }
        ItemListaHelper helper = new ItemListaHelper(view,_aluno);
        return helper.getView();
    }
}
