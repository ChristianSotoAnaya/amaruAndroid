package com.example.a2106088.amaru.Instructor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.MainActivity;
import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.Usuario.PrincipalPageAmaru;
import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.Comment;
import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaGrupos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CustomListAdapter adapter;
    SearchView buscar;

    List<Group> gruposfiltro;
    List<Group> grupos;
    ListView lista;
    String[] itemname ;
    int[] itemid ;

    String[] descr;
    String user;

    String[] imgid;
    ProgressDialog progressDialog;
    RetrofitNetwork rfn;
    String quitar;
    String usuario;

    Bundle memoria;
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
        setContentView(R.layout.activity_lista_grupos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
       setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog( this );


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        buscar = (SearchView) findViewById(R.id.buscr);

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
                        Intent intento=new Intent(ActivityListaGrupos.this,ActivityListaGrupos.class);
                        Bundle datosExtra = new Bundle();
                        ArrayList<Group> temp= new ArrayList(gruposfiltro);
                        datosExtra.putSerializable("grupos",temp);
                        datosExtra.putString("instructor",usuario);
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

        user= memoria.getString("instructor");
        usuario=user;
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

                else{
                    for (Group g : grupos) {
                        if(g.getInstructor().equals(user)) {
                                temp.add(g);
                            }
                        }
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

                adapter=new CustomListAdapter(ActivityListaGrupos.this, itemname, imgid,descr);
                lista=(ListView) findViewById(R.id.listaaa2);
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
                                showProgressDialog();
                                rfn.getGroupbyId(new RequestCallback<Group>() {
                                @Override
                                public void onSuccess(Group response2) {
                                    Intent intento=new Intent(ActivityListaGrupos.this,ActivitySelectedGroup.class);
                                    Bundle datosExtra = new Bundle();
                                    datosExtra.putSerializable("grupo",response2);
                                    datosExtra.putSerializable("myuser",user);
                                    intento.putExtras(datosExtra);
                                    startActivity(intento);
                                    dismissProgressDialog();

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
        getMenuInflater().inflate(R.menu.activity_lista_grupos, menu);
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
            Intent ingreso = new Intent(ActivityListaGrupos.this, MainActivity.class);
            startActivity(ingreso);        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.createi) {
            Intent intento = new Intent(ActivityListaGrupos.this, CrearGrupo.class);
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
                    Intent ingreso = new Intent(ActivityListaGrupos.this, PrincipalPageInstructor.class);
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


            Intent intento = new Intent(ActivityListaGrupos.this, ActivityListaGrupos.class);
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
                    Intent intento = new Intent(ActivityListaGrupos.this, PerfilInstructor.class);
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
            Intent intento = new Intent(ActivityListaGrupos.this, Categorias.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        //Mis grupos
        else if (id == R.id.nav_send) {

            Intent intento = new Intent(ActivityListaGrupos.this, ActivityListaGrupos.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("instructor", usuario);
            datosExtra.putString("quitar",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }}



