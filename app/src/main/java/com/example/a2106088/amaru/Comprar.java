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
import android.widget.Toast;

import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

public class Comprar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int tickets = 0;
    String usuario;
    RetrofitNetwork rfn;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        usuario = memoria.getString("username");
        rfn= new RetrofitNetwork();


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
        getMenuInflater().inflate(R.menu.comprar, menu);
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
            Intent intento=new Intent(Comprar.this,Grupo.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        // SI OPRIME EN MI PERFIL
        else if (id == R.id.nav_slideshow) {
            Intent intento=new Intent(Comprar.this,PerfilAmaru.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

        } // SI OPRIME EN COMPRAR
        else if (id == R.id.nav_manage) {

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

    public void compraVeinteTickets(View view) {
        this.tickets = 20;
    }

    public void compraDoceTickets(View view) {
        this.tickets = 12;
    }

    public void compraOchoTickets(View view) {
        this.tickets = 8;
    }

    public void compraCuatroTickets(View view) {
        this.tickets = 4;
    }

    public void compraTicketUnico(View view) {
        this.tickets = 1;
    }

    public void comprar(View view) {
        final int tickets = this.tickets;
        rfn.getuser(new RequestCallback<User>() {
            @Override
            public void onSuccess(User response) {
                user = response;
                int cupo = user.getCupo();
                user.setCupo(cupo + tickets);
                rfn.buy(new RequestCallback<User>() {
                    @Override
                    public void onSuccess(User response) {
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Compra exitosa...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error comprando tickets...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },user);
            }

            @Override
            public void onFailed(NetworkException e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error cargando usuario...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },usuario);
    }

    public void cancelarCompra(View view) {
    }
}