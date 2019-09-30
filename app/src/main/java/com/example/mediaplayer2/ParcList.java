package com.example.mediaplayer2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ParcList implements Parcelable {

    ArrayList<Song> mSongs = new ArrayList<>();

    protected ParcList(Parcel in) {
    }

    public static final Creator<ParcList> CREATOR = new Creator<ParcList>() {
        @Override
        public ParcList createFromParcel(Parcel in) {
            return new ParcList(in);
        }

        @Override
        public ParcList[] newArray(int size) {
            return new ParcList[size];
        }
    };

    public ParcList() {

    }

    public void setmSongs (ArrayList<Song> mSongs){
        this.mSongs = mSongs;

    }

    public Parcelable getmSongs(){
        return (Parcelable) mSongs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
