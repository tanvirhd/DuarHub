package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterSalaryCalculation;
import com.duarbd.duarhcentralhub.databinding.ActivitySalaryCalculationBinding;
import com.duarbd.duarhcentralhub.model.ModelRiderSalary;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.duarbd.duarhcentralhub.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActivitySalaryCalculation extends AppCompatActivity {
    private static final String TAG = "ActivitySalaryCalculati";
    private ActivitySalaryCalculationBinding binding;

    private List<ModelRiderSalary> salaryList;
    private AdapterSalaryCalculation adapterSalaryCalculation;
    private Dialog dialogloadig;
    private ViewModelHub viewModelHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySalaryCalculationBinding.inflate(getLayoutInflater());
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
        dialogloadig= Utils.setupLoadingDialog(ActivitySalaryCalculation.this);
        salaryList=new ArrayList<>();
        adapterSalaryCalculation=new AdapterSalaryCalculation(salaryList,ActivitySalaryCalculation.this);
        binding.recycRiderSalary.setLayoutManager(new LinearLayoutManager(ActivitySalaryCalculation.this));
        binding.recycRiderSalary.setAdapter(adapterSalaryCalculation);
    }

    void getRiderSalary(){
        dialogloadig.show();
        viewModelHub.getRiderSalary().observe(ActivitySalaryCalculation.this, new Observer<List<ModelRiderSalary>>() {
            @Override
            public void onChanged(List<ModelRiderSalary> modelRiderSalaries) {
                if(modelRiderSalaries!=null&&modelRiderSalaries.get(0).getResponse()==1){
                    salaryList.clear();
                    salaryList.addAll(modelRiderSalaries);
                    adapterSalaryCalculation.notifyDataSetChanged();
                    dialogloadig.dismiss();
                }else {
                    dialogloadig.dismiss();
                    Toast.makeText(ActivitySalaryCalculation.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRiderSalary();
    }
}