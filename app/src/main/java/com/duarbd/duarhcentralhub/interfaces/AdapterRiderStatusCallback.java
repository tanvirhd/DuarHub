package com.duarbd.duarhcentralhub.interfaces;

import com.duarbd.duarhcentralhub.model.ModelRider;

public interface AdapterRiderStatusCallback {
    void  onCallRiderClicked(ModelRider rider);
    void onRiderSutyStatusSwitchClicked(ModelRider rider,boolean dutystatus);
}
