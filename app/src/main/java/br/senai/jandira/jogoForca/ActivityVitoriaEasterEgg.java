package br.senai.jandira.jogoForca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.matheus.jogoforca.R;

public class ActivityVitoriaEasterEgg extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vitoriaeasteregg);
    }

    public void inicio(View v) {
        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        startActivity(intent);
        finish();
    }

    public void sair(View v) {
        finish();

    }
}