package com.duarbd.duarhcentralhub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelResponse {

    @SerializedName("response")
    @Expose
    private Integer response;
    @SerializedName("status")
    @Expose
    private String status;

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
}
