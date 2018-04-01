
package com.example.ahmed.jobtask.variants;

import com.example.ahmed.jobtask.allvariants.Translations;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Variants {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("optionValues")
    @Expose
    private List<Object> optionValues = null;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("translations")
    @Expose
    private List<Translations> translations = null;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("onHold")
    @Expose
    private Integer onHold;
    @SerializedName("onHand")
    @Expose
    private Integer onHand;
    @SerializedName("tracked")
    @Expose
    private Boolean tracked;
    @SerializedName("channelPricings")
    @Expose
    private List<Object> channelPricings = null;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Object> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<Object> optionValues) {
        this.optionValues = optionValues;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<Translations> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translations> translations) {
        this.translations = translations;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getOnHold() {
        return onHold;
    }

    public void setOnHold(Integer onHold) {
        this.onHold = onHold;
    }

    public Integer getOnHand() {
        return onHand;
    }

    public void setOnHand(Integer onHand) {
        this.onHand = onHand;
    }

    public Boolean getTracked() {
        return tracked;
    }

    public void setTracked(Boolean tracked) {
        this.tracked = tracked;
    }

    public List<Object> getChannelPricings() {
        return channelPricings;
    }

    public void setChannelPricings(List<Object> channelPricings) {
        this.channelPricings = channelPricings;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
