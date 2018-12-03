package com.example.eriks.singletontest;

import java.util.ArrayList;

class DataManager {
    private static final DataManager ourInstance = new DataManager();

    public ArrayList<Album> mAlbums = new ArrayList<>();

    public Album originalAlbum;

    static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }
}
