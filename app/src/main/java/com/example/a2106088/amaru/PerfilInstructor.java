package com.example.a2106088.amaru;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PerfilInstructor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    RetrofitNetwork rfn;
    ImageView instructorimage;
    EditText instrcutorEmail;
    EditText instrcutorPhone;
    EditText instructorDescription;
    TextView instructoruserna;
    Button buttoneditimage;
    Button buttoneditmail;
    Button buttoneditphone;
    User u;
    Button buttoneditdescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_instructor);
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
        rfn= new RetrofitNetwork();
        instructorimage = (ImageView) findViewById(R.id.instructorimage);
        instrcutorEmail = (EditText) findViewById(R.id.instrcutorEmail);
        instrcutorPhone= (EditText) findViewById(R.id.instrcutorPhone);
        instructorDescription= (EditText) findViewById(R.id.instructorDescription);
        instructoruserna = (TextView) findViewById(R.id.nombredelinss);
        buttoneditimage = (Button) findViewById(R.id.buttoneditimage);
        buttoneditmail = (Button) findViewById(R.id.buttoneditmail);
        buttoneditphone = (Button) findViewById(R.id.buttoneditphone);
        buttoneditdescription = (Button) findViewById(R.id.buttoneditdescription);
        buttoneditimage.setOnClickListener(this);
        buttoneditmail.setOnClickListener(this);
        buttoneditphone.setOnClickListener(this);
        buttoneditdescription.setOnClickListener(this);
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        u= (User) memoria.getSerializable("ins");

        instructoruserna.setText("Username: "+ u.getUsername());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    try {
                        URL url = null;
                        url = new URL(u.getImage());
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        ponerimagen(bmp);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        instrcutorEmail.setText(u.getEmail());
        instrcutorPhone.setText(u.getPhone());
        instructorDescription.setText(u.getDescription());
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
        getMenuInflater().inflate(R.menu.perfil_instructor, menu);
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

    public void ponerimagen(Bitmap b){
        instructorimage.setImageBitmap(b);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.createi1) {
            String usuario="";
            usuario = u.getUsername();
            Intent intento=new Intent(PerfilInstructor.this,CrearGrupo.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putSerializable("user",u);
            intento.putExtras(datosExtra);
            startActivity(intento);
        } else if (id == R.id.clasesi1) {
            Intent intento=new Intent(PerfilInstructor.this,PrincipalPageInstructor.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putSerializable("usuario",u);
            intento.putExtras(datosExtra);
            startActivity(intento);

        } else if (id == R.id.groupsi1) {

        } else if (id == R.id.profilei1) {


        } else if (id == R.id.categoriesi1) {

        } else if (id == R.id.nav_send1) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttoneditimage.getId()) {

        }
        else if (view.getId() == buttoneditmail.getId()) {
            User temp= new User();
            temp.setUsername(u.getUsername());
            temp.setEmail(instrcutorEmail.getText().toString());
            rfn.editemail(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Editado Exitosamente", Toast.LENGTH_SHORT).show();
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
        else if (view.getId() == buttoneditphone.getId()) {
            User temp= new User();
            temp.setUsername(u.getUsername());
            temp.setPhone(instrcutorPhone.getText().toString());
            rfn.editphone(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Editado Exitosamente", Toast.LENGTH_SHORT).show();
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
        else if (view.getId() == buttoneditdescription.getId()) {
            User temp= new User();
            temp.setUsername(u.getUsername());
            temp.setDescription(instructorDescription.getText().toString());
            rfn.editdescription(new RequestCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Editado Exitosamente", Toast.LENGTH_SHORT).show();
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
}
