package com.example.eriks.singletontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAlbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_album);

        Album currentAlbum = getSelectedAlbum();

        showAlbumTitle(currentAlbum);

        addTracksToList(currentAlbum);

        //Returns you to the main menu
        Button backBtn = findViewById(R.id.button_show_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //returns the selected album from the main-menu
    public Album getSelectedAlbum(){
        int albumIndex = getIntent().getIntExtra("position", -1 );
        DataManager data = DataManager.getInstance();
        Album currentAlbum = data.mAlbums.get(albumIndex);
        return currentAlbum;
    }

    //Sets the text at the top of the screen to show the albums artist and title
    public void showAlbumTitle(Album currentAlbum){
        TextView albumText = findViewById(R.id.textView_show_album_title);
        albumText.setText(currentAlbum.toString());
    }

    //Takes the tracks added to the album and shows them on screen
    public void addTracksToList(Album currentAlbum){
        ArrayAdapter<Track> albumTrackArrayAdapter = new ArrayAdapter<Track>(
                this, android.R.layout.simple_list_item_1, currentAlbum.getTracks()
        );

        ListView albumTracksList = findViewById(R.id.show_album_tracks);
        albumTracksList.setAdapter(albumTrackArrayAdapter);
    }


}
