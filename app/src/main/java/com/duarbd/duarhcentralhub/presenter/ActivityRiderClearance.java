package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterRiderBillClearance;
import com.duarbd.duarhcentralhub.databinding.ActivityRiderClearanceBinding;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;

import java.util.ArrayList;
import java.util.List;

public class ActivityRiderClearance extends AppCompatActivity {
    private static final String TAG = "ActivityRiderClearance";
    private ActivityRiderClearanceBinding binding;

    List<ModelDeliveryRequest> deliveryList;
    AdapterRiderBillClearance adapterRiderBillClearance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRiderClearanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        dummydata();

    }

    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Rider Bill Clearance");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deliveryList=new ArrayList<>();
        adapterRiderBillClearance=new AdapterRiderBillClearance(deliveryList,ActivityRiderClearance.this);
        binding.recycRiderBillList.setLayoutManager(new LinearLayoutManager(ActivityRiderClearance.this));
        binding.recycRiderBillList.setAdapter(adapterRiderBillClearance);
    }

    void dummydata(){
        deliveryList.add(new ModelDeliveryRequest("","Monir Hossain",""));
        deliveryList.add(new ModelDeliveryRequest("","Shafin",""));
        deliveryList.get(0).setResponse("9");
        deliveryList.get(1).setResponse("11");
        adapterRiderBillClearance.notifyDataSetChanged();
    }
}