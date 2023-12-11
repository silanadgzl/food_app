package com.example.xxxxxxxxxxx.data.remote;
import com.example.xxxxxxxxxxx.model.SepettekiYemekleriGetirDto;
import com.example.xxxxxxxxxxx.model.TumYemekleriGetirDto;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface YemekServisi {


    @GET("yemekler/tumYemekleriGetir.php")
    Call<TumYemekleriGetirDto> tumYemekleriGetir();

    @FormUrlEncoded
    @POST("yemekler/sepeteYemekEkle.php")
    Call<SepettekiYemekleriGetirDto> sepeteYemekEkle(
            @Field("yemek_adi") String yemekAdi,
            @Field("yemek_resim_adi") String yemekResimAdi,
            @Field("yemek_fiyat") int yemekFiyat,
            @Field("yemek_siparis_adet") int yemekSiparisAdet,
            @Field("kullanici_adi") String kullaniciAdi
    );

    @FormUrlEncoded
    @POST("yemekler/sepettekiYemekleriGetir.php")
    Call<SepettekiYemekleriGetirDto> sepettekiYemekleriGetir(@Field("kullanici_adi") String kullaniciAdi);

    @FormUrlEncoded
    @POST("yemekler/sepettenYemekSil.php")
    Call<SepettekiYemekleriGetirDto> sepettenYemekSil(
            @Field("sepet_yemek_id") int sepetYemekId,
            @Field("kullanici_adi") String kullaniciAdi
    );
}



