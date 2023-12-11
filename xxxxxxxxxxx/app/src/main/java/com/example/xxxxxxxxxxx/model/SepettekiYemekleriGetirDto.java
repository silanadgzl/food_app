package com.example.xxxxxxxxxxx.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SepettekiYemekleriGetirDto {
    @SerializedName("sepet_yemekler")
    private List<SepettekiYemekDto> sepettekiYemeklerDto;

    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;

    // Getter metotları

    public List<SepettekiYemekDto> getSepetYemekler() {
        return sepettekiYemeklerDto;
    }


    public SepettekiYemekleriGetirDto(List<SepettekiYemekDto> sepettekiYemeklerDto, int success, String message) {
        this.sepettekiYemeklerDto = sepettekiYemeklerDto;
        this.success = success;
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
}
