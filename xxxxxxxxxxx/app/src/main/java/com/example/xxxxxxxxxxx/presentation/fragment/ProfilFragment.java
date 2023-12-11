package com.example.xxxxxxxxxxx.presentation.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxxxxxxxxxx.databinding.FragmentProfilBinding;

public class ProfilFragment extends Fragment {
    FragmentProfilBinding binding;
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}