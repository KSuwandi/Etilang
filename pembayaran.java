package com.example.app_etilank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class pembayaran extends AppCompatActivity{

    private TextView masukanData;
    private FirebaseAuth mAuth;
    private EditText editTextkodePem, editTextjenisPel, editTextnoSeri, editTextjenisKend, editTexttotalDenda;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        mAuth = FirebaseAuth.getInstance();
        editTextkodePem = (EditText) findViewById(R.id.kodepemb);
        editTextjenisPel = (EditText) findViewById(R.id.jenispel);
        editTextnoSeri = (EditText) findViewById(R.id.noseri);
        editTextjenisKend = (EditText) findViewById(R.id.jeniskend);
        editTexttotalDenda = (EditText) findViewById(R.id.totaldenda);

        databaseReference = FirebaseDatabase.getInstance().getReference("Pembayaran");

        TextView msg_text  = findViewById(R.id.txt_msg);
        ImageButton btn_fingerprint = findViewById(R.id.btnbayardenda);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                msg_text.setText("Anda bisa menggunakan fingerprint untuk membayar");
                msg_text.setTextColor(Color.parseColor("#Fafafa"));
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msg_text.setText("Device tidak memiliki sensor fingerprint");
                btn_fingerprint.setVisibility(View.GONE);
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msg_text.setText("Tidak ada layanan biometric sensor");
                btn_fingerprint.setVisibility(View.GONE);
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msg_text.setText("Device anda tidak mempunyai data fingerprint, tolong cek setting security anda ");
                btn_fingerprint.setVisibility(View.GONE);
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(pembayaran.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),"Pembayaran berhasil", Toast.LENGTH_SHORT).show();
                btnbayardenda();
                startActivity(new Intent(pembayaran.this, Statuspembayaran.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Pembayaran")
                .setDescription("Pakai fingerprint anda untuk melakukan pembayaran")
                .setNegativeButtonText("Batalkan")
                .build();

        btn_fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }

        private void btnbayardenda(){
            String kodepemb = editTextkodePem.getText().toString().trim();
            String jenispel = editTextjenisPel.getText().toString().trim();
            String noseri = editTextnoSeri.getText().toString().trim();
            String jeniskend = editTextjenisKend.getText().toString().trim();
            String totaldenda = editTexttotalDenda.getText().toString().trim();

            if (!TextUtils.isEmpty(kodepemb) && !TextUtils.isEmpty(jenispel) && !TextUtils.isEmpty(noseri)
                    && !TextUtils.isEmpty(jeniskend) && !TextUtils.isEmpty(totaldenda)){
                String id = databaseReference.push().getKey();

                UserPembayaran userPembayaran = new UserPembayaran(id,kodepemb,jenispel,noseri,jeniskend,totaldenda);
                databaseReference.child(id).setValue(userPembayaran);
                editTextkodePem.setText("");
                editTextjenisPel.setText("");
                editTextnoSeri.setText("");
                editTextjenisKend.setText("");
                editTexttotalDenda.setText("");
            }


            if (kodepemb.isEmpty()) {
                editTextkodePem.setError("Tolong di isi !");
                editTextkodePem.requestFocus();
            }
            if (jenispel.isEmpty()) {
                editTextjenisPel.setError("Tolong di isi !");
                editTextjenisPel.requestFocus();
                return;
            }
            if (noseri.isEmpty()) {
                editTextnoSeri.setError("Tolong di isi !");
                editTextnoSeri.requestFocus();
                return;
            }
            if (jeniskend.isEmpty()) {
                editTextjenisKend.setError("Tolong di isi !n!");
                editTextjenisKend.requestFocus();
                return;
            }
            if (totaldenda.isEmpty()) {
                editTexttotalDenda.setError("Tolong di isi !");
                editTexttotalDenda.requestFocus();
                return;
            }

        }
    }
