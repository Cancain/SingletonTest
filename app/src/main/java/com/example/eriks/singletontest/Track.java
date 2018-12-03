package com.example.eriks.singletontest;

public class Track {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isNullOrEmpty(name)){

            throw new NullPointerException();

        } else {

            this.name = name;

        }
    }

    private int trackNumber;

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String toString(){
        return trackNumber + " - " + name;
    }

    public boolean isNullOrEmpty(String value){
        if (value == null || value.isEmpty()) return true;
        else return false;
    }
}
