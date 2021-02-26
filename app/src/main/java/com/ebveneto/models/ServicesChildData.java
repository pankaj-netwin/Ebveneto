package com.ebveneto.models;

/**
 * Created by Asmita on 05-01-2017.
 */

public class ServicesChildData {
    private String sequence = "";
    private String name = "";
    private String serviceId;

    public ServicesChildData()
    {
    }

    // for requested service screen
    public ServicesChildData(String name, String serviceId)
    {
        this.name = name;
        this.serviceId = serviceId;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
