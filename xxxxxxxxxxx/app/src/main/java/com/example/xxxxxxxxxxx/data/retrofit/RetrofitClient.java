package com.example.xxxxxxxxxxx.data.retrofit;
import com.example.xxxxxxxxxxx.model.TumYemekleriGetirDto;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Loglama interceptor'ını oluştur
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttpClient oluştur ve loglama interceptor'ını ekle
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Retrofit istemcisini oluştur ve OkHttpClient'ini belirle
            retrofit = new Retrofit.Builder()
                    .baseUrl(TumYemekleriGetirDto.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
