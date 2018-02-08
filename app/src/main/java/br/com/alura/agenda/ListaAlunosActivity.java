package br.com.alura.agenda;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.DAO.AlunoDAO;
import br.com.alura.agenda.adapter.AlunoAdapter;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.fragments.ListaProvasFragment;
import br.com.alura.agenda.funcoes.Funcoes;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {


    private ListView listaAlunos;

    // Override's
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        listaAlunos= (ListView) findViewById(R.id.listalunos);

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);

        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id) {
                Aluno _aluno = (Aluno) listaAlunos.getItemAtPosition(posicao);
                //Toast.makeText(ListaAlunosActivity.this,_aluno.getNome()+" Selecionado(a)",Toast.LENGTH_SHORT).show();
                Intent vaiparaFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                vaiparaFormulario.putExtra("aluno",_aluno);
                startActivity(vaiparaFormulario);


            }
        });

        PersistePermissaoUsuario(ListaAlunosActivity.this,Manifest.permission.RECEIVE_SMS,124);



    }

    @Override
    protected void onResume()
    {
        super.onResume();
        carregaLista();


    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, final ContextMenu.ContextMenuInfo menuInfo){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno _aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);


        //region
        MenuItem deletar = contextMenu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(_aluno);
                Toast.makeText(ListaAlunosActivity.this,"Aluno  " +_aluno.getNome() + " deletado !",Toast.LENGTH_LONG).show();
                carregaLista();


                return false;
            }
        });

        MenuItem visitarSite = contextMenu.add("Visite o site do aluno");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String site= _aluno.getSite();
        if(!site.startsWith("http://") )
            site = "http://"+site;
        intent.setData(Uri.parse(site));
        visitarSite.setIntent(intent);

        MenuItem enviarSms = contextMenu.add("Enviar SMS");
        Intent   intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:"+_aluno.getTelefone()));
        enviarSms.setIntent(intentSms);

        //region Item MenuMapa
        MenuItem visualizarNoMapa = contextMenu.add("Visualizar no Mapa");
        Intent   intentMapa =  new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0.0?q="+_aluno.getEndereco()));
        visualizarNoMapa.setIntent(intentMapa);
        //endregion

        //region Item Ligar
        MenuItem ligar = contextMenu.add("Ligar para o Aluno");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {



                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);

                }else
                    {
                        Intent intentLigacao = new Intent(Intent.ACTION_CALL);
                        intentLigacao.setData(Uri.parse("tel:"+_aluno.getTelefone()));
                        startActivity(intentLigacao);
                    }


                return false;
            }
        });
        //endregion




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_lista_enviar:
                if(Funcoes.VerificaConexaoComInternet(ListaAlunosActivity.this))
                    new EnviaAlunosTask(ListaAlunosActivity.this).execute();
                break;
            case R.id.menu_receber_provas:
                intent = new Intent(ListaAlunosActivity.this,ProvasActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_mapa:
                intent = new Intent(ListaAlunosActivity.this,MapaActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista,menu);
        return super.onCreateOptionsMenu(menu);

    }

    //Fim Override

    // Metodos da Classe
    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoAdapter adapter = new AlunoAdapter(ListaAlunosActivity.this,alunos);
        listaAlunos.setAdapter(adapter);
    }



    private boolean PossuiPermissao(Context context, String permissao){
        Boolean status = false;
        status = ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == 0 ? false : true;
        return  status;
    }

    private void SolicitaPermissaoAoUsuario(Activity  activity, String permissao, int requestCode)
    {

        ActivityCompat.requestPermissions(activity,new String[]{permissao},requestCode);
    }

    private void PersistePermissaoUsuario( Activity activity, String permissao , int requestCode)
    {
        if(!PossuiPermissao(activity,permissao))
        {
            SolicitaPermissaoAoUsuario(activity,permissao,requestCode);
        }
    }



    }



