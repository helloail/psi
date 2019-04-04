package com.example.ail.psi.getset;

public class updateLahangetset {
    private String  nama,id,createdon,status;
    private int periode;

    public updateLahangetset() {
    }

    public updateLahangetset(String nama,String id, String createdon, int periode) {

        this. id = id;
        this. nama = nama;
        this.periode = periode;
        this.createdon=createdon;

    }

    public String getNama() { return nama; }

    public String getCreatedon (){return  createdon;}

    public String getStatus (){return  status;}

    public int getPeriode () {return periode;}

    public String getId (){return  id;}


    public void setCreatedon (String createdon) {this.createdon = createdon;}

    public void setStatus (String status) {this.status = status;}

    public  int setPeriode (int periode) {return this.periode=periode;}

    public void setID (String id) {this.id = id; }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
