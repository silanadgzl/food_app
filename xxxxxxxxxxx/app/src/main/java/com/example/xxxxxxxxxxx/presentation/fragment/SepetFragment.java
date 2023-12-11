package com.example.xxxxxxxxxxx.presentation.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xxxxxxxxxxx.R;
import com.example.xxxxxxxxxxx.data.retrofit.RetrofitClient;
import com.example.xxxxxxxxxxx.presentation.adapter.SepetAdapter;
import com.example.xxxxxxxxxxx.data.remote.YemekServisi;
import com.example.xxxxxxxxxxx.model.SepettekiYemekDto;
import com.example.xxxxxxxxxxx.model.SepettekiYemekleriGetirDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SepetFragment extends Fragment {

    private RecyclerView recyclerView;
    private SepetAdapter sepetAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sepet, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSepet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sepetAdapter = new SepetAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(sepetAdapter);

        getSepetVerileri();

        return view;
    }

    public void getSepetVerileri() {
        YemekServisi yemekServisi = RetrofitClient.getClient().create(YemekServisi.class);
        Call<SepettekiYemekleriGetirDto> call = yemekServisi.sepettekiYemekleriGetir("aabbccdd");
        call.enqueue(new Callback<SepettekiYemekleriGetirDto>() {
            @Override
            public void onResponse(Call<SepettekiYemekleriGetirDto> call, Response<SepettekiYemekleriGetirDto> response) {
                if (response.isSuccessful()) {
                    SepettekiYemekleriGetirDto sepettekiYemekleriGetirDto = response.body();
                    int success = sepettekiYemekleriGetirDto.getSuccess();

                    if (success == 1) {
                        // Başarıyla alındı
                        List<SepettekiYemekDto> sepettekiYemeklerDto = sepettekiYemekleriGetirDto.getSepetYemekler();
                        sepetAdapter.setSepetYemekListesi(sepettekiYemeklerDto);

                    } else {
                        Log.e("Retrofit", "Sepetteki yemekleri getirirken başarısız: " + sepettekiYemekleriGetirDto.getMessage());
                        Log.e("Retrofit", "Sepetteki yemekleri getirirken başarısız: " + sepettekiYemekleriGetirDto.getSuccess());
                    }
                } else {
                    Log.e("Retrofit", "Hata kodu: " + response.code());
                    Log.e("Retrofit", "Hata mesajı: " + response.message());
                    try {
                        Log.e("Retrofit", "Hata body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SepettekiYemekleriGetirDto> call, Throwable t) {
                Log.e("Retrofit", "Sepet verileri alınırken hata oluştu: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
