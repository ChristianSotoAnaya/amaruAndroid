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
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a2106088.amaru.MainActivity;
import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.Usuario.PrincipalPageAmaru;
import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.util.ArrayList;

public class AlmunosInscritos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<User> users;
    ListView lista;
    String[] itemname ;
    ProgressDialog progressDialog;

    String[] descr;
    String usuario;
    RetrofitNetwork rfn;
    String[] imgid;

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
        setContentView(R.layout.activity_almunos_inscritos);
        progressDialog = new ProgressDialog( this );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        usuario= memoria.getString("user");
        users= (ArrayList<User>) memoria.getSerializable("usuarios");

        rfn= new RetrofitNetwork();
        itemname = new String[users.size()];

        descr= new String[users.size()]; ;


        imgid = new String[users.size()]; ;
        for (int i=0;i<users.size();i++){

            itemname[i]=users.get(i).getNombre()+" "+ users.get(i).getLastname();
            descr[i]=users.get(i).getUsername();
            imgid[i]=users.get(i).getImage();
        }


        CustomListAdapter adapter=new CustomListAdapter(AlmunosInscritos.this, descr, imgid, itemname);
        lista=(ListView) findViewById(R.id.listaaa4);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String seleccionado = (String) lista.getItemAtPosition(position);
                System.out.println(seleccionado);
                showProgressDialog();
                rfn.getuser(new RequestCallback<User>() {
                    @Override
                    public void onSuccess(User response) {
                        Intent intento=new Intent(AlmunosInscritos.this,VerUsuario.class);
                        Bundle datosExtra = new Bundle();
                        datosExtra.putSerializable("usuario",response);
                        intento.putExtras(datosExtra);
                        startActivity(intento);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailed(NetworkException e) {

                    }
                },seleccionado);
            }
        });


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
        getMenuInflater().inflate(R.menu.almunos_inscritos, menu);
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
            Intent ingreso = new Intent(AlmunosInscritos.this, MainActivity.class);
            startActivity(ingreso);        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.createi) {
            Intent intento = new Intent(AlmunosInscritos.this, CrearGrupo.class);
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
                    Intent ingreso = new Intent(AlmunosInscritos.this, PrincipalPageInstructor.class);
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


            Intent intento = new Intent(AlmunosInscritos.this, ActivityListaGrupos.class);
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
                    Intent intento = new Intent(AlmunosInscritos.this, PerfilInstructor.class);
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
            Intent intento = new Intent(AlmunosInscritos.this, Categorias.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        //Mis grupos
        else if (id == R.id.nav_send) {

            Intent intento = new Intent(AlmunosInscritos.this, ActivityListaGrupos.class);
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
