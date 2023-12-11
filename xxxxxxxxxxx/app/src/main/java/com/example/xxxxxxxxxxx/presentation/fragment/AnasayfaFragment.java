package com.example.xxxxxxxxxxx.presentation.fragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import com.example.xxxxxxxxxxx.R;
import com.example.xxxxxxxxxxx.presentation.adapter.TumYemeklerAdapter;
import com.example.xxxxxxxxxxx.data.remote.YemekServisi;
import com.example.xxxxxxxxxxx.model.TumYemekleriGetirDto;
import com.example.xxxxxxxxxxx.model.YemekDto;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnasayfaFragment extends Fragment {

    private RecyclerView recyclerView;
    private TumYemeklerAdapter tumYemeklerAdapter;
    private SearchView searchView;
    private List<YemekDto> originalYemekListDto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        tumYemeklerAdapter = new TumYemeklerAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(tumYemeklerAdapter);

        searchView = view.findViewById(R.id.searchview);
        originalYemekListDto = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TumYemekleriGetirDto.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YemekServisi yemekServisi = retrofit.create(YemekServisi.class);

        Call<TumYemekleriGetirDto> call = yemekServisi.tumYemekleriGetir();
        call.enqueue(new Callback<TumYemekleriGetirDto>() {
            @Override
            public void onResponse(Call<TumYemekleriGetirDto> call, Response<TumYemekleriGetirDto> response) {
                if (response.isSuccessful()) {
                    TumYemekleriGetirDto tumYemekleriGetirDto = response.body();
                    List<YemekDto> yemekler = tumYemekleriGetirDto.getYemekler();

                    tumYemeklerAdapter.setYemekListesi(yemekler);
                    originalYemekListDto.addAll(yemekler);
                } else {
                    // Hata durumu
                }
            }

            @Override
            public void onFailure(Call<TumYemekleriGetirDto> call, Throwable t) {
                // Hata durumu
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterYemekList(newText);
                return true;
            }
        });



        return view;
    }

    private void filterYemekList(String query) {
        List<YemekDto> filteredList = new ArrayList<>();

        for (YemekDto yemekDto : originalYemekListDto) {
            if (yemekDto.getYemekAdi().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(yemekDto);
            }
        }

        tumYemeklerAdapter.setYemekListesi(filteredList);
    }
}
