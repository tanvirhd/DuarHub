package com.duarbd.duarhcentralhub.interfaces;

import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;

public interface AdapterRiderRideHistoryCallback {

    void onMoreClicked(ModelDeliveryRequest delivery);
    void onGiveClearanceClicked(ModelDeliveryRequest delivery);
}
