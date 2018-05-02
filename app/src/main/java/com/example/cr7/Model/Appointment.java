package com.example.cr7.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_expert")
    @Expose
    private String idExpert;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isAccepted")
    @Expose
    private int isAccepted;
    public Appointment() {
    }


    public Appointment(Integer id, String idExpert, String idUser, String date, String time, double lat, double lon, String message, int isAccepted) {
        this.id = id;
        this.idExpert = idExpert;
        this.idUser = idUser;
        this.date = date;
        this.time = time;
        this.lat = lat;
        this.lon = lon;
        this.message = message;
        this.isAccepted = isAccepted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdExpert() {
        return idExpert;
    }

    public void setIdExpert(String idExpert) {
        this.idExpert = idExpert;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }
}