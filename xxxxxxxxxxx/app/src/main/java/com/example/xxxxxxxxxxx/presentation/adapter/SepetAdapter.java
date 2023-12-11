package com.example.xxxxxxxxxxx.presentation.adapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.xxxxxxxxxxx.R;
import com.example.xxxxxxxxxxx.data.remote.YemekServisi;
import com.example.xxxxxxxxxxx.data.retrofit.RetrofitClient;
import com.example.xxxxxxxxxxx.model.SepettekiYemekDto;
import com.example.xxxxxxxxxxx.model.SepettekiYemekleriGetirDto;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SepetAdapter extends RecyclerView.Adapter<SepetAdapter.SepetViewHolder> implements OnQuantityChangeListener {
    private List<SepettekiYemekDto> sepettekiYemekDtoListesi;
    private Context context;

    public SepetAdapter(Context context, List<SepettekiYemekDto> sepettekiYemekDtoListesi) {
        this.context = context;
        this.sepettekiYemekDtoListesi = sepettekiYemekDtoListesi;
    }

    public void setSepetYemekListesi(List<SepettekiYemekDto> sepettekiYemekDtoListesi) {
        if (sepettekiYemekDtoListesi != null) {
            this.sepettekiYemekDtoListesi = sepettekiYemekDtoListesi;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public SepetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_sepet, parent, false);
        return new SepetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SepetViewHolder holder, int position) {
        SepettekiYemekDto sepettekiYemekDto = sepettekiYemekDtoListesi.get(position);

        String resimAdi = sepettekiYemekDto.getYemekResimAdi();
        String resimURL = resimAdi.toLowerCase();

        holder.txtYemekAdi.setText(sepettekiYemekDto.getYemekAdi());
        holder.txtYemekFiyati.setText(sepettekiYemekDto.getYemekFiyat() + "₺");
        holder.txtYemekSiparisAdet.setText("Adet: " + sepettekiYemekDto.getYemekSiparisAdet());

        Glide.with(context)
                .load(resimURL)
                .placeholder(R.drawable.placeholder_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Resim yüklenirken hata oluştu: " + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imgYemekResim);

        holder.imageButtonArttir2.setOnClickListener(v -> onIncreaseQuantity(position));
        holder.imageButtonAzalt2.setOnClickListener(v -> onDecreaseQuantity(position));

        holder.imageViewsil.setOnClickListener(v -> {
            int sepetYemekId = sepettekiYemekDto.getSepetYemekId();
            String kullaniciAdi = sepettekiYemekDto.getKullaniciAdi();
            showDeleteConfirmationSnackbar(sepetYemekId, kullaniciAdi, position);
        });

        int yemekFiyati = sepettekiYemekDto.getYemekFiyat();
        int yemekAdeti = sepettekiYemekDto.getYemekSiparisAdet();
        int toplamFiyat = yemekFiyati * yemekAdeti;

        holder.cardToplam.setText("Toplam: " + toplamFiyat + "₺");
    }

    @Override
    public int getItemCount() {
        return sepettekiYemekDtoListesi.size();
    }

    @Override
    public void onIncreaseQuantity(int position) {
        SepettekiYemekDto yemekDto = sepettekiYemekDtoListesi.get(position);
        yemekDto.setYemekSiparisAdet(yemekDto.getYemekSiparisAdet() + 1);
        notifyItemChanged(position);
    }

    @Override
    public void onDecreaseQuantity(int position) {
        SepettekiYemekDto yemekDto = sepettekiYemekDtoListesi.get(position);
        if (yemekDto.getYemekSiparisAdet() > 1) {
            yemekDto.setYemekSiparisAdet(yemekDto.getYemekSiparisAdet() - 1);
            notifyItemChanged(position);
        }
    }

    private void silSepettenYemek(int sepetYemekId, String kullaniciAdi, int position) {
        YemekServisi yemekServisi = RetrofitClient.getClient().create(YemekServisi.class);
        Call<SepettekiYemekleriGetirDto> call = yemekServisi.sepettenYemekSil(sepetYemekId, kullaniciAdi);
        call.enqueue(new Callback<SepettekiYemekleriGetirDto>() {
            @Override
            public void onResponse(Call<SepettekiYemekleriGetirDto> call, Response<SepettekiYemekleriGetirDto> response) {
                if (response.isSuccessful()) {
                    SepettekiYemekleriGetirDto sepettekiYemekleriGetirDto = response.body();
                    int success = sepettekiYemekleriGetirDto.getSuccess();

                    if (success == 1) {
                        sepettekiYemekDtoListesi.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Log.e("Retrofit", "YemekDto silinirken başarısız: " + sepettekiYemekleriGetirDto.getMessage());
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
                Log.e("Retrofit", "YemekDto silinirken hata oluştu: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void showDeleteConfirmationSnackbar(int sepetYemekId, String kullaniciAdi, int position) {
        Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content),
                        "Bu yemeği sepetten silmek istediğinizden emin misiniz?", Snackbar.LENGTH_SHORT)
                .setAction("Evet", v -> silSepettenYemek(sepetYemekId, kullaniciAdi, position))
                .show();
    }

    class SepetViewHolder extends RecyclerView.ViewHolder {
        TextView cardToplam;
        ImageView imgYemekResim, imageViewsil;
        TextView txtYemekAdi, txtYemekFiyati, txtYemekSiparisAdet;
        ImageButton imageButtonArttir2, imageButtonAzalt2;

        SepetViewHolder(@NonNull View itemView) {
            super(itemView);
            imgYemekResim = itemView.findViewById(R.id.imgYemekResim);
            txtYemekAdi = itemView.findViewById(R.id.txtYemekAdi);
            txtYemekFiyati = itemView.findViewById(R.id.txtYemekFiyati);
            txtYemekSiparisAdet = itemView.findViewById(R.id.txtYemekSiparisAdet);
            imageViewsil = itemView.findViewById(R.id.imageViewsil);
            imageButtonArttir2 = itemView.findViewById(R.id.imageButtonArttir2);
            imageButtonAzalt2 = itemView.findViewById(R.id.imageButtonAzalt2);
            cardToplam = itemView.findViewById(R.id.cardToplam);
        }
    }
}
