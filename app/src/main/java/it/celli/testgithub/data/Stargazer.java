package it.celli.testgithub.data;


import com.google.gson.annotations.SerializedName;

public class Stargazer {

    @SerializedName("login")
    String username;

    @SerializedName("avatar_url")
    String avatar;

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getAvatar() {

        return avatar;
    }

    public void setAvatar(String avatar) {

        this.avatar = avatar;
    }
}
