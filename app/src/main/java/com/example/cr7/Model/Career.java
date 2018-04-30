package com.example.cr7.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CR7 on 4/23/2018.
 */

public class Career implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name_career")
    @Expose
    private String nameCareer;

    public Career() {
    }


    public Career(int id, String nameCareer) {
        super();
        this.id = id;
        this.nameCareer = nameCareer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCareer() {
        return nameCareer;
    }

    public void setNameCareer(String nameCareer) {
        this.nameCareer = nameCareer;
    }

}
