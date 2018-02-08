package br.com.alura.agenda;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by wesley on 13/12/17.
 */

public class WebClient {

    public  String Post(String json)
    {
        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-type","application/json");
            connection.setRequestProperty("Accept","application/json");

            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.println(json);

            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();
            return resposta;

        }catch (Exception e )
        {
            throw new RuntimeException(e);
        }

    }

    public void insere(String json) {
        String endereco = "http://192.168.1.108:8080/api/aluno";
        this.realizaConexao(json,endereco);
    }

    private  String realizaConexao(String json, String endereco)
    {
        try
        {
            URL url = new URL(endereco);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setDoOutput(true);
            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);
            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String retorno = scanner.next();
            return  retorno;

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return  null;
    }
}
