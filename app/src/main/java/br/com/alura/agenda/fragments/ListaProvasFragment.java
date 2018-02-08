package br.com.alura.agenda.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.DetalhesProvaActivity;
import br.com.alura.agenda.ProvasActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Prova;

/**
 * Created by wesley on 14/12/17.
 */

public class ListaProvasFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.activity_lista_provas,container,false);

        List<String> TopicosPotugues = Arrays.asList("Sujeito","Objeto Direto","Objeto Indireto");
        Prova provaPort = new Prova("Portugues","12/11/2017",TopicosPotugues);
        List<String> TopicosMatematica = Arrays.asList("Teste","Logica","Trigonometria");
        Prova provaMat = new Prova("Matematica","14/11/2017",TopicosMatematica);
        List<Prova> provas = Arrays.asList(provaMat,provaPort);


        ArrayAdapter<Prova> arrayAdapter = new ArrayAdapter<Prova>(getContext(),android.R.layout.simple_list_item_1,provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prova prova = (Prova) adapterView.getItemAtPosition(i);

                Toast.makeText(getContext(),"Você clicou na prova : "+prova,Toast.LENGTH_SHORT).show();
                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);



            }
        });




        return view;
    }
}
