package com.ebveneto.models;

/**
 * Created by Asmita on 18-01-2017.
 */

public class Location {
    private int locationId;
    private String locationTilte;
    private String locationRagione;
    private String locationIVA;
    private String locationcf;
    private String locationVia;
    private String locationCAP;
    private String locationComune;
    private String locationProvince;
    private String locationTelephone;
    private String locationFax;
    private String locationEmail;
    private String locationAttivia;
    private String locationLegalerap;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationTilte() {
        return locationTilte;
    }

    public void setLocationTilte(String locationTilte) {
        this.locationTilte = locationTilte;
    }

    @Override
    public String toString() {
        return locationTilte;
    }


    public String getLocationRagione() {
        return locationRagione;
    }

    public void setLocationRagione(String locationRagione) {
        this.locationRagione = locationRagione;
    }

    public String getLocationIVA() {
        return locationIVA;
    }

    public void setLocationIVA(String locationIVA) {
        this.locationIVA = locationIVA;
    }

    public String getLocationcf() {
        return locationcf;
    }

    public void setLocationcf(String locationcf) {
        this.locationcf = locationcf;
    }

    public String getLocationVia() {
        return locationVia;
    }

    public void setLocationVia(String locationVia) {
        this.locationVia = locationVia;
    }

    public String getLocationCAP() {
        return locationCAP;
    }

    public void setLocationCAP(String locationCAP) {
        this.locationCAP = locationCAP;
    }

    public String getLocationComune() {
        return locationComune;
    }

    public void setLocationComune(String locationComune) {
        this.locationComune = locationComune;
    }

    public String getLocationProvince() {
        return locationProvince;
    }

    public void setLocationProvince(String locationProvince) {
        this.locationProvince = locationProvince;
    }

    public String getLocationTelephone() {
        return locationTelephone;
    }

    public void setLocationTelephone(String locationTelephone) {
        this.locationTelephone = locationTelephone;
    }

    public String getLocationFax() {
        return locationFax;
    }

    public void setLocationFax(String locationFax) {
        this.locationFax = locationFax;
    }

    public String getLocationEmail() {
        return locationEmail;
    }

    public void setLocationEmail(String locationEmail) {
        this.locationEmail = locationEmail;
    }

    public String getLocationAttivia() {
        return locationAttivia;
    }

    public void setLocationAttivia(String locationAttivia) {
        this.locationAttivia = locationAttivia;
    }

    public String getLocationLegalerap() {
        return locationLegalerap;
    }

    public void setLocationLegalerap(String locationLegalerap) {
        this.locationLegalerap = locationLegalerap;
    }
}
