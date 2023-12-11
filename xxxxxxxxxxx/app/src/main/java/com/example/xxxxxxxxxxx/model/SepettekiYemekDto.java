package com.example.xxxxxxxxxxx.model;
import com.google.gson.annotations.SerializedName;

public class SepettekiYemekDto {
    @SerializedName("sepet_yemek_id")
    private int sepetYemekId;

    @SerializedName("yemek_adi")
    private String yemekAdi;

    @SerializedName("yemek_resim_adi")
    private String yemekResimAdi;

    @SerializedName("yemek_fiyat")
    private int yemekFiyat;

    @SerializedName("yemek_siparis_adet")
    private int yemekSiparisAdet;

    @SerializedName("kullanici_adi")
    private String kullaniciAdi;

    // Getter metotları

    public int getSepetYemekId() {
        return sepetYemekId;
    }

    public String getYemekAdi() {
        return yemekAdi;
    }

    public String getYemekResimAdi() {
        return yemekResimAdi;
    }

    public int getYemekFiyat() {
        return yemekFiyat;
    }

    public int getYemekSiparisAdet() {
        return yemekSiparisAdet;
    }

    public void setYemekSiparisAdet(int yemekSiparisAdet) {
        this.yemekSiparisAdet = yemekSiparisAdet;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

}
