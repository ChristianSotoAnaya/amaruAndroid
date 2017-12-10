package com.example.a2106088.amaru.Usuario;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

public class Comprar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog progressDialog;
    int tickets = 0;
    String usuario;
    RetrofitNetwork rfn;
    TextView row1,row2,row3,row4,row5;
    TextView row1num,row2num,row3num,row4num,row5num;
    TextView row1value,row2value,row3value,row4value,row5value;
    User user;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        progressDialog = new ProgressDialog( this );
        usuario = memoria.getString("username");
        row1num = (TextView) findViewById(R.id.row1num);
        row2num = (TextView) findViewById(R.id.row2num);
        row3num = (TextView) findViewById(R.id.row3num);
        row4num = (TextView) findViewById(R.id.row4num);
        row5num = (TextView) findViewById(R.id.row5num);
        row1value = (TextView) findViewById(R.id.row1value);
        row2value = (TextView) findViewById(R.id.row2value);
        row3value = (TextView) findViewById(R.id.row3value);
        row4value = (TextView) findViewById(R.id.row4value);
        row5value = (TextView) findViewById(R.id.row5value);
        rfn= new RetrofitNetwork();
        rfn.getuser(new RequestCallback<User>() {
            @Override
            public void onSuccess(User response) {
                user = response;
            }

            @Override
            public void onFailed(NetworkException e) {

            }
        },usuario);



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
            showProgressDialog();
            rfn.getuser(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Intent intento=new Intent(Comprar.this,PrincipalPageAmaru.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putString("instructor",usuario );
                    datosExtra.putSerializable("user",response);
                    datosExtra.putString("tipoUsuario", response.getType());
                    datosExtra.putString("quitar", "");
                    intento.putExtras(datosExtra);
                    startActivity(intento);
                    dismissProgressDialog();
                }

                @Override
                public void onFailed(NetworkException e) {

                }
            },usuario);

        }
        // SI ESPICHA EN ALL GROUPS
        else if (id == R.id.nav_gallery) {
            Intent intento=new Intent(Comprar.this,UserListas.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("user",usuario );
            datosExtra.putString("quitar", "");
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        // SI ESPICHA EN MY PROFILE
        else if (id == R.id.nav_slideshow) {
            showProgressDialog();
            rfn.getuser(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Intent intento=new Intent(Comprar.this,PerfilAmaru.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putSerializable("userAmaru",response);
                    intento.putExtras(datosExtra);
                    startActivity(intento);
                    dismissProgressDialog();
                }

                @Override
                public void onFailed(NetworkException e) {dismissProgressDialog();
                }
            },user.getUsername());


        } // SI ESPICHA EN COMPRAR
        else if (id == R.id.nav_manage) {
            Intent intento=new Intent(Comprar.this,Comprar.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

            // SI ESPICHA EN CATEGOR
        } else if (id == R.id.categories) {
            Intent intento = new Intent(Comprar.this, UserCategory.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void compraVeinteTickets(View view) {
        //normal = "#FFFFFF", "#2ECCFA"
        this.tickets = 20;
        row1num.setBackgroundResource(R.color.blanco);
        row2num.setBackgroundResource(R.color.blanco);
        row3num.setBackgroundResource(R.color.blanco);
        row4num.setBackgroundResource(R.color.blanco);
        row5num.setBackgroundResource(R.color.blue);
        row1value.setBackgroundResource(R.color.blanco);
        row2value.setBackgroundResource(R.color.blanco);
        row3value.setBackgroundResource(R.color.blanco);
        row4value.setBackgroundResource(R.color.blanco);
        row5value.setBackgroundResource(R.color.blue);
    }

    public void compraDoceTickets(View view) {
        this.tickets = 12;
        row1num.setBackgroundResource(R.color.blanco);
        row2num.setBackgroundResource(R.color.blanco);
        row3num.setBackgroundResource(R.color.blanco);
        row4num.setBackgroundResource(R.color.blue);
        row5num.setBackgroundResource(R.color.blanco);
        row1value.setBackgroundResource(R.color.blanco);
        row2value.setBackgroundResource(R.color.blanco);
        row3value.setBackgroundResource(R.color.blanco);
        row4value.setBackgroundResource(R.color.blue);
        row5value.setBackgroundResource(R.color.blanco);
    }

    public void compraOchoTickets(View view) {

        this.tickets = 8;
        row1num.setBackgroundResource(R.color.blanco);
        row2num.setBackgroundResource(R.color.blanco);
        row3num.setBackgroundResource(R.color.blue);
        row4num.setBackgroundResource(R.color.blanco);
        row5num.setBackgroundResource(R.color.blanco);
        row1value.setBackgroundResource(R.color.blanco);
        row2value.setBackgroundResource(R.color.blanco);
        row3value.setBackgroundResource(R.color.blue);
        row4value.setBackgroundResource(R.color.blanco);
        row5value.setBackgroundResource(R.color.blanco);
    }

    public void compraCuatroTickets(View view) {

        this.tickets = 4;
        row1num.setBackgroundResource(R.color.blanco);
        row2num.setBackgroundResource(R.color.blue);
        row3num.setBackgroundResource(R.color.blanco);
        row4num.setBackgroundResource(R.color.blanco);
        row5num.setBackgroundResource(R.color.blanco);
        row1value.setBackgroundResource(R.color.blanco);
        row2value.setBackgroundResource(R.color.blue);
        row3value.setBackgroundResource(R.color.blanco);
        row4value.setBackgroundResource(R.color.blanco);
        row5value.setBackgroundResource(R.color.blanco);
    }

    public void compraTicketUnico(View view) {

        this.tickets = 1;
        row1num.setBackgroundResource(R.color.blue);
        row2num.setBackgroundResource(R.color.blanco);
        row3num.setBackgroundResource(R.color.blanco);
        row4num.setBackgroundResource(R.color.blanco);
        row5num.setBackgroundResource(R.color.blanco);
        row1value.setBackgroundResource(R.color.blue);
        row2value.setBackgroundResource(R.color.blanco);
        row3value.setBackgroundResource(R.color.blanco);
        row4value.setBackgroundResource(R.color.blanco);
        row5value.setBackgroundResource(R.color.blanco);
    }

    public void comprar(View view) {
        final int tickets = this.tickets;
        int cupo = user.getCupo();
        System.out.println("Cupo: " + cupo);
        System.out.println("Tickets: " + tickets);
        user.setCupo(tickets);
        showProgressDialog();
        rfn.buy(new RequestCallback<User>() {
            @Override
            public void onSuccess(User response) {
                dismissProgressDialog();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                            public void run() {Toast.makeText(getApplicationContext(), "Compra exitosa...", Toast.LENGTH_SHORT).show();}
                });
            }

            @Override
            public void onFailed(NetworkException e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                            public void run() {Toast.makeText(getApplicationContext(), "Error comprando tickets...", Toast.LENGTH_SHORT).show();}
                });
            }
        },user);
    }

    public void cancelarCompra(View view) {
        finish();
    }
}
