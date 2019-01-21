package co.disperindag.pkl_01.Pengguna;

import java.io.Serializable;

public class DataPengguna implements Serializable {

    private String name;
    private String nik;
    private String ttl;
    private String agama;
    private String jk;
    private String alamat;
    private String telp;
    private String pkj;
    private String pwd;
    private String fto;
    private String urlfto;

    DataPengguna() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getPkj() {
        return pkj;
    }

    public void setPkj(String pkj) {
        this.pkj = pkj;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFto() {
        return fto;
    }

    public void setFto(String fto) {
        this.fto = fto;
    }

    public String getUrlfto() {
        return urlfto;
    }

    public void setUrlfto(String urlfto) {
        this.urlfto = urlfto;
    }

    public DataPengguna(String Uname, String Unik, String Uttl, String Uagama, String Ujk, String Ualamat, String Utelp, String Upkj, String Upwd, String Ufto, String Uurl) {

        name = Uname;
        nik = Unik;
        ttl = Uttl;
        agama = Uagama;
        jk = Ujk;
        alamat = Ualamat;
        telp = Utelp;
        pkj = Upkj;
        pwd = Upwd;
        fto = Ufto;
        urlfto = Uurl;

    }
}
