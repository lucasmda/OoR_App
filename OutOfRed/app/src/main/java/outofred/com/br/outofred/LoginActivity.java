package outofred.com.br.outofred;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText campoCpf;
    private EditText campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoCpf = (EditText) findViewById(R.id.login_cpf);
        campoSenha = (EditText) findViewById(R.id.login_senha);

    }

    public void buscar(View view){
        //Chama o webservice através do AsynTask
        BuscaTask task = new BuscaTask();
        task.execute(campoCpf.getText().toString());
    }

    private class BuscaTask extends AsyncTask<String,Void,String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LoginActivity.this,
                    "Aguarde","Chamando o servidor");
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss(); // fecha a caixa de progresso
            if (s != null){
                try {
                    //Recuperar os valores do JSON
                    JSONObject json = new JSONObject(s);
                    String cpf = json.getString("cpf");
                    String senha = json.getString("senha");

                    if(cpf.equals(campoCpf) && senha.equals(campoSenha)){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(LoginActivity.this,"Nao foi possivel fazer o login",Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //Criar a URL
                URL url = new URL("http://10.20.63.61:8080/MercadoFiap/rest/mercado/" + params[0]);
                //Obter uma conexão
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //Configurar a requisição
                //Method -> GET (Busca)
                connection.setRequestMethod("GET");
                //Definindo o tipo do retorno que ele espera
                connection.setRequestProperty("Accept","application/json");

                //Valida o Status HTTP 200 OK
                if (connection.getResponseCode() == 200){
                    //Ler os dados retornados do servidor
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    //Variável para armazenar o valor total
                    StringBuilder retorno = new StringBuilder();
                    String linha; //variável auxiliar
                    //Ler todas as linhas do retorno
                    while ((linha = reader.readLine()) != null){
                        retorno.append(linha);
                    }
                    connection.disconnect();
                    return retorno.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}



/*
        Button login = (Button) findViewById(R.id.login_button_entrar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
        */
