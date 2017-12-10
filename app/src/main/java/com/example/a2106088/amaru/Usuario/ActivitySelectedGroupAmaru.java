package com.example.a2106088.amaru.Usuario;

import android.content.DialogInterface;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.Instructor.ActivitySelectedGroup;
import com.example.a2106088.amaru.MainActivity;
import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.Comment;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.Pojo;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivitySelectedGroupAmaru extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Group grupo;
    TextView groupName;
    TextView groupInstructor;
    TextView groupDescription;
    //TextView txtgroupTotalVotes;
    //TextView txtGroupCurrentRating;
    TextView txtRateNumber;
    RatingBar ratingBar;
    RatingBar ratingBarGroup;
    Button btnRate;
    TableLayout table;
    TableLayout tableGrupoComents;
    ImageView foto;
    ArrayList<Long> ids;
    User user;
    RetrofitNetwork rfn;
    String usuario;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_group_amaru);
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
        rfn=new RetrofitNetwork();

        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();

        grupo = (Group) memoria.getSerializable("grupo");
        usuario = memoria.getString("usuario");
        user = (User) memoria.getSerializable("user");


        groupName = (TextView) findViewById(R.id.txtGroupName);
        groupInstructor = (TextView) findViewById(R.id.txtGroupInstructor);
        groupDescription = (TextView) findViewById(R.id.txtGroupDescription);
        //txtgroupTotalVotes = (TextView) findViewById(R.id.txtGroupTotalVotes);
        //txtGroupCurrentRating = (TextView) findViewById(R.id.txtGroupCurrentRating);
        txtRateNumber = (TextView) findViewById(R.id.txtRateNumber);
        btnRate = (Button) findViewById(R.id.btnRateGroup);
        table = (TableLayout) findViewById(R.id.tableGrupo);
        tableGrupoComents = (TableLayout) findViewById(R.id.tableGrupoComents);
        foto = (ImageView) findViewById(R.id.imageGroup);
        ids = new ArrayList<Long>() ;


        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);

        showRatingBars();

        groupName.setText(String.valueOf(grupo.getNombre()));
        groupInstructor.setText(String.valueOf(grupo.getInstructor()));
        groupDescription.setText(String.valueOf(grupo.getDescription()));
        //txtgroupTotalVotes.setText(String.valueOf(grupo.getTotalVotes()));
        //txtGroupCurrentRating.setText(String.valueOf(grupo.getRate()));
        Picasso.with(this).load(grupo.getImage()).into(foto);


        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group temp = new Group();
                temp.setRate(Double.parseDouble(String.valueOf(ratingBar.getRating())));
                temp.setId(grupo.getId());
                showProgressDialog();
                rfn.editRateGroup(new RequestCallback<Group>() {
                    @Override
                    public void onSuccess(Group response) {
                        ratingBarGroup.setRating(Float.parseFloat(String.valueOf((response.getRate()))));
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                ratingBar.setEnabled(false);
                                txtRateNumber.setEnabled(false);
                                btnRate.setEnabled(false);
                                ratingBar.setVisibility(View.GONE);
                                txtRateNumber.setVisibility(View.GONE);
                                btnRate.setVisibility(View.GONE);
                                dismissProgressDialog();
                                Toast.makeText(getApplicationContext(), "Calificacion realizada", Toast.LENGTH_SHORT).show();
                            }
                        });
                        
                    }

                    @Override
                    public void onFailed(NetworkException e) {

                    }
                },temp);
            }
        });


        MostrarClases();

        MostrarComentarios();

    }

    private void MostrarClases() {
        for (final Clase clase :grupo.getClases()) {
            if (clase.getUsuario().equals(grupo.getInstructor())) {


                TableRow tr_head = new TableRow(this);
                final TextView label_lugar, label_fecha, label_hora;
                // part1
                tr_head.setLayoutParams(new DrawerLayout.LayoutParams(
                        DrawerLayout.LayoutParams.MATCH_PARENT,
                        DrawerLayout.LayoutParams.WRAP_CONTENT));
                label_lugar = new TextView(this);
                label_fecha = new TextView(this);
                label_hora = new TextView(this);
                label_lugar.setText(clase.getPlace());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    label_lugar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                label_lugar.setClickable(true);
                label_lugar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ids.contains(clase.getIdclase())) {
                            Toast.makeText(ActivitySelectedGroupAmaru.this, "Ya estas inscrito a esta clase", Toast.LENGTH_SHORT).show();
                        } else {

                            String msg = "Desea inscribirse a la clase en: " + label_lugar.getText() + " El dia: " + label_fecha.getText();
                            showMessageDialog(msg,clase,v);
                        }
                    }
                });

                // part2
                label_lugar.setPadding(5, 5, 5, 5);
                tr_head.addView(label_lugar);// add the column to the table row here

                // part3
                label_fecha.setText(clase.getFecha());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    label_fecha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                label_fecha.setClickable(true);
                label_fecha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ids.contains(clase.getIdclase())) {
                            Toast.makeText(ActivitySelectedGroupAmaru.this, "Ya estas inscrito a esta clase", Toast.LENGTH_SHORT).show();
                        } else {
                            String msg = "Desea inscribirse a la clase en: " + label_lugar.getText() + " El dia: " + label_fecha.getText();
                            showMessageDialog(msg,clase,v);
                        }
                    }
                });
                label_fecha.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head.addView(label_fecha); // add the column to the table row here

                // part3
                label_hora.setText(clase.getHour());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    label_hora.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                label_hora.setClickable(true);
                label_hora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ids.contains(clase.getIdclase())) {
                            Toast.makeText(ActivitySelectedGroupAmaru.this, "Ya estas inscrito a esta clase", Toast.LENGTH_SHORT).show();
                        } else {
                            String msg = "Desea inscribirse a la clase en: " + label_lugar.getText() + " El dia: " + label_fecha.getText();
                            showMessageDialog(msg,clase,v);
                        }
                    }
                });
                label_hora.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head.addView(label_hora); // add the column to the table row here

                table.addView(tr_head, new TableLayout.LayoutParams(
                        DrawerLayout.LayoutParams.MATCH_PARENT,
                        DrawerLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    private void MostrarComentarios() {
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
            label_lugar.setPadding(5, 5, 5, 5);
            tr_head.addView(label_lugar);// add the column to the table row here
            // part2

            TextView label_comentario = new TextView(this);    // part3
            String tmp = comentario.getContenido();
            for (int i=1; i<comentario.getContenido().length();i++){
                if (i%30 == 0){
                    String part1 =tmp.substring(0,i);
                    String part2 =tmp.substring(i,tmp.length());
                    tmp=part1+"\n"+part2;
                }

            }
            label_comentario.setText(tmp);
            label_comentario.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(label_comentario); // add the column to the table row here

            // part3
            TextView label_fecha = new TextView(this);    // part3
            Log.d("FECHA",comentario.getFecha());
            String[] fecha = comentario.getFecha().split(" ");
            String date = fecha[1]+" "+fecha[2]+" "+fecha[5];
            label_fecha.setText(date);
            label_fecha.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(label_fecha); // add the column to the table row here

            tableGrupoComents.addView(tr_head,
                    new TableLayout.LayoutParams(
                    DrawerLayout.LayoutParams.WRAP_CONTENT,//ancho
                    DrawerLayout.LayoutParams.WRAP_CONTENT));//Alto
        }
    }

    private void showRatingBars() {
        ratingBar.setEnabled(false);
        txtRateNumber.setEnabled(false);
        btnRate.setEnabled(false);
        ratingBar.setVisibility(View.GONE);
        txtRateNumber.setVisibility(View.GONE);
        btnRate.setVisibility(View.GONE);
        for (Clase clase : user.getClases()){
            for (Clase claseGrupo : grupo.getClases()){
                if (clase.getIdclase()==claseGrupo.getIdclase()){
                    ratingBar.setEnabled(true);
                    txtRateNumber.setEnabled(true);
                    btnRate.setEnabled(true);
                    ratingBar.setVisibility(View.VISIBLE);
                    txtRateNumber.setVisibility(View.VISIBLE);
                    btnRate.setVisibility(View.VISIBLE);

                }
            }

        }
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        ratingBar.setRating((float) 3.0);
        txtRateNumber.setText(String.valueOf((int) ratingBar.getRating()));


        ratingBarGroup = (RatingBar) findViewById(R.id.ratingBarGroup);
        ratingBarGroup.setNumStars(5);
        ratingBarGroup.setStepSize((float) 1.0);
        ratingBarGroup.setRating(Float.parseFloat(String.valueOf((grupo.getRate()))));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRateNumber.setText(String.valueOf((int) rating));
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
            Intent ingreso = new Intent(ActivitySelectedGroupAmaru.this, MainActivity.class);
            startActivity(ingreso);        }
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
                    Intent intento=new Intent(ActivitySelectedGroupAmaru.this,PrincipalPageAmaru.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putString("instructor",usuario );
                    datosExtra.putSerializable("user",response);
                    datosExtra.putString("tipoUsuario", response.getType());
                    datosExtra.putString("quitar", "");
                    intento.putExtras(datosExtra);
                    startActivity(intento);
                    dismissProgressDialog();
                }

                @Override
                public void onFailed(NetworkException e) {

                }
            },usuario);

        }
        // SI ESPICHA EN ALL GROUPS
        else if (id == R.id.nav_gallery) {
            Intent intento=new Intent(ActivitySelectedGroupAmaru.this,UserListas.class);
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
                    Intent intento=new Intent(ActivitySelectedGroupAmaru.this,PerfilAmaru.class);
                    Bundle datosExtra = new Bundle();
                    datosExtra.putSerializable("userAmaru",response);
                    intento.putExtras(datosExtra);
                    startActivity(intento);
                    dismissProgressDialog();
                }

                @Override
                public void onFailed(NetworkException e) {dismissProgressDialog();
                }
            },user.getUsername());


        } // SI ESPICHA EN COMPRAR
        else if (id == R.id.nav_manage) {
            Intent intento=new Intent(ActivitySelectedGroupAmaru.this,Comprar.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

            // SI ESPICHA EN CATEGOR
        } else if (id == R.id.categories) {
            Intent intento = new Intent(ActivitySelectedGroupAmaru.this, UserCategory.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void verPerfilInstructor(View view) {
        showProgressDialog();
        rfn.getuser(new RequestCallback<User>() {
            @Override
            public void onSuccess(final User response1) {
                rfn.getuser(new RequestCallback<User>() {
                    @Override
                    public void onSuccess(User response2) {
                        Intent verPerfilInstructor = new Intent(ActivitySelectedGroupAmaru.this,VerPerfilInstructor.class);
                        Bundle memoria = new Bundle();
                        memoria.putSerializable("usuario",response1);
                        memoria.putSerializable("ins",response2);
                        verPerfilInstructor.putExtras(memoria);
                        startActivity(verPerfilInstructor);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailed(NetworkException e) {

                    }
                },grupo.getInstructor());
            }

            @Override
            public void onFailed(NetworkException e) {

            }
        },usuario);

    }
    private void showMessageDialog(String msg,final Clase clase, View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage(msg)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Pojo pojo = new Pojo(clase.getIdclase(), grupo.getId(), usuario);
                        rfn.subscribe(new RequestCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean response) {
                                ids.add(clase.getIdclase());
                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {
                                        Toast.makeText(ActivitySelectedGroupAmaru.this, "Inscripcion realizada", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailed(NetworkException e) {

                            }
                        }, pojo);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void comentarGrupo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final EditText txt = new EditText(this);
        txt.setHint("Comentario ...");
        builder.setMessage("Escribe tu comentario").setView(txt)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Comment comentario = new Comment(txt.getText().toString(),grupo.getId(),user.getUsername(),"fehca",grupo.getId());
                        Log.d("COMMENT",grupo.getNombre()+" -- id: "+String.valueOf(grupo.getId()));
                        rfn.addCommentGroup(new RequestCallback<Group>() {
                            @Override
                            public void onSuccess(Group response) {
                                Log.d("COMMENT RESPONSE",response.getNombre()+" -- id: "+String.valueOf(response.getId()));
                                Intent repetir = new Intent(ActivitySelectedGroupAmaru.this,ActivitySelectedGroupAmaru.class);
                                Bundle datosExtra = new Bundle();
                                datosExtra.putSerializable("grupo",response);
                                datosExtra.putString("usuario",user.getUsername());
                                datosExtra.putSerializable("user",user);
                                repetir.putExtras(datosExtra);
                                startActivity(repetir);
                                finish();

                            }

                            @Override
                            public void onFailed(NetworkException e) {

                            }
                        },comentario);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


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
}

