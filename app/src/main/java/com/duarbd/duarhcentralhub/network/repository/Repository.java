package com.duarbd.duarhcentralhub.network.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.duarbd.duarhcentralhub.model.ModelClient;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelToken;
import com.duarbd.duarhcentralhub.network.ApiClient;
import com.duarbd.duarhcentralhub.network.ApiInterface;

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
}
