package com.example.a2106088.amaru;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUsuarioPerfil;
    EditText edtClavePerfil;
    EditText edtClaveRepetidaPerfil;
    EditText edtNombrePerfil;
    EditText edtApellidoPerfil;
    EditText edtEdadPerfil;
    EditText edtCorreoPerfil;
    Button btnGuardarPerfil;
    Button btnFotoPerfil;
    Button btnCancelarPerfil;
    SharedPreferences infoUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        edtUsuarioPerfil=(EditText) findViewById(R.id.edtUsuarioPerfil);
        edtClavePerfil=(EditText) findViewById(R.id.edtClavePerfil);
        edtClaveRepetidaPerfil=(EditText) findViewById(R.id.edtClaveRepetidaPerfil);
        edtNombrePerfil=(EditText) findViewById(R.id.edtNombrePerfil);
        edtApellidoPerfil=(EditText) findViewById(R.id.edtApellidoPerfil);
        edtEdadPerfil=(EditText) findViewById(R.id.edtEdadPerfil);
        edtCorreoPerfil=(EditText) findViewById(R.id.edtCorreoPerfil);

        btnGuardarPerfil=(Button) findViewById(R.id.btnGuardarPerfil);
        btnCancelarPerfil=(Button) findViewById(R.id.btnCancelarPerfil);
        btnFotoPerfil=(Button) findViewById(R.id.btnFotoPerfil);
        btnGuardarPerfil.setOnClickListener(this);
        btnCancelarPerfil.setOnClickListener(this);
        btnFotoPerfil.setOnClickListener(this);

        infoUsuarios=this.getSharedPreferences("asd", Context.MODE_PRIVATE);

        edtUsuarioPerfil.setText(infoUsuarios.getString("usuario","void"));
        edtClavePerfil.setText(infoUsuarios.getString("clave","void"));
        edtClaveRepetidaPerfil.setText(infoUsuarios.getString("clave","void"));
        edtNombrePerfil.setText(infoUsuarios.getString("nombre","void"));
        edtApellidoPerfil.setText(infoUsuarios.getString("apellido","void"));
        edtEdadPerfil.setText(infoUsuarios.getString("edad","void"));
        edtCorreoPerfil.setText(infoUsuarios.getString("correo","void"));


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnGuardarPerfil.getId()){
            String usuario = edtUsuarioPerfil.getText().toString();
            String clave = edtClavePerfil.getText().toString();
            String RClave = edtClaveRepetidaPerfil.getText().toString();
            String nombre = edtNombrePerfil.getText().toString();
            String apellido = edtApellidoPerfil.getText().toString();
            String edad = edtEdadPerfil.getText().toString();
            String correo = edtCorreoPerfil.getText().toString();

            if (!clave.equals(RClave)){
                Toast.makeText(this,"No coinciden las claves",Toast.LENGTH_LONG).show();
            }else{
                SharedPreferences.Editor editor = infoUsuarios.edit();
                editor.putString("usuario",usuario);
                editor.putString("clave",clave);
                editor.putString("nombre",nombre);
                editor.putString("apellido",apellido);
                editor.putString("edad",edad);
                editor.putString("correo",correo);
                editor.commit();
                Intent siguiente = new Intent();
                Bundle memoria = new Bundle();
                memoria.putString("usuario",usuario);
                siguiente.putExtras(memoria);
                setResult(Activity.RESULT_OK,siguiente);
                finish();
            }
        }else if (v.getId()==btnCancelarPerfil.getId()){
            Intent inicio = new Intent();
            //Toast.makeText(this,RESULT_CANCELED, Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_CANCELED,inicio);
            finish();
        }
    }
}
