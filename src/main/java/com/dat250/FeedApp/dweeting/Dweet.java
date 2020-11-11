package com.dat250.FeedApp.dweeting;

import com.google.gson.JsonObject;

public class Dweet {


    public String thing;
    public String created;
    public JsonObject content;


    @Override
    public String toString() {
        return "Dweet:\n" +
                "\tthing = '" + thing + ", \n" +
                "\tcreated = '" + created + ",\n" +
                "\tcontent = " + content;
    }
}
