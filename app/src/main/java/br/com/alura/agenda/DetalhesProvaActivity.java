package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.alura.agenda.fragments.DetalhesProvaFragment;
import br.com.alura.agenda.helper.DetalheProvaHelper;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    private Prova prova;
    private DetalheProvaHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);
        final Intent intent = getIntent();

        //this.helper = new DetalheProvaHelper();
        this.prova = (Prova) intent.getSerializableExtra("prova");

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.SetDataProva(this.prova);
    }

    private void SetDataProva(Prova prova) {
        this.helper.CarregaDadosProva(prova);
    }
}
