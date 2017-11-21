package com.example.a2106088.amaru;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
    RetrofitNetwork rfn;

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
        rfn= new RetrofitNetwork();
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==btnRegistro.getId()) {


            String username = edtUsername.getText().toString();
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

                User temp= new User();
                temp.setNombre(name);

                temp.setLastname(lastName);
                temp.setPhone(phone);
                temp.setPassword(password);
                temp.setEmail(email);
                temp.setDescription("");
                temp.setType(tipo);
                temp.setUsername(username);
                temp.setRate(0.0);
                temp.setTotalVotes(0);
                temp.setClases(new ArrayList<Clase>());
                temp.setCupo(5);
                temp.setImage("https://tctechcrunch2011.files.wordpress.com/2014/10/anonymity.jpg");

                rfn.createUser(new RequestCallback<User>() {
                    @Override
                    public void onSuccess(User response) {
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Editado Exitosamente", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "NO Editado Exitosamente", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },temp);

                SharedPreferences.Editor editor = infoUsuarios.edit();
                editor.putString("usuario", username);
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
