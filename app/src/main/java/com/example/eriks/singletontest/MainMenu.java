package com.example.eriks.singletontest;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    private ArrayAdapter<Album> createdAlbums;
    private Spinner albumSpinner;
    TextView reporter;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        createdAlbums.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        reporter = findViewById(R.id.textView_mainMenu_reporter);

        final DataManager data = DataManager.getInstance();

        addAlbumstoSpinner(data);

        //Takes the user to the create album form
        final Button createAlbumbtn = (Button) findViewById(R.id.button_mainMenu_create_album);

        createAlbumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, CreateAlbum.class));
            }
        });

        //takes the user to the show album view with the selected album
        final Button showAlbumBtn = findViewById(R.id.button_mainMenu_show_album);

        showAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedAlbum(ShowAlbum.class);
            }
        });

        //Takes the user to the create album view
        //with the selected album already filled out
        Button editAlbumBtn = findViewById(R.id.button_mainMenu_edit_album);

        editAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedAlbum(CreateAlbum.class);
            }
        });

        //Attempts to delete the selected album
        //deletes from bot mAlbums and the spinner
        //Send errormessage if nothing is selected
        Button deleteAlbumBtn = findViewById(R.id.button_mainMenu_delete_album);

        deleteAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = albumSpinner.getSelectedItemPosition();

                if (!createdAlbums.isEmpty()) {
                    data.mAlbums.remove(index);
                    albumSpinner.setAdapter(createdAlbums);
                } else {
                    reporter.setText("No album selected");
                }

            }
        });

    }

    //Creates the adapter for the spinner
    //the adapter adds all created albums to the spinner
    //and shows them by "artist" - "track-name"
    private void addAlbumstoSpinner(DataManager data){
        albumSpinner = findViewById(R.id.spinner_albums);

        createdAlbums = new ArrayAdapter<Album>(
                this, android.R.layout.simple_spinner_item, data.mAlbums);
        createdAlbums.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        albumSpinner.setAdapter(createdAlbums);
    }

    //checks the selected album
    //and shows the album with it's tracks in a new view
    private void showSelectedAlbum(Class target){
        if (!createdAlbums.isEmpty()) {
            Intent showAlbumWithAlbumIndex = new Intent(
                    MainMenu.this, target);
            showAlbumWithAlbumIndex.putExtra(
                    "position", albumSpinner.getSelectedItemPosition());
            startActivity(showAlbumWithAlbumIndex);
        }else{

            reporter.setText("No album selected");
        }
    }
}
