package com.example.xxxxxxxxxxx.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TumYemekleriGetirDto {

    public static final String BASE_URL = "http://kasimadalan.pe.hu/";

    @SerializedName("yemekler")
    private List<YemekDto> yemekler;

    // Getter metodu

    public List<YemekDto> getYemekler() {
        return yemekler;
    }
}