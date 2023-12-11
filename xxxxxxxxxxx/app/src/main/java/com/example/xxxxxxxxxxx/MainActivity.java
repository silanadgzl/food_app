package com.example.xxxxxxxxxxx;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.xxxxxxxxxxx.presentation.fragment.AnasayfaFragment;
import com.example.xxxxxxxxxxx.presentation.fragment.FavorilerFragment;
import com.example.xxxxxxxxxxx.presentation.fragment.ProfilFragment;
import com.example.xxxxxxxxxxx.presentation.fragment.SepetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.anasayfa) {
                        selectedFragment = new AnasayfaFragment();
                    } else if (item.getItemId() == R.id.favoriler) {
                        selectedFragment = new FavorilerFragment();
                    } else if (item.getItemId() == R.id.sepet) {
                        selectedFragment = new SepetFragment();
                    } else if (item.getItemId() == R.id.profil) {
                        selectedFragment = new ProfilFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new AnasayfaFragment())
                .commit();

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_navigation);
        bottomNavView.setOnNavigationItemSelectedListener(navListener);
    }
}

