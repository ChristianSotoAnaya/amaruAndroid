package com.example.a2106088.amaru;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUsername;
    EditText edtPassword;
    EditText edtConfirmPassword;
    EditText edtName;
    EditText edtLastName;
    EditText edtPhone;
    EditText edtEmail;
    Button btnRegistro;
    SharedPreferences infoUsuarios;
    Spinner spinner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edtUsername=(EditText) findViewById(R.id.edtUsername);
        edtPassword=(EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword=(EditText) findViewById(R.id.edtConfirmPassword);
        edtName=(EditText) findViewById(R.id.edtName);
        edtLastName=(EditText) findViewById(R.id.edtLastName);
        edtPhone=(EditText) findViewById(R.id.edtPhone);
        edtEmail=(EditText) findViewById(R.id.edtEmail);

        btnRegistro=(Button) findViewById(R.id.Registrarse);
        btnRegistro.setOnClickListener(this);
        spinner=(Spinner) findViewById(R.id.spinner);
        String[] letra = {"Instructor","Amaru"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));
        infoUsuarios=this.getSharedPreferences("asd", Context.MODE_PRIVATE);
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==btnRegistro.getId()) {


            String user = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            String confirmPassword = edtConfirmPassword.getText().toString();
            String name = edtName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String phone = edtPhone.getText().toString();
            String email = edtEmail.getText().toString();
            String tipo = spinner.getSelectedItem().toString();


            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "No coinciden las claves", Toast.LENGTH_LONG).show();
            } else {

                SharedPreferences.Editor editor = infoUsuarios.edit();
                editor.putString("usuario", user);
                editor.putString("clave", password);
                editor.putString("nombre", name);
                editor.putString("apellido", lastName);
                editor.putString("phone", phone);
                editor.putString("correo", email);
                editor.putString("tipo", tipo);
                editor.commit();
                finish();
                Intent siguiente = new Intent();
                setResult(Activity.RESULT_OK, siguiente);
            }
        }
    }
}
