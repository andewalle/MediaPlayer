package com.example.mediaplayer2;

import android.graphics.Bitmap;

public class Song{


    private String artist;
    private String duration;
    private String title;
    private String fileName;
    private String album;
    private Bitmap cover;
    private int favorite;

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    private Boolean fav;



    public Song(String artist, String duration, String title, String fileName, String album, Bitmap cover, Boolean fav) {
        this.artist = artist;
        this.duration = duration;
        this.title = title;
        this.fileName = fileName;
        this.cover = cover;
        this.fav = fav;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }


    public int getFavorite() {
        if (fav){
            return R.drawable.ic_favfull;
        }
        else return R.drawable.ic_favourite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
