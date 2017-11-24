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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.Comment;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.util.ArrayList;
import java.util.List;

public class CrearGrupo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText edtNombreCrearGrupo;
    EditText edtDescripcionCrearGrupo;
    EditText edtlugar;
    EditText edtfecha;
    EditText edthora;
    Spinner spinner ;
    TableLayout table;
    long idClase = 0;
    long idGrupo = 0;
    ArrayList<Clase> clases = new ArrayList<Clase>();
    User u;
    RetrofitNetwork rfn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtNombreCrearGrupo=(EditText) findViewById(R.id.edtNombreCrearGrupo);
        edtDescripcionCrearGrupo=(EditText) findViewById(R.id.edtDescripcionCrearGrupo);
        edtlugar = (EditText) findViewById(R.id.edtlugar);
        edtfecha = (EditText) findViewById(R.id.edtfecha);
        edthora = (EditText) findViewById(R.id.edthora);
        table = (TableLayout) findViewById(R.id.table);
        spinner=(Spinner) findViewById(R.id.spinner2);
        rfn= new RetrofitNetwork();
        rfn.getallgroups(new RequestCallback<List<Group>>(){
            @Override
            public void onSuccess(List<Group> response) {
                idGrupo = response.size();
            }

            @Override
            public void onFailed(NetworkException e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error al cargar grupos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        u= (User) memoria.getSerializable("user");
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
        getMenuInflater().inflate(R.menu.crear_grupo, menu);
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

        if (id == R.id.createi3) {

        } else if (id == R.id.clasesi3) {
            Intent intento=new Intent(CrearGrupo.this,PrincipalPageInstructor.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putSerializable("usuario",u);
            intento.putExtras(datosExtra);
            startActivity(intento);
        } else if (id == R.id.groupsi3) {

        } else if (id == R.id.profilei3) {
            Intent intento=new Intent(CrearGrupo.this,PerfilInstructor.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putSerializable("ins",u);
            intento.putExtras(datosExtra);
            startActivity(intento);

        } else if (id == R.id.categoriesi) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void nuevaClase(View view){
        /*TableRow tableRow = new TableRow(this);
        table.addView(tableRow);
        EditText edtlugartxt = new EditText(this);
        edtlugartxt.setText(edtlugar.getText().toString());
        tableRow.addView(edtlugartxt);*/
        Clase clase = new Clase(idGrupo,edtfecha.getText().toString(),edthora.getText().toString(),edtlugar.getText().toString(),idClase,edtNombreCrearGrupo.getText().toString(),0, u.getUsername());
        System.out.println(u.getUsername());
        idClase+=1;
        clases.add(clase);
        TableRow tr_head = new TableRow(this);
       // part1
        tr_head.setLayoutParams(new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT));
        TextView label_lugar = new TextView(this);
        label_lugar.setText(edtlugar.getText().toString());
        // part2
        label_lugar.setPadding(5, 5, 5, 5);
        tr_head.addView(label_lugar);// add the column to the table row here

        TextView label_fecha = new TextView(this);    // part3
        label_fecha.setText(edtfecha.getText().toString());
        label_fecha.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_fecha); // add the column to the table row here

        TextView label_hora = new TextView(this);    // part3
        label_hora.setText(edthora.getText().toString());
        label_hora.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_hora); // add the column to the table row here

        table.addView(tr_head,new TableLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT));
    }

    public void crearGrupo(View view) {
        String categoria = spinner.getSelectedItem().toString();
        String nombre =  edtNombreCrearGrupo.getText().toString();
        String descripcion = edtDescripcionCrearGrupo.getText().toString();
        Group nuevo = new Group(idGrupo, nombre, u.getUsername(), null, descripcion, categoria, 0.0, 0,"http://cdn.pcwallart.com/images/ubuntu-default-wallpaper-wallpaper-3.jpg",clases);
        rfn.createGroup(new RequestCallback<Group>() {
            @Override
            public void onSuccess(Group response) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Grupo creado exitosamente", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailed(NetworkException e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error en la creación del grupo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },nuevo);
    }

    public void cancelar(View view) {
        Intent intento=new Intent(CrearGrupo.this,PrincipalPageInstructor.class);
        Bundle datosExtra = new Bundle();
        datosExtra.putSerializable("usuario",u);
        intento.putExtras(datosExtra);
        startActivity(intento);
    }
}
