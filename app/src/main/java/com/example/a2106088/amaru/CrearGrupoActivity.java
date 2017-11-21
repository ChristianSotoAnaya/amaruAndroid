package com.example.a2106088.amaru;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CrearGrupoActivity extends AppCompatActivity implements View.OnClickListener {

    Button crearGrupo;
    Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo_activity);
        crearGrupo = (Button) findViewById(R.id.btnCrearGrupo);
        cancelar = (Button) findViewById(R.id.btnCancelarCrearGrupo);
        crearGrupo.setOnClickListener(this);
        cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent inicio = new Intent();
        setResult(Activity.RESULT_CANCELED,inicio);
        finish();
    }
}
