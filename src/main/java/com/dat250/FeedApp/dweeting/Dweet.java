package com.dat250.FeedApp.dweeting;

import com.google.gson.JsonObject;

import java.util.Objects;

public class Dweet {


    public String thing;
    public String created;
    public JsonObject content;

    // TODO check if dweet is at correct format, with correct command and payload
    public String getCommand() {
        return content.get("command").toString();
    }


    @Override
    public String toString() {
        return "Dweet:\n" +
                "\tthing = '" + thing + ", \n" +
                "\tcreated = '" + created + ",\n" +
                "\tcontent = " + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dweet dweet = (Dweet) o;
        return Objects.equals(thing, dweet.thing) &&
                Objects.equals(created, dweet.created) &&
                Objects.equals(content, dweet.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thing, created, content);
    }
}
