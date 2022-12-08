package com.gauss243j.appgeo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitoyenImg {

    @SerializedName("idCitoyen")
    @Expose
    private int id;
    @SerializedName("photo")
    @Expose
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImage() {
        return image;
    }

    public void setImage1(String image) {
        this.image = image;
    }


}