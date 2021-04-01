package com.example.app_etilank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    TextView textUser, textNonik, textgreeting ;
    ImageButton profilbtn , bayarbtn;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        textgreeting=(TextView)findViewById(R.id.greeting);
        textUser=(TextView)findViewById(R.id.homenama);
        textNonik=(TextView)findViewById(R.id.homenonik);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null){
                    String nama = userprofile.nama;
                    String nonik = userprofile.nonik;
                    textgreeting.setText("Selamat Datang, " + nama + "!");
                    textUser.setText(nama);
                    textNonik.setText(nonik);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this,"Sesuatu kesalahan terjadi!", Toast.LENGTH_LONG).show();
            }
        });


        profilbtn = (ImageButton) findViewById(R.id.btnprofil);
        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Home.this, Profil.class);
                startActivity(intentLoadNewActivity);
            }
        });

        bayarbtn = (ImageButton) findViewById(R.id.btnbayar);
        bayarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Home.this, pembayaran.class);
                startActivity(intentLoadNewActivity);
            }
        });

    }

}