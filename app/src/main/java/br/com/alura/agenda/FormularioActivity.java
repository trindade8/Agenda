package br.com.alura.agenda;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.DAO.AlunoDAO;
import br.com.alura.agenda.helper.FormularioHelper;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.retro.RetrofitInializador;
import br.com.alura.agenda.tasks.InsereAlunoTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);
        final Intent intent = getIntent();

        if(savedInstanceState != null)
            restauraEstadoActivity(savedInstanceState);
        else
            this.aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null) {
            this.helper.preencheFormulario(aluno);
        }

        Button botao_foto = (Button) findViewById(R.id.formulario_botao_foto);

        botao_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FormularioActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);

            }


        });


    }


    //region Retorno camera
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            helper.carregaImagemAluno(caminhoFoto);
        }
    }
    //endregion

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putSerializable("STATE_ALUNO", this.aluno);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void restauraEstadoActivity(Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            this.aluno = (Aluno) savedInstanceState.getSerializable("STATE_ALUNO");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAluno();
                SalvaAluno(aluno);
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo! Nota :" + aluno.getNota().toString(), Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void SalvaAluno(Aluno aluno) {
        AlunoDAO dao = new AlunoDAO(this);
        if (aluno.getId() != null)
            dao.atualiza(aluno);
        else
            dao.insere(aluno);

        dao.close();

        //new InsereAlunoTask(aluno).execute();
        Call insere = new RetrofitInializador().getAlunoService().insere(aluno);
        insere.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("onResponse","Requisição com sucesso!" );
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("onFailure","Requisição com erro!" );

            }
        });
    }

    //propriedades
    private FormularioHelper helper;
    private String caminhoFoto;
    private Aluno aluno;
    public static final int CODIGO_CAMERA = 123;

}
