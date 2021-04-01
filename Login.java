package com.example.app_etilank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextemail, editTextpassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = (Button)findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextemail = (EditText)findViewById(R.id.email);
        editTextpassword = (EditText)findViewById(R.id.password);
        progressBar = (ProgressBar)findViewById(R.id.progressBarlogin);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signIn:
                userLogin();
                break;
        }

    }

    private void userLogin(){
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextemail.setError("Email di butuhkan!");
            editTextemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Tolong masukkan email valid");
            editTextemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpassword.setError("Password di butuhkan!");
            editTextpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("Minimal password harus 6 huruf!");
            editTextpassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //ke profil menu utama
                            startActivity(new Intent(Login.this, Home.class));

                        }else{
                            Toast.makeText(Login.this,"Data akun salah! Coba kembali!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}