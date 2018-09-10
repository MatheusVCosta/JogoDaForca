package br.senai.jandira.jogoForca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matheus.jogoforca.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    //Array de botões que recebe o id dos botões que está no XML
    //também será setado um OnClickListener e uma tag
    //a tag está sendo usada para indicar a posição do vetor de botões
    Button [] botao = new Button[26];
    TextView [] texto;
    TextView txtDica, txtPontos, txtChance;
    Random random = new Random();
    LinearLayout layout;
    ImageView trocarImagem;
    ArrayList<Integer>guardarIndex = new ArrayList<Integer>();
    int indexPalavra,contador,numeroAleatorio,id,contadorDeLetras,pontuacao;
    int QntAcertos, QntErros, xErros,vidas, palavrasCertas;


    String [][] palavra = {
            {"T","E","C","L","A","D","O"},
            {"C","A","R","R","O"},
            {"B","I","C","I","C","L","E","T","A"},
            {"B","A","T","A","T","A"},
            {"N","I","C","O","L","A","S"},
            {"V","I","T","O","R","I","A"},
            {"M","I","K","A","E","L","A"},
            {"B","E","T","E","R","R","A","B","A"},
            {"R","E","P","O","L","H","O"},
            {"B","R","O","C","O","L","I","S"},
            {"C","O","X","I","N","H","A"},
            {"C","A","S","A","M","E","N","T","O"}

    };
    String [] dicas = {
            "PERIFÉRICO DE COMPUTADOR",
            "ULTILIZADO PARA LOCOMOÇÃO",
            "ULTILIZADA NO CICLISMO",
            "FRITA É UMA DELICIA",
            "CABO DE REDE SEM FIO",
            "DRAMA QUEEN",
            "GOSTOSINHO NO AZEITE",
            "TUBERCOLO COLORIRIDO",
            "ULTILIZADO EM COMIDA ALEMÃ",
            "ADORADO PELOS VEGETARIANOS",
            "MELHOR SALGADO QUE EXISTE",
            "CONTRATO SOCIAL"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txtDica = findViewById(R.id.txtDica);
        txtPontos = findViewById(R.id.txtPontos);
        trocarImagem = findViewById(R.id.imagem);
        layout = findViewById(R.id.linearLayout);
        txtChance = findViewById(R.id.txtChances);

        iniciarJogo();
    }

    final View.OnClickListener btnLetra = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int recebeTag = (int) view.getTag();
            String letra = botao[recebeTag].getText().toString();
            contador = 0;
            for (int i = 0; i < palavra[indexPalavra].length; i++) {
                if (palavra[indexPalavra][i].equals(letra)) {
                    texto[i].setText(letra);
                    contador++;
                    contadorDeLetras++;
                }
            }
            botao[recebeTag].setEnabled(false);
            if (contador != 0) {
                botao[recebeTag].setBackgroundResource(R.drawable.btnradius);
                QntAcertos++;//contar quantas letras foram certas
            } else {
                botao[recebeTag].setBackgroundResource(R.drawable.btnradius2);
                QntErros++;
                xErros++;
                vidas--;
                txtChance.setText(String.valueOf(vidas));
                mudarImagem();
            }
            if (contadorDeLetras >= palavra[indexPalavra].length) {
                trocarImagem.setImageResource(R.drawable.img1);
                alerta("Palavra certa", "A palavra é:  "
                        + Arrays.deepToString(palavra[indexPalavra]).replaceAll("," , "").
                        replaceAll("]","").replaceAll("\\[",""));
                proximaPalavra();
                pontuacao = pontuacao + 50;
                palavrasCertas++;
                txtPontos.setText(String.valueOf(pontuacao));

            }
            if(palavrasCertas >= 10){
                vitoria();
            }
        }
    };
    public void btnClick(View v){
        if(pontuacao < 100){
            alerta("Pontos insuficiente", "Para pedir dica você precisa de no mínimo 100 pontos");
        }
        else{
            txtDica.setText(dicas[indexPalavra]);
            pontuacao = pontuacao - 50;
            txtPontos.setText(String.valueOf(pontuacao));
        }
    }
    ///////////////Métodos///////////////////
    public void vitoria(){
        if(pontuacao>=400){
            Intent intent = new Intent(this, ActivityVitoriaEasterEgg.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, ActivityVitoria.class);
            startActivity(intent);
            finish();
        }
    }
    public void gerarIndex(){
        texto = new TextView[0];
        numeroAleatorio = palavra.length;
        indexPalavra = random.nextInt(numeroAleatorio);
        if(guardarIndex.contains(indexPalavra)){
            indexPalavra = random.nextInt(numeroAleatorio);
        }else{
            guardarIndex.add(indexPalavra);
        }
        texto = new TextView[palavra[indexPalavra].length];

    }
    public void gerarBtnTxt(){
        for(int i = 0; i < palavra[indexPalavra].length; i++){
            texto[i] = (TextView) LayoutInflater.from(this).inflate(R.layout.txt_gerar, null);
            layout.addView(texto[i]);
        }
        for(int in = 0; in <= 25; in++){
            //getResources serve para ter acesso a pasta RES.getIdentifier serve para pegar o id
            id = getResources().getIdentifier("btn" + in, "id", getPackageName());
            botao[in] = findViewById(id);
            botao[in].setTag(in);
            botao[in].setOnClickListener(btnLetra);
            botao[in].setEnabled(true);
            botao[in].setBackgroundResource(R.drawable.btnradius3);
        }
    }
    public void proximaPalavra(){
        vidas = 5;
        txtChance.setText(String.valueOf(vidas));
        contadorDeLetras = 0;
        txtDica.setText("");
        QntErros = 0;
        xErros = 0;
        gerarIndex();
        layout.removeAllViews();
        for(int i = 0; i < palavra[indexPalavra].length; i++){
            texto[i] = (TextView) LayoutInflater.from(this).inflate(R.layout.txt_gerar, null);
            layout.addView(texto[i]);
        }
        for(int in = 0; in <= 25; in++){
            botao[in].setEnabled(true);
            botao[in].setBackgroundResource(R.drawable.btnradius3);
        }

    }
    public void mudarImagem(){
        if(QntErros >= 1){
            trocarImagem.setImageResource(R.drawable.img2);
        }
        if(QntErros >= 2){
            trocarImagem.setImageResource(R.drawable.img3);
        }
        if(QntErros >= 3){
            trocarImagem.setImageResource(R.drawable.img4);
        }
        if(QntErros >= 4){
            trocarImagem.setImageResource(R.drawable.img5);
        }
        if(QntErros >= 5){
            trocarImagem.setImageResource(R.drawable.img6);
            gameOver();
        }
    }
    public void gameOver(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Letras certas: "+ QntAcertos+ "\n\nLetras erradas: " + QntErros);
        alerta.setTitle("Você perdeu!");
        alerta.setCancelable(false);
        alerta.setNegativeButton("sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alerta.setPositiveButton("Jogar novamente?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               iniciarJogo();
            }
        });
        alerta.create().show();
    }
    public void iniciarJogo(){
        layout.removeAllViews();
        gerarIndex();
        gerarBtnTxt();
        contador = 0;
        contadorDeLetras = 0;
        QntErros = 0;
        QntAcertos = 0;
        xErros = 0;
        pontuacao = 0;
        vidas = 5;
        palavrasCertas = 0;
        txtPontos.setText(String.valueOf("0"));
        trocarImagem.setImageResource(R.drawable.img);
        txtChance.setText(String.valueOf(vidas));
    }
    public void alerta (String titulo, String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(mensagem);
        alerta.setTitle(titulo);
        alerta.create().show();
    }
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}