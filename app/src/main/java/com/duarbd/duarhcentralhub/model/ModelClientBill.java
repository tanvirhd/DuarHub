package com.duarbd.duarhcentralhub.model;

public class ModelClientBill {
    private String client_name,total_bill,total_deliveryCharge,total_order;

    public ModelClientBill(String client_name, String total_bill, String total_deliveryCharge, String total_order) {
        this.client_name = client_name;
        this.total_bill = total_bill;
        this.total_deliveryCharge = total_deliveryCharge;
        this.total_order = total_order;
    }

    public ModelClientBill() {
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(String total_bill) {
        this.total_bill = total_bill;
    }

    public String getTotal_deliveryCharge() {
        return total_deliveryCharge;
    }

    public void setTotal_deliveryCharge(String total_deliveryCharge) {
        this.total_deliveryCharge = total_deliveryCharge;
    }

    public String getTotal_order() {
        return total_order;
    }

    public void setTotal_order(String total_order) {
        this.total_order = total_order;
    }
}
