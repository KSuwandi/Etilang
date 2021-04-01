package com.example.app_etilank;

public class User {

    public String nonik, email, alamat, pekerjaan, password, nama;

    public User(){

    }

    public  User(String nonik, String email, String alamat, String pekerjaan, String password, String nama){
        this.nonik = nonik;
        this.email = email;
        this.alamat = alamat;
        this.pekerjaan = pekerjaan;
        this.password = password;
        this.nama = nama;
    }
}
