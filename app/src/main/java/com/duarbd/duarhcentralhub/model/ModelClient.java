package com.duarbd.duarhcentralhub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelClient {

    @SerializedName("clientid")
    @Expose
    private  String clientid;

    @SerializedName("clientContactNumber")
    @Expose
    private String clientContactNumber;

    @SerializedName("clientBusinessName")
    @Expose
    private String clientBusinessName;

    @SerializedName("clientBusinessLocationlat")
    @Expose
    private  double clientBusinessLocationlat;

    @SerializedName("clientBusinessLocationlang")
    @Expose
    private double clientBusinessLocationlang;

    @SerializedName("clientAddress")
    @Expose
    private String clientAddress;

    @SerializedName("clientProductType")
    @Expose
    private String clientProductType;

    @SerializedName("clientPassword")
    @Expose
    private String clientPassword;

    @SerializedName("pickupCharge")
    @Expose
    private int pickupCharge;

    public ModelClient(String clientid, String clientContactNumber, String clientBusinessName, double clientBusinessLocationlat, double clientBusinessLocationlang, String clientAddress, String clientProductType, String clientPassword, int pickupCharge) {
        this.clientid = clientid;
        this.clientContactNumber = clientContactNumber;
        this.clientBusinessName = clientBusinessName;
        this.clientBusinessLocationlat = clientBusinessLocationlat;
        this.clientBusinessLocationlang = clientBusinessLocationlang;
        this.clientAddress = clientAddress;
        this.clientProductType = clientProductType;
        this.clientPassword = clientPassword;
        this.pickupCharge = pickupCharge;
    }

    public int getPickupCharge() {
        return pickupCharge;
    }

    public void setPickupCharge(int pickupCharge) {
        this.pickupCharge = pickupCharge;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientContactNumber() {
        return clientContactNumber;
    }

    public void setClientContactNumber(String clientContactNumber) {
        this.clientContactNumber = clientContactNumber;
    }

    public String getClientBusinessName() {
        return clientBusinessName;
    }

    public void setClientBusinessName(String clientBusinessName) {
        this.clientBusinessName = clientBusinessName;
    }

    public double getClientBusinessLocationlat() {
        return clientBusinessLocationlat;
    }

    public void setClientBusinessLocationlat(double clientBusinessLocationlat) {
        this.clientBusinessLocationlat = clientBusinessLocationlat;
    }

    public double getClientBusinessLocationlang() {
        return clientBusinessLocationlang;
    }

    public void setClientBusinessLocationlang(double clientBusinessLocationlang) {
        this.clientBusinessLocationlang = clientBusinessLocationlang;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientProductType() {
        return clientProductType;
    }

    public void setClientProductType(String clientProductType) {
        this.clientProductType = clientProductType;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }
}
