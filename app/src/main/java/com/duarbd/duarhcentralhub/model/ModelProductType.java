package com.duarbd.duarhcentralhub.model;

public class ModelProductType {
    private String productTypeName;
    private boolean isSelected;

    public ModelProductType(String productTypeName, boolean isSelected) {
        this.productTypeName = productTypeName;
        this.isSelected = isSelected;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
