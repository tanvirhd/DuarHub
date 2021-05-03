package com.duarbd.duarhcentralhub.presenter.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.databinding.FragmentDuarOrderBinding;

public class FragmentDuarOrder extends Fragment {
    private static final String TAG = "FragmentDuarOrder";
    private FragmentDuarOrderBinding binding;

    public FragmentDuarOrder() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentDuarOrderBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}