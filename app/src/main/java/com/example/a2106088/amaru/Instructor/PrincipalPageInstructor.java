package com.example.a2106088.amaru.Instructor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.a2106088.amaru.MainActivity;
import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.Usuario.PrincipalPageAmaru;
import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PrincipalPageInstructor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    ProgressDialog progressDialog;


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
        setContentView(R.layout.activity_principal_page_instructor);
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


        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        user= (User) memoria.getSerializable("usuario");
        usuario = user.getUsername();
        clases=user.getClases();

        test();

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

        CustomListAdapter adapter=new CustomListAdapter(PrincipalPageInstructor.this, itemname, imgid,descr);
        lista=(ListView) findViewById(R.id.listaaa3);
        lista.setAdapter(adapter);
        rfn=new RetrofitNetwork();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                Log.d("presss",String.valueOf(+position));
                showProgressDialog();
                rfn.getUsers(new RequestCallback<List<User>>() {
                    @Override
                    public void onSuccess(List<User> response) {
                        todos=response;

                        rfn.getGroupbyId(new RequestCallback<Group>() {
                            @Override
                            public void onSuccess(Group response2) {
                                Clase temp= clases.get(+position);
                                ArrayList<String> usernames=new ArrayList<String>();
                                usuarios= new ArrayList<User>();
                                for (Clase cl: response2.getClases()){
                                    if(temp.equals1(cl) ){
                                        User uo= buscar(todos,cl.getUsuario());
                                        if (!usernames.contains(uo.getUsername())){
                                            usuarios.add(uo);
                                            usernames.add(uo.getUsername());
                                        }


                                    }
                                }
                                Intent intento=new Intent(PrincipalPageInstructor.this,AlmunosInscritos.class);
                                Bundle datosExtra = new Bundle();
                                datosExtra.putString("user",usuario);
                                datosExtra.putSerializable("usuarios",usuarios);
                                intento.putExtras(datosExtra);
                                startActivity(intento);
                                dismissProgressDialog();
                            }

                            @Override
                            public void onFailed(NetworkException e) {

                            }
                        },Integer.valueOf(ids[+position]));
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        dismissProgressDialog();
                    }
                });





                //String Slecteditem= itemname[+position];
                //Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();}
        }});

    }

    public User buscar(List<User> todos,String username ){
        User temp=null;
        for (User y: todos){
            if (y.getUsername().equals(username)){
                temp=y;
                break;
            }
        }
        return temp;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal_page_instructor, menu);
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
            Intent ingreso = new Intent(PrincipalPageInstructor.this, MainActivity.class);
            startActivity(ingreso);        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.createi) {
            Intent intento = new Intent(PrincipalPageInstructor.this, CrearGrupo.class);
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
                        Intent ingreso = new Intent(PrincipalPageInstructor.this, PrincipalPageInstructor.class);
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


                    Intent intento = new Intent(PrincipalPageInstructor.this, ActivityListaGrupos.class);
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
                    Intent intento = new Intent(PrincipalPageInstructor.this, PerfilInstructor.class);
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
            },user.getUsername());


        } else if (id == R.id.categoriesi) {
                    Intent intento = new Intent(PrincipalPageInstructor.this, Categorias.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putString("usuario", usuario);
                    intento.putExtras(datosExtra);
                    startActivity(intento);


        }
        //Mis grupos
        else if (id == R.id.nav_send) {

                    Intent intento = new Intent(PrincipalPageInstructor.this, ActivityListaGrupos.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putString("instructor", user.getUsername());
                    datosExtra.putString("quitar",user.getUsername());
                    intento.putExtras(datosExtra);
                    startActivity(intento);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
