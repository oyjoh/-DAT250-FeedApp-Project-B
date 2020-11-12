package com.dat250.FeedApp.dweeting;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class DweetLog {

    private Set<Dweet> dweetLogSet;
    private Set<Dweet> newEntries;

    public DweetLog() {
        dweetLogSet = new LinkedHashSet<>();
        newEntries = new LinkedHashSet<>();
    }

    public void addToLog(Dweet dweet) {
        dweetLogSet.add(dweet);
        newEntries.clear();
        newEntries.add(dweet);


    }

    public void addToLog(ArrayList<Dweet> newList) {
        dweetLogSet.addAll(newList);
        newEntries.clear();
        newEntries.addAll(newList);
    }

    public boolean isInLog(Dweet dweet) {
        return dweetLogSet.contains(dweet);
    }

    public boolean isInLog(ArrayList<Dweet> newList) {
        for (Dweet dweet : newList) {
            if (dweetLogSet.contains(dweet)) {
                return true;
            }
        }
        return false;
    }

    public void resetLog() {
        dweetLogSet = new LinkedHashSet<>();
    }

    public ArrayList<Dweet> getNewEntries() {
        return new ArrayList<>(newEntries);
    }
}
