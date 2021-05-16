package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.duarbd.duarhcentralhub.adapter.AdapterClientBill;
import com.duarbd.duarhcentralhub.databinding.ActivityClientClearanceBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterClientBillCallBack;
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

public class ActivityClientClearance extends AppCompatActivity implements AdapterClientBillCallBack {
    private static final String TAG = "ActivityClientClearance";
    private ActivityClientClearanceBinding binding;
    private Dialog dialogLoading;
    private ViewModelHub viewModelHub;
    private DecimalFormat twodigits = new DecimalFormat("00");

    private ArrayList<ModelDeliveryRequest> deliveryList;
    private HashMap<String,ArrayList<ModelDeliveryRequest>> deliveryHistoryByClient;
    private ArrayList<ModelClientBill> clientBillList;
    private AdapterClientBill adapterClientBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClientClearanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.showClientBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date=twodigits.format(binding.datepicker.getSelectedDay())+"-"+
                        twodigits.format( (binding.datepicker.getSelectedMonth()+1))+"-"+
                        twodigits.format(binding.datepicker.getSelectedYear());

                getDeliveriesFromServer(date);
            }
        });
    }

    void  init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Client Bill Clearance");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialogLoading= Utils.setupLoadingDialog(ActivityClientClearance.this);
        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        binding.datepicker.setFirstVisibleDate(2021, Calendar.JANUARY, 01);
        binding.datepicker.setLastVisibleDate(2021, Calendar.DECEMBER, 31);
        binding.datepicker.setFollowScroll(true);
        String date[]= Utils.getCustentDateArray();
        binding.datepicker.setSelectedDate(Integer.valueOf(date[2]),Integer.valueOf(date[1])-1,Integer.valueOf(date[0]));


        deliveryHistoryByClient=new HashMap<>();deliveryList=new ArrayList<>();


        clientBillList=new ArrayList<>();
        adapterClientBill=new AdapterClientBill(clientBillList,ActivityClientClearance.this,ActivityClientClearance.this);
        binding.recycClientBillList.setAdapter(adapterClientBill);
        binding.recycClientBillList.setLayoutManager(new LinearLayoutManager(ActivityClientClearance.this));
    }

    void getDeliveriesFromServer(String date){
        Log.d(TAG, "getDeliveriesFromServer: date="+date);
        dialogLoading.show();
        viewModelHub.getAllCompletedDeliveryRequests().observe(ActivityClientClearance.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        deliveryList.clear();
                        if(modelDeliveryRequests!=null&&modelDeliveryRequests.get(0).getResponse().equals("1")){
                            if(modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                deliveryList.clear();
                                dialogLoading.dismiss();
                                Toast.makeText(ActivityClientClearance.this, "0 Delivery Found.", Toast.LENGTH_SHORT).show();
                            }else{
                                deliveryList.clear();

                                for(ModelDeliveryRequest delivery:modelDeliveryRequests){
                                    String palceddate[]=delivery.getRequestPlacedTime().split(" ");
                                    if(date.equals(palceddate[0])){
                                        deliveryList.add(delivery);
                                    }
                                }

                                deliveryHistoryByClient.clear();deliveryHistoryByClient.putAll(generateClientBill(deliveryList));
                                generateClientBillRecyc(deliveryHistoryByClient);
                            }
                        }
                    }
                });
    }


    //todo not an efficient way.must introduce an efficient way
    HashMap<String,ArrayList<ModelDeliveryRequest>> generateClientBill(ArrayList<ModelDeliveryRequest> allDeliveries){

        HashMap<String,ArrayList<ModelDeliveryRequest>> clientBillList=new HashMap<>();

        for(ModelDeliveryRequest delivery:allDeliveries){
            if(clientBillList.containsKey(delivery.getClientName())){
                ArrayList<ModelDeliveryRequest> templist=new ArrayList<>();
                templist.addAll(clientBillList.get(delivery.getClientName()));
                templist.add(delivery);
                clientBillList.put(delivery.getClientName(),templist);
            }else {
                ArrayList<ModelDeliveryRequest> templist=new ArrayList<>();templist.add(delivery);
                clientBillList.put(delivery.getClientName(),templist);
            }
        }

        /*for(Map.Entry me:clientBillList.entrySet()){
            List<ModelDeliveryRequest> list=new ArrayList<>();
            list.addAll((Collection<? extends ModelDeliveryRequest>) me.getValue());
            Log.d(TAG, "generateClientBill: "+me.getKey()+" size="+list.size());
        }*/

        return clientBillList;
    }
    void generateClientBillRecyc(HashMap<String,ArrayList<ModelDeliveryRequest>> hashMap){
        clientBillList.clear();

        for(Map.Entry me:hashMap.entrySet()){
            int totalBill=0,orderCount=0,totalDeliveryCharge=0;

            for (ModelDeliveryRequest delivery:(List<ModelDeliveryRequest>)me.getValue()){
                totalBill+=delivery.getProductPrice();
                totalDeliveryCharge+=delivery.getDeliveryCharge();
                orderCount++;
            }

            clientBillList.add(new ModelClientBill((String)me.getKey(),totalBill+"",totalDeliveryCharge+"",orderCount+""));
        }

        adapterClientBill.notifyDataSetChanged();
        dialogLoading.dismiss();
    }

    @Override
    public void onViewDetailsClicked(String clientName) {
        if(deliveryHistoryByClient.get(clientName).size()!=0){
            Intent intent = new Intent(ActivityClientClearance.this, ActivityClientOrderHistory.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getResources().getString(R.string.intent_data), (ArrayList<? extends Parcelable>) deliveryHistoryByClient.get(clientName));
            intent.putExtras(bundle);
            this.startActivity(intent);
        }else Toast.makeText(this, "0 delivery. Nothing to show", Toast.LENGTH_SHORT).show();
    }
}