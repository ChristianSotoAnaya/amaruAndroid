package com.example.a2106088.amaru.Instructor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CrearGrupo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog progressDialog;
    EditText edtNombreCrearGrupo;
    EditText edtDescripcionCrearGrupo;
    Spinner spnlugar;
    EditText edtfecha;
    EditText edthora;
    Bitmap photo;
    Spinner spinner ;
    TableLayout table;
    long idClase = 0;
    long idGrupo = 0;
    ArrayList<Clase> clases = new ArrayList<Clase>();
    String usuario;
    RetrofitNetwork rfn;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://amaru-cosw.appspot.com");
    StorageReference mountainsRef ;
    String urlImagen;

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
        setContentView(R.layout.activity_crear_grupo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog( this );
        setSupportActionBar(toolbar);
        edtNombreCrearGrupo=(EditText) findViewById(R.id.edtNombreCrearGrupo);
        edtDescripcionCrearGrupo=(EditText) findViewById(R.id.edtDescripcionCrearGrupo);
        spnlugar = (Spinner) findViewById(R.id.spinnerLugar);
        edtfecha = (EditText) findViewById(R.id.edtfecha);
        edthora = (EditText) findViewById(R.id.edthora);
        table = (TableLayout) findViewById(R.id.table);
        spinner=(Spinner) findViewById(R.id.spinner2);
        rfn= new RetrofitNetwork();
        showProgressDialog();
        rfn.getallgroups(new RequestCallback<List<Group>>(){
            @Override
            public void onSuccess(List<Group> response) {
                idGrupo = response.size();
                dismissProgressDialog();
            }

            @Override
            public void onFailed(NetworkException e) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error al cargar grupos", Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    }
                });
            }
        });
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        usuario= memoria.getString("user");

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

        if (id == R.id.createi) {
            Intent intento = new Intent(CrearGrupo.this, CrearGrupo.class);
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
                    Intent ingreso = new Intent(CrearGrupo.this, PrincipalPageInstructor.class);
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


            Intent intento = new Intent(CrearGrupo.this, ActivityListaGrupos.class);
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
                    Intent intento = new Intent(CrearGrupo.this, PerfilInstructor.class);
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
            Intent intento = new Intent(CrearGrupo.this, Categorias.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("usuario", usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        //Mis grupos
        else if (id == R.id.nav_send) {

            Intent intento = new Intent(CrearGrupo.this, ActivityListaGrupos.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("instructor", usuario);
            datosExtra.putString("quitar",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

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
        String lugar = spnlugar.getSelectedItem().toString();
        Clase clase = new Clase(idGrupo,edtfecha.getText().toString(),edthora.getText().toString(),lugar,idClase,edtNombreCrearGrupo.getText().toString(),0, usuario);
        idClase+=1;
        clases.add(clase);
        TableRow tr_head = new TableRow(this);
       // part1
        tr_head.setLayoutParams(new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT));
        TextView label_lugar = new TextView(this);
        label_lugar.setText(lugar);
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

    public void subirfotogrupol(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("How do you want import the image?")
                .setCancelable(true)
                .setPositiveButton("From Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 1);
                    }
                })
                .setNegativeButton("Take a Photo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 2);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void crearGrupo(View view) {
        final String categoria = spinner.getSelectedItem().toString();
        final String nombre =  edtNombreCrearGrupo.getText().toString();
        final String descripcion = edtDescripcionCrearGrupo.getText().toString();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        mountainsRef = storageRef.child(usuario+nombre+".jpg");
        UploadTask uploadTask = mountainsRef.putBytes(byteArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("urlimagen",downloadUrl.toString());
                urlImagen=downloadUrl.toString();
                Group nuevo = new Group(idGrupo, nombre, usuario, null, descripcion, categoria, 0.0, 0,urlImagen,clases);
                showProgressDialog();
                rfn.createGroup(new RequestCallback<Group>() {
                    @Override
                    public void onSuccess(Group response) {
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Grupo creado exitosamente", Toast.LENGTH_LONG).show();
                            }
                        });
                        rfn.getuser(new RequestCallback<User>() {
                            @Override
                            public void onSuccess(User response) {
                                Bundle memoria = new Bundle();
                                memoria.putSerializable("usuario",response);
                                Intent ingreso = new Intent(CrearGrupo.this, PrincipalPageInstructor.class);
                                ingreso.putExtras(memoria);
                                startActivity(ingreso);
                                dismissProgressDialog();
                            }
                            @Override
                            public void onFailed(NetworkException e) {
                                dismissProgressDialog();
                            }
                        },usuario);
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
        });


    }

    public void cancelar(View view) {
        Bundle memoria = new Bundle();
        memoria.putSerializable("usuario",usuario);
        Intent ingreso = new Intent(CrearGrupo.this, PrincipalPageInstructor.class);
        ingreso.putExtras(memoria);
        startActivity(ingreso);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == 2 && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
        }

        else if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = this.getApplicationContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                photo=selectedImage;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
