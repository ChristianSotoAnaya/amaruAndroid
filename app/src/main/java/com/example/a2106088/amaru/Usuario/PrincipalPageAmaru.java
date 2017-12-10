package com.example.a2106088.amaru.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.Instructor.PrincipalPageInstructor;
import com.example.a2106088.amaru.MainActivity;
import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.RegistroActivity;
import com.example.a2106088.amaru.entity.User;

import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.util.ArrayList;
import java.util.List;


public class PrincipalPageAmaru extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog progressDialog;
    SharedPreferences infousuario;
    TextView username;
    String usuario;
    ListView lista;
    String[] itemname ;
    String[] descr;
    String[] ids;
    String[] imgid;
    User user;
    RetrofitNetwork rfn;
    List<Group> grupos;
    List<Clase> clases;
    ArrayList<User> usuarios;
    List<User> todos;

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
        setContentView(R.layout.activity_prueba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog( this );
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        user= (User) memoria.getSerializable("user");
        usuario = user.getUsername();
        rfn= new RetrofitNetwork();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        clases=user.getClases();
        test();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prueba, menu);
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
            Intent ingreso = new Intent(PrincipalPageAmaru.this, MainActivity.class);
            startActivity(ingreso);        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // SI ESPICHA EN MY CLASS
        if (id == R.id.nav_camera) {
            showProgressDialog();
            rfn.getuser(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Intent intento=new Intent(PrincipalPageAmaru.this,PrincipalPageAmaru.class);
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
            Intent intento=new Intent(PrincipalPageAmaru.this,UserListas.class);
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
                    Intent intento=new Intent(PrincipalPageAmaru.this,PerfilAmaru.class);
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
            Intent intento=new Intent(PrincipalPageAmaru.this,Comprar.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

            // SI ESPICHA EN CATEGOR
        } else if (id == R.id.categories) {
            Intent intento = new Intent(PrincipalPageAmaru.this, UserCategory.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void test() {
        itemname = new String[clases.size()];
        ids=new String[clases.size()];
        descr= new String[clases.size()];
        imgid= new String[clases.size()];

        for (int i=0;i<clases.size();i++){
            itemname[i]=clases.get(i).getNombregrupo();
            descr[i]="Fecha: "+clases.get(i).getFecha()+ " Hora: "+clases.get(i).getHour()+ "\nLugar: "+clases.get(i).getPlace()+ " Num Inscritos: "+clases.get(i).getNuminscritos();
            imgid[i]="https://cdn4.iconfinder.com/data/icons/date-and-time-3/32/109-01-512.png";
            ids[i]=String.valueOf(clases.get(i).getIdgrupo());
        }

        CustomListAdapter adapter=new CustomListAdapter(PrincipalPageAmaru.this, itemname, imgid,descr);
        lista=(ListView) findViewById(R.id.listaaa2user);
        lista.setAdapter(adapter);
        rfn=new RetrofitNetwork();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                showProgressDialog();
                rfn.getGroupbyId(new RequestCallback<Group>() {
                    @Override
                    public void onSuccess(Group response) {
                        Intent grupo = new Intent(PrincipalPageAmaru.this,ActivitySelectedGroupAmaru.class);
                        Bundle memoria = new Bundle();
                        memoria.putSerializable("grupo",response);
                        memoria.putString("usuario",usuario);
                        memoria.putSerializable("user",user);
                        grupo.putExtras(memoria);
                        startActivity(grupo);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        dismissProgressDialog();
                    }
                },Integer.valueOf(ids[+position]));
            }});

    }


}
