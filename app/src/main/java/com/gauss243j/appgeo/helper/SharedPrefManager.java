package com.gauss243j.appgeo.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.gauss243j.appgeo.models.Adresse;
import com.gauss243j.appgeo.models.Alerte;
import com.gauss243j.appgeo.models.Citoyen;
import com.gauss243j.appgeo.models.CoordoGeo;

/**
 * Created by Belal on 14/04/17.
 */

public class SharedPrefManager {

    private static com.gauss243j.appgeo.helper.SharedPrefManager mInstance;
    private static Context mCtx;


    private static final String TAG_TOKEN = "tagtoken";

    private static final String SHARED_PREF_NAME = "AppGeo";
    private static final String KEY_USER_ETAT_CONNECT = "keyetatconnect";

    private static final String KEY_USER_IDCITOYEN = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_PASSWORD= "keyuserpassword";
    private static final String KEY_USER_PHONE= "keyuserphoto";
    private static final String KEY_USER_DATEPOST= "keyuserdatepost";
    private static final String KEY_USER_PHOTO= "keyuserphoto";
    private static final String KEY_USER_NUMEROCARTE= "keyusernumcarte";

    private static final String KEY_USER_IDADRESSE= "keyuseridadd";
    private static final String KEY_USER_COMMUNE= "keyusercommune";
    private static final String KEY_USER_QUARTIER= "keyuserquartier";
    private static final String KEY_USER_AVENUE= "keyuseravenue";
    private static final String KEY_USER_NUMERO= "keyusernumero";

    private static final String KEY_USER_IDCOORDOGEO= "keyuseridcoordo";
    private static final String KEY_USER_LONGITUDE= "keyuserlongitude";
    private static final String KEY_USER_LATITUDE= "keyuserlatitude";

    private static final String KEY_USER_ALERTE= "keyusercoordos";
    private static final String KEY_USER_ALERTEID= "keyusercoordoids";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized com.gauss243j.appgeo.helper.SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new com.gauss243j.appgeo.helper.SharedPrefManager(context);
        }
        return mInstance;
    }


    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

//-----======================================= CITOYEN ======================================================


    public boolean citoyenLogin(Citoyen citoyen) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_IDCITOYEN, citoyen.getIdCitoyen());
        editor.putString(KEY_USER_NAME, citoyen.getNom());
        editor.putString(KEY_USER_EMAIL, citoyen.getAdresseMail());
        editor.putString(KEY_USER_PHONE, citoyen.getNumeroPhone());
        editor.putString(KEY_USER_PASSWORD, citoyen.getMotdePass());
        editor.putString(KEY_USER_PHOTO, citoyen.getPhoto());
        editor.putString(KEY_USER_NUMEROCARTE, citoyen.getNumeroCarte());
        editor.apply();
        return true;  //editor.putString(KEY_USER_GENDER, user.getGender());
    }

    public Citoyen getCitoyen() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Citoyen(
                sharedPreferences.getInt(KEY_USER_IDCITOYEN, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_PHONE, null),
                sharedPreferences.getString(KEY_USER_PASSWORD, null),
                sharedPreferences.getString(KEY_USER_PHOTO, null),
                sharedPreferences.getString(KEY_USER_NUMEROCARTE, null)

        );

    }

    //-----==========================================ADRESSE======================================================
    public boolean adresseLogin(Adresse adresse) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_IDADRESSE, adresse.getIdAdresse());
        editor.putString(KEY_USER_COMMUNE, adresse.getCommune());
        editor.putString(KEY_USER_QUARTIER, adresse.getQuartier());
        editor.putString(KEY_USER_AVENUE, adresse.getAvenue());
        editor.putInt(KEY_USER_NUMERO, adresse.getNumero());
        editor.apply();
        return true;
    }

    public Adresse getAdresse() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Adresse(
                sharedPreferences.getInt(KEY_USER_IDADRESSE, 0),
                sharedPreferences.getString(KEY_USER_COMMUNE, null),
                sharedPreferences.getString(KEY_USER_QUARTIER, null),
                sharedPreferences.getString(KEY_USER_AVENUE, null),
                sharedPreferences.getInt(KEY_USER_NUMERO, 0)
        );

    }

    //-----========================================COORDONNEEGEO======================================================
    public boolean CoordoGeo(CoordoGeo coordoGeo) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_IDCOORDOGEO, coordoGeo.getIdCoordoGeo());
        editor.putFloat(KEY_USER_LONGITUDE, coordoGeo.getLongitude());
        editor.putFloat(KEY_USER_LATITUDE, coordoGeo.getLatutide());
        editor.apply();
        return true;
    }

    public CoordoGeo getCoordoGeo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new CoordoGeo(
                sharedPreferences.getInt(KEY_USER_IDCOORDOGEO, 0),
                sharedPreferences.getFloat(KEY_USER_LONGITUDE, 0),
                sharedPreferences.getFloat(KEY_USER_LATITUDE, 0)
        );

    }




    //-----========================================ALERTE======================================================
    public boolean Alerte(Alerte alerte) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ALERTE, alerte.isAccuseReception());
        editor.putInt(KEY_USER_ALERTEID, alerte.getIdAlerte());
        editor.apply();
        return true;
    }

    public int getAlerte() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ALERTE, 0);
    }
    public int getIdAlerte() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ALERTEID, 0);
    }

    //-----========================================CONNECT======================================================
    public void connect(int connect) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ETAT_CONNECT, connect);
        editor.apply();
    }

    public int getConnect() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ETAT_CONNECT, 0);


    }


}

















