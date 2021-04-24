package com.duarbd.duarhcentralhub.presenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterProductType;
import com.duarbd.duarhcentralhub.databinding.ActivityRegisterNewClientBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterProductTypeCallBacks;
import com.duarbd.duarhcentralhub.model.ModelClient;
import com.duarbd.duarhcentralhub.model.ModelProductType;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActivityRegisterNewClient extends AppCompatActivity implements AdapterProductTypeCallBacks {
    private static final String TAG = "ActivityRegisterNewClie";
    private ActivityRegisterNewClientBinding binding;
    private List<ModelProductType> productTypeList;
    private AdapterProductType adapterProductType;
    public static Place clientBusinessPlace=null;
    private int selectedTypePosition=-1;

    private FirebaseAuth mAuth;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthCredential credential;
    private  String mVerificationId;
    private  PhoneAuthProvider.ForceResendingToken mResendToken;

    private String OTP_STATUS="Send OTP";
    private ViewModelHub viewModelHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterNewClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setupProductType();

        binding.openmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityRegisterNewClient.this,MapsActivity.class));
            }
        });

        binding.btnPhoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (OTP_STATUS){
                    case "Send OTP":
                        binding.btnPhoneAuth.setText("Verify OTP");OTP_STATUS="Verify OTP";
                        binding.etOTP.setEnabled(true);
                        sendVerificationCode(binding.clientPhoneNumber.getText().toString());
                        break;
                    case "Verify OTP":
                        credential=createPhoneAuthCredential(mVerificationId,binding.etOTP.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                        break;
                }

            }
        });

        binding.btnCompleteRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etClientBusinessName.equals("")||binding.tvClientAddress.equals("")||selectedTypePosition==-1||
                     !binding.btnPhoneAuth.getText().equals("Verified")||clientBusinessPlace==null){
                    Toast.makeText(ActivityRegisterNewClient.this, "Fill-up all Field", Toast.LENGTH_SHORT).show();
                } else {
                    ModelClient newClient=new ModelClient(binding.clientPhoneNumber.getText().toString(), binding.clientPhoneNumber.getText().toString(),
                            binding.etClientBusinessName.getText().toString(),clientBusinessPlace.getLatLng().latitude, clientBusinessPlace.getLatLng().longitude,
                            binding.tvClientAddress.getText().toString(), productTypeList.get(selectedTypePosition).getProductTypeName(),
                            binding.etClientBusinessName.getText().toString().substring(0,3).replaceAll("\\s+","")+binding.clientPhoneNumber.getText().toString(),
                            0); //todo add pickup charge field to XMl
                    viewModelHub.registerNewClient(newClient).observe(ActivityRegisterNewClient.this,
                            new Observer<ModelResponse>() {
                                @Override
                                public void onChanged(ModelResponse modelResponse) {
                                    if(modelResponse!=null && modelResponse.getResponse()==1){
                                        //reg complete
                                        clientBusinessPlace=null;finish();
                                    }else {
                                        Toast.makeText(ActivityRegisterNewClient.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        /*SafetyNet.getClient(this).isVerifyAppsEnabled() // check If SaftyNet is enabled or not
                .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.VerifyAppsUserResponse>() {
                    @Override
                    public void onComplete(Task<SafetyNetApi.VerifyAppsUserResponse> task) {
                        if (task.isSuccessful()) {
                            SafetyNetApi.VerifyAppsUserResponse result = task.getResult();
                            if (result.isVerifyAppsEnabled()) {
                                Log.d("MY_APP_TAG", "The Verify Apps feature is enabled.");
                            } else {
                                Log.d("MY_APP_TAG", "The Verify Apps feature is disabled.");
                            }
                        } else {
                            Log.e("MY_APP_TAG", "A general error occurred.");
                        }
                    }
                });*/
    }


    void  init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Client Registration");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAuth=FirebaseAuth.getInstance();
        initPhoneAuthProvider();
        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);

        productTypeList=new ArrayList<>();
        adapterProductType=new AdapterProductType(productTypeList,this,this);
        binding.recycProductType.setAdapter(adapterProductType);
        binding.recycProductType.setLayoutManager(new GridLayoutManager(this,3));
    }

    void setupProductType(){
        productTypeList.add(new ModelProductType("Food",false));
        productTypeList.add(new ModelProductType("Clothing",false));
        productTypeList.add(new ModelProductType("Crafts",false));
        productTypeList.add(new ModelProductType("Electronics",false));
        productTypeList.add(new ModelProductType("Fashion Accessories",false));
        productTypeList.add(new ModelProductType("Beauty Product",false));
        productTypeList.add(new ModelProductType("Jewellery",false));
        productTypeList.add(new ModelProductType("Other",false));
        adapterProductType.notifyDataSetChanged();
    }

    @Override
    public void onProductTypeClicked(int position) {

        if(productTypeList.get(position).isSelected()){
            selectedTypePosition=-1;
            for(ModelProductType type:productTypeList)
                type.setSelected(false);
        }
        else {
            for(ModelProductType type:productTypeList)
                type.setSelected(false);
            productTypeList.get(position).setSelected(true);
            selectedTypePosition=position;
        }
        adapterProductType.notifyDataSetChanged();
    }

    void  sendVerificationCode(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+88"+phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    void  initPhoneAuthProvider(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted: Auto Completed");

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.d(TAG, "onVerificationFailed: error= "+ e.getMessage());

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.d(TAG, "onVerificationFailed: error= "+ e.getMessage());
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent : Called");
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    PhoneAuthCredential  createPhoneAuthCredential(String vID, String vCode){
        return  PhoneAuthProvider.getCredential(vID, vCode);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            onVerificationCompleteUIUpdate();
                        } else {
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ActivityRegisterNewClient.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private void onVerificationCompleteUIUpdate(){
        binding.btnPhoneAuth.setText("Verified");binding.outlinedTextField1.setEnabled(false);
        binding.btnPhoneAuth.setBackground(getResources().getDrawable(R.drawable.bg_cr8_green));
        binding.etOTP.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(clientBusinessPlace!=null){
            binding.tvClientAddress.setText(clientBusinessPlace.getAddress());
        }
    }


}