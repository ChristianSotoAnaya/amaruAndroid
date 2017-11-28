package com.example.a2106088.amaru;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    String tipoUser;

    String[] imgid;

    RetrofitNetwork rfn;
    String quitar;

    Bundle memoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_grupos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        buscar = (SearchView) findViewById(R.id.buscr);

        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                Log.d("texty",String.valueOf(buscar.getQuery()));
                rfn.getGrByName(new RequestCallback<List<Group>>() {
                    @Override
                    public void onSuccess(List<Group> response) {
                        gruposfiltro=response;
                        Intent intento=new Intent(ActivityListaGrupos.this,ActivityListaGrupos.class);
                        Bundle datosExtra = new Bundle();
                        ArrayList<Group> temp= new ArrayList(gruposfiltro);
                        datosExtra.putSerializable("grupos",temp);
                        datosExtra.putSerializable("instructor","");
                        datosExtra.putSerializable("quitar","cate");
                        intento.putExtras(datosExtra);
                        startActivity(intento);
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

        user= (String) memoria.getSerializable("instructor");
        tipoUser= (String) memoria.getSerializable("tipoUsuario");
        quitar =(String) memoria.getSerializable("quitar");
        rfn = new RetrofitNetwork();

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
                                public void onSuccess(Group response2) {
                                    Intent intento=new Intent(ActivityListaGrupos.this,ActivitySelectedGroup.class);
                                    Bundle datosExtra = new Bundle();
                                    datosExtra.putSerializable("grupo",response2);
                                    intento.putExtras(datosExtra);
                                    startActivity(intento);

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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
