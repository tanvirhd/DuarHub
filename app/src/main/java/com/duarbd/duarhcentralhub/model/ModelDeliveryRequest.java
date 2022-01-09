package com.duarbd.duarhcentralhub.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDeliveryRequest implements Parcelable {

    @SerializedName("deliveryRequestId")
    @Expose
    private String deliveryRequestId;

    @SerializedName("clientID")
    @Expose
    private String clientID;

    @SerializedName("clientName")
    @Expose
    private String clientName;

    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerNumber")
    @Expose
    private String customerNumber;

    @SerializedName("productType")
    @Expose
    private  String productType;
    @SerializedName("deliveryArea")
    @Expose
    private String deliveryArea;
    @SerializedName("deliveryAddressExtra")
    @Expose
    private String deliveryAddressExtra;
    @SerializedName("pickUpAddress")
    @Expose
    private String pickUpAddress;
    @SerializedName("pickUpAddressLat")
    @Expose
    private String pickUpAddressLat;
    @SerializedName("pickUpAddressLang")
    @Expose
    private String pickUpAddressLang;


    @SerializedName("clientToken")
    @Expose
    private String clientToken;

    @SerializedName("pickupCharge")
    @Expose
    private int pickupCharge;
    @SerializedName("deliveryCharge")
    @Expose
    private int deliveryCharge;
    @SerializedName("productPrice")
    @Expose
    private int productPrice;


    @SerializedName("pickupTime")
    @Expose
    private int pickupTime;//in minute
    @SerializedName("requestPlacedTime")
    @Expose
    private String requestPlacedTime;

    @SerializedName("deliveryStatus")
    @Expose
    private int deliveryStatus;
    @SerializedName("riderName")
    @Expose
    private String riderName;

    @SerializedName("riderid")
    @Expose
    private String riderid;

    @SerializedName("riderClearance")
    @Expose
    private  String riderClearance;

    @SerializedName("clientPaymentStatus")
    @Expose
    private String clientPaymentStatus;

    @SerializedName("pickupCode")
    @Expose
    private String pickupCode;

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("status")
    @Expose
    private String status;




    public ModelDeliveryRequest(){

    }

    public ModelDeliveryRequest(String deliveryRequestId, String riderName, String riderid) {
        this.deliveryRequestId = deliveryRequestId;
        this.riderName = riderName;
        this.riderid = riderid;
    }

    public ModelDeliveryRequest(String deliveryRequestId, String clientID, String clientName, String customerName, String customerNumber,
                                String productType, String deliveryArea, String deliveryAddressExtra, String pickUpAddress,
                                String pickUpAddressLat, String pickUpAddressLang, String clientToken,
                                int pickupCharge, int deliveryCharge, int productPrice, int pickupTime,
                                String requestPlacedTime) {
        this.deliveryRequestId = deliveryRequestId;
        this.clientID=clientID;
        this.clientName=clientName;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.productType = productType;
        this.deliveryArea = deliveryArea;
        this.deliveryAddressExtra = deliveryAddressExtra;
        this.pickUpAddress = pickUpAddress;
        this.pickUpAddressLat = pickUpAddressLat;
        this.pickUpAddressLang = pickUpAddressLang;
        this.clientToken = clientToken;
        this.pickupCharge = pickupCharge;
        this.deliveryCharge = deliveryCharge;
        this.productPrice = productPrice;
        this.pickupTime = pickupTime;
        this.requestPlacedTime = requestPlacedTime;

        this.deliveryStatus=0;
        this.riderName="";
        this.clientPaymentStatus="due";
    }


    public String getDeliveryRequestId() {
        return deliveryRequestId;
    }

    public void setDeliveryRequestId(String deliveryRequestId) {
        this.deliveryRequestId = deliveryRequestId;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public String getDeliveryAddressExtra() {
        return deliveryAddressExtra;
    }

    public void setDeliveryAddressExtra(String deliveryAddressExtra) {
        this.deliveryAddressExtra = deliveryAddressExtra;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getPickUpAddressLat() {
        return pickUpAddressLat;
    }

    public void setPickUpAddressLat(String pickUpAddressLat) {
        this.pickUpAddressLat = pickUpAddressLat;
    }

    public String getPickUpAddressLang() {
        return pickUpAddressLang;
    }

    public void setPickUpAddressLang(String pickUpAddressLang) {
        this.pickUpAddressLang = pickUpAddressLang;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public int getPickupCharge() {
        return pickupCharge;
    }

    public void setPickupCharge(int pickupCharge) {
        this.pickupCharge = pickupCharge;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(int pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getRequestPlacedTime() {
        return requestPlacedTime;
    }

    public void setRequestPlacedTime(String requestPlacedTime) {
        this.requestPlacedTime = requestPlacedTime;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

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

    public String getRiderClearance() {
        return riderClearance;
    }

    public void setRiderClearance(String riderClearance) {
        this.riderClearance = riderClearance;
    }

    public String getClientPaymentStatus() {
        return clientPaymentStatus;
    }

    public void setClientPaymentStatus(String clientPaymentStatus) {
        this.clientPaymentStatus = clientPaymentStatus;
    }

    public String getPickupCode() {
        return pickupCode;
    }

    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //==============================================================================================

    protected ModelDeliveryRequest(Parcel in) {
        deliveryRequestId = in.readString();
        clientID = in.readString();
        clientName = in.readString();
        customerName = in.readString();
        customerNumber = in.readString();
        productType = in.readString();
        deliveryArea = in.readString();
        deliveryAddressExtra = in.readString();
        pickUpAddress = in.readString();
        pickUpAddressLat = in.readString();
        pickUpAddressLang = in.readString();
        clientToken = in.readString();
        pickupCharge = in.readInt();
        deliveryCharge = in.readInt();
        productPrice = in.readInt();
        pickupTime = in.readInt();
        requestPlacedTime = in.readString();
        deliveryStatus = in.readInt();
        riderName = in.readString();
        riderid = in.readString();
        riderClearance = in.readString();
        clientPaymentStatus = in.readString();
        pickupCode = in.readString();
        response = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deliveryRequestId);
        dest.writeString(clientID);
        dest.writeString(clientName);
        dest.writeString(customerName);
        dest.writeString(customerNumber);
        dest.writeString(productType);
        dest.writeString(deliveryArea);
        dest.writeString(deliveryAddressExtra);
        dest.writeString(pickUpAddress);
        dest.writeString(pickUpAddressLat);
        dest.writeString(pickUpAddressLang);
        dest.writeString(clientToken);
        dest.writeInt(pickupCharge);
        dest.writeInt(deliveryCharge);
        dest.writeInt(productPrice);
        dest.writeInt(pickupTime);
        dest.writeString(requestPlacedTime);
        dest.writeInt(deliveryStatus);
        dest.writeString(riderName);
        dest.writeString(riderid);
        dest.writeString(riderClearance);
        dest.writeString(clientPaymentStatus);
        dest.writeString(pickupCode);
        dest.writeString(response);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelDeliveryRequest> CREATOR = new Creator<ModelDeliveryRequest>() {
        @Override
        public ModelDeliveryRequest createFromParcel(Parcel in) {
            return new ModelDeliveryRequest(in);
        }

        @Override
        public ModelDeliveryRequest[] newArray(int size) {
            return new ModelDeliveryRequest[size];
        }
    };
}
