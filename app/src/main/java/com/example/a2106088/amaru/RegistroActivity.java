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

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUsuario;
    EditText edtClave;
    EditText edtClaveRepetida;
    EditText edtNombre;
    EditText edtApellido;
    EditText edtEdad;
    EditText edtCorreo;
    Button btnRegistro;
    SharedPreferences infoUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edtUsuario=(EditText) findViewById(R.id.edtUsuario);
        edtClave=(EditText) findViewById(R.id.edtClave);
        edtClaveRepetida=(EditText) findViewById(R.id.edtClave);
        edtNombre=(EditText) findViewById(R.id.edtNombre);
        edtApellido=(EditText) findViewById(R.id.edtApellido);
        edtEdad=(EditText) findViewById(R.id.edtEdad);
        edtCorreo=(EditText) findViewById(R.id.edtCorreo);

        btnRegistro=(Button) findViewById(R.id.Registrarse);
        btnRegistro.setOnClickListener(this);

        infoUsuarios=this.getSharedPreferences("asd", Context.MODE_PRIVATE);
    }


    @Override
    public void onClick(View view) {
        String usuario = edtUsuario.getText().toString();
        String clave = edtClave.getText().toString();
        String RClave = edtClaveRepetida.getText().toString();
        String nombre = edtNombre.getText().toString();
        String apellido = edtApellido.getText().toString();
        String edad = edtEdad.getText().toString();
        String correo = edtCorreo.getText().toString();



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
            finish();
            Intent siguiente = new Intent();
            setResult(Activity.RESULT_OK,siguiente);
        }
    }
}
