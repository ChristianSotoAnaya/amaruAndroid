package com.example.a2106088.amaru.Instructor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.squareup.picasso.Picasso;

public class VerUsuario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView amaruimage;
    TextView amaruEmail;
    TextView amaruPhone;
    TextView amaruDescription;
    TextView amaruUserna;
    User u;
    ProgressDialog progressDialog;
    String usuario;
    RetrofitNetwork rfn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rfn=new RetrofitNetwork();
        progressDialog = new ProgressDialog( this );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        amaruimage = (ImageView) findViewById(R.id.amaruimage1);
        amaruUserna = (TextView) findViewById(R.id.nombreAmaru1);
        amaruEmail = (TextView) findViewById(R.id.amaruEmail1);
        amaruDescription = (TextView) findViewById(R.id.amaruDescription1);
        amaruPhone = (TextView) findViewById(R.id.amaruPhone1);

        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        u= (User) memoria.getSerializable("usuario");
        usuario=u.getUsername();
        System.out.println(u.getUsername());
        amaruUserna.setText("Nombre: " + u.getNombre() + " " + u.getLastname());
        Picasso.with(this).load(u.getImage()).into(amaruimage);
        amaruEmail.setText(u.getEmail());
        amaruPhone.setText(u.getPhone());
        amaruDescription.setText(u.getDescription());
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ver_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.createi) {
            Intent intento = new Intent(VerUsuario.this, CrearGrupo.class);
            Bundle datosExtra = new Bundle();

            datosExtra.putString("user", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        } else if (id == R.id.clasesi) {
            showProgressDialog();
            rfn.getuser(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Bundle memoria = new Bundle();
                    memoria.putSerializable("usuario",response);
                    Intent ingreso = new Intent(VerUsuario.this, PrincipalPageInstructor.class);
                    ingreso.putExtras(memoria);
                    startActivity(ingreso);
                    dismissProgressDialog();
                }
                @Override
                public void onFailed(NetworkException e) {
                    dismissProgressDialog();
                }
            },usuario);

        } else if (id == R.id.groupsi) {


            Intent intento = new Intent(VerUsuario.this, ActivityListaGrupos.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("instructor",usuario );
            datosExtra.putString("quitar", "");
            intento.putExtras(datosExtra);
            startActivity(intento);


        } else if (id == R.id.profilei) {
            showProgressDialog();
            rfn.getuser(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Intent intento = new Intent(VerUsuario.this, PerfilInstructor.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putSerializable("ins", response);
                    intento.putExtras(datosExtra);
                    startActivity(intento);
                    dismissProgressDialog();
                }

                @Override
                public void onFailed(NetworkException e) {
                    dismissProgressDialog();
                }
            },usuario);


        } else if (id == R.id.categoriesi) {
            Intent intento = new Intent(VerUsuario.this, Categorias.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        //Mis grupos
        else if (id == R.id.nav_send) {

            Intent intento = new Intent(VerUsuario.this, ActivityListaGrupos.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("instructor", usuario);
            datosExtra.putString("quitar",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
