package com.example.a2106088.amaru.Instructor;

/**
 * Created by User on 09/12/2017.
 */

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

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.Comment;
import com.example.a2106088.amaru.entity.CustomListAdapter;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.squareup.picasso.Picasso;

public class ActivitySelectedGroup extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Group grupo;
    TextView groupName;
    TextView groupInstructor;
    TextView groupDescription;
    TextView txtgroupTotalVotes;
    TextView txtGroupCurrentRating;
    TextView txtRateNumber;
    RatingBar ratingBar;
    Button btnRate;
    TableLayout table;
    TableLayout tableGrupoComents;
    ImageView foto;
    String usuario;


    RetrofitNetwork rfn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_group);
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
        Bundle memoria = anterior.getExtras();
        grupo = (Group) memoria.getSerializable("grupo");
        usuario= memoria.getString("myuser");
        groupName = (TextView) findViewById(R.id.txtGroupName);
        groupInstructor = (TextView) findViewById(R.id.txtGroupInstructor);
        groupDescription = (TextView) findViewById(R.id.txtGroupDescription);
        txtgroupTotalVotes = (TextView) findViewById(R.id.txtGroupTotalVotes);
        txtGroupCurrentRating = (TextView) findViewById(R.id.txtGroupCurrentRating);
        txtRateNumber = (TextView) findViewById(R.id.txtRateNumber);
        btnRate = (Button) findViewById(R.id.btnRateGroup);
        table = (TableLayout) findViewById(R.id.tableGrupo);
        tableGrupoComents = (TableLayout) findViewById(R.id.tableGrupoComents);
        foto = (ImageView) findViewById(R.id.imageGroup);
        rfn=new RetrofitNetwork();

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        ratingBar.setNumStars(5);
        txtRateNumber.setText(String.valueOf((int) ratingBar.getRating()));

        groupName.setText(String.valueOf(grupo.getNombre()));
        groupInstructor.setText(String.valueOf(grupo.getInstructor()));
        groupDescription.setText(String.valueOf(grupo.getDescription()));
        txtgroupTotalVotes.setText(String.valueOf(grupo.getTotalVotes()));
        txtGroupCurrentRating.setText(String.valueOf(grupo.getRate()));
        Picasso.with(this).load(grupo.getImage()).into(foto);
        btnRate.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);
        txtRateNumber.setVisibility(View.GONE);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRateNumber.setText(String.valueOf((int) rating));
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group temp = new Group();
                temp.setRate(Double.parseDouble(String.valueOf(ratingBar.getRating())));
                temp.setId(grupo.getId());
                rfn.editRateGroup(new RequestCallback<Group>() {
                    @Override
                    public void onSuccess(Group response) {

                        txtgroupTotalVotes.setText(String.valueOf(response.getTotalVotes()));
                        txtGroupCurrentRating.setText(String.valueOf(response.getRate()));
                        ratingBar.setEnabled(false);
                        txtRateNumber.setEnabled(false);

                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Successful rating", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailed(NetworkException e) {

                    }
                },temp);
            }
        });



        for (Clase clase :grupo.getClases()) {
            if (clase.getUsuario().equals(grupo.getInstructor())) {


                TableRow tr_head = new TableRow(this);
                // part1
                tr_head.setLayoutParams(new DrawerLayout.LayoutParams(
                        DrawerLayout.LayoutParams.MATCH_PARENT,
                        DrawerLayout.LayoutParams.WRAP_CONTENT));
                TextView label_lugar = new TextView(this);
                label_lugar.setText(clase.getPlace());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    label_lugar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                // part2
                label_lugar.setPadding(5, 5, 5, 5);
                tr_head.addView(label_lugar);// add the column to the table row here

                TextView label_fecha = new TextView(this);    // part3
                label_fecha.setText(clase.getFecha());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    label_fecha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                label_fecha.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head.addView(label_fecha); // add the column to the table row here

                TextView label_hora = new TextView(this);    // part3
                label_hora.setText(clase.getHour());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    label_hora.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                label_hora.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head.addView(label_hora); // add the column to the table row here

                table.addView(tr_head, new TableLayout.LayoutParams(
                        DrawerLayout.LayoutParams.MATCH_PARENT,
                        DrawerLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        for (Comment comentario :grupo.getComments()) {
            TableRow tr_head = new TableRow(this);
            // part1
            tr_head.setLayoutParams(new DrawerLayout.LayoutParams(
                    DrawerLayout.LayoutParams.MATCH_PARENT,
                    DrawerLayout.LayoutParams.WRAP_CONTENT));
            TextView label_lugar = new TextView(this);
            label_lugar.setText(comentario.getUsuario());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                label_lugar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            // part2
            label_lugar.setPadding(5, 5, 5, 5);
            tr_head.addView(label_lugar);// add the column to the table row here

            TextView label_fecha = new TextView(this);    // part3
            label_fecha.setText(comentario.getContenido());
            label_fecha.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(label_fecha); // add the column to the table row here

            tableGrupoComents.addView(tr_head, new TableLayout.LayoutParams(
                    DrawerLayout.LayoutParams.MATCH_PARENT,
                    DrawerLayout.LayoutParams.WRAP_CONTENT));
        }

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
        getMenuInflater().inflate(R.menu.activity_selected_group, menu);
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

        if (id == R.id.createi) {
            Intent intento = new Intent(ActivitySelectedGroup.this, CrearGrupo.class);
            Bundle datosExtra = new Bundle();

            datosExtra.putSerializable("user", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        } else if (id == R.id.clasesi) {

        } else if (id == R.id.groupsi) {

        } else if (id == R.id.profilei) {

        } else if (id == R.id.categoriesi) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}