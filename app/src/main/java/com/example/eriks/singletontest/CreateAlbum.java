package com.example.eriks.singletontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateAlbum extends AppCompatActivity {
    public static final int NO_ALBUM_FOUND = -1;
    private Album currentAlbum;
    private ListView addedTracksList;
    private ArrayAdapter<Track> trackArrayAdapter;
    private int trackNumber = 1;
    private DataManager data = DataManager.getInstance();
    private TextView albumTitle;
    private TextView artistName;
    private int mAlbumIndex;
    private ArrayList<Track> mOriginalTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);

        addedTracksList = findViewById(R.id.create_added_tracks);
        albumTitle = findViewById(R.id.editText_create_album_title);
        artistName = findViewById(R.id.editText_create_artist_name);

        final TextView reporter = findViewById(R.id.textView_create_album_reporter);

        getSelectedAlbum();

        //restores values if there is an earlier state saved
        //this exist due to the list-items dissapearing when the screen rotates
        if (savedInstanceState != null){

            restoreValues(savedInstanceState);
        }

        initializeAdapter();

        //Creates the written down track, adding it to the album
        //and displays it on the Listview
        //Throws NullPointerException if empty and shows error msg
        Button addTrack = findViewById(R.id.button_create_add_track);
        addTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Track track = new Track();

                    setTrackValues(track);

                    saveTrack(track);

                    addedTracksList.setAdapter(trackArrayAdapter);
                } catch (NullPointerException e) {

                    e.printStackTrace();

                    reporter.setText("Track-name cannot be empty");
                }
            }
        });

        //Adds the Album to the DataManager
        //and brigs you back to the mainmenu
        //Throws NullPointerException if empty and shows error msg
        Button createAlbum = findViewById(R.id.button_create_album);

        createAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (containsAlbumtitle(currentAlbum)) data.mAlbums.remove(mAlbumIndex);
                try {
                    currentAlbum.setTitle(albumTitle.getText().toString());
                    currentAlbum.setArtist(artistName.getText().toString());

                    data.mAlbums.add(currentAlbum);

                    finish();
                } catch (NullPointerException e) {

                    e.printStackTrace();

                    reporter.setText("Album name or title cannot be empty");
                }
            }
        });
    }

    //Sets the name and track-number for the track
    //empties the textview for the track-name
    private void setTrackValues(Track track){

        TextView trackName = findViewById(R.id.editText_create_track_name);

        track.setName(trackName.getText().toString());
        track.setTrackNumber(trackNumber);

        trackName.setText(null);
    }

    //Adds the track to the album
    //displays the track to the listview with the track-number before it
    //increments the track-number for the next added track
    private void saveTrack(Track track){

        currentAlbum.addTrack(track);

        trackNumber++;
    }

    //Saves the Album whenever the app pauses
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        data.originalAlbum = currentAlbum;
    }

    //Restores the values the normaly would dissapear on pause
    //as long as there is no album fetched from intent-extras
    private void restoreValues(Bundle savedInstanceState) {

        if (getIntent().getIntExtra("position", -1 ) == -1) {
            currentAlbum = data.originalAlbum;
            data.originalAlbum = null;

            trackArrayAdapter = new ArrayAdapter<Track>(
                    this,android.R.layout.simple_list_item_1, currentAlbum.getTracks());
            trackArrayAdapter.addAll(currentAlbum.getTracks());
            addedTracksList.setAdapter(trackArrayAdapter);
        }
    }

    //builds the List and Adapter for populating the listView
    //with the name of the tracks added
    private void initializeAdapter() {

        trackArrayAdapter = new ArrayAdapter<Track>(
                this,android.R.layout.simple_list_item_1, currentAlbum.getTracks());
        addedTracksList.setAdapter(trackArrayAdapter);
    }

    //returns the selected album from the main-menu
    private Album getSelectedAlbum(){
        mAlbumIndex = getIntent().getIntExtra("position", NO_ALBUM_FOUND);

        if (mAlbumIndex != NO_ALBUM_FOUND) {
            DataManager data = DataManager.getInstance();

            currentAlbum = data.mAlbums.get(mAlbumIndex);
            fillAlbumInfo(currentAlbum);

            return currentAlbum;

        } else return currentAlbum = new Album();
    }

    //Fills out the from with info from the selected Album
    private void fillAlbumInfo(Album currentAlbum){
        albumTitle.setText(currentAlbum.getTitle());
        artistName.setText(currentAlbum.getArtist());

        trackNumber = currentAlbum.getTracks().size() + 1;

        trackArrayAdapter = new ArrayAdapter<Track>(
                this, android.R.layout.simple_list_item_1, currentAlbum.getTracks());
    }

    //Checks for duplicate album-names
    //returns true is duplicate found
    private boolean containsAlbumtitle(Album album){
        for (int i = 0; i < data.mAlbums.size(); i++){
            Album storedAlbumName = data.mAlbums.get(i);

            if (album.getTitle() == storedAlbumName.getTitle()){
                return true;
            }
        }
        return false;
    }
}
