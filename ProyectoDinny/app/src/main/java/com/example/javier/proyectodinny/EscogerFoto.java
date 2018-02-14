package com.example.javier.proyectodinny;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.sax.TextElementListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import Get_Data.Uid;
import Get_Data.Usuario;

public class EscogerFoto extends AppCompatActivity {

    private Button BtnFoto,BtnGaleria;
    private ProgressBar Barradeprogreso;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int GALLERY_INTENT=1;
    private String idUser;
    private ProgressDialog mProgressDialog;
    final FirebaseDatabase referencia= FirebaseDatabase.getInstance();
    private DatabaseReference table_user= referencia.getReference("Usuarios");
    private DatabaseReference table_user2= referencia.getReference("Usuarios");
    private String Nombre;
    private String Telefono;
    private String keyto;
    String Dinnyphoto;
    private ValueEventListener tinny ;
    private ValueEventListener tinny2 ;
    private DataSnapshot dataSnapshot;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoger_foto);

        mStorage= FirebaseStorage.getInstance().getReference();


        BtnFoto=(Button)findViewById(R.id.BtnFoto);
        BtnGaleria=(Button)findViewById(R.id.BtnGaleria);
        mProgressDialog=new ProgressDialog(this);


        BtnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK)
    {
        mProgressDialog.setTitle("Subiendo Foto...");
        mProgressDialog.setMessage("Subiendo Foto a Firebase");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

       Uri uri=data.getData();
        String uidt=user.getUid();



        StorageReference filePath=mStorage.child(uidt).child(uri.getLastPathSegment());
       filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

               mProgressDialog.dismiss();
               Uri descargarFoto=taskSnapshot.getDownloadUrl();

               //REGISTRAR EN METODO DE BASE DE DATOS

               String dinnyfoto=descargarFoto.toString();


               setActualizarFotodb(dinnyfoto);



               Intent Inicio =new Intent(EscogerFoto.this, VisualizarFoto.class);
               startActivity(Inicio);
               Toast.makeText(EscogerFoto.this,"La foto ha sido subida correctamente ",Toast.LENGTH_SHORT).show();



           }
       });
    }
    }



private  void setActualizarFotodb(final  String Dinnyphoto )
{
//el problema del error

         table_user.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {


                         table_user.child(user.getUid()+"/foto").setValue(Dinnyphoto);



                 }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

}







}


