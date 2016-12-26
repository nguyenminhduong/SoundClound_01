package com.example.framgia.soundclound_01.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("id")
    private int mId;
    @SerializedName("username")
    private String mUserName;

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }
}
