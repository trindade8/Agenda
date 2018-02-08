package br.com.alura.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.DAO.AlunoDAO;
import br.com.alura.agenda.R;

/**
 * Created by wesley on 13/12/17.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String telefone = "";

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        AlunoDAO dao = new AlunoDAO(context);
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String ) intent.getSerializableExtra("format");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SmsMessage sms = SmsMessage.createFromPdu(pdu,formato);
            telefone = sms.getDisplayOriginatingAddress();
        }

        String notificacaoUsuario = "Sms recebido "+telefone;

        if(dao.ehAluno(telefone))
        {
            notificacaoUsuario = "Sms recebido de um aluno ";
            Toast.makeText(context, notificacaoUsuario,Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }



        dao.close();


    }
}
