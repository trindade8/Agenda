package br.com.alura.agenda.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 13/12/17.
 */

public class ItemListaHelper {

    private TextView  nomeAluno;
    private TextView  telefoneAluno;
    private ImageView campoFoto;
    private TextView  siteAluno;
    private TextView  enderecoAluno;
    private Aluno aluno;
    private View view;

    public ItemListaHelper(View view,Aluno aluno)
    {
        this.view = view;
        this.aluno = aluno;
        this.IniciaCampos(this.view);
        this.SetDadosAluno(this.aluno);
    }

    private void IniciaCampos(View view)
    {
        this.nomeAluno = (TextView) view.findViewById(R.id.item_nome);
        this.telefoneAluno = (TextView) view.findViewById(R.id.item_telefone);
        this.campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        this.siteAluno = (TextView) view.findViewById(R.id.item_site);
        this.enderecoAluno = (TextView) view.findViewById(R.id.item_endereco);
    }

    private void SetDadosAluno(Aluno aluno)
    {
        this.nomeAluno.setText(aluno.getNome());
        this.telefoneAluno.setText(aluno.getTelefone());
        if(this.siteAluno != null)
            this.siteAluno.setText(aluno.getSite());
        if(this.enderecoAluno != null)
            this.enderecoAluno.setText(aluno.getEndereco());
        this.carregaImagemAluno(aluno.getCaminhoFoto());
    }

    public View getView()
    {
        return this.view;
    }



    private void carregaImagemAluno(String caminhoFoto)
    {
        if(caminhoFoto != null && caminhoFoto != "")
        {
            try
            {
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = bitmap.createScaledBitmap(bitmap, 100, 100, true);
                carregaImagemAluno(bitmapReduzido);
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
