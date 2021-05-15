package com.duarbd.duarhcentralhub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRiderSalary {

    @SerializedName("riderName")
    @Expose
    private String riderName;

    @SerializedName("vehicletype")
    @Expose
    private String riderVehicle;

    @SerializedName("riderSalary")
    @Expose
    private String riderSalary;

    @SerializedName("response")
    @Expose
    private Integer response;
    @SerializedName("status")
    @Expose
    private Integer status;

    public ModelRiderSalary(String riderName, String riderVehicle, String riderSalary) {
        this.riderName = riderName;
        this.riderVehicle = riderVehicle;
        this.riderSalary = riderSalary;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderVehicle() {
        return riderVehicle;
    }

    public void setRiderVehicle(String riderVehicle) {
        this.riderVehicle = riderVehicle;
    }

    public String getRiderSalary() {
        return riderSalary;
    }

    public void setRiderSalary(String riderSalary) {
        this.riderSalary = riderSalary;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
