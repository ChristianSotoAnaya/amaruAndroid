package com.example.a2106088.amaru;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUsuario ;
    EditText edtClave;
    Button btnIngresar;
    Button btnRegistrarse;
    TextView mensajeError;
    SharedPreferences inforUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsuario=(EditText) findViewById(R.id.edtUsuario);
        edtClave=(EditText) findViewById(R.id.edtClave);
        btnIngresar=(Button) findViewById(R.id.btnIngreso);
        btnRegistrarse=(Button) findViewById(R.id.btnRegistro);
        mensajeError=(TextView) findViewById(R.id.texvError);
        btnIngresar.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
        //inforUsuario
        inforUsuario=this.getSharedPreferences("asd", Context.MODE_PRIVATE);
        if (!inforUsuario.contains("usuario")){
            edtUsuario.setEnabled(false);
            edtClave.setEnabled(false);
            btnIngresar.setEnabled(false);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (inforUsuario.contains("usuario")){
            edtUsuario.setEnabled(true);
            edtClave.setEnabled(true);
            btnIngresar.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == btnIngresar.getId()) {
            String usuario = edtUsuario.getText().toString();
            String clave = edtClave.getText().toString();
            String usuarioGuardado = inforUsuario.getString("usuario","jose");
            String claveGuardada = inforUsuario.getString("clave","123");
//  Print          Toast.makeText(this,"Click de Ingreso",Toast.LENGTH_LONG).show();
            if (edtUsuario.getText().toString().equals(usuarioGuardado) && edtClave.getText().toString().equals(claveGuardada)) {
                mensajeError.setText("");
                Bundle memoria = new Bundle();
                memoria.putString("usuario",usuario);

                Intent ingreso = new Intent(MainActivity.this, InicioActivity.class);
                ingreso.putExtras(memoria);
                startActivity(ingreso);
            } else {
                mensajeError.setText("Usuario y Clave invalidos");
            }
        } else{
            Intent ingreso = new Intent(MainActivity.this, RegistroActivity.class);
            startActivityForResult(ingreso,1);
        }
    }

}