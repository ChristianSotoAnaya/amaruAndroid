package com.example.a2106088.amaru.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.SearchView;

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.util.ArrayList;
import java.util.List;

public class UserListas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    CustomListAdapter adapter;
    ProgressDialog progressDialog;
    List<Group> grupos;
    List<Group> gruposfiltro;

    ListView lista;
    String[] itemname ;
    int[] itemid ;

    String[] descr;
    String user;
    String usuario;

    String[] imgid;

    RetrofitNetwork rfn;
    String quitar;

    Bundle memoria;

    SearchView buscar;

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
        setContentView(R.layout.activity_user_listas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog( this );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buscar = (SearchView) findViewById(R.id.buscar);

        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                Log.d("texty",String.valueOf(buscar.getQuery()));
                showProgressDialog();
                rfn.getGrByName(new RequestCallback<List<Group>>() {
                    @Override
                    public void onSuccess(List<Group> response) {
                        gruposfiltro=response;
                        Intent intento=new Intent(UserListas.this,UserListas.class);
                        Bundle datosExtra = new Bundle();
                        ArrayList<Group> temp= new ArrayList(gruposfiltro);
                        datosExtra.putSerializable("grupos",temp);
                        datosExtra.putString("user",usuario);
                        datosExtra.putString("quitar","cate");
                        intento.putExtras(datosExtra);
                        startActivity(intento);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailed(NetworkException e) {

                    }
                },String.valueOf(buscar.getQuery()));


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        Intent anterior = getIntent();
        memoria = anterior.getExtras();

        usuario =  memoria.getString("user");
        quitar =memoria.getString("quitar");
        rfn = new RetrofitNetwork();
        showProgressDialog();
        rfn.getallgroups(new RequestCallback<List<Group>>() {
            @Override
            public void onSuccess(List<Group> response) {
                grupos = response;
                ArrayList<Group> temp= new ArrayList<Group>();
                if (quitar.equals("")){
                    temp=new ArrayList(grupos);
                }
                else if(quitar.equals("cate")){
                    ArrayList<Group> groupss=(ArrayList<Group>) memoria.getSerializable("grupos");
                    temp=new ArrayList(groupss);
                }


                itemname = new String[temp.size()];
                itemid  = new int[temp.size()];
                descr= new String[temp.size()]; ;


                imgid = new String[temp.size()]; ;
                for (int i=0;i<temp.size();i++){
                    itemname[i]=temp.get(i).getNombre();
                    descr[i]="Instructor: "+temp.get(i).getInstructor();
                    imgid[i]=temp.get(i).getImage();
                    itemid[i]=(int) temp.get(i).getId();
                }

                adapter=new CustomListAdapter(UserListas.this, itemname, imgid,descr);
                lista=(ListView) findViewById(R.id.listaaauser);
                dismissProgressDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lista.setAdapter(adapter);
                        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // TODO Auto-generated method stub
                                rfn.getGroupbyId(new RequestCallback<Group>() {
                                    @Override
                                    public void onSuccess(final Group response2) {
                                        rfn.getuser(new RequestCallback<User>() {
                                            @Override
                                            public void onSuccess(User response) {
                                                Intent intento=new Intent(UserListas.this,ActivitySelectedGroupAmaru.class);
                                                Bundle datosExtra = new Bundle();
                                                datosExtra.putSerializable("grupo",response2);
                                                datosExtra.putString("username",usuario);
                                                datosExtra.putSerializable("user",response);
                                                intento.putExtras(datosExtra);
                                                startActivity(intento);
                                            }

                                            @Override
                                            public void onFailed(NetworkException e) {

                                            }
                                        },usuario);



                                    }
                                    @Override
                                    public void onFailed(NetworkException e) {
                                    }
                                },itemid[+position]);}
                        });
                    }
                });




            }

            @Override
            public void onFailed(NetworkException e) {
                dismissProgressDialog();
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
        getMenuInflater().inflate(R.menu.user_listas, menu);
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
                    Intent intento=new Intent(UserListas.this,PrincipalPageAmaru.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putSerializable("user",response);
                    intento.putExtras(datosExtra);
                    dismissProgressDialog();
                    startActivity(intento);

                }

                @Override
                public void onFailed(NetworkException e) {

                }
            },usuario);

        }
        // SI ESPICHA EN ALL GROUPS
        else if (id == R.id.nav_gallery) {
            Intent intento=new Intent(UserListas.this,UserListas.class);
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
                    Intent intento=new Intent(UserListas.this,PerfilAmaru.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putSerializable("userAmaru",response);
                    intento.putExtras(datosExtra);
                    dismissProgressDialog();
                    startActivity(intento);

                }

                @Override
                public void onFailed(NetworkException e) {dismissProgressDialog();
                }
            },usuario);


        } // SI ESPICHA EN COMPRAR
        else if (id == R.id.nav_manage) {
            Intent intento=new Intent(UserListas.this,Comprar.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

            // SI ESPICHA EN CATEGOR
        } else if (id == R.id.categories) {
            Intent intento = new Intent(UserListas.this, UserCategory.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
