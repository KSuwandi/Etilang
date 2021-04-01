package com.example.app_etilank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    private Button daftar_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/d2/cb/59/d2cb5971f816171fdd1a52d97fcfca36.png",""));
        slideModels.add(new SlideModel("https://syarisandi33.files.wordpress.com/2018/07/pocil_lantas_promoter_by_winarasetyo-dakj3d8.png",""));
        slideModels.add(new SlideModel("https://lh3.googleusercontent.com/proxy/dp6_rfSr8QJ6hilmE5dKW0nDSPSnUuOXGtnxKyECapVihKIUtO-OKocIdNmYHbrucsSR-1l_d3ARd5w",""));
        slideModels.add(new SlideModel("https://cdn.medcom.id/dynamic/content/2021/01/21/1234059/8UZOvI3xfm.jpg?w=480",""));
        imageSlider.setImageList(slideModels,true);


        login_btn = (Button)findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                keloginpage();
            }
        });
        daftar_btn = (Button)findViewById(R.id.btn_daftar);
        daftar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kedaftarpage();
            }
        });
    }

    public void keloginpage(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void kedaftarpage(){
        Intent intent = new Intent(this, Daftar.class);
        startActivity(intent);
    }

}