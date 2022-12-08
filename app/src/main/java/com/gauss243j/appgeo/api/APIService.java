package com.gauss243j.appgeo.api;

import com.gauss243j.appgeo.models.Adresse;
import com.gauss243j.appgeo.models.Alerte;
import com.gauss243j.appgeo.models.Citoyen;
import com.gauss243j.appgeo.models.CitoyenImg;
import com.gauss243j.appgeo.models.CoordoGeo;
import com.gauss243j.appgeo.models.MyResponse;

import java.sql.Timestamp;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {

    @FormUrlEncoded
    @POST("connection/rectoapp.php")
    Call<List<Citoyen>> createCitoyen(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("numerophone") String numerophone,
                                      @Field("password") String password,
                                      @Field("numcarte") String numCarte);

    @FormUrlEncoded
    @POST("connection/contoapp.php")
    Call<MyResponse> loginCitoyen(@Field("name") String name,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("connection/recadress.php")
    Call<List<Adresse>> createAdress(@Field("idCitoyen") int idCitoyen,
                                     @Field("commune") String commune,
                                     @Field("quartier") String quartier,
                                     @Field("avenue") String avenue,
                                     @Field("numero") int numero);

    @FormUrlEncoded
    @POST("connection/reccoordogeo.php")
    Call<List<CoordoGeo>> createCoordoGeo(@Field("idAdresse") int idAdresse,
                                     @Field("longitude") float longitude,
                                     @Field("latutide") float latutide);

    @FormUrlEncoded
    @POST("connection/alerte.php")
    Call<List<Alerte>> createAlerte( @Field("idCitoyen") int idCitoyen);

    @FormUrlEncoded
    @POST("connection/rectoapp.php")
    Call<List<CoordoGeo>> createCoodoGeo(@Field("name") float longitue,
                                         @Field("email") float latitude);

    @GET("tecnologie/essaie.php")
    Call<List<Citoyen>> getCitoyen(@Query("field")int query);

    @GET("tecnologie/essaie.php")
    Call<List<Adresse>> getAdress(@Query("field")int query);

    @GET("connection/getAlerte.php")
    Call<List<Alerte>> getAlerte(@Query("idCitoyen")int query1, @Query("idAlerte")int query2);

 /*   //the sign in call
    @FormUrlEncoded
    @POST("connection/contoapp.php")
    Call<List<Resultcon>> userLogin(
            @Field("email") String email,
            @Field("password") String password);

   //post token
    @FormUrlEncoded
    @POST("fcm/RegisterDevice.php")
    Call<MessageResponse> postNotific(
            @Field("token") String code,
            @Field("email") String email);

    //sending message
  @FormUrlEncoded
    @POST("tecnologie/sendmessage.php")
    Call<MessageResponse> sendMessage(
            @Field("autor") int author,
            @Field("id_message") int to,
            @Field("answer") String answer,
            @Field("field") int field
            );
    @GET("tecnologie/essaie.php")
    Call<List<Topic>> getTopict(@Query("field")int query);
 */
        @Multipart
    @POST("connection/updeteavatar1.php?apicall=upload")
    Call<List<CitoyenImg>> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("desc") RequestBody desc,@Query("id")int query);

    @GET("connection/updeteavatar1.php?apicall=getallimages")
    Call<List<CitoyenImg>> getImageCitoyen(@Query("id")int query);

}

