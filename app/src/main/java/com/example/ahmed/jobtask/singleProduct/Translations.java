
package com.example.ahmed.jobtask.singleProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translations {

    @SerializedName("en_US")
    @Expose
    private EnUS enUS;

    public EnUS getEnUS() {
        return enUS;
    }

    public void setEnUS(EnUS enUS) {
        this.enUS = enUS;
    }

}
