package com.example.javier.proyectodinny;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroFacebook extends AppCompatActivity {

    protected ProgressBar Barradeprogreso;
    private Button btnempezar;
    private EditText txtTelefono,txtnombre;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Usuarios");
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private String Nombre,Telefono;
    private String UIDAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_facebook);

        Barradeprogreso=(ProgressBar)findViewById(R.id.Barradeprogreso);
        btnempezar=(Button)findViewById(R.id.btnempezar);
        txtnombre=(EditText) findViewById(R.id.txtnombre);
        txtTelefono=(EditText)findViewById(R.id.txtTelefono);


        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user =mAuth.getCurrentUser();
                if(user!=null) {


                    Intent Inicio =new Intent(RegistroFacebook.this, Inicio.class);
                    startActivity(Inicio);



                }


            }

        };





        btnempezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UIDAuth=mAuth.getUid();
                Barradeprogreso.setVisibility(View.VISIBLE);
                Nombre=txtnombre.getText().toString();
                Telefono=txtTelefono.getText().toString();

                CrearUsuariodb(UIDAuth,Nombre,Telefono);
            }
        });
    }

    private void CrearUsuariodb(String uid,String Nombre,String Telefono)
    {
        try{


            User user=new User("null",Nombre,Telefono,"0","0");
            myRef.child(uid).setValue(user);
            Intent Inicio =new Intent(RegistroFacebook.this, Inicio.class);
            startActivity(Inicio);

        }
        catch (Exception e)
        {
            Toast.makeText(RegistroFacebook.this, "Error", Toast.LENGTH_LONG).show();
            Barradeprogreso.setVisibility(View.INVISIBLE);
        }

    }



}
