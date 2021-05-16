package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterClientOrderHistory;
import com.duarbd.duarhcentralhub.adapter.AdapterRiderRideHistory;
import com.duarbd.duarhcentralhub.databinding.ActivityRiderRideHistoryBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterRiderRideHistoryCallback;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.duarbd.duarhcentralhub.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActivityRiderRideHistory extends AppCompatActivity implements AdapterRiderRideHistoryCallback {
    private static final String TAG = "ActivityRiderRideHistor";
    private ActivityRiderRideHistoryBinding binding;

    private ArrayList<ModelDeliveryRequest> allRides;
    private AdapterRiderRideHistory adapterRiderRideHistory;

    private ViewModelHub viewModelHub;
    private Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRiderRideHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("All Rides");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        dialogLoading= Utils.setupLoadingDialog(ActivityRiderRideHistory.this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            allRides = bundle.getParcelableArrayList(getResources().getString(R.string.intent_data));
            calculateBill(allRides);
            adapterRiderRideHistory=new AdapterRiderRideHistory(allRides,ActivityRiderRideHistory.this,ActivityRiderRideHistory.this);
            binding.recycRiderRideHistory.setAdapter(adapterRiderRideHistory);
            binding.recycRiderRideHistory.setLayoutManager(new LinearLayoutManager(ActivityRiderRideHistory.this));
        }
        else Toast.makeText(this, "Nothing Found", Toast.LENGTH_SHORT).show();
    }

    void calculateBill(List<ModelDeliveryRequest> deliveries){
        int totalbill=0,totalride=0;
        for(ModelDeliveryRequest ride:deliveries){
            if(ride.getRiderClearance().equals("due")){
                totalbill+=ride.getProductPrice()+ride.getDeliveryCharge();
                totalride++;
            }
        }
        binding.tvTotalBill.setText("Total Bill: "+totalbill+", Total Ride: "+totalride);
    }

    @Override
    public void onMoreClicked(ModelDeliveryRequest delivery) {
        startActivity(new Intent(ActivityRiderRideHistory.this,ActivityDeliveryDetails.class)
                .putExtra(getResources().getString(R.string.intent_data),delivery));
    }

    @Override
    public void onGiveClearanceClicked(ModelDeliveryRequest delivery) {
        delivery.setRiderClearance("ok");
        dialogLoading.show();
        viewModelHub.updateRiderPaymentStatusByDeliveryId(delivery).observe(ActivityRiderRideHistory.this,
                new Observer<ModelResponse>() {
                    @Override
                    public void onChanged(ModelResponse modelResponse) {
                        if(modelResponse!=null&&modelResponse.getResponse()==1){
                            dialogLoading.dismiss();
                            adapterRiderRideHistory.notifyDataSetChanged();
                            calculateBill(allRides);
                        }
                        else {
                            delivery.setClientPaymentStatus("due");
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityRiderRideHistory.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}