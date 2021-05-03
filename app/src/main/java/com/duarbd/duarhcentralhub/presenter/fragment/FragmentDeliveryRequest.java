package com.duarbd.duarhcentralhub.presenter.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterDeliveryRequest;
import com.duarbd.duarhcentralhub.databinding.FragmentDeliveryRequestBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterDeliveryRequestCallbacks;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.duarbd.duarhcentralhub.presenter.ActivityDeliveryDetails;
import com.duarbd.duarhcentralhub.presenter.ActivityHome;

import java.util.ArrayList;
import java.util.List;

public class FragmentDeliveryRequest extends Fragment implements AdapterDeliveryRequestCallbacks {
    private static final String TAG = "FragmentDeliveryRequest";
    private  FragmentDeliveryRequestBinding binding;

    private List<ModelDeliveryRequest> deliveryRequestList;
    private AdapterDeliveryRequest adapterDeliveryRequest;
    private ViewModelHub viewModelHub;
    private Dialog dialogLoading;
    private FragmentActivity fragmentActivity;

    public FragmentDeliveryRequest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentActivity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentDeliveryRequestBinding.inflate(inflater,container,false);
        init();

        binding.swipeRefreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDeliveryRequestFromServer("refreshList");
            }
        });

        return binding.getRoot();
    }

    void init(){
        dialogLoading=setupDialog(fragmentActivity);
        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(ViewModelHub.class);
        deliveryRequestList=new ArrayList<>();
        adapterDeliveryRequest=new AdapterDeliveryRequest(deliveryRequestList,fragmentActivity,this);
        binding.recycRequestedDelivery.setAdapter(adapterDeliveryRequest);
        binding.recycRequestedDelivery.setLayoutManager(new LinearLayoutManager(fragmentActivity));
    }


    //todo getting result for delivery status below 4!!! update this
    void getAllDeliveryRequestFromServer(){
        dialogLoading.show();
        viewModelHub.getAllDeliveryRequest().observe(fragmentActivity,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        if(modelDeliveryRequests!=null&&!modelDeliveryRequests.get(0).getResponse().equals("0")){
                            if(!modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                deliveryRequestList.clear();
                                for(ModelDeliveryRequest request:modelDeliveryRequests)
                                    if(!request.getStatus().equals("4")) deliveryRequestList.add(request);
                                adapterDeliveryRequest.notifyDataSetChanged();
                                dialogLoading.dismiss();
                            }else {
                                deliveryRequestList.clear();adapterDeliveryRequest.notifyDataSetChanged();
                                dialogLoading.dismiss();
                                Toast.makeText(fragmentActivity, "No delivery request found", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(fragmentActivity, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void getAllDeliveryRequestFromServer(String refreshlist){
        viewModelHub.getAllDeliveryRequest().observe(fragmentActivity,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        if(modelDeliveryRequests!=null&&!modelDeliveryRequests.get(0).getResponse().equals("0")){
                            deliveryRequestList.clear();
                            if(!modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                for(ModelDeliveryRequest request:modelDeliveryRequests)
                                    if(!request.getStatus().equals("4")) deliveryRequestList.add(request);
                                adapterDeliveryRequest.notifyDataSetChanged();
                                binding.swipeRefreshList.setRefreshing(false);
                            }else {
                                binding.swipeRefreshList.setRefreshing(false);
                                Toast.makeText(fragmentActivity, "No delivery request found", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            binding.swipeRefreshList.setRefreshing(false);
                            Toast.makeText(fragmentActivity, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private Dialog setupDialog(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //this prevents dimming effect
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onRequestedDeliveryItemClicked(int position) {
        fragmentActivity.startActivity(new Intent(fragmentActivity, ActivityDeliveryDetails.class)
                .putExtra("intent_data",deliveryRequestList.get(position)));
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllDeliveryRequestFromServer();
    }
}

/* private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static FragmentDeliveryRequest newInstance(String param1, String param2) {
        FragmentDeliveryRequest fragment = new FragmentDeliveryRequest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/