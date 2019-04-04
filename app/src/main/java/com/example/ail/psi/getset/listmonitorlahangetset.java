package com.example.ail.psi.getset;

public class listmonitorlahangetset {

        private String  nama,id,alamat,lastcek,periode;
//        private int periode;

        public listmonitorlahangetset() {
        }

        public listmonitorlahangetset(String nama,String id, String status, String periode) {

            this. id = id;
            this. nama = nama;
            this. lastcek = lastcek;


        }

        public String getNama() {
            return nama;
        }

        public String getAlamat (){return  alamat;}

        public String getLastcek (){return  lastcek;}

        public String getPeriode () {return periode;}

        public String getId (){return  id;}


        public void setAlamat (String alamat) {this.alamat = alamat;}

        public void setLastcek (String lastcek) {this.lastcek = lastcek;}

        public String setPeriode (String periode) {return this.periode=periode;}

        public void setID (String id) {this.id = id; }

        public void setNama(String nama) {
            this.nama = nama;
        }

}
