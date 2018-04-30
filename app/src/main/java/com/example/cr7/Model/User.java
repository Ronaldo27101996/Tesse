package com.example.cr7.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CR7 on 3/13/2018.
 */

public class User implements Serializable{
    @SerializedName("IdUser")
    @Expose
    private String idUser;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("FName")
    @Expose
    private String fName;
    @SerializedName("LName")
    @Expose
    private String lName;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("isExpert")
    @Expose
    private int isExpert;

    public User() {
    }

    public User(String idUser, String password, String fName, String lName, String avatar, int isExpert) {
        this.idUser = idUser;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.avatar = avatar;
        this.isExpert = isExpert;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIsExpert() {
        return isExpert;
    }

    public void setIsExpert(int isExpert) {
        this.isExpert = isExpert;
    }
}
