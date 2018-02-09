package com.example.javier.proyectodinny;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    protected ProgressBar Barradeprogreso;
    protected Button BtnInicio;
    protected Button BtnFacebook;
    private String idFaacebook;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference nDatabaseReference= FirebaseDatabase.getInstance().getReference();
    DatabaseReference nDataChild=nDatabaseReference.child("texto");
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Usuarios");
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creación CallBackManager
        callbackManager=CallbackManager.Factory.create();

        //creacion de Botones
        BtnInicio=(Button)findViewById(R.id.BtnInicio);
        BtnFacebook=(Button)findViewById(R.id.BtnFacebook);

        //Creación de los Edit Text
        Barradeprogreso=(ProgressBar)findViewById(R.id.Barradeprogreso);



        BtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Barradeprogreso.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        Barradeprogreso.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Se canceló la Operación", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Barradeprogreso.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Ocurrio un Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user =mAuth.getCurrentUser();
                if(user==null) {
                 //No esta logueado
             }
             else
                {


                    Intent Inicio =new Intent(MainActivity.this, com.example.javier.proyectodinny.Inicio.class);
                    startActivity(Inicio);
                    mAuth.getUid();

                    Toast.makeText(MainActivity.this, mAuth.getUid(), Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Credenciales Correctas", Toast.LENGTH_LONG).show();
                    Barradeprogreso.setVisibility(View.INVISIBLE);
                }

            }

        };



        BtnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Barradeprogreso.setVisibility(View.VISIBLE);
                Intent Inicio =new Intent(MainActivity.this, InicioCorreo.class);
                startActivity(Inicio);
                Barradeprogreso.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


try {
    CrearUsuariodb("0", "0", "0");
   }
   catch(Exception e)
                {

                }


            }
        });
    }

    private void CrearUsuariodb(String uid,String Nombre,String Telefono)
    {
        idFaacebook=mAuth.getUid();
        User user=new User("null",Nombre,Telefono,"0","0");
        myRef.child(idFaacebook).setValue(user);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
  }

  public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
  }
  public void onStop()
  {
      super.onStop();
      if(mAuthListener!= null)
      {
          mAuth.removeAuthStateListener(mAuthListener);
      }
  }




}
