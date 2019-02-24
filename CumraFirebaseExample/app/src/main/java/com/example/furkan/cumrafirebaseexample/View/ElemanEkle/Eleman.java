package com.example.furkan.cumrafirebaseexample.View.ElemanEkle;

public class Eleman {

    public Eleman() {
    }
    //fotograf=fotografın url adresidir..
    public String ad_soyad;
    public String fotograf;
    public String ogrenim;
    public String telefon;
    public String cinsiyet;
    public String görev;

    public Eleman(String ad_soyad, String fotograf, String ogrenim, String telefon, String cinsiyet, String görev, String email, String ise_giris_tarihi, String departman, String maas) {
        this.ad_soyad = ad_soyad;
        this.fotograf = fotograf;
        this.ogrenim = ogrenim;
        this.telefon = telefon;
        this.cinsiyet = cinsiyet;
        this.görev = görev;
        this.email = email;
        this.ise_giris_tarihi = ise_giris_tarihi;
        this.departman = departman;
        this.maas = maas;
    }

    public String email;
    public String ise_giris_tarihi;
    public String departman;
    public String maas;

    public String getAd_soyad() {
        return ad_soyad;
    }

    public void setAd_soyad(String ad_soyad) {
        this.ad_soyad = ad_soyad;
    }

    public String getFotograf() {
        return fotograf;
    }

    public void setFotograf(String fotograf) {
        this.fotograf = fotograf;
    }

    public String getOgrenim() {
        return ogrenim;
    }

    public void setOgrenim(String ogrenim) {
        this.ogrenim = ogrenim;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getGörev() {
        return görev;
    }

    public void setGörev(String görev) {
        this.görev = görev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIse_giris_tarihi() {
        return ise_giris_tarihi;
    }

    public void setIse_giris_tarihi(String ise_giris_tarihi) {
        this.ise_giris_tarihi = ise_giris_tarihi;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getMaas() {
        return maas;
    }

    public void setMaas(String maas) {
        this.maas = maas;
    }


}
