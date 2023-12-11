package com.example.xxxxxxxxxxx.presentation.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.xxxxxxxxxxx.R;
import com.example.xxxxxxxxxxx.data.retrofit.RetrofitClient;
import com.example.xxxxxxxxxxx.data.remote.YemekServisi;
import com.example.xxxxxxxxxxx.databinding.FragmentDetayBinding;
import com.example.xxxxxxxxxxx.model.SepettekiYemekleriGetirDto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetayFragment extends Fragment {

    private TextView tvCount, textViewDetayToplam;
    private int count = 1;
    private int yemekFiyati = 0;
    private String yemekAdi = "";
    private String yemekResimURL = "";
    private FragmentDetayBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetayBinding.inflate(inflater, container, false);

        tvCount = binding.textYemekSiparisAdet;
        textViewDetayToplam = binding.textViewDetayToplam;
        FloatingActionButton btnIncrease = binding.btnIncrease;
        FloatingActionButton btnDecrease = binding.btnDecrease;
        RatingBar ratingBar = binding.ratingBar;


        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                updateToplamFiyat();
                tvCount.setText(String.valueOf(count));
            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    count--;
                    updateToplamFiyat();
                    tvCount.setText(String.valueOf(count));
                }
            }
        });



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            }
        });


        binding.buttonSepeteEkle.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.detaydansepetegecis);
        });

        return binding.getRoot();
    }

    private void updateToplamFiyat() {
        int toplamFiyat = count * yemekFiyati;
        textViewDetayToplam.setText("Toplam: "+toplamFiyat + " ₺");
    }

    public void sepeteYemekEkle() {
        // Retrofit çağrısı yaparak sepete yemek ekle
        YemekServisi yemekServisi = RetrofitClient.getClient().create(YemekServisi.class);
        Call<SepettekiYemekleriGetirDto> call = yemekServisi.sepeteYemekEkle(yemekAdi, yemekResimURL, yemekFiyati, count, "aabbccdd");
        call.enqueue(new Callback<SepettekiYemekleriGetirDto>() {
            @Override
            public void onResponse(Call<SepettekiYemekleriGetirDto> call, Response<SepettekiYemekleriGetirDto> response) {
                if (response.isSuccessful()) {
                    Log.d("Sepete Ekle", "YemekDto başarıyla sepete eklendi");
                    Log.d("Sepete Ekle", "İşlem başarılı: " + response.isSuccessful());


                } else {
                    Log.e("Hata", "Sepete ekleme işlemi başarısız: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<SepettekiYemekleriGetirDto> call, Throwable t) {
                Log.e("Hata", "Sepete ekleme işlemi başarısız: " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            yemekAdi = bundle.getString("yemekAdi", "");
            yemekFiyati = bundle.getInt("yemekFiyati", 0);
            yemekResimURL = bundle.getString("yemekResimURL", "");

            binding.txtDetayYemekAdi.setText(yemekAdi);
            binding.txtDetayYemekFiyati.setText(yemekFiyati + " ₺");

            Glide.with(requireContext())
                    .load(yemekResimURL)
                    .placeholder(R.drawable.placeholder_image)
                    .into(binding.imageViewDetay);

            updateToplamFiyat();
        }

        binding.buttonSepeteEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sepeteYemekEkle();
            }
        });
    }

}