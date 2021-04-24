package com.duarbd.duarhcentralhub.network;

import com.duarbd.duarhcentralhub.model.ModelClient;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelToken;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("storeHubFcmToken.php")
    Observable<ModelResponse> updateFCMToken(@Body ModelToken hubToken);

    @POST("clientRegistration.php")
    Observable<ModelResponse> registerNewClient(@Body ModelClient client);

}
