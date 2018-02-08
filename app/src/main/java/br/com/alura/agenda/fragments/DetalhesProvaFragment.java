package br.com.alura.agenda.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.alura.agenda.R;
import br.com.alura.agenda.helper.DetalheProvaHelper;
import br.com.alura.agenda.modelo.Prova;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesProvaFragment extends Fragment {


    private Prova prova;
    private DetalheProvaHelper helper;

    public DetalhesProvaFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);
        this.helper = new DetalheProvaHelper(view);

        Bundle parametros = getArguments();
        if(parametros != null)
        {
            Prova prova = (Prova) parametros.getSerializable("prova");
            if(prova !=null)
                carregaDadosProva(prova);

        }


        return view;
    }

    public void carregaDadosProva(Prova p)
    {
        this.helper.CarregaDadosProva(p);
    }

}
