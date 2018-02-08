package br.com.alura.agenda.helper;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Prova;

/**
 * Created by wesley on 14/12/17.
 */

public class DetalheProvaHelper {

    private TextView nomeProva;
    private TextView dataProva;
    private ListView topicosProva;
    private Activity activity;
    private View view;



    public DetalheProvaHelper(View v)
    {
        this.view = v;
        IniciaWidgets(this.view);
    }




    private void IniciaWidgets( Activity activity)
    {
        this.nomeProva = (TextView) activity.findViewById(R.id.detalhes_prova_materia);
        this.dataProva = (TextView) activity.findViewById(R.id.detalhes_prova_data);
        this.topicosProva = (ListView) activity.findViewById(R.id.detalhe_prova_topicos);
    }

    private void IniciaWidgets(View v )
    {
        this.nomeProva = (TextView) v.findViewById(R.id.detalhes_prova_materia);
        this.dataProva = (TextView) v.findViewById(R.id.detalhes_prova_data);
        this.topicosProva = (ListView) v.findViewById(R.id.detalhe_prova_topicos);
    }





    public void CarregaDadosProva(Prova prova)
    {
        this.nomeProva.setText(prova.getMateria());
        this.dataProva.setText(prova.getData());
        this.CarregaDadosLista(prova.getTopicos());
    }

    private void CarregaDadosLista(List<String> topicos) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,topicos);
        this.topicosProva.setAdapter(arrayAdapter);
    }




}
