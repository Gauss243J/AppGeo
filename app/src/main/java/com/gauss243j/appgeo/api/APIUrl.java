package com.gauss243j.appgeo.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUrl {
    public static final String BASE_URL = "http://192.168.43.228/AppGeo/";
    public static Retrofit RETROFIT     = null;
    public static Retrofit getClient(){
        if(RETROFIT==null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }

}
