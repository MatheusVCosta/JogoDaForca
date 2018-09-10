package br.senai.jandira.jogoForca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.matheus.jogoforca.R;

public class InicioActivity extends Activity {
    ImageView imagem;
    Animation shake;
    Animation fadein;
    int img = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        imagem = findViewById(R.id.img);

        fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        imagem.startAnimation(fadein);

        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img++;
                imagem.startAnimation(shake);
                if(img >= 1){
                    imagem.setImageResource(R.drawable.neutro);
                }
                if(img >= 2){
                    imagem.setImageResource(R.drawable.feliz);
                }
                if(img >= 3){
                    imagem.setImageResource(R.drawable.img);
                }
                if(img >= 4){
                    imagem.setImageResource(R.drawable.neutro);
                }
                if(img >= 5){
                    imagem.setImageResource(R.drawable.assustado);
                }
                if(img >= 6){
                    imagem.setImageResource(R.drawable.bravinho);
                }
                if(img >= 7){
                    imagem.setImageResource(R.drawable.dormindo);
                    img = 0;
                }
            }
        });
    }


    public void iniciar(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

        Toast.makeText(this,  "Que os jogos comecem!", Toast.LENGTH_SHORT).show();
    }
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
