package com.example.mediaplayer2;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {


    private String artist;
    private String duration;
    private String title;
    private String fileName;
    private String album;
    private Bitmap cover;
    private int favorite;

    protected Song(Parcel in) {
        artist = in.readString();
        duration = in.readString();
        title = in.readString();
        fileName = in.readString();
        album = in.readString();
        cover = in.readParcelable(Bitmap.class.getClassLoader());
        favorite = in.readInt();
        byte tmpFav = in.readByte();
        fav = tmpFav == 0 ? null : tmpFav == 1;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

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

    public String getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public Bitmap getCover() {
        return cover;
    }


    public int getFavorite() {
        if (fav){
            return R.drawable.ic_favfull;
        }
        else return R.drawable.ic_favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artist);
        parcel.writeString(duration);
        parcel.writeString(title);
        parcel.writeString(fileName);
        parcel.writeString(album);
        parcel.writeParcelable(cover, i);
        parcel.writeInt(favorite);
        parcel.writeByte((byte) (fav == null ? 0 : fav ? 1 : 2));
    }
}
