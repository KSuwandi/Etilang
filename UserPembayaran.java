package com.example.app_etilank;

public class UserPembayaran {

    public String pemid ,kodepem , jenispel, noseri, jeniskend, totaldenda;

    public UserPembayaran(){

    }

    public UserPembayaran(String pemid , String kodepem , String jenispel, String noseri, String jeniskend, String totaldenda){
        this.pemid = pemid;
        this.kodepem = kodepem;
        this.jenispel = jenispel;
        this.noseri = noseri;
        this.jeniskend = jeniskend;
        this.totaldenda = totaldenda;

    }

}
