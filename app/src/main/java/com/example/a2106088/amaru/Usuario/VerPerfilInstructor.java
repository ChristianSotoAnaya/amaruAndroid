package com.example.a2106088.amaru.Usuario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.Instructor.CrearGrupo;
import com.example.a2106088.amaru.Instructor.PrincipalPageInstructor;
import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class VerPerfilInstructor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    RetrofitNetwork rfn;
    ImageView instructorimage;
    TextView instrcutorEmail;
    TextView instrcutorPhone;
    TextView instructorDescription;
    TextView instructorNameA,instructorLastNameA;
    User u;
    User logedUser;
    RatingBar ratingBar;
    RatingBar ratingBarUser;
    TextView rateNumber;
    Button btnRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil_instructor);
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
        instructorNameA = (TextView) findViewById(R.id.txtInstructorNameA);
        instructorLastNameA = (TextView) findViewById(R.id.txtInstructorLastNameA);
        instrcutorEmail = (TextView) findViewById(R.id.instrcutorEmail1);
        instrcutorPhone= (TextView) findViewById(R.id.instrcutorPhone1);
        instructorDescription= (TextView) findViewById(R.id.instructorDescription1);
        instructorimage = (ImageView) findViewById(R.id.imageUser);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        rateNumber= (TextView) findViewById(R.id.rateNumber);
        btnRate = (Button) findViewById(R.id.btnRate);


        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        u= (User) memoria.getSerializable("ins");
        logedUser= (User) memoria.getSerializable("usuario");
        instructorNameA.setText(String.valueOf(u.getNombre())+" ");
        instructorLastNameA.setText(String.valueOf(u.getLastname()));

        Picasso.with(this).load(u.getImage()).into(instructorimage);
        instrcutorEmail.setText(u.getEmail());
        instrcutorPhone.setText(u.getPhone());
        instructorDescription.setText(u.getDescription());



        ratingBar.setEnabled(false);
        rateNumber.setEnabled(false);
        btnRate.setEnabled(false);
        ratingBar.setVisibility(View.GONE);
        rateNumber.setVisibility(View.GONE);
        btnRate.setVisibility(View.GONE);
        for (Clase clase : u.getClases()){
            for (Clase claseUsuario : logedUser.getClases()){
                if (clase.getIdgrupo()==claseUsuario.getIdgrupo()){
                    ratingBar.setEnabled(true);
                    rateNumber.setEnabled(true);
                    btnRate.setEnabled(true);
                    ratingBar.setVisibility(View.VISIBLE);
                    rateNumber.setVisibility(View.VISIBLE);
                    btnRate.setVisibility(View.VISIBLE);
                    break;
                }
            }

        }

        // Current rating
        ratingBarUser = (RatingBar) findViewById(R.id.ratingBarUser);
        ratingBarUser.setNumStars(5);
        ratingBarUser.setStepSize(1);
        ratingBar.isIndicator();
        ratingBarUser.setRating(Float.parseFloat(String.valueOf((u.getRate()))));


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateNumber.setText(String.valueOf((int) rating));
            }
        });


        rfn= new RetrofitNetwork();

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
        getMenuInflater().inflate(R.menu.ver_perfil_instructor, menu);
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

        if (id == R.id.createi2) {
            String usuario="";
            Intent intento=new Intent(VerPerfilInstructor.this,CrearGrupo.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        } else if (id == R.id.clasesi2) {
            Intent intento=new Intent(VerPerfilInstructor.this,PrincipalPageInstructor.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putSerializable("usuario",u);
            intento.putExtras(datosExtra);
            startActivity(intento);

        } else if (id == R.id.groupsi2) {

        } else if (id == R.id.profilei2) {


        } else if (id == R.id.categoriesi2) {

        } else if (id == R.id.nav_send2) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void RateInstructor(View view) {
        User temp= new User();
        temp.setUsername(u.getUsername());
        temp.setRate(Double.parseDouble(String.valueOf(ratingBar.getRating())));
        rfn.editRate(new RequestCallback<User>() {
            @Override
            public void onSuccess(User response) {
                ratingBarUser.setRating(Float.parseFloat(String.valueOf((response.getRate()))));
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Calificacion realizada", Toast.LENGTH_SHORT).show();
                        ratingBar.setEnabled(false);
                        rateNumber.setEnabled(false);
                        btnRate.setEnabled(false);
                        ratingBar.setVisibility(View.GONE);
                        rateNumber.setVisibility(View.GONE);
                        btnRate.setVisibility(View.GONE);

                    }
                });
            }

            @Override
            public void onFailed(NetworkException e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Ocurrió Algo problema, vuelvelo a intentar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },temp);

    }


}
