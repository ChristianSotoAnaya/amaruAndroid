package com.example.a2106088.amaru;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.squareup.picasso.Picasso;

public class PerfilAmaru extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    RetrofitNetwork rfn;
    ImageView amaruimage;
    EditText amaruEmail;
    EditText amaruPhone;
    EditText amaruDescription;
    TextView amaruuserna;
    TextView amarucupo;
    Button buttoneditimage;
    Button buttoneditmail;
    Button buttoneditphone;
    User u;
    Button buttoneditdescription;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amaru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        rfn= new RetrofitNetwork();
        amaruimage = (ImageView) findViewById(R.id.amaruimage);
        amaruEmail = (EditText) findViewById(R.id.amaruEmail);
        amaruPhone= (EditText) findViewById(R.id.amaruPhone);
        amaruDescription= (EditText) findViewById(R.id.amaruDescription);
        amaruuserna = (TextView) findViewById(R.id.nombreAmaru);
        amarucupo = (TextView) findViewById(R.id.cupo);
        buttoneditimage = (Button) findViewById(R.id.buttonImage);
        buttoneditmail = (Button) findViewById(R.id.buttonEmail);
        buttoneditphone = (Button) findViewById(R.id.buttonPhone);
        buttoneditdescription = (Button) findViewById(R.id.buttonDescription);
        buttoneditimage.setOnClickListener(this);
        buttoneditmail.setOnClickListener(this);
        buttoneditphone.setOnClickListener(this);
        buttoneditdescription.setOnClickListener(this);
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        u = (User) memoria.getSerializable("userAmaru");
        usuario = u.getUsername();
        amaruuserna.setText("Username: "+usuario);
        Picasso.with(getApplicationContext()).load(u.getImage()).into(amaruimage);
        amaruEmail.setText(u.getEmail());
        amaruPhone.setText(u.getPhone());
        amaruDescription.setText(u.getDescription());
        amarucupo.setText(String.valueOf(u.getCupo()));
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
        getMenuInflater().inflate(R.menu.perfil_amaru, menu);
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

        // SI OPRIME EN MIS CLASES
        if (id == R.id.nav_camera) {

        }
        // SI OPRIME EN TODOS LOS GRUPOS
        else if (id == R.id.nav_gallery) {
            Intent intento=new Intent(PerfilAmaru.this,Grupo.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        // SI OPRIME EN MI PERFIL
        else if (id == R.id.nav_slideshow) {


        } // SI OPRIME EN COMPRAR
        else if (id == R.id.nav_manage) {
            Intent intento=new Intent(PerfilAmaru.this,Comprar.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

            // SI OPRIME EN CATEGORIAS
        } else if (id == R.id.categories) {
            Toast.makeText(this, "nav_share", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "nav_send", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttoneditimage.getId()) {

        }
        else if (view.getId() == buttoneditmail.getId()) {
            User temp= new User();
            temp.setUsername(u.getUsername());
            temp.setEmail(amaruEmail.getText().toString());
            rfn.editemail(new RequestCallback<User>() {
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
                            Toast.makeText(getApplicationContext(), "Ocurrió un problema editando Email...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            },temp);
        }
        else if (view.getId() == buttoneditphone.getId()) {
            User temp= new User();
            temp.setUsername(u.getUsername());
            temp.setPhone(amaruPhone.getText().toString());
            rfn.editphone(new RequestCallback<User>() {
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
                            Toast.makeText(getApplicationContext(), "Ocurrió un problema editando telefono...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            },temp);
        }
        else if (view.getId() == buttoneditdescription.getId()) {
            User temp= new User();
            temp.setUsername(u.getUsername());
            temp.setDescription(amaruDescription.getText().toString());
            rfn.editdescription(new RequestCallback<User>() {
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
                            Toast.makeText(getApplicationContext(), "Ocurrió un problema editando la descripción...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            },temp);
        }
    }
}
