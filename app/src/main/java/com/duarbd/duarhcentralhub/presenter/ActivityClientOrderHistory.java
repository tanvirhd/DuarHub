package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterClientOrderHistory;
import com.duarbd.duarhcentralhub.databinding.ActivityClientOrderHistoryBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterClientOrderHistoryCallback;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;

import java.util.ArrayList;
import java.util.List;

public class ActivityClientOrderHistory extends AppCompatActivity implements AdapterClientOrderHistoryCallback {
    private static final String TAG = "ActivityClientOrderHist";
    private ActivityClientOrderHistoryBinding binding;
    private List<ModelDeliveryRequest>  deliveryList;
    private AdapterClientOrderHistory adapterClientOrderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClientOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("All Deliveries");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            deliveryList = bundle.getParcelableArrayList(getResources().getString(R.string.intent_data));
            calculateBill(deliveryList);
            adapterClientOrderHistory=new AdapterClientOrderHistory(deliveryList,ActivityClientOrderHistory.this,ActivityClientOrderHistory.this);

            binding.recycClientOrderHistory.setAdapter(adapterClientOrderHistory);
            binding.recycClientOrderHistory.setLayoutManager(new LinearLayoutManager(ActivityClientOrderHistory.this));
        }
        else Toast.makeText(this, "Nothing Found", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMoreClicked(ModelDeliveryRequest delivery) {
        startActivity(new Intent(ActivityClientOrderHistory.this,ActivityDeliveryDetails.class)
                .putExtra(getResources().getString(R.string.intent_data),delivery));
    }

    void calculateBill(List<ModelDeliveryRequest> deliveryList){
        int bill=0;
        for(ModelDeliveryRequest delivery:deliveryList){
            bill+=delivery.getProductPrice();
        }
        binding.tvTotalBill.setText("Total Bill: "+bill+ " Taka");
    }
}