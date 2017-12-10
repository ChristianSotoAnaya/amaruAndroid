package com.example.a2106088.amaru.Usuario;

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
import android.widget.ImageButton;

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.util.ArrayList;
import java.util.List;

public class UserCategory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    ImageButton imageButtonaero;
    ImageButton imageButtondance;
    ImageButton imageButtonflexi;
    ImageButton imageButtonmartial;
    ImageButton imageButtonsports;
    ImageButton imageButtonother;
    RetrofitNetwork rfn;
    List<Group> grupos;
    String usuario;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog( this );
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        rfn= new RetrofitNetwork();
        usuario=  memoria.getString("usuario");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imageButtonaero = (ImageButton) findViewById(R.id.imageButtonaerou);
        imageButtondance= (ImageButton) findViewById(R.id.imageButtondanceu);
        imageButtonflexi =(ImageButton) findViewById(R.id.imageButtonflexiu);
        imageButtonmartial =(ImageButton) findViewById(R.id.imageButtonmartialu);
        imageButtonsports =(ImageButton) findViewById(R.id.imageButtonsportsu);
        imageButtonother =(ImageButton) findViewById(R.id.imageButtonotheru);
        imageButtonaero.setOnClickListener(this);
        imageButtondance.setOnClickListener(this);
        imageButtonflexi.setOnClickListener(this);
        imageButtonmartial.setOnClickListener(this);
        imageButtonsports.setOnClickListener(this);
        imageButtonother.setOnClickListener(this);
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
        getMenuInflater().inflate(R.menu.user_category, menu);
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


        if (id == R.id.nav_camera) {
            showProgressDialog();
            rfn.getuser(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Intent intento=new Intent(UserCategory.this,PrincipalPageAmaru.class);
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
            Intent intento=new Intent(UserCategory.this,UserListas.class);
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
                    Intent intento=new Intent(UserCategory.this,PerfilAmaru.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putSerializable("userAmaru",response);
                    intento.putExtras(datosExtra);
                    startActivity(intento);
                    dismissProgressDialog();
                }

                @Override
                public void onFailed(NetworkException e) {dismissProgressDialog();
                }
            },usuario);


        } // SI ESPICHA EN COMPRAR
        else if (id == R.id.nav_manage) {
            Intent intento=new Intent(UserCategory.this,Comprar.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

            // SI ESPICHA EN CATEGOR
        } else if (id == R.id.categories) {
            Intent intento = new Intent(UserCategory.this, UserCategory.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onClick(View view) {
        String selected="";
        if (view.getId() == imageButtonaero.getId()) {
            selected="Aerobics";
        }
        else if (view.getId() == imageButtondance.getId()) {
            selected="Dance";
        }
        else if (view.getId() == imageButtonflexi.getId()) {
            selected="Flexibility";
        }
        else if (view.getId() == imageButtonmartial.getId()) {
            selected="Martial arts";
        }
        else if (view.getId() == imageButtonsports.getId()) {
            selected="Sports";
        }
        else if (view.getId() == imageButtonother.getId()) {
            selected="Others";
        }

        showProgressDialog();
        rfn.getcategory(new RequestCallback<List<Group>>() {
            @Override
            public void onSuccess(List<Group> response) {
                grupos=response;
                Intent intento=new Intent(UserCategory.this,UserListas.class);
                Bundle datosExtra = new Bundle();
                ArrayList<Group> temp= new ArrayList(grupos);
                datosExtra.putSerializable("grupos",temp);
                datosExtra.putSerializable("instructor","");
                datosExtra.putSerializable("quitar","cate");
                intento.putExtras(datosExtra);
                startActivity(intento);
                dismissProgressDialog();
            }

            @Override
            public void onFailed(NetworkException e) {

            }
        },selected);
    }
}
