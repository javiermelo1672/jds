package com.example.javier.proyectodinny;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VisualizarFoto extends AppCompatActivity {

    private ImageView ImagenMuestra;
    private Button BtnInicio;
    private String idUser;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference mStorage;
    private FirebaseStorage mAuthListenerPhoto;
    private String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_foto);
        ImagenMuestra=(ImageView) findViewById(R.id.ImagenMuestra);
        BtnInicio=(Button)findViewById(R.id.BtnInicio);
        mStorage= FirebaseStorage.getInstance().getReference();


        BtnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Inicio =new Intent(VisualizarFoto.this, Inicio.class);
                startActivity(Inicio);

            }
        });


    }



    public void MostrarImagen()
    {
        Uid=user.getUid();
        Toast.makeText(VisualizarFoto.this,Uid,Toast.LENGTH_SHORT).show();
        mStorage.child("gs://betaexampledinny-6d5de.appspot.com/"+Uid+"/21").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                Glide.with(VisualizarFoto.this).load(uri).into(ImagenMuestra);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(VisualizarFoto.this,"Error al descargar la foto",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onStart(){
        super.onStart();
        MostrarImagen();
    }
}
