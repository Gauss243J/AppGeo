package com.gauss243j.appgeo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CoordoGeo {

    @SerializedName("idCoordoGeo")
    @Expose
    private int idCoordoGeo;

    @SerializedName("longitude")
    @Expose
    private float longitude;

    @SerializedName("latutide")
    @Expose
    private float latutide;

    public CoordoGeo(int idCoordoGeo, float longitude, float latutide) {
        this.idCoordoGeo = idCoordoGeo;
        this.longitude = longitude;
        this.latutide = latutide;
    }

    public int getIdCoordoGeo() {
        return idCoordoGeo;
    }

    public void setIdCoordoGeo(int idCoordoGeo) {
        this.idCoordoGeo = idCoordoGeo;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatutide() {
        return latutide;
    }

    public void setLatutide(float latutide) {
        this.latutide = latutide;
    }
}