package br.com.alura.agenda.helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.File;

import br.com.alura.agenda.FormularioActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 06/12/17.
 */

public class FormularioHelper {

    private EditText  campoNome;
    private EditText  campoEndereco;
    private EditText  campoTelefone;
    private EditText  campoSite;
    private RatingBar campoNota;
    private ImageView campoFoto;
    private Aluno     aluno;


    public FormularioHelper(final FormularioActivity activity) {
        this.campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        this.campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        this.campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        this.campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        this.campoNota = (RatingBar) activity.findViewById(R.id.formulario_rating);
        this.campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        this.aluno = new Aluno ();
    }

    public Aluno pegaAluno()   {

        this.aluno.setNome(this.campoNome.getText().toString());
        this.aluno.setEndereco(this.campoEndereco.getText().toString());
        this.aluno.setTelefone(this.campoTelefone.getText().toString());
        this.aluno.setSite(this.campoSite.getText().toString());
        this.aluno.setNota(Double.valueOf(this.campoNota.getProgress()));
        this.aluno.setCaminhoFoto((String)campoFoto.getTag());
        return this.aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        this.campoNome.setText(aluno.getNome());
        this.campoEndereco.setText(aluno.getEndereco());
        this.campoTelefone.setText(aluno.getTelefone());
        this.campoSite.setText(aluno.getSite());
        this.campoNota.setProgress( aluno.getNota().intValue());
        this.carregaImagemAluno(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagemAluno(String caminhoFoto)
    {
        if(caminhoFoto != null && caminhoFoto != "")
        {
            try
            {
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = bitmap.createScaledBitmap(bitmap, 150, 150, true);
                carregaImagemAluno(bitmapReduzido);
                this.campoFoto.setTag(caminhoFoto);
            }
            catch (Exception e )
            {

            }
        }

    }

    private void carregaImagemAluno(Bitmap imagem)
    {
        try
        {
            this.campoFoto.setImageBitmap(imagem);
            this.campoFoto.setTag(imagem);
            this.campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }catch (Exception e )
        {

        }
    }
}
