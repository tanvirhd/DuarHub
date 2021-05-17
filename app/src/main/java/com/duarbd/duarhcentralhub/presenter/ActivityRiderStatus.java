package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterRiderStatus;
import com.duarbd.duarhcentralhub.databinding.ActivityRiderStatusBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterRiderStatusCallback;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelRider;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.duarbd.duarhcentralhub.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActivityRiderStatus extends AppCompatActivity implements AdapterRiderStatusCallback{
    private static final String TAG = "ActivityRiderStatus";
    private ActivityRiderStatusBinding binding;

    private List<ModelRider> riderList;
    private AdapterRiderStatus adapterRiderStatus;

    private Dialog dialogLoading;
    private ViewModelHub viewModelHub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRiderStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        dialogLoading= Utils.setupLoadingDialog(ActivityRiderStatus.this);

        riderList=new ArrayList<>();
        adapterRiderStatus=new AdapterRiderStatus(riderList,ActivityRiderStatus.this,ActivityRiderStatus.this);
        binding.recycRiders.setAdapter(adapterRiderStatus);
        binding.recycRiders.setLayoutManager(new LinearLayoutManager(ActivityRiderStatus.this));

    }

    @Override
    public void onCallRiderClicked(ModelRider rider) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "" + rider.getRiderContactNumber()));
        startActivity(intent);
    }

    @Override
    public void onRiderSutyStatusSwitchClicked(ModelRider rider, boolean dutystatus) {
        dialogLoading.show();
        int temp=rider.getWorkingStatus();
        rider.setWorkingStatus(dutystatus?1:0);
        viewModelHub.updateRiderDutyStatus(rider).observe(ActivityRiderStatus.this,
                new Observer<ModelResponse>() {
                    @Override
                    public void onChanged(ModelResponse modelResponse) {
                        if(modelResponse!=null&&modelResponse.getResponse()==1){
                            adapterRiderStatus.notifyDataSetChanged();
                            getAllRiderInfoFromServer();
                        }else {
                            rider.setWorkingStatus(temp);
                            adapterRiderStatus.notifyDataSetChanged();
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityRiderStatus.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void getAllRiderInfoFromServer(){
        dialogLoading.show();
        viewModelHub.getAllRegisteredRiderInfo().observe(ActivityRiderStatus.this,
                new Observer<List<ModelRider>>() {
                    @Override
                    public void onChanged(List<ModelRider> modelRiders) {
                        if(modelRiders!=null&&modelRiders.get(0).getResponse()==1){
                            if(!modelRiders.get(0).getStatus().equals("NothingFound")){
                                riderList.clear();
                                riderList.addAll(modelRiders);
                                adapterRiderStatus.notifyDataSetChanged();
                                dialogLoading.dismiss();
                            }else {
                                riderList.clear();
                                adapterRiderStatus.notifyDataSetChanged();
                                dialogLoading.dismiss();
                                Toast.makeText(ActivityRiderStatus.this, "No Rider Found!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityRiderStatus.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllRiderInfoFromServer();
    }
}