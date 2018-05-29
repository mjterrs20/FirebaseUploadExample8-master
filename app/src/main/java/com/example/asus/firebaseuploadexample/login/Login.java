package com.example.asus.firebaseuploadexample.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.firebaseuploadexample.Depan;
import com.example.asus.firebaseuploadexample.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignup;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance ();
        if (firebaseAuth.getCurrentUser () != null) {
            finish();
            startActivity ( new Intent ( getApplicationContext (), Depan.class ));
        }
        editTextEmail = (EditText) findViewById ( R.id.editTextEmail );
        editTextPassword = (EditText) findViewById ( R.id.editTextPassword );
        buttonSignup = (Button) findViewById ( R.id.buttonSignup );
        textViewSignup = (TextView) findViewById ( R.id.textViewSignup );

        progressDialog = new ProgressDialog ( this );
        buttonSignup.setOnClickListener ( this );
        textViewSignup.setOnClickListener ( this );
    }

    private void userLogin(){

        String email = editTextEmail.getText ().toString ().trim ();
        String password = editTextPassword.getText ().toString ().trim ();

        if(TextUtils.isEmpty ( email )){
            // email is empty
            Toast.makeText ( this,"Please enter email",Toast.LENGTH_SHORT).show ();
            return;
        }
        if(TextUtils.isEmpty ( password )){
            // password is empty
            Toast.makeText ( this,"Please enter password",Toast.LENGTH_SHORT).show ();
            return;
        }

        progressDialog.setMessage ( "Register User" );
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword ( email,password )
                .addOnCompleteListener ( this, new OnCompleteListener <AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss ();
                        if (task.isSuccessful ()){
                            // start profile activity
                            finish();
                            startActivity ( new Intent ( getApplicationContext (), Depan.class ));
                        }
                    }
                } );

    }
    @Override
    public void onClick(View v) {
        if(v == buttonSignup){
            userLogin();
        }
        if (v == textViewSignup){
            finish();
            startActivity ( new Intent ( this, Register.class ) );
        }
    }

}
