package com.example.javier.proyectodinny;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccessToken;

import Get_Data.Uid;

import static com.example.javier.proyectodinny.FacebookSms.APP_REQUEST_CODE;

public class Registro extends AppCompatActivity {

    protected ProgressBar Barradeprogreso;
    private Button btnempezar;
    private FirebaseAuth mAuth;
    private EditText txtcorreo,txtcontraseña,txtTelefono,txtnombre;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Usuarios");
    private String Uidt;




    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnempezar=(Button)findViewById(R.id.btnempezar);
        txtcontraseña=(EditText) findViewById(R.id.txtcontraseña);
        txtcorreo=(EditText) findViewById(R.id.txtcorreo);
        txtTelefono=(EditText)findViewById(R.id.txtTelefono);
        txtnombre=(EditText)findViewById(R.id.txtnombre);
        mAuth = FirebaseAuth.getInstance();
        Barradeprogreso=(ProgressBar)findViewById(R.id.Barradeprogreso);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user =mAuth.getCurrentUser();
                if(user==null) {
                    //No esta logueado
                }
                else
                {


                    Intent Inicio =new Intent(Registro.this, com.example.javier.proyectodinny.Inicio.class);
                    startActivity(Inicio);
                    mAuth.getUid();

                    Toast.makeText(Registro.this, mAuth.getUid(), Toast.LENGTH_LONG).show();
                    Toast.makeText(Registro.this, "Credenciales Correctas", Toast.LENGTH_LONG).show();
                    Barradeprogreso.setVisibility(View.INVISIBLE);
                }

            }

        };


        btnempezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Barradeprogreso.setVisibility(View.VISIBLE);
                CrearUsuario();
            }
        });
    }

    private void CrearUsuario()
    {
        String email = txtcorreo.getText().toString();
        String password = txtcontraseña.getText().toString();
        final String nombre= txtnombre.getText().toString();
        final String telefono=txtTelefono.getText().toString();


        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)) {

            Toast.makeText(Registro.this, "Ingresa la información en los campos de Texto", Toast.LENGTH_LONG).show();
            Barradeprogreso.setVisibility(View.INVISIBLE);
        }
        else
        {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {






                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        try {




                                            Uidt=mAuth.getUid();
                                            Uid Objuid=new Uid(Uidt);


                                            CrearUsuariodb(Uidt,nombre,telefono);
                                                Toast.makeText(Registro.this, "Correcto", Toast.LENGTH_LONG).show();
                                                //phoneLogin(LoginType.PHONE);
                                                Barradeprogreso.setVisibility(View.INVISIBLE);
                                            Intent Inicio =new Intent(Registro.this, EscogerFoto.class);
                                            startActivity(Inicio);

                                        }
                                        catch(Exception Ex1){
                                            Toast.makeText(Registro.this, "Error al crear el usuario, Correo ya Existente", Toast.LENGTH_LONG).show();
                                            Barradeprogreso.setVisibility(View.INVISIBLE);
                                        }

                                    }


                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(Registro.this, "Error", Toast.LENGTH_LONG).show();
                                        Barradeprogreso.setVisibility(View.INVISIBLE);
                                    }
                                });


                            }
                            else {


                                Toast.makeText(Registro.this, "Error al crear el usuario", Toast.LENGTH_LONG).show();
                            }   Barradeprogreso.setVisibility(View.INVISIBLE);



                            // ...
                        }
                    });
        }


    }

    private void CrearUsuariodb(String uid,String Nombre,String Telefono)
    {
        User user=new User("null",Nombre,Telefono,"0","0");
        myRef.child(Uidt).setValue(user);
    }

    public void phoneLogin(LoginType loginType) {


        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);

    }

    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(Registro.this, "Error", Toast.LENGTH_LONG).show();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Operación Cancelada";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();


                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0,10));


                }
                startActivity(new Intent(this,Inicio.class));



            }


        }
    }


    private void updatesmsdb(){

    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

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
