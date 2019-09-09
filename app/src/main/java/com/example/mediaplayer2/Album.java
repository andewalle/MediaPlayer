package com.example.mediaplayer2;

import java.util.ArrayList;

public class Album {

    String AlbumName;
    ArrayList<Song> albumName = new ArrayList<>();

    public ArrayList<Song> createAlbum(Song song){

        albumName.add(song);
        return albumName;

    }


}
