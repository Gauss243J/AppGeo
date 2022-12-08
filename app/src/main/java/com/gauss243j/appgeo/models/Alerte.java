package com.gauss243j.appgeo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;


public class Alerte {

    @SerializedName("idAlerte")
    @Expose
    private int idAlerte;

    @SerializedName("accuseReception")
    @Expose
    private int accuseReception;


    public Alerte(int idAlerte,int accuseReception) {
        this.idAlerte = idAlerte;
        this.accuseReception = accuseReception;
    }

    public int getIdAlerte() {
        return idAlerte;
    }

    public void setIdAlerte(int idAlerte) {
        this.idAlerte = idAlerte;
    }

    public int isAccuseReception() {
        return accuseReception;
    }

    public void setAccuseReception(int accuseReception) {
        this.accuseReception = accuseReception;
    }
}