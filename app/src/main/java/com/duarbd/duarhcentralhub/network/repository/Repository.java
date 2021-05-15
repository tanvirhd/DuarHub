package com.duarbd.duarhcentralhub.network.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.duarbd.duarhcentralhub.model.ModelActiveRider;
import com.duarbd.duarhcentralhub.model.ModelClient;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelRider;
import com.duarbd.duarhcentralhub.model.ModelRiderSalary;
import com.duarbd.duarhcentralhub.model.ModelToken;
import com.duarbd.duarhcentralhub.network.ApiClient;
import com.duarbd.duarhcentralhub.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class Repository {
    private static final String TAG = "Repository";
    private ApiInterface apiRequest;


    public Repository() {
        apiRequest= ApiClient.getApiInterface();
    }

    public LiveData<ModelResponse> updateToken(ModelToken hubtoken){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
       apiRequest.updateFCMToken(hubtoken).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Consumer<ModelResponse>() {
                   @Override
                   public void accept(ModelResponse modelResponse) throws Exception {
                       result.postValue(modelResponse);
                       if(modelResponse!=null){
                           if(modelResponse.getResponse()==1)
                               Log.d(TAG, "onChanged: token updated");
                           else Log.d(TAG, "onChanged: token update failed");
                       }
                   }
               }, new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable throwable) throws Exception {
                       Log.d(TAG, "updateToken: error:"+throwable.getMessage());
                   }
               });
        return result;
    }

    public LiveData<ModelResponse> registerNewClient(ModelClient client){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.registerNewClient(client).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        if(modelResponse==null) Log.d(TAG, "accept: yes response is null");
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "registerNewClient: error:"+throwable.getMessage());
                    }
                });
        return  result;
    }

    public  LiveData<ModelResponse> registerNewRider(ModelRider rider){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.registerNewRider(rider).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "registerNewRider: error:"+throwable.getMessage());
                    }
                });
        return  result;
    }

    public LiveData<List<ModelDeliveryRequest>> getAllDeliveryRequest(){
        MutableLiveData<List<ModelDeliveryRequest>> result=new MutableLiveData<>();
        apiRequest.getAllDeliveryRequest().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void accept(List<ModelDeliveryRequest> modelDeliveryRequests) throws Exception {
                        result.postValue(modelDeliveryRequests);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "getAllDeliveryRequest: error:"+throwable.getMessage());
                    }
                });
        return  result;
    }

    public LiveData<ModelResponse> updateDeliveryStatusByRequestId(ModelDeliveryRequest request){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.updateDeliveryStatusByRequestId(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "updateDeliveryStatusByRequestId: error:"+throwable.getMessage());
                    }
                });
        return result;
    }

    public LiveData<List<ModelActiveRider>> getActiveRiderList(){
        MutableLiveData<List<ModelActiveRider>> result=new MutableLiveData<>();
        apiRequest.getActiveRiderList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelActiveRider>>() {
                    @Override
                    public void accept(List<ModelActiveRider> modelActiveRiders) throws Exception {
                        result.postValue(modelActiveRiders);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "getActiveRiderList: error:"+throwable.getMessage());
                    }
                });
        return result;
    }


    public  LiveData<ModelResponse> assignRiderByDeliveryRequestId(ModelDeliveryRequest deliveryRequest){
        MutableLiveData<ModelResponse> result=new MutableLiveData<>();
        apiRequest.assignRiderByDeliveryRequestId(deliveryRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ModelResponse>() {
                    @Override
                    public void accept(ModelResponse modelResponse) throws Exception {
                        result.postValue(modelResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "assignRiderByDeliveryRequestId: error:"+throwable.getMessage());
                    }
                });

        return  result;
    }

    public LiveData<List<ModelDeliveryRequest>>  getAllCompletedDeliveryRequests(){
        MutableLiveData<List<ModelDeliveryRequest>> result=new MutableLiveData<>();
        apiRequest.getAllCompletedDeliveryRequests().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void accept(List<ModelDeliveryRequest> modelDeliveryRequests) throws Exception {
                        result.postValue(modelDeliveryRequests);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "getAllCompletedDeliveryRequests: error:"+throwable.getMessage());
                    }
                });
        return result;
    }

    public  LiveData<List<ModelRiderSalary>> getRiderSalary(){
        MutableLiveData<List<ModelRiderSalary>> result=new MutableLiveData<>();
        apiRequest.getRiderSalary().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelRiderSalary>>() {
                    @Override
                    public void accept(List<ModelRiderSalary> modelRiderSalaries) throws Exception {
                        result.postValue(modelRiderSalaries);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "getRiderSalary: error"+throwable.getMessage());
                    }
                });
        return  result;
    }
}
