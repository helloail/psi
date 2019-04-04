
package com.example.ail.psi.getset;

public class tampilMonitoringgetset {

    private String  nama,id,alamat,tanggal;
    private int periode;

    public tampilMonitoringgetset () {
    }

    public tampilMonitoringgetset (String nama,String id, String tanggal, int periode) {

        this. id = id;
        this. nama = nama;
        this.periode = periode;
        this.tanggal = tanggal;

    }

    public String getNama() {
        return nama;
    }

    public String getAlamat (){return  alamat;}

    public String getTanggal (){return tanggal;}

    public int getPeriode () {return periode;}

    public String getId (){return  id;}


    public void setAlamat (String alamat) {this.alamat = alamat;}

    public void setTanggal (String tanggal) {
        this.tanggal = tanggal;
    }

    public  int setPeriode (int periode) {return this.periode=periode;}

    public void setID (String id) {this.id = id; }

    public void setNama(String nama) {
        this.nama = nama;
    }


}
