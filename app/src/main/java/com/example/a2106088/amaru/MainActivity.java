package com.example.a2106088.amaru;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Handler;
        import android.os.Looper;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.a2106088.amaru.Instructor.PrincipalPageInstructor;
        import com.example.a2106088.amaru.Usuario.PrincipalPageAmaru;
        import com.example.a2106088.amaru.entity.User;
        import com.example.a2106088.amaru.model.LoginWrapper;
        import com.example.a2106088.amaru.model.NetworkException;
        import com.example.a2106088.amaru.model.RequestCallback;
        import com.example.a2106088.amaru.model.RetrofitNetwork;
        import com.example.a2106088.amaru.model.Token;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUsuario ;
    EditText edtClave;
    Button btnIngresar;
    Button btnRegistrarse;
    SharedPreferences inforUsuario;
    RetrofitNetwork rfn;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog( this );

        edtUsuario=(EditText) findViewById(R.id.edtUsuario);
        edtClave=(EditText) findViewById(R.id.edtClave);
        btnIngresar=(Button) findViewById(R.id.btnIngreso);
        btnRegistrarse=(Button) findViewById(R.id.btnRegistro);
        btnIngresar.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
        rfn= new RetrofitNetwork();
        //inforUsuario
        inforUsuario=this.getSharedPreferences("asd", Context.MODE_PRIVATE);
        /*if (!inforUsuario.contains("usuario")){
            edtUsuario.setEnabled(false);               ----- Relacion 1
            edtClave.setEnabled(false);
            btnIngresar.setEnabled(false);
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (inforUsuario.contains("usuario")){
            edtUsuario.setEnabled(true);                ----- Relacion 1
            edtClave.setEnabled(true);
            btnIngresar.setEnabled(true);
        }*/
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == btnIngresar.getId()) {
            final String usuario = edtUsuario.getText().toString();
            String clave = edtClave.getText().toString();
            //String usuarioGuardado = inforUsuario.getString("usuario","jose");
            //String claveGuardada = inforUsuario.getString("clave","123");
            //String tipoAlmacenado= inforUsuario.getString("tipo","Instructor");
//  Print          Toast.makeText(this,"Click de Ingreso",Toast.LENGTH_LONG).show();



            /*if (edtUsuario.getText().toString().equals(usuarioGuardado) && edtClave.getText().toString().equals(claveGuardada)) {
                if (tipoAlmacenado.equals("Instructor")){
                    Bundle memoria = new Bundle();
                    memoria.putString("usuario",usuario);
                    Intent ingreso = new Intent(MainActivity.this, PrincipalPageInstructor.class);
                    ingreso.putExtras(memoria);
                    startActivity(ingreso);
                }
                else{
                    Bundle memoria = new Bundle();
                    memoria.putString("usuario",usuario);
                    Intent ingreso = new Intent(MainActivity.this, PrincipalPageAmaru.class);
                    ingreso.putExtras(memoria);
                    startActivity(ingreso);
                }


            } else {
                Toast.makeText(this,"Usuario o clave incorrectos",Toast.LENGTH_LONG).show();
            }

           */
            showProgressDialog();
            rfn.login(new LoginWrapper(usuario,clave), new RequestCallback<Token>() {
                @Override
                public void onSuccess(Token response) {
                    //SharedPreferences.Editor datosguardados;
                    //datosguardados = infousuario.edit();
                    //datosguardados.putString("token",response.getAccessToken());
                    //datosguardados.commit();

                    rfn.getuser(new RequestCallback<User>() {
                        @Override
                        public void onSuccess(User response) {
                            if(response.getType().equals("INSTRUCTOR")){
                                Bundle memoria = new Bundle();
                                memoria.putSerializable("usuario",response);
                                Intent ingreso = new Intent(MainActivity.this, PrincipalPageInstructor.class);
                                ingreso.putExtras(memoria);
                                startActivity(ingreso);
                            }
                            else{
                                Bundle memoria = new Bundle();
                                memoria.putSerializable("usuario",response);
                                Intent ingreso = new Intent(MainActivity.this, PrincipalPageAmaru.class);
                                ingreso.putExtras(memoria);
                                startActivity(ingreso);
                                dismissProgressDialog();
                            }
                        }

                        @Override
                        public void onFailed(NetworkException e) {
                            dismissProgressDialog();
                        }
                    },usuario);




                }

                @Override
                public void onFailed(NetworkException e) {
                    dismissProgressDialog();
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Usuario O Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    });                }

            });



        } else{
            Intent ingreso = new Intent(MainActivity.this, RegistroActivity.class);
            startActivityForResult(ingreso,1);
        }
    }


    protected void showProgressDialog()
    {
        runOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                progressDialog.show();
            }
        } );
    }


    protected void dismissProgressDialog()
    {
        runOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                progressDialog.dismiss();
            }
        } );
    }
}