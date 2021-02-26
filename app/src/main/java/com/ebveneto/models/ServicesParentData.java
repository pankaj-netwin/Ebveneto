package com.ebveneto.models;

import java.util.ArrayList;

/**
 * Created by Asmita on 05-01-2017.
 */

public class ServicesParentData {
    private String name;
    private ArrayList<ServicesChildData> list = new ArrayList<ServicesChildData>();
    private String serviceId;
    private String imageUrl;

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ServicesChildData> getProductList() {
        return list;
    }

    public void setProductList(ArrayList<ServicesChildData> productList) {
        this.list = productList;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
