package com.gauss243j.appgeo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Citoyen {

    @SerializedName("idCitoyen")
    @Expose
    private int idCitoyen;

    @SerializedName("nom")
    @Expose
    private String nom;

    @SerializedName("adresseMail")
    @Expose
    private String adresseMail;

    @SerializedName("numeroPhone")
    @Expose
    private String numeroPhone;

    @SerializedName("motdePass")
    @Expose
    private String motdePass;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("numeroCarte")
    @Expose
    private String numeroCarte;

    public Citoyen(int idCitoyen, String nom, String adresseMail, String numeroPhone, String motdePass, String photo, String numeroCarte) {
        this.idCitoyen = idCitoyen;
        this.nom = nom;
        this.adresseMail = adresseMail;
        this.numeroPhone = numeroPhone;
        this.motdePass = motdePass;
        this.photo = photo;
        this.numeroCarte = numeroCarte;
    }

    public Citoyen(int idCitoyen, String nom, String adresseMail, String numeroPhone, String numeroCarte) {
        this.idCitoyen = idCitoyen;
        this.nom = nom;
        this.adresseMail = adresseMail;
        this.numeroPhone = numeroPhone;
        this.numeroCarte = numeroCarte;
    }

    public Citoyen(String nom, String adresseMail, String numeroPhone, String photo, String numeroCarte) {
        this.nom = nom;
        this.adresseMail = adresseMail;
        this.numeroPhone = numeroPhone;
        this.photo = photo;
        this.numeroCarte = numeroCarte;
    }

    public int getIdCitoyen() {
        return idCitoyen;
    }

    public void setIdCitoyen(int idCitoyen) {
        this.idCitoyen = idCitoyen;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getNumeroPhone() {
        return numeroPhone;
    }

    public void setNumeroPhone(String numeroPhone) {
        this.numeroPhone = numeroPhone;
    }

    public String getMotdePass() {
        return motdePass;
    }

    public void setMotdePass(String motdePass) {
        this.motdePass = motdePass;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }
}


