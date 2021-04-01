package com.example.app_etilank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Statuspembayaran extends AppCompatActivity {
    private TextView hasilpem1,hasilpem2,hasilpem3,hasilpem4,hasilpem5;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statuspembayaran);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Pembayaran");
        userID = user.getUid();

        hasilpem1 = (TextView)findViewById(R.id.hasilpem1);
        hasilpem2 = (TextView)findViewById(R.id.hasilpem2);
        hasilpem3 = (TextView)findViewById(R.id.hasilpem3);
        hasilpem4 = (TextView)findViewById(R.id.hasilpem4);
        hasilpem5 = (TextView)findViewById(R.id.hasilpem5);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserPembayaran userprofile = snapshot.getValue(UserPembayaran.class);

                if (userprofile != null){
                    String pemid = userprofile.pemid;
                    String kodepem = userprofile.kodepem;
                    String jenispel = userprofile.jenispel;
                    String noseri = userprofile.noseri;
                    String jeniskendaraan = userprofile.jeniskend;
                    String totaldenda = userprofile.totaldenda;
                    hasilpem1.setText(kodepem);
                    hasilpem2.setText(jenispel);
                    hasilpem3.setText(noseri);
                    hasilpem4.setText(jeniskendaraan);
                    hasilpem5.setText(totaldenda);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Statuspembayaran.this,"Sesuatu kesalahan terjadi!", Toast.LENGTH_LONG).show();
            }
        });
    }
}