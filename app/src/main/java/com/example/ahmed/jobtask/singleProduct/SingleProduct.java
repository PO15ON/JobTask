
package com.example.ahmed.jobtask.singleProduct;

import java.util.List;

import com.example.ahmed.jobtask.data.allProducts.Option;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleProduct {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("attributes")
    @Expose
    private List<Object> attributes = null;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("associations")
    @Expose
    private List<Object> associations = null;
    @SerializedName("translations")
    @Expose
    private Translations translations;
    @SerializedName("productTaxons")
    @Expose
    private List<Object> productTaxons = null;
    @SerializedName("channels")
    @Expose
    private List<Object> channels = null;
    @SerializedName("reviews")
    @Expose
    private List<Object> reviews = null;
    @SerializedName("averageRating")
    @Expose
    private Integer averageRating;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;
    @SerializedName("_links")
    @Expose
    private Links links;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Object> getAssociations() {
        return associations;
    }

    public void setAssociations(List<Object> associations) {
        this.associations = associations;
    }

    public Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

    public List<Object> getProductTaxons() {
        return productTaxons;
    }

    public void setProductTaxons(List<Object> productTaxons) {
        this.productTaxons = productTaxons;
    }

    public List<Object> getChannels() {
        return channels;
    }

    public void setChannels(List<Object> channels) {
        this.channels = channels;
    }

    public List<Object> getReviews() {
        return reviews;
    }

    public void setReviews(List<Object> reviews) {
        this.reviews = reviews;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
