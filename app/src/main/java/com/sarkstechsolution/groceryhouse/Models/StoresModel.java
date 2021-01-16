package com.sarkstechsolution.groceryhouse.Models;

public class StoresModel {

    private String storename, storeid, storetype, password, ownername, logo, latitude, longitude, phone1, phone2, storecloses, storeopens;



    public StoresModel() {
    }

    public StoresModel(String storename, String storeid, String storetype, String password, String ownername, String logo, String latitude, String longitude, String phone1, String phone2, String storecloses, String storeopens) {
        this.storename = storename;
        this.storeid = storeid;
        this.storetype = storetype;
        this.password = password;
        this.ownername = ownername;
        this.logo = logo;
        this.latitude = latitude;
        this.longitude = longitude; 
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.storecloses = storecloses;
        this.storeopens = storeopens;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStoretype() {
        return storetype;
    }

    public void setStoretype(String storetype) {
        this.storetype = storetype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getStorecloses() {
        return storecloses;
    }

    public void setStorecloses(String storecloses) {
        this.storecloses = storecloses;
    }

    public String getStoreopens() {
        return storeopens;
    }

    public void setStoreopens(String storeopens) {
        this.storeopens = storeopens;
    }
}
