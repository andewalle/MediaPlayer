package com.example.mediaplayer2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FavouritesFragment extends Fragment {


    private static final String TAG = "FavouritesFragment";
    private ArrayList<String> mNames = new ArrayList<>();

    public ArrayList<String> getmNames() {
        return mNames;
    }

    public void setmNames(ArrayList<String> mNames) {
        this.mNames = mNames;
    }

    public ArrayList<Song> getmSongs() {
        return mSongs;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public void setAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    private ArrayList<Song> mSongs;
    private ArrayList<Song> mFavList = new ArrayList<>();
    private RecyclerView recyclerView;


    AudioPlayer audioPlayer;

    public void getList(ArrayList<Song> mSongs){

        this.mSongs = mSongs;

    }

//    public void initFavList(){
//
//        for (int i = 0; i < mSongs.size() ; i++) {
//            if (mSongs.get(i).getFav()){
//                mFavList.add(mSongs.get(i));
//            }
//
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listsongsfragment_layout, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            audioPlayer = bundle.getParcelable("audioplayer" );
            mSongs = bundle.getParcelableArrayList("list");
        }

        Log.d(TAG, "initRecyclerView: init recyclerview.");
        recyclerView =  view.findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mSongs, audioPlayer);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

}



