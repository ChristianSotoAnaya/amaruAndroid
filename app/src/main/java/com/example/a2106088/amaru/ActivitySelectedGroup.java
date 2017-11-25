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
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;

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
        groupName = (TextView) findViewById(R.id.txtGroupName);
        groupInstructor = (TextView) findViewById(R.id.txtGroupInstructor);
        groupDescription = (TextView) findViewById(R.id.txtGroupDescription);
        txtgroupTotalVotes = (TextView) findViewById(R.id.txtGroupTotalVotes);
        txtGroupCurrentRating = (TextView) findViewById(R.id.txtGroupCurrentRating);
        txtRateNumber = (TextView) findViewById(R.id.txtRateNumber);
        btnRate = (Button) findViewById(R.id.btnRateGroup);
        rfn=new RetrofitNetwork();

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        ratingBar.setNumStars(5);
        txtRateNumber.setText(String.valueOf((int) ratingBar.getRating()));

        groupName.setText(String.valueOf(grupo.getNombre()));
        groupInstructor.setText(String.valueOf(grupo.getInstructor()));
        groupDescription.setText(String.valueOf(grupo.getDescription()));
        txtgroupTotalVotes.setText(String.valueOf(grupo.getTotalVotes()));
        txtGroupCurrentRating.setText(String.valueOf(grupo.getRate()));

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
                        /*
                        txtgroupTotalVotes.setText(String.valueOf(response.getTotalVotes()));
                        txtGroupCurrentRating.setText(String.valueOf(response.getRate()));
                        ratingBar.setEnabled(false);
                        txtRateNumber.setEnabled(false);
                        */
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

