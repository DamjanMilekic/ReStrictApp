package com.example.laptop.restrict.Model;

/**
 * Created by durma on 14.2.18..
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("logged_at")
    @Expose
    private String loggedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("notification_settings")
    @Expose
    private NotificationSettings notificationSettings;
    @SerializedName("profile")
    @Expose
    private Profile profile;;

    /**
     * No args constructor for use in serialization
     *
     */
    public Data() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param lastName
     * @param title
     * @param notificationSettings
     * @param email
     * @param createdAt
     * @param role
     * @param firstName
     * @param loggedAt
     * @param profile
     */
    public Data(Integer id, String firstName, String lastName, String email, String title, String role, String loggedAt, String createdAt, String updatedAt, NotificationSettings notificationSettings, Profile profile) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.role = role;
        this.loggedAt = loggedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.notificationSettings = notificationSettings;
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(String loggedAt) {
        this.loggedAt = loggedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public NotificationSettings getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(NotificationSettings notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
