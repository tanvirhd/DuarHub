package com.duarbd.duarhcentralhub.model;

public class ModelRiderClearance {
    private String riderName;
    private int totalRide;

    public ModelRiderClearance(String riderName, int totalRide) {
        this.riderName = riderName;
        this.totalRide = totalRide;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public int getTotalRide() {
        return totalRide;
    }

    public void setTotalRide(int totalRide) {
        this.totalRide = totalRide;
    }
}
