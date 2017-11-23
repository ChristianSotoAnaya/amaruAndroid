package com.example.a2106088.amaru;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Grupo extends AppCompatActivity implements View.OnClickListener {

    Button btnUnirseGrupo;
    Button btnVolverGrupo;
    Button btnComentariosGrupo;
    Button btnDenunciarGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        btnUnirseGrupo = (Button) findViewById(R.id.btnUnirseGrupo);
        btnVolverGrupo = (Button) findViewById(R.id.btnVolverGrupo);
        btnComentariosGrupo = (Button) findViewById(R.id.btnComentariosGrupo);
        btnDenunciarGrupo = (Button) findViewById(R.id.btnDenunciarGrupo);
        btnUnirseGrupo.setOnClickListener(this);
        btnVolverGrupo.setOnClickListener(this);
        btnComentariosGrupo.setOnClickListener(this);
        btnDenunciarGrupo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==btnComentariosGrupo.getId()){
            Intent comentarios = new Intent(Grupo.this, Comentario.class);
            startActivityForResult(comentarios,1);
        }else if (v.getId()==btnDenunciarGrupo.getId()){
            // realizar la respectiva denuncia
        }else{
            Intent inicio = new Intent();
            setResult(Activity.RESULT_CANCELED,inicio);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==Activity.RESULT_OK) {
            //Agregar comentario
        }
    }

}
