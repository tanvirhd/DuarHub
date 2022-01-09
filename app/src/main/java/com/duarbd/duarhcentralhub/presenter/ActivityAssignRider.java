package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterActiveRiders;
import com.duarbd.duarhcentralhub.databinding.ActivityAssignRiderBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterActiveRiderCallbacks;
import com.duarbd.duarhcentralhub.model.ModelActiveRider;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.duarbd.duarhcentralhub.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActivityAssignRider extends AppCompatActivity implements AdapterActiveRiderCallbacks {
    private static final String TAG = "ActivityAssignRider";
    private ActivityAssignRiderBinding binding;
    private ViewModelHub viewModelHub;
    private List<ModelActiveRider> activeRiderList;
    private AdapterActiveRiders adapterActiveRiders;
    private Dialog dialogLoading;

    private  ModelDeliveryRequest deliveryRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAssignRiderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }



    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Rider Assignment");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        dialogLoading= Utils.setupLoadingDialog(ActivityAssignRider.this);
        deliveryRequest=getIntent().getParcelableExtra(getResources().getString(R.string.intent_data));


        activeRiderList=new ArrayList<>();
        adapterActiveRiders=new AdapterActiveRiders(activeRiderList,ActivityAssignRider.this,ActivityAssignRider.this);
        binding.recycActiveRiderList.setAdapter(adapterActiveRiders);
        binding.recycActiveRiderList.setLayoutManager(new LinearLayoutManager(ActivityAssignRider.this));
    }

    void getActiveRideListFromServer(){
        dialogLoading.show();
        viewModelHub.getActiveRiderList().observe(ActivityAssignRider.this,
                new Observer<List<ModelActiveRider>>() {
                    @Override
                    public void onChanged(List<ModelActiveRider> modelActiveRiders) {
                        if(modelActiveRiders!=null&&modelActiveRiders.get(0).getResponse()==1){
                            activeRiderList.clear();
                            activeRiderList.addAll(modelActiveRiders);
                            adapterActiveRiders.notifyDataSetChanged();
                            dialogLoading.dismiss();
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityAssignRider.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActiveRideListFromServer();
    }

    @Override
    public void onAssignNewRideClicked(int position) {
        Log.d(TAG, "onAssignNewRideClicked: "+deliveryRequest.getDeliveryStatus());
        dialogLoading.show();
        deliveryRequest.setRiderName(activeRiderList.get(position).getRiderName());
        deliveryRequest.setRiderid( activeRiderList.get(position).getRiderid());
        //Log.d(TAG, "onAssignNewRideClicked: "+deliveryRequest.getPickupCode());
        viewModelHub.assignRiderByDeliveryRequestId(deliveryRequest).observe(ActivityAssignRider.this,
                new Observer<ModelResponse>() {
                    @Override
                    public void onChanged(ModelResponse modelResponse) {
                        if(modelResponse!=null&&modelResponse.getResponse()==1){
                            getActiveRideListFromServer();
                            Toast.makeText(ActivityAssignRider.this, "Rider Assigned", Toast.LENGTH_SHORT).show();
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityAssignRider.this, "Failed to assign Rider", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}