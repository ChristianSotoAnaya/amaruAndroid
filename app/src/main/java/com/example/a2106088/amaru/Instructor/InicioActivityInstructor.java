package com.example.a2106088.amaru.Instructor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.Usuario.Grupo;

public class InicioActivityInstructor extends AppCompatActivity implements View.OnClickListener{

    TextView txtUsuario;
    ImageButton btnPerfil;
    Button btnCrearGrupo;
    Button btnKarateInicio;
    SharedPreferences infoUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_instructor);
        txtUsuario = (TextView) findViewById(R.id.txtNombre);
        btnPerfil = (ImageButton)  findViewById(R.id.btnPerfil);
        btnCrearGrupo = (Button) findViewById(R.id.btnCrearGrupo);
        btnKarateInicio = (Button) findViewById(R.id.btnKarateInicio);
        btnPerfil.setOnClickListener(this);
        btnCrearGrupo.setOnClickListener(this);
        btnKarateInicio.setOnClickListener(this);


        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        txtUsuario.setText(memoria.getString("usuario"));

        // falta programar los botones de perfil, grupo y crear grupo

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==btnPerfil.getId()){
            Intent perfil = new Intent(InicioActivityInstructor.this, PerfilInstructor.class);
            startActivityForResult(perfil,1);

        }else if(v.getId()==btnCrearGrupo.getId()){
            Intent perfil = new Intent(InicioActivityInstructor.this, CrearGrupo.class);
            startActivityForResult(perfil,2);

        }else if (v.getId()==btnKarateInicio.getId()){
            Intent grupo = new Intent(InicioActivityInstructor.this, Grupo.class);
            startActivityForResult(grupo,3);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==Activity.RESULT_OK) {
            Bundle memoria = data.getExtras();
            txtUsuario.setText(memoria.getString("usuario"));
        }
    }

}
