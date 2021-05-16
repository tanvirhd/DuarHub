package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterRiderBillClearance;
import com.duarbd.duarhcentralhub.adapter.AdapterRiderClearance;
import com.duarbd.duarhcentralhub.databinding.ActivityRiderClearanceBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterRiderClearanceCallback;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelRiderClearance;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.duarbd.duarhcentralhub.tools.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityRiderClearance extends AppCompatActivity implements AdapterRiderClearanceCallback {
    private static final String TAG = "ActivityRiderClearance";
    private ActivityRiderClearanceBinding binding;
    private ViewModelHub viewModelHub;
    private Dialog dialogLoading;
    private DecimalFormat twodigits = new DecimalFormat("00");

    ArrayList<ModelDeliveryRequest> deliveryList;
    ArrayList<ModelRiderClearance> riderClearanceList;
    HashMap<String,ArrayList<ModelDeliveryRequest>> deliveryListByRider;

    AdapterRiderClearance adapterRiderClearance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRiderClearanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.showRiderClearance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=twodigits.format(binding.datepicker.getSelectedDay())+"-"+
                        twodigits.format( (binding.datepicker.getSelectedMonth()+1))+"-"+
                        twodigits.format(binding.datepicker.getSelectedYear());
                getDeliveriesFromServer(date);
            }
        });
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

        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        dialogLoading= Utils.setupLoadingDialog(ActivityRiderClearance.this);
        binding.datepicker.setFirstVisibleDate(2021, Calendar.JANUARY, 01);
        binding.datepicker.setLastVisibleDate(2021, Calendar.DECEMBER, 31);
        binding.datepicker.setFollowScroll(true);
        String date[]= Utils.getCustentDateArray();
        binding.datepicker.setSelectedDate(Integer.valueOf(date[2]),Integer.valueOf(date[1])-1,Integer.valueOf(date[0]));

        deliveryList=new ArrayList<>();riderClearanceList=new ArrayList<>();
        deliveryListByRider=new HashMap<>();

        adapterRiderClearance=new AdapterRiderClearance(riderClearanceList,ActivityRiderClearance.this,ActivityRiderClearance.this);
        binding.recycRiderBillList.setLayoutManager(new LinearLayoutManager(ActivityRiderClearance.this));
        binding.recycRiderBillList.setAdapter(adapterRiderClearance);
    }

    void getDeliveriesFromServer(String date){
        dialogLoading.show();
        viewModelHub.getAllCompletedDeliveryRequests().observe(ActivityRiderClearance.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        deliveryList.clear();
                        if(modelDeliveryRequests!=null&&modelDeliveryRequests.get(0).getResponse().equals("1")){
                            if(modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                deliveryList.clear();
                                dialogLoading.dismiss();
                                Toast.makeText(ActivityRiderClearance.this, "0 Delivery Found.", Toast.LENGTH_SHORT).show();
                            }else{
                                deliveryList.clear();

                                for(ModelDeliveryRequest delivery:modelDeliveryRequests){
                                    String palceddate[]=delivery.getRequestPlacedTime().split(" ");
                                    if(date.equals(palceddate[0])){
                                        deliveryList.add(delivery);
                                    }
                                }

                                deliveryListByRider.clear();deliveryListByRider.putAll(generateRiderClearance(deliveryList));
                                generateRiderClearanceRecyc(deliveryListByRider);
                            }
                        }
                    }
                });
    }

    HashMap<String,ArrayList<ModelDeliveryRequest>> generateRiderClearance(ArrayList<ModelDeliveryRequest> deliveryList){
        HashMap<String,ArrayList<ModelDeliveryRequest>> riderClearanceList=new HashMap<>();
        for(ModelDeliveryRequest delivery:deliveryList){
            if(riderClearanceList.containsKey(delivery.getRiderName())){
                ArrayList<ModelDeliveryRequest> templist=new ArrayList<>();
                templist.addAll(riderClearanceList.get(delivery.getRiderName()));
                templist.add(delivery);
                riderClearanceList.put(delivery.getRiderName(),templist);
            }else {
                ArrayList<ModelDeliveryRequest> templist=new ArrayList<>();templist.add(delivery);
                riderClearanceList.put(delivery.getRiderName(),templist);
            }
        }

       /* for(Map.Entry me:riderClearanceList.entrySet()){
            List<ModelDeliveryRequest> list=new ArrayList<>();
            list.addAll((Collection<? extends ModelDeliveryRequest>) me.getValue());
            Log.d(TAG, "generateClientBill: "+me.getKey()+" size="+list.size());
        }*/
        return riderClearanceList;
    }

    void generateRiderClearanceRecyc(HashMap<String,ArrayList<ModelDeliveryRequest>> hashMap){
        riderClearanceList.clear();

        for(Map.Entry me:hashMap.entrySet()){
            int totalRide=0;
            for (ModelDeliveryRequest delivery:(List<ModelDeliveryRequest>)me.getValue()){
                totalRide++;
            }
            riderClearanceList.add(new ModelRiderClearance((String)me.getKey(),totalRide));
        }

        adapterRiderClearance.notifyDataSetChanged();
        dialogLoading.dismiss();
    }

    @Override
    public void onViewDetailsClicked(String riderName) {
        if(deliveryListByRider.get(riderName).size()!=0){
            Intent intent = new Intent(ActivityRiderClearance.this, ActivityRiderRideHistory.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getResources().getString(R.string.intent_data), (ArrayList<? extends Parcelable>) deliveryListByRider.get(riderName));
            intent.putExtras(bundle);
            this.startActivity(intent);
        }else Toast.makeText(this, "0 delivery. Nothing to show", Toast.LENGTH_SHORT).show();
    }
}