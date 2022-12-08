package com.gauss243j.appgeo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Police {

    @SerializedName("idPolice")
    @Expose
    private int idPolice;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("adresseMail")
    @Expose
    private String adresseMail;

    public Police(int idPolice, String password, String adresseMail) {
        this.idPolice = idPolice;
        this.password = password;
        this.adresseMail = adresseMail;
    }

    public int getIdPolice() {
        return idPolice;
    }

    public void setIdPolice(int idPolice) {
        this.idPolice = idPolice;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }
}