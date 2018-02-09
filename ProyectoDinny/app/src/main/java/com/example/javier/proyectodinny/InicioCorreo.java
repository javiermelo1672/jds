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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioCorreo extends AppCompatActivity {

    private TextView terminos;
    private Button btnempezarcorreo;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText txtcorreo,txtcontrasena;
    private TextView txtcrearcuenta;
    protected ProgressBar Barradeprogreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_correo);
        mAuth = FirebaseAuth.getInstance();
        btnempezarcorreo=(Button)findViewById(R.id.btnempezarcorreo);
        txtcrearcuenta=(TextView)findViewById(R.id.txtcrearcuenta);
        txtcontrasena=(EditText)findViewById(R.id.txtcontrasena);
        terminos=(TextView)findViewById(R.id.terminos);
        txtcorreo=(EditText)findViewById(R.id.txtcorreo);
        Barradeprogreso=(ProgressBar)findViewById(R.id.Barradeprogreso);
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user =mAuth.getCurrentUser();
                if(user==null) {
                    //No esta logueado
                }
                else
                {
                    Barradeprogreso.setVisibility(View.VISIBLE);
                    Intent Inicio =new Intent(InicioCorreo.this, com.example.javier.proyectodinny.Inicio.class);
                    startActivity(Inicio);
                    Barradeprogreso.setVisibility(View.INVISIBLE);
                    Toast.makeText(InicioCorreo.this, "Credenciales Correctas", Toast.LENGTH_LONG).show();
                }

            }

        };


        btnempezarcorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Barradeprogreso.setVisibility(View.VISIBLE);
                LogearUsuario();
            }
        });


        txtcrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Inicio =new Intent(InicioCorreo.this, com.example.javier.proyectodinny.Registro.class);
                startActivity(Inicio);
            }
        });
    }

    private void LogearUsuario() {
        String email = txtcorreo.getText().toString();
        String password = txtcontrasena.getText().toString();

        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)) {

            Toast.makeText(InicioCorreo.this, "Ingresa la información en los campos de Texto", Toast.LENGTH_LONG).show();
            Barradeprogreso.setVisibility(View.INVISIBLE);
        }
        else
        {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(InicioCorreo.this, "Credenciales Correctas", Toast.LENGTH_LONG).show();
                                Intent Inicio =new Intent(InicioCorreo.this, Inicio.class);
                                startActivity(Inicio);
                                Barradeprogreso.setVisibility(View.INVISIBLE);

                            }
                            else {
                                Toast.makeText(InicioCorreo.this, "Credenciales Incorrectas", Toast.LENGTH_LONG).show();
                                Barradeprogreso.setVisibility(View.INVISIBLE);
                            }

                            Barradeprogreso.setVisibility(View.INVISIBLE);

                            // ...
                        }
                    });
        }
    }
}
