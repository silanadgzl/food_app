package com.example.xxxxxxxxxxx.presentation.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxxxxxxxxxx.databinding.FragmentFavorilerBinding;

public class FavorilerFragment extends Fragment {
    FragmentFavorilerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavorilerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}