package com.example.a2106088.amaru.Usuario;

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

public class PerfilAmaru extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    RetrofitNetwork rfn;
    ImageView amaruimage;
    EditText amaruEmail;
    EditText amaruPhone;
    EditText amaruDescription;
    TextView amaruuserna;
    TextView amarucupo;
    Button buttoneditimageam;
    Button editamaru;
    Bitmap photo;
    User u;
    String usuario;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://amaru-cosw.appspot.com");
    StorageReference mountainsRef ;
    String urlImagen;
    User temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amaru);
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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        rfn= new RetrofitNetwork();
        amaruimage = (ImageView) findViewById(R.id.amaruimage);
        amaruEmail = (EditText) findViewById(R.id.amaruEmail);
        amaruPhone= (EditText) findViewById(R.id.amaruPhone);
        amaruDescription= (EditText) findViewById(R.id.amaruDescription);
        amaruuserna = (TextView) findViewById(R.id.amaruuserna);
        amarucupo = (TextView) findViewById(R.id.cupo);
        buttoneditimageam = (Button) findViewById(R.id.buttoneditimageam);
        editamaru = (Button) findViewById(R.id.editamaru);

        buttoneditimageam.setOnClickListener(this);
        editamaru.setOnClickListener(this);

        Intent anterior = getIntent();
        Bundle memoria = anterior.getExtras();
        u = (User) memoria.getSerializable("userAmaru");
        usuario = u.getUsername();
        amaruuserna.setText("Username: "+usuario);
        Picasso.with(getApplicationContext()).load(u.getImage()).into(amaruimage);
        amaruEmail.setText(u.getEmail());
        amaruPhone.setText(u.getPhone());
        amaruDescription.setText(u.getDescription());
        amarucupo.setText(String.valueOf(u.getCupo()));
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
        getMenuInflater().inflate(R.menu.perfil_amaru, menu);
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

        // SI OPRIME EN MIS CLASES
        if (id == R.id.nav_camera) {

        }
        // SI OPRIME EN TODOS LOS GRUPOS
        else if (id == R.id.nav_gallery) {
            Intent intento=new Intent(PerfilAmaru.this,Grupo.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);


        }
        // SI OPRIME EN MI PERFIL
        else if (id == R.id.nav_slideshow) {


        } // SI OPRIME EN COMPRAR
        else if (id == R.id.nav_manage) {
            Intent intento=new Intent(PerfilAmaru.this,Comprar.class);
            Bundle datosExtra = new Bundle();
            datosExtra.putString("username",usuario);
            intento.putExtras(datosExtra);
            startActivity(intento);

            // SI OPRIME EN CATEGORIAS
        } else if (id == R.id.categories) {
            Toast.makeText(this, "nav_share", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "nav_send", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() ==editamaru.getId()) {
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
        else if (view.getId() == editamaru.getId()) {


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
                    temp.setImage(urlImagen);
                    temp.setUsername(u.getUsername());
                    temp.setEmail(amaruEmail.getText().toString());
                    temp.setPhone(amaruPhone.getText().toString());
                    temp.setDescription(amaruDescription.getText().toString());


                    rfn.editemail(new RequestCallback<User>() {
                        @Override
                        public void onSuccess(User response) {
                            rfn.editphone(new RequestCallback<User>() {
                                @Override
                                public void onSuccess(User response) {
                                    rfn.editdescription(new RequestCallback<User>() {
                                        @Override
                                        public void onSuccess(User response) {

                                            rfn.editimage(new RequestCallback<User>() {
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
                                                            Toast.makeText(getApplicationContext(), "No Ha Sido Editado Exitosamente", Toast.LENGTH_SHORT).show();

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
            amaruimage.setImageBitmap(photo);
        }

        else if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = this.getApplicationContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                photo=selectedImage;
                amaruimage.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    }

