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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private ValueEventListener tinny ;
    final FirebaseDatabase referencia= FirebaseDatabase.getInstance();
    private DatabaseReference table_user= referencia.getReference("Usuarios");

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



        tinny = table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user =dataSnapshot.child(Uid).getValue(User.class);
               String Foto =user.getFoto();

                Glide.with(VisualizarFoto.this).load(Foto).into(ImagenMuestra);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VisualizarFoto.this,"Error ",Toast.LENGTH_SHORT).show();
            }
        });





    }
    public void onStart(){
        super.onStart();
        MostrarImagen();
    }
}
