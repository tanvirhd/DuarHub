package com.duarbd.duarhcentralhub.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.duarbd.duarhcentralhub.presenter.fragment.FragmentDeliveryRequest;
import com.duarbd.duarhcentralhub.presenter.fragment.FragmentDuarOrder;

public class AdapterViewPager extends FragmentStateAdapter {


    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new FragmentDuarOrder();
            default:
                return new FragmentDeliveryRequest();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
