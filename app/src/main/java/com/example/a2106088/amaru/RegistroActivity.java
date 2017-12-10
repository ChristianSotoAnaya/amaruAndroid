package com.example.a2106088.amaru;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtUsername;
    ProgressDialog progressDialog;
    EditText edtPassword;
    EditText edtConfirmPassword;
    EditText edtName;
    EditText edtLastName;
    EditText edtPhone;
    EditText edtEmail;
    Button btnRegistro;
    Bitmap photo;
    SharedPreferences infoUsuarios;
    Spinner spinner ;
    RetrofitNetwork rfn;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://amaru-cosw.appspot.com");
    StorageReference mountainsRef ;
    String urlImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edtUsername=(EditText) findViewById(R.id.edtUsername);
        edtPassword=(EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword=(EditText) findViewById(R.id.edtConfirmPassword);
        edtName=(EditText) findViewById(R.id.edtName);
        edtLastName=(EditText) findViewById(R.id.edtLastName);
        edtPhone=(EditText) findViewById(R.id.edtPhone);
        edtEmail=(EditText) findViewById(R.id.edtEmail);
        progressDialog = new ProgressDialog( this );
        btnRegistro=(Button) findViewById(R.id.Registrarse);
        btnRegistro.setOnClickListener(this);
        spinner=(Spinner) findViewById(R.id.spinner);
        String[] letra = {"INSTRUCTOR","AMARU"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));
        infoUsuarios=this.getSharedPreferences("asd", Context.MODE_PRIVATE);
        rfn= new RetrofitNetwork();
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==btnRegistro.getId()) {


            final String username = edtUsername.getText().toString();
            final String password = edtPassword.getText().toString();
            final String confirmPassword = edtConfirmPassword.getText().toString();
            final String name = edtName.getText().toString();
            final String lastName = edtLastName.getText().toString();
            final String phone = edtPhone.getText().toString();
            final String email = edtEmail.getText().toString();
            final String tipo = spinner.getSelectedItem().toString();


            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "No coinciden las claves", Toast.LENGTH_LONG).show();
            } else {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                mountainsRef = storageRef.child(username+".jpg");
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
                        User temp= new User();
                        temp.setNombre(name);
                        temp.setLastname(lastName);
                        temp.setPhone(phone);
                        temp.setPassword(password);
                        temp.setEmail(email);
                        temp.setDescription("");
                        temp.setType(tipo);
                        temp.setUsername(username);
                        temp.setRate(0.0);
                        temp.setTotalVotes(0);
                        temp.setClases(new ArrayList<Clase>());
                        temp.setCupo(5);
                        temp.setImage(urlImagen);
                        showProgressDialog();
                        rfn.createUser(new RequestCallback<User>() {
                            @Override
                            public void onSuccess(User response) {
                                dismissProgressDialog();
                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailed(NetworkException e) {
                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "NO Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        },temp);


                        finish();
                        Intent siguiente = new Intent();
                        setResult(Activity.RESULT_OK, siguiente);
                    }
                });












            }
        }
    }



    public void adjuntar(View view) {
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
