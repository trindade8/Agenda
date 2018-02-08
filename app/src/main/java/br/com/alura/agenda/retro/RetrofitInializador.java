package br.com.alura.agenda.retro;

/**
 * Created by wesley on 05/02/18.
 */






import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInializador {

    public  RetrofitInializador()
    {
        new Retrofit.Builder().baseUrl("http://192.168.1.108:8080/api/aluno").addConverterFactory(JacksonConverterFactory.create())
                .build();

        // teste;

    }
}
