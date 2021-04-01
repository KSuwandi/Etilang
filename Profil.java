package com.example.app_etilank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Profil extends AppCompatActivity {

    private TextView profilnama, profilalamat, profilpekerjaan, profilemail;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logout;
    ImageButton btnprofilhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        profilnama=(TextView)findViewById(R.id.profilnama);
        profilalamat=(TextView)findViewById(R.id.profilalamat);
        profilpekerjaan=(TextView)findViewById(R.id.profilpekerjaan);
        profilemail=(TextView)findViewById(R.id.profilemail);

        logout = (Button) findViewById(R.id.btnlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profil.this,Login.class));
            }
        });

        btnprofilhome = (ImageButton) findViewById(R.id.btnprofilhome);
        btnprofilhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Profil.this, Home.class);
                startActivity(intentLoadNewActivity);
            }
        });


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null){
                    String nama = userprofile.nama;
                    String alamat = userprofile.alamat;
                    String pekerjaan = userprofile.pekerjaan;
                    String email = userprofile.email;
                    profilnama.setText(nama);
                    profilalamat.setText(alamat);
                    profilpekerjaan.setText(pekerjaan);
                    profilemail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profil.this,"Sesuatu kesalahan terjadi!", Toast.LENGTH_LONG).show();
            }
        });


    }
}