package com.duarbd.duarhcentralhub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelActiveRider {

    @SerializedName("riderName")
    @Expose
    private String riderName;

    @SerializedName("riderid")
    @Expose
    private String riderid;

    @SerializedName("response")
    @Expose
    private Integer response;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("delivery")
    @Expose
    private List<ModelAssignedDelivery> delivery = null;

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderid() {
        return riderid;
    }

    public void setRiderid(String riderid) {
        this.riderid = riderid;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ModelAssignedDelivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<ModelAssignedDelivery> delivery) {
        this.delivery = delivery;
    }

    public class ModelAssignedDelivery{
        @SerializedName("pickUpAddress")
        @Expose
        private String pickUpAddress;
        @SerializedName("deliveryArea")
        @Expose
        private String deliveryArea;
        @SerializedName("deliveryStatus")
        @Expose
        private String deliveryStatus;

        public String getPickUpAddress() {
            return pickUpAddress;
        }

        public void setPickUpAddress(String pickUpAddress) {
            this.pickUpAddress = pickUpAddress;
        }

        public String getDeliveryArea() {
            return deliveryArea;
        }

        public void setDeliveryArea(String deliveryArea) {
            this.deliveryArea = deliveryArea;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }
    }
}


