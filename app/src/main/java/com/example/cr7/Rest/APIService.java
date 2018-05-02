package com.example.cr7.Rest;

import com.example.cr7.Model.Appointment;
import com.example.cr7.Model.Career;
import com.example.cr7.Model.Expert;
import com.example.cr7.Model.Skill;
import com.example.cr7.Model.User;
import com.example.cr7.Model.use_map.Directions;

import org.json.JSONArray;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by huong.tx on 1/27/2018.
 */

public interface APIService {

 @GET("expert")
 Call<List<Expert>> getListExpert();

 @GET("users")
 Call<List<User>> getUser(@Query("id") String id, @Query("pass") String pass);

 @POST("users")
 @FormUrlEncoded
 Call<User> addUser( @Field("IdUser") String IdUser,
                     @Field("Password") String Password,
                     @Field("FName") String Fname,
                     @Field("LName") String LName,
                     @Field("avatar") String avatar,
                     @Field("isExpert") int isExpert
 );
 @PUT("users/{id}")
 @FormUrlEncoded
 Call<Integer> updateUserbecomeExpert( @Path("id") String id,
                                       @Field("isExpert") int isExpert);
 @PUT("users/update/{id}")
 @FormUrlEncoded
 Call<Integer> updateUserInfo( @Path("id") String id,
                                       @Field("Password") String Password,
                                       @Field("FName") String Fname,
                                       @Field("LName") String LName,
                                       @Field("avatar") String avatar
 );
 @GET("expert/{id}")
 Call<List<Expert>> getExpert(@Path("id") String id);
 @POST("expert")
 @FormUrlEncoded
 Call<Expert> addExpert( @Field("IdExpert") String IdExpert,
                     @Field("Password") String Password,
                     @Field("LName") String LName,
                     @Field("FName") String FName,
                     @Field("career") String career,
                     @Field("country") String country,
                     @Field("image") String image,
                     @Field("isOnline") int isOnline,
                     @Field("lat") double lat,
                     @Field("lon") double lon,
                     @Field("sdt") String sdt
 );

 @PUT("expert/update/{id}")
 @FormUrlEncoded
 Call<Integer> updateExpertInfo( @Path("id") String id,
                               @Field("Password") String Password,
                               @Field("FName") String Fname,
                               @Field("LName") String LName,
                               @Field("image") String avatar
 );
 @GET("career")
 Call<List<Career>> getListCareer();

 @GET("api/directions/json?key=AIzaSyDomAp4eyEtU9PeMEnro1m8dD9S0SccLIM")
 Call<Directions> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);

 @GET("skill/{id_career}")
 Call<List<Skill>> getSkillByCareer(@Path("id_career") int id);

 @GET("expert_skill/{id_expert}")
 Call<List<Skill>> getSkillByExpert(@Path("id_expert") String id);

 @POST("expert_skill")
 @FormUrlEncoded
 Call<Integer> addExpert_Skill( @Field("expert_id") String id,
                     @Field("skill_id") int skill
 );
 @POST("appointment")
 @FormUrlEncoded
 Call<Integer> addAppointment(@Field("id_expert") String id_expert,
                           @Field("id_user") String id_user,
                           @Field("date") String date,
                           @Field("time") String time,
                           @Field("lat") double lat,
                           @Field("lon") double lon,
                           @Field("message") String message
 );
 @GET("appointmentuser/{id_user}")
 Call<List<Appointment>> getAppointmentOfUser(@Path("id_user") String id);

 @GET("appointmentexpert/{id_expert}")
 Call<List<Appointment>> getAppointmentOfExpert(@Path("id_expert") String id);
}
