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
import com.duarbd.duarhcentralhub.adapter.AdapterDeliveryHistory;
import com.duarbd.duarhcentralhub.databinding.ActivityDeliveryHistoryBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterDeliveryHistoryCallback;
import com.duarbd.duarhcentralhub.model.ModelClientBill;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.duarbd.duarhcentralhub.tools.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDeliveryHistory extends AppCompatActivity implements AdapterDeliveryHistoryCallback {
    private static final String TAG = "ActivityDeliveryHistory";
    private ActivityDeliveryHistoryBinding binding;
    private Dialog dialogLoading;
    private ViewModelHub viewModelHub;

    private List<ModelDeliveryRequest> deliveryHistory;
    private AdapterDeliveryHistory adapterDeliveryHistory;
    private DecimalFormat twodigits = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeliveryHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.showDEliveryHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=twodigits.format(binding.datepicker.getSelectedDay())+"-"+
                        twodigits.format( (binding.datepicker.getSelectedMonth()+1))+"-"+
                        twodigits.format(binding.datepicker.getSelectedYear());
                Log.d(TAG, "onClick: "+date);
                getDeliveriesFromServer(date);
            }
        });
    }

    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("History");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        dialogLoading= Utils.setupLoadingDialog(ActivityDeliveryHistory.this);
        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        binding.datepicker.setFirstVisibleDate(2021, Calendar.JANUARY, 01);
        binding.datepicker.setLastVisibleDate(2021, Calendar.DECEMBER, 31);
        binding.datepicker.setFollowScroll(true);
        String date[]= Utils.getCustentDateArray();
        binding.datepicker.setSelectedDate(Integer.valueOf(date[2]),Integer.valueOf(date[1])-1,Integer.valueOf(date[0]));

        deliveryHistory=new ArrayList<>();
        adapterDeliveryHistory=new AdapterDeliveryHistory(deliveryHistory,ActivityDeliveryHistory.this,ActivityDeliveryHistory.this);
        binding.recycDeliveryHistory.setLayoutManager(new LinearLayoutManager(ActivityDeliveryHistory.this));
        binding.recycDeliveryHistory.setAdapter(adapterDeliveryHistory);
    }

    @Override
    public void onMoreClicked(ModelDeliveryRequest delivery) {
       startActivity(new Intent(ActivityDeliveryHistory.this,ActivityDeliveryDetails.class)
       .putExtra(getResources().getString(R.string.intent_data),delivery));
    }


    void getDeliveriesFromServer(String date){
        dialogLoading.show();
        viewModelHub.getAllCompletedDeliveryRequests().observe(ActivityDeliveryHistory.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        if(modelDeliveryRequests!=null && modelDeliveryRequests.get(0).getResponse().equals("1")){
                            if(modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                deliveryHistory.clear();
                                adapterDeliveryHistory.notifyDataSetChanged();
                                dialogLoading.dismiss();
                                Toast.makeText(ActivityDeliveryHistory.this, "0 Delivery Found.", Toast.LENGTH_SHORT).show();
                            }else{
                                deliveryHistory.clear();
                                for(ModelDeliveryRequest delivery:modelDeliveryRequests){
                                    String palceddate[]=delivery.getRequestPlacedTime().split(" ");
                                    if(date.equals(palceddate[0])){
                                        deliveryHistory.add(delivery);
                                    }
                                }
                                adapterDeliveryHistory.notifyDataSetChanged();
                                dialogLoading.dismiss();
                            }
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityDeliveryHistory.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




}