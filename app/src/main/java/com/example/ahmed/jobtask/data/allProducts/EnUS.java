package com.example.ahmed.jobtask.data.allProducts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnUS {

    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("value")
    @Expose
    private String value;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}