package com.duarbd.duarhcentralhub.network.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelToken;
import com.duarbd.duarhcentralhub.network.ApiClient;
import com.duarbd.duarhcentralhub.network.ApiInterface;

import static com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub.PRAM1;

public class WorkUpdateToken extends Worker {
    ApiInterface apirequest;
    public WorkUpdateToken(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        apirequest= ApiClient.getApiInterface();
    }

    @NonNull
    @Override
    public Result doWork() {
        /*Data inputData=getInputData();
        String _token=inputData.getString(PRAM1);
        ModelToken token=new ModelToken(_token);
        ModelResponse response=apirequest.updateFCMToken(token);
        if(response.getResponse()==1){
            return Result.success();
        } else return Result.failure();*/
        return null;
    }
}
