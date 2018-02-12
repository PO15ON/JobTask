package com.example.ahmed.jobtask;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmed on 2/12/2018.
 */

public class Account {
    @SerializedName("id")
    private int id;

    @SerializedName("usernameCanonical")
    private String username;

    @SerializedName("roles")
    private List<String> roles;

    @SerializedName("emailCanonical")
    private String email;

    @SerializedName("enabled")
    private boolean enabled;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    public Account(int id, String username, List<String> roles, String email, boolean enabled, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
