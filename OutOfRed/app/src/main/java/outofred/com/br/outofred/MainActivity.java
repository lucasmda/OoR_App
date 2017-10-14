package outofred.com.br.outofred;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView campoCpf;
    private TextView dataNascimento;
    private TextView dataVencimento;
    private TextView valorDivida;
    private TextView totalPagar;

    private double valorAtual;
    private double valorAPagar;

    Bundle bundle = getIntent().getExtras();
    int idCliente = bundle.getInt("idCliente");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoCpf = (TextView) findViewById(R.id.main_cpf_number);
        dataNascimento = (TextView) findViewById(R.id.main_bday_date);
        dataVencimento = (TextView) findViewById(R.id.main_data_vencimento);
        valorDivida = (TextView) findViewById(R.id.main_valor_divida);
        totalPagar = (TextView) findViewById(R.id.main_total_pagar);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_parcelas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.parcelas_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button simular = (Button) findViewById(R.id.main_button_simular);
        simular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularDesconto(valorAtual, spinner.getSelectedItem().toString());
                totalPagar.setText(String.valueOf(valorAPagar));
            }
        });

    }


    public void calcularDesconto(double valor, String parcela){
        double valorFinal = 0;

        switch (parcela){
            case "Pagar a vista":
                valorFinal = valor*0.5;
                break;
            case "Pagar em 2x":
                valorFinal = valor*0.6;
                break;
            case "Pagar em 3x":
                valorFinal = valor*0.7;
                break;
            case "Pagar em 4x":
                valorFinal = valor*0.8;
                break;
            case "Pagar em 5x":
                valorFinal = valor*0.9;
                break;
        }

        valorAPagar = valorFinal;
    }


    //Classe para realizar a chamada ao ws
    private class BuscaTask extends AsyncTask<String,Void,String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this,
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
                    String nasci = json.getString("dataNascimento");

                    String venci = json.getString("dataVencimento");
                    double divida = json.getDouble("valor");

                    //Atualizar a tela
                    campoCpf.setText(cpf);
                    dataNascimento.setText(nasci);

                    dataVencimento.setText(venci);
                    valorDivida.setText(String.valueOf(divida));

                    valorAtual = divida;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(MainActivity.this,"Erro",Toast.LENGTH_LONG).show();
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
        Button pagar = (Button) findViewById(R.id.main_button_pagar);
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        */
