
package com.example.ahmed.jobtask.credentials;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Account {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("usernameCanonical")
    @Expose
    private String usernameCanonical;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("emailCanonical")
    @Expose
    private String emailCanonical;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;

    /**
     * No args constructor for use in serialization
     *
     */
    public Account() {
    }

    /**
     *
     * @param id
     * @param enabled
     * @param username
     * @param email
     * @param roles
     * @param emailCanonical
     * @param usernameCanonical
     */
    public Account(Integer id, String username, String usernameCanonical, List<String> roles, String email, String emailCanonical, Boolean enabled) {
        super();
        this.id = id;
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.roles = roles;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameCanonical() {
        return usernameCanonical;
    }

    public void setUsernameCanonical(String usernameCanonical) {
        this.usernameCanonical = usernameCanonical;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCanonical() {
        return emailCanonical;
    }

    public void setEmailCanonical(String emailCanonical) {
        this.emailCanonical = emailCanonical;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}