package com.example.a2106088.amaru.Instructor;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2106088.amaru.R;
import com.example.a2106088.amaru.entity.Clase;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.NetworkException;
import com.example.a2106088.amaru.model.RequestCallback;
import com.example.a2106088.amaru.model.RetrofitNetwork;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class PerfilInstructor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    RetrofitNetwork rfn;
    ImageView instructorimage;
    EditText instrcutorEmail;
    EditText instrcutorPhone;
    EditText instructorDescription;
    TextView instructoruserna;
    Button buttoneditimage;
    Bitmap photo;
    User u;
    Button edituser;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://amaru-cosw.appspot.com");
    StorageReference mountainsRef ;
    String urlImagen;
    User temp;


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
        edituser = (Button) findViewById(R.id.editinstructor);
        buttoneditimage.setOnClickListener(this);
        edituser.setOnClickListener(this);
        temp= new User();
        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        u= (User) memoria.getSerializable("ins");

        instructoruserna.setText("Username: "+ u.getUsername());
        Picasso.with(this).load(u.getImage()).into(instructorimage);
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
        else if (view.getId() == edituser.getId()) {


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            mountainsRef = storageRef.child(u.getUsername()+".jpg");
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

                    u.setImage(urlImagen);
                    u.setUsername(u.getUsername());
                    u.setEmail(instrcutorEmail.getText().toString());
                    u.setPhone(instrcutorPhone.getText().toString());
                    u.setDescription(instructorDescription.getText().toString());
                    temp.setImage(urlImagen);
                    temp.setUsername(u.getUsername());
                    temp.setEmail(instrcutorEmail.getText().toString());
                    temp.setPhone(instrcutorPhone.getText().toString());
                    temp.setDescription(instructorDescription.getText().toString());

                    rfn.editimage(new RequestCallback<User>() {
                        @Override
                        public void onSuccess(User response) {
                            rfn.editphone(new RequestCallback<User>() {
                                @Override
                                public void onSuccess(User response) {
                                    rfn.editdescription(new RequestCallback<User>() {
                                        @Override
                                        public void onSuccess(User response) {
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
                                                            Toast.makeText(getApplicationContext(), "Ocurrió Un Error Al Editar", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            },temp);
                                        }

                                        @Override
                                        public void onFailed(NetworkException e) {

                                        }
                                    },temp);
                                }

                                @Override
                                public void onFailed(NetworkException e) {

                                }
                            },temp);
                        }

                        @Override
                        public void onFailed(NetworkException e) {

                        }
                    },temp);



                }
            });

        }


    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == 2 && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            instructorimage.setImageBitmap(photo);
        }

        else if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = this.getApplicationContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                photo=selectedImage;
                instructorimage.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
