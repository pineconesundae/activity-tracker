package com.ruslander.leo.leoactivitytracker.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String email;
    private String firstName;
    private String lastName;

    public User() { }

    public User(int id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.email = object.getString("email");
            this.firstName = object.getString("firstName");
            this.lastName = object.getString("lastName");
        } catch (JSONException ex) {
            // Do nothing
        }
    }

    public String getDisplayName() {
        String lastInitial = this.lastName.substring(0, 1);
        return this.firstName + " " + lastInitial + ".";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
