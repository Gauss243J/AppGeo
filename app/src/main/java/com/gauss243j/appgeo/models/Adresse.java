package com.gauss243j.appgeo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Adresse {

    @SerializedName("idAdresse")
    @Expose
    private int idAdresse;

    @SerializedName("commune")
    @Expose
    private String commune;

    @SerializedName("quartier")
    @Expose
    private String quartier;

    @SerializedName("avenue")
    @Expose
    private String avenue;

    @SerializedName("numero")
    @Expose
    private int numero;

    public Adresse(int idAdresse, String commune, String quartier, String avenue, int numero) {
        this.idAdresse = idAdresse;
        this.commune = commune;
        this.quartier = quartier;
        this.avenue = avenue;
        this.numero = numero;
    }

    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getAvenue() {
        return avenue;
    }

    public void setAvenue(String avenue) {
        this.avenue = avenue;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}