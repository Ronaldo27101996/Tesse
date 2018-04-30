package com.example.cr7.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skill {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("career_id")
    @Expose
    private int careerId;

    public Skill() {
    }

    public Skill(Integer id, String name, Integer careerId) {
        super();
        this.id = id;
        this.name = name;
        this.careerId = careerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCareerId() {
        return careerId;
    }

    public void setCareerId(Integer careerId) {
        this.careerId = careerId;
    }

}
