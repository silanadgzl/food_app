package com.example.xxxxxxxxxxx.model;
import com.google.gson.annotations.SerializedName;

public class YemekDto {
    @SerializedName("yemek_id")
    private int yemekId;

    @SerializedName("yemek_adi")
    private String yemekAdi;

    @SerializedName("yemek_resim_adi")
    private String yemekResimAdi;

    @SerializedName("yemek_fiyat")
    private int yemekFiyat;

    // Getter metotları

    public String getYemekAdi() {
        return yemekAdi;
    }

    public String getYemekResimAdi() {
        return yemekResimAdi;
    }

    public int getYemekFiyat() {
        return yemekFiyat;
    }

}