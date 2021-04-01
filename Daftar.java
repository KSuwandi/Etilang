package com.example.app_etilank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Daftar extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private FirebaseAuth mAuth;
    private EditText editTextNoNik, editTextAlamat, editTextPekerjaan, editTextPassword, editTextEmail, editTextNama;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        editTextNoNik = (EditText) findViewById(R.id.nonik);
        editTextAlamat = (EditText) findViewById(R.id.alamat);
        editTextPekerjaan = (EditText) findViewById(R.id.pekerjaan);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextNama = (EditText) findViewById(R.id.nama);

        progressBar = (ProgressBar) findViewById(R.id.progressBardaftar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
            case R.id.registerUser:
                btn_regis();
                break;
        }
    }

    private void btn_regis() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String nama = editTextNama.getText().toString().trim();
        String nonik = editTextNoNik.getText().toString().trim();
        String alamat = editTextAlamat.getText().toString().trim();
        String pekerjaan = editTextPekerjaan.getText().toString().trim();


        if (nonik.isEmpty()) {
            editTextNoNik.setError("Nonik harus di isi!");
            editTextNoNik.requestFocus();
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email di butuhkan!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Tolong masukkan email valid");
            editTextEmail.requestFocus();
            return;
        }
        if (nama.isEmpty()) {
            editTextNama.setError("Nama lengkap di butuhkan!");
            editTextNama.requestFocus();
            return;
        }
        if (alamat.isEmpty()) {
            editTextNama.setError("Alamat di butuhkan!");
            editTextNama.requestFocus();
            return;
        }
        if (pekerjaan.isEmpty()) {
            editTextPekerjaan.setError("Pekerjaan di butuhkan!");
            editTextPekerjaan.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password di butuhkan!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimal password harus 6 huruf!");
            editTextPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(nonik,email,alamat,pekerjaan,password,nama);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Daftar.this,"Akun berhasil terdaftar!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        startActivity(new Intent(Daftar.this, Login.class));

                                        //Beralih ke page Login
                                    }else {
                                        Toast.makeText(Daftar.this,"Akun gagal terdaftar! Coba kembali!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(Daftar.this,"Akun gagal terdaftar! Coba kembali!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}