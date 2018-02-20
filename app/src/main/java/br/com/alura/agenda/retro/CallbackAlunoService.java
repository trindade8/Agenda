package br.com.alura.agenda.retro;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wesley on 19/02/18.
 */

public class CallbackAlunoService implements Callback {
    @Override
    public void onResponse(Call call, Response response) {
        Log.i("onResponse", "Requisição com sucesso!");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("onFailure", "Requisição com erro!");

    }
}
