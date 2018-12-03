package com.example.eriks.singletontest;

import java.util.ArrayList;

public class Album {

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        if (isNullOrEmpty(artist)){
            throw new NullPointerException();

        } else {
            this.artist = artist;
        }
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (isNullOrEmpty(title)){

            throw new NullPointerException();

        }else {

            this.title = title;

        }
    }

    private ArrayList<Track> tracks = new ArrayList<>();

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public String toString(){
        return artist + " - " + title;
    }

    public boolean isNullOrEmpty(String value){
        if (value == null || value.isEmpty()) return true;
        else return false;
    }
}
