package com.example.a2106088.amaru;

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

import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.util.ArrayList;
import java.util.List;

public class UserListas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    CustomListAdapter adapter;

    List<Group> grupos;
    ListView lista;
    String[] itemname ;
    int[] itemid ;

    String[] descr;
    String user;

    String[] imgid;

    RetrofitNetwork rfn;
    String quitar;

    Bundle memoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listas);
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        Intent anterior = getIntent();
        memoria = anterior.getExtras();

        user= (String) memoria.getSerializable("instructor");
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

                adapter=new CustomListAdapter(UserListas.this, itemname, imgid,descr);
                lista=(ListView) findViewById(R.id.listaaauser);

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
                                        Intent intento=new Intent(UserListas.this,ActivitySelectedGroup.class);
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
