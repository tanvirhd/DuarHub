package com.duarbd.duarhcentralhub.network;

import com.duarbd.duarhcentralhub.model.ModelActiveRider;
import com.duarbd.duarhcentralhub.model.ModelClient;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelRider;
import com.duarbd.duarhcentralhub.model.ModelRiderSalary;
import com.duarbd.duarhcentralhub.model.ModelToken;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("storeHubFcmToken.php")
    Observable<ModelResponse> updateFCMToken(@Body ModelToken hubToken);

    @POST("clientRegistration.php")
    Observable<ModelResponse> registerNewClient(@Body ModelClient client);

    @POST("riderRegistration.php")
    Observable<ModelResponse> registerNewRider(@Body ModelRider rider);

    @POST("getAllRequestedDelivery.php")
    Observable<List<ModelDeliveryRequest>> getAllDeliveryRequest();

    @POST("updateRequestedDeliveryStatusByRequestId.php")
    Observable<ModelResponse> updateDeliveryStatusByRequestId(@Body  ModelDeliveryRequest request);

    @POST("getActiveRiderStatus.php")
    Observable<List<ModelActiveRider>> getActiveRiderList();

    @POST("assignRiderByDeliveryRequestId.php")
    Observable<ModelResponse> assignRiderByDeliveryRequestId(@Body ModelDeliveryRequest deliveryRequest);

    @POST("getAllCompletedDeliveries.php")
    Observable<List<ModelDeliveryRequest>> getAllCompletedDeliveryRequests();

    @POST("getRiderPaymentAll.php")
    Observable<List<ModelRiderSalary>> getRiderSalary();

    @POST("updateRiderPaymentStatusByDeliveryId.php")
    Observable<ModelResponse> updateRiderPaymentStatusByDeliveryId(@Body ModelDeliveryRequest deliveryRequest);

    @POST("getAllRegisteredRiderInformation.php")
    Observable<List<ModelRider>> getAllRegisteredRiderInfo();

    @POST("updateRiderDutyStatus.php")
    Observable<ModelResponse> updateRiderDutyStatus(@Body ModelRider rider);

}
