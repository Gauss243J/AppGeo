package com.gauss243j.appgeo.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyResponse {
    @SerializedName("error")
    @Expose
   private boolean error;

    public Boolean getError(){
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}