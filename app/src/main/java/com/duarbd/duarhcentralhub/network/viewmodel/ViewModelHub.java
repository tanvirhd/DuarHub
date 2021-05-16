package com.duarbd.duarhcentralhub.network.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.duarbd.duarhcentralhub.model.ModelActiveRider;
import com.duarbd.duarhcentralhub.model.ModelClient;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelRider;
import com.duarbd.duarhcentralhub.model.ModelRiderSalary;
import com.duarbd.duarhcentralhub.model.ModelToken;
import com.duarbd.duarhcentralhub.network.repository.Repository;
import com.duarbd.duarhcentralhub.network.work.WorkUpdateToken;

import java.util.List;

public class ViewModelHub extends AndroidViewModel {
    private Repository repository;
    private Context context;
    public static final String PRAM1="key1";
    public static final String PRAM2="key2";
    public static final String PRAM3="key3";
    public static final String PRAM4="key4";
    public static final String PRAM5="key5";

    public ViewModelHub(@NonNull Application application) {
        super(application);
        repository=new Repository();
        context=application.getApplicationContext();
    }

    public void updateFCMToken(String token){
        Data inputdata=new Data.Builder()
                .putString(PRAM1,token)
                .build();

        Constraints constraints=new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest request=new OneTimeWorkRequest.Builder(WorkUpdateToken.class)
                .setInputData(inputdata)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(context).enqueue(request);
    }//not used anymore

    public LiveData<ModelResponse> updateFCMToken(ModelToken hubToken){
        return  repository.updateToken(hubToken);
    }

    public LiveData<ModelResponse> registerNewClient (ModelClient client){
        return repository.registerNewClient(client);
    }

    public  LiveData<ModelResponse> registerNewRider(ModelRider rider){
        return  repository.registerNewRider(rider);
    }

    public LiveData<List<ModelDeliveryRequest>> getAllDeliveryRequest(){
        return repository.getAllDeliveryRequest();
    }

    public LiveData<ModelResponse> updateDeliveryStatusByRequestId(ModelDeliveryRequest request){
        return repository.updateDeliveryStatusByRequestId(request);
    }

    public LiveData<List<ModelActiveRider>> getActiveRiderList(){
        return repository.getActiveRiderList();
    }

    public  LiveData<ModelResponse> assignRiderByDeliveryRequestId(ModelDeliveryRequest deliveryRequest){
        return repository.assignRiderByDeliveryRequestId(deliveryRequest);
    }

    public LiveData<List<ModelDeliveryRequest>>  getAllCompletedDeliveryRequests(){
        return repository.getAllCompletedDeliveryRequests();
    }

    public  LiveData<List<ModelRiderSalary>> getRiderSalary(){
        return repository.getRiderSalary();
    }
    public LiveData<ModelResponse> updateRiderPaymentStatusByDeliveryId(ModelDeliveryRequest delivery){
        return repository.updateRiderPaymentStatusByDeliveryId(delivery);
    }

}
