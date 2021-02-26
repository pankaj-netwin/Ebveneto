package com.ebveneto.models;

/**
 * Created by Asmita on 24-01-2017.
 */

public class Country {
    private String countryName;
    private String countryKey;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return countryName;
    }

    public String getCountryKey() {
        return countryKey;
    }

    public void setCountryKey(String countryKey) {
        this.countryKey = countryKey;
    }
}
