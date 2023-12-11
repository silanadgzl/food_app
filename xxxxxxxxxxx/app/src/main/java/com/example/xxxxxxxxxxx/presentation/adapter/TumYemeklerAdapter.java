package com.example.xxxxxxxxxxx.presentation.adapter;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.xxxxxxxxxxx.MainActivity;
import com.example.xxxxxxxxxxx.R;
import com.example.xxxxxxxxxxx.model.TumYemekleriGetirDto;
import com.example.xxxxxxxxxxx.model.YemekDto;
import com.example.xxxxxxxxxxx.presentation.fragment.DetayFragment;
import java.util.List;

public class TumYemeklerAdapter extends RecyclerView.Adapter<TumYemeklerAdapter.YemekViewHolder> {

    private List<YemekDto> yemekDtoListesi;
    private Context context;

    public TumYemeklerAdapter(Context context, List<YemekDto> yemekDtoListesi) {
        this.context = context;
        this.yemekDtoListesi = yemekDtoListesi;
    }

    public void setYemekListesi(List<YemekDto> yemekDtoListesi) {
        this.yemekDtoListesi = yemekDtoListesi;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public YemekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_anasayfa, parent, false);
        return new YemekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YemekViewHolder holder, int position) {
        YemekDto yemekDto = yemekDtoListesi.get(position);

        String resimAdi = yemekDto.getYemekResimAdi();
        String resimURL = TumYemekleriGetirDto.BASE_URL + "yemekler/resimler/" + resimAdi.toLowerCase();

        holder.txtYemekAdi.setText(yemekDto.getYemekAdi());
        holder.txtYemekFiyati.setText(yemekDto.getYemekFiyat() + " ₺");

        Glide.with(context)
                .load(resimURL)
                .placeholder(R.drawable.placeholder_image) // Varsayılan resim
                .into(holder.imgYemekResim);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("yemekAdi", yemekDto.getYemekAdi());
                bundle.putInt("yemekFiyati", yemekDto.getYemekFiyat());
                bundle.putString("yemekResimURL", resimURL);

                DetayFragment detayFragment = new DetayFragment();
                detayFragment.setArguments(bundle);

                if (context instanceof MainActivity) {
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, detayFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return yemekDtoListesi.size();
    }

    static class YemekViewHolder extends RecyclerView.ViewHolder {
        ImageView imgYemekResim;
        TextView txtYemekAdi, txtYemekFiyati;

        YemekViewHolder(@NonNull View itemView) {
            super(itemView);
            imgYemekResim = itemView.findViewById(R.id.imgYemekResim);
            txtYemekAdi = itemView.findViewById(R.id.txtYemekAdi);
            txtYemekFiyati = itemView.findViewById(R.id.txtYemekFiyati);
        }
    }
}
