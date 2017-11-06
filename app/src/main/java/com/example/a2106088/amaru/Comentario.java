package com.example.a2106088.amaru;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Comentario extends AppCompatActivity implements View.OnClickListener{

    Button btnVolverComentario;
    Button btnComentarComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
        btnVolverComentario = (Button) findViewById(R.id.btnVolverComentario);
        btnComentarComentario  = (Button) findViewById(R.id.btnComentarComentario);
        btnVolverComentario.setOnClickListener(this);
        btnComentarComentario.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==btnVolverComentario.getId()){
            Intent grupo = new Intent();
            setResult(Activity.RESULT_CANCELED,grupo);
            finish();
        }
    }
}
