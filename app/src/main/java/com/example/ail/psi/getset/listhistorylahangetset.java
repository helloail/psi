package com.example.ail.psi.getset;

public class listhistorylahangetset {



    private String  nama,id,alamat,status;
    private int periode;

    public listhistorylahangetset() {
    }

    public listhistorylahangetset(String nama,String id, String status, int periode) {

        this. id = id;
        this. nama = nama;
        this.periode = periode;
        this.status=status;

    }

    public String getNama() {
        return nama;
    }

    public String getAlamat (){return  alamat;}

    public String getStatus (){return  status;}

    public int getPeriode () {return periode;}

    public String getId (){return  id;}


    public void setAlamat (String alamat) {this.alamat = alamat;}

    public void setStatus (String status) {this.status = status;}

    public  int setPeriode (int periode) {return this.periode=periode;}

    public void setID (String id) {this.id = id; }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
