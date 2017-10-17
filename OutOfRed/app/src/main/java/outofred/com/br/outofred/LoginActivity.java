package outofred.com.br.outofred;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import outofred.com.br.outofred.model.Cliente;

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

    public void buscar(View view) {
        //Chama o webservice através do AsynTask
        ListaTask task = new ListaTask();
        task.execute();
    }

    private class ListaTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            if (s != null){
                try {
                    JSONObject json = new JSONObject(s);
                    JSONArray jsonArray = json.getJSONArray("itens");

                    List<Cliente> lista = new ArrayList<>();

                    for (int i=0; i < jsonArray.length(); i++){
                        //Recupera cada item do vetor
                        JSONObject item = (JSONObject) jsonArray.get(i);
                        int id = item.getInt("Id");
                        String nome = item.getString("nome");
                        String senha = item.getString("senha");
                        String cep = item.getString("cep");
                        String telefone = item.getString("telefone");
                        String cpf = item.getString("cpf");
                        String dataNascimento = item.getString("dataNascimento");
                        String email = item.getString("email");
                        double saldo = item.getDouble("saldo");
                        Cliente model = new Cliente(id,nome,senha,cep,telefone,cpf,dataNascimento,email,saldo);
                        lista.add(model);
                    }

                    for(int i=0; i < jsonArray.length(); i++){
                        if(lista.get(i).getCpf().equals(campoCpf) && lista.get(i).getSenha().equals(campoSenha)){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("idCliente",lista.get(i).getId());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(LoginActivity.this,"Nao foi possivel fazer o login",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(
                        "http://10.20.63.61:8080/MercadoFiap/rest/mercado/");
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept","application/json");

                if (connection.getResponseCode() == 200){
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(
                                    connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String linha;
                    while ((linha = reader.readLine()) != null){
                        builder.append(linha);
                    }
                    connection.disconnect();
                    return builder.toString();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}

