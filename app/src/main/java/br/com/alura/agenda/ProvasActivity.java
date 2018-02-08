package br.com.alura.agenda;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.fragments.DetalhesProvaFragment;
import br.com.alura.agenda.fragments.ListaProvasFragment;
import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_provas);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.frame_principal, new ListaProvasFragment());
        if(getResources().getBoolean(R.bool.modoPaisagem))
        {
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        tx.commit();


    }


    private boolean isModoPaisagem()
    {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {

        FragmentManager fm = getSupportFragmentManager();
        if(!isModoPaisagem())
        {
            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle b = new Bundle();
            b.putSerializable("prova",prova);
            detalhesProvaFragment.setArguments(b);
            FragmentTransaction tx = fm.beginTransaction();
            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            tx.addToBackStack(null);
            tx.commit();

        }
        else
        {
            DetalhesProvaFragment detalhesProvaFragment = (DetalhesProvaFragment) fm.findFragmentById(R.id.frame_secundario);
            detalhesProvaFragment.carregaDadosProva(prova);

        }


    }
}
