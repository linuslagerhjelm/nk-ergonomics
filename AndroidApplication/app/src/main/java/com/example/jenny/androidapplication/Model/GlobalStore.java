package com.example.jenny.androidapplication.Model;

/**
 * Created by Jenny on 2017-05-11.
 */

public class GlobalStore {
    private static final GlobalStore ourInstance = new GlobalStore();
    private User user;

    public static GlobalStore getInstance() {
        return ourInstance;
    }

    private GlobalStore() {
    }

    public User getUser() {
        return (user != null) ? user : new User(0, "","", Office.SKELLEFTEÅ);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
