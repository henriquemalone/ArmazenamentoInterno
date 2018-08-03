package armazenamentointerno.cursoandroid.com.armazenamentointerno;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private ImageView salvar;
    private EditText anotacoes;

    private static final String NOME_ARQUIVO = "Arquivo anotacao.txt";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salvar = (ImageView) findViewById(R.id.botaoSalvarId);
        anotacoes = (EditText) findViewById(R.id.editTextId);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDigitado = anotacoes.getText().toString();
                gravarNoArquivo(textoDigitado);

                Toast.makeText(MainActivity.this, "SALVO", Toast.LENGTH_SHORT).show();
            }
        });

        //Recuperar o que foi gravado
        if(lerArquivo() != null){
            anotacoes.setText(lerArquivo());
        }
    }

    private void gravarNoArquivo(String texto){

        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo(){
        String resultado = "";

        try{
            //Abrir o arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);

            if(arquivo != null){
                //Ler o arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //Gerar Buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //Recuperar textos do arquivo
                String linhaArquivo = "";
                while((linhaArquivo = bufferedReader.readLine()) != null) {
                    resultado += linhaArquivo;
                }

             arquivo.close();
            }
        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }

        return  resultado;
    }
}
