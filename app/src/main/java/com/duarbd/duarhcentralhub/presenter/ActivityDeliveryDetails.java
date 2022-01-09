package com.duarbd.duarhcentralhub.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.databinding.ActivityDeliveryDetailsBinding;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;

public class ActivityDeliveryDetails extends AppCompatActivity {
    private static final String TAG = "ActivityDeliveryDetails";
    private ActivityDeliveryDetailsBinding binding;
    private ModelDeliveryRequest deliveryRequest;
    private ViewModelHub viewModelHub;
    private Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeliveryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryRequest.setDeliveryStatus(1);
                Log.d(TAG, "onClick: "+deliveryRequest.getPickupCode());
                updateDeliveryStatusByid(deliveryRequest);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryRequest.setDeliveryStatus(7);
                Log.d(TAG, "onClick: "+deliveryRequest.getPickupCode());
                updateDeliveryStatusByid(deliveryRequest);
            }
        });

        binding.btnAssignRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+deliveryRequest.getDeliveryStatus());
                startActivity(new Intent(ActivityDeliveryDetails.this,ActivityAssignRider.class)
                    .putExtra(getResources().getString(R.string.intent_data),deliveryRequest));
            }
        });
    }

    private void updateDeliveryStatusByid(ModelDeliveryRequest request){
        dialogLoading.show();
        viewModelHub.updateDeliveryStatusByRequestId(request).observe(ActivityDeliveryDetails.this,
                new Observer<ModelResponse>() {
                    @Override
                    public void onChanged(ModelResponse modelResponse) {
                        if(modelResponse!=null&&modelResponse.getResponse()==1){
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityDeliveryDetails.this, "Status Updated Successfully.", Toast.LENGTH_SHORT).show();
                            updateUI(request);
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityDeliveryDetails.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init(){

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Request Details");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        deliveryRequest =getIntent().getParcelableExtra(getResources().getString(R.string.intent_data));
        if(deliveryRequest!=null) updateUI(deliveryRequest);
        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        dialogLoading=setupDialog(ActivityDeliveryDetails.this);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Delivery Request Details");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void updateUI(ModelDeliveryRequest deliveryRequest){
        binding.tvRequestId.setText("Requets id: "+deliveryRequest.getDeliveryRequestId());
        binding.tvRequestBy.setText("Request by: "+deliveryRequest.getClientName());

        binding.tvCustomerName.setText("Customer Name: "+deliveryRequest.getCustomerName());
        binding.tvCustomerNumber.setText("Customer Number: "+deliveryRequest.getCustomerNumber());

        binding.tvProductType.setText("Product Type: "+deliveryRequest.getProductType());
        binding.tvPIckupAddress.setText("Pickup address: "+deliveryRequest.getPickUpAddress());
        binding.tvDeliveryArea.setText("Delivery address (Area): "+deliveryRequest.getDeliveryArea());
        binding.tvDeliveryAddressNote.setText("Delivery address note: "+deliveryRequest.getDeliveryAddressExtra());
        String ridername=deliveryRequest.getRiderName().equals("")?"":("Rider ->"+deliveryRequest.getRiderName());
        switch (deliveryRequest.getDeliveryStatus()){
            case 0:
                binding.tvRequestStatusText.setText("Waiting for Request Acceptance"+ridername);
                binding.status1.setVisibility(View.VISIBLE);
                binding.status2.setVisibility(View.GONE);
                break;
            case 1:
                binding.tvRequestStatusText.setText("Request Accepted. Assign a Aider"+ridername);
                binding.status1.setVisibility(View.GONE);
                binding.status2.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.tvRequestStatusText.setText("Rider Assigned."+"\n\n"+ridername);
                binding.status1.setVisibility(View.GONE);
                binding.status2.setVisibility(View.VISIBLE);
                binding.btnAssignRider.setText("Re-Assign Rider");
                break;
            case 3:
                binding.tvRequestStatusText.setText("Rider is on his way to pick-up "+ridername);
                binding.status1.setVisibility(View.GONE);
                binding.status2.setVisibility(View.VISIBLE);
                binding.btnAssignRider.setText("Re-Assign Rider");
                break;
            case 4:
                binding.tvRequestStatusText.setText("Received by "+deliveryRequest.getRiderName());
                binding.status1.setVisibility(View.GONE);
                binding.status2.setVisibility(View.VISIBLE);
                binding.btnAssignRider.setText("Re-Assign Rider");
                break;
            case 5:
                binding.tvRequestStatusText.setText("On the way to be delivered "+ridername);
                binding.status1.setVisibility(View.GONE);
                binding.status2.setVisibility(View.VISIBLE);
                binding.btnAssignRider.setText("Re-Assign Rider");
                break;
            case 6:
                binding.tvRequestStatusText.setText("Deliverd."+"\n\n"+ridername);
                binding.status1.setVisibility(View.GONE);
                binding.status2.setVisibility(View.GONE);
                break;
        }
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

}