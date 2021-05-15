package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterClientBill;
import com.duarbd.duarhcentralhub.databinding.ActivityClientClearanceBinding;
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

public class ActivityClientClearance extends AppCompatActivity {
    private static final String TAG = "ActivityClientClearance";
    private ActivityClientClearanceBinding binding;
    private Dialog dialogLoading;
    private ViewModelHub viewModelHub;
    private DecimalFormat twodigits = new DecimalFormat("00");

    private List<ModelDeliveryRequest> deliveryList;
    private HashMap<String,List<ModelDeliveryRequest>> deliveryHistoryByClient;
    private List<String> clientIdList;


    private List<ModelClientBill> clientBillList;
    private AdapterClientBill adapterClientBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClientClearanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        dummydata();

        binding.showClientBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date=twodigits.format(binding.datepicker.getSelectedDay())+"-"+
                        twodigits.format( (binding.datepicker.getSelectedMonth()+1))+"-"+
                        twodigits.format(binding.datepicker.getSelectedYear());

                Toast.makeText(ActivityClientClearance.this, date, Toast.LENGTH_SHORT).show();

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

        clientIdList=new ArrayList<>();



        clientBillList=new ArrayList<>();
        adapterClientBill=new AdapterClientBill(ActivityClientClearance.this,clientBillList);
        binding.recycClientBillList.setAdapter(adapterClientBill);
        binding.recycClientBillList.setLayoutManager(new LinearLayoutManager(ActivityClientClearance.this));
    }

    void clientBillCalculation(HashMap<String,List<ModelDeliveryRequest>> hashMap){
        for(Map.Entry me:hashMap.entrySet()){
            List<ModelDeliveryRequest> list=new ArrayList<>();
            list.addAll((Collection<? extends ModelDeliveryRequest>) me.getValue());
            Log.d(TAG, "clientBillCalculation: key="+me.getKey()+"size="+list.size());
        }
    }

    void getDeliveriesFromServer(String date){
        Log.d(TAG, "getDeliveriesFromServer: date="+date);
        dialogLoading.show();
        viewModelHub.getAllCompletedDeliveryRequests().observe(ActivityClientClearance.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {

                    }
                });
    }

    void  dummydata(){
        clientBillList.add(new ModelClientBill("MFoods Bogura","1250","140","5"));
        clientBillList.add(new ModelClientBill("Rajdhani Diner","890","90","3"));
        adapterClientBill.notifyDataSetChanged();
    }
}