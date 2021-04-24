package com.duarbd.duarhcentralhub.presenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.databinding.ActivityRegisterNewRiderBinding;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelRider;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivityRegisterNewRider extends AppCompatActivity {
    private static final String TAG = "ActivityRegisterNewRide";
    private ActivityRegisterNewRiderBinding binding;

    private FirebaseAuth mAuth;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthCredential credential;
    private  String mVerificationId;
    private  PhoneAuthProvider.ForceResendingToken mResendToken;
    private ViewModelHub viewModelHub;

    private String OTP_STATUS="Send OTP";
    private String selectedRiderVehicleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterNewRiderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

        binding.btnPhoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (OTP_STATUS){
                    case "Send OTP":
                        binding.btnPhoneAuth.setText("Verify OTP");OTP_STATUS="Verify OTP";
                        binding.etOTP.setEnabled(true);
                        sendVerificationCode(binding.etRiderPhnNumber.getText().toString());
                        break;
                    case "Verify OTP":
                        credential=createPhoneAuthCredential(mVerificationId,binding.etOTP.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                        break;
                }
            }
        });

        binding.rgRiderVehicleType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbBicycle:
                        selectedRiderVehicleType="Bicycle";
                        break;
                    case R.id.rbMotorBike:
                        selectedRiderVehicleType="Motor Bike";
                        break;
                    case R.id.rbEasyBike:
                        selectedRiderVehicleType="Easy Bike";
                        break;
                }
            }
        });

        binding.btnRiderRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etRiderName.getText().toString().equals("")||
                    binding.etRiderPhnNumber.getText().toString().equals("")||
                     binding.etRiderPhnNumber.getText().toString().length()!=11||
                       binding.etOTP.getText().toString().equals("")||
                        selectedRiderVehicleType.equals("")||selectedRiderVehicleType==null){
                    Toast.makeText(ActivityRegisterNewRider.this, "Fill all Fields", Toast.LENGTH_SHORT).show();
                }else {
                    ModelRider rider=new ModelRider(
                            binding.etRiderPhnNumber.getText().toString(),
                            binding.etRiderName.getText().toString(),
                            binding.etRiderPhnNumber.getText().toString(),
                            selectedRiderVehicleType,
                            binding.etRiderName.getText().toString().substring(0,3).replaceAll("\\s+","")+binding.etRiderPhnNumber.getText().toString()

                    );
                    viewModelHub.registerNewRider(rider).observe(ActivityRegisterNewRider.this,
                            new Observer<ModelResponse>() {
                                @Override
                                public void onChanged(ModelResponse modelResponse) {
                                    if(modelResponse!=null && modelResponse.getResponse()==1){
                                        //reg complete

                                    }else {
                                        Toast.makeText(ActivityRegisterNewRider.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }//end of onCreate

    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Rider Registration");
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
        selectedRiderVehicleType="";
    }

    void  sendVerificationCode(String phoneNumber){
        if(phoneNumber.length()!=11){
            Toast.makeText(this, "Input an valid phone number", Toast.LENGTH_SHORT).show();
        }else {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+88"+phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(mCallbacks)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
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
                            //FirebaseUser user = task.getResult().getUser();
                            onVerificationCompleteUIUpdate();
                        } else {
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ActivityRegisterNewRider.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
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
}