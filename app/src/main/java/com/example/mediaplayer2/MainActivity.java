package com.example.mediaplayer2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Context context;
    AudioPlayer ap ;
    ArrayList<Song> mSongs = new ArrayList<>();
    ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: started.");
        context = getApplicationContext();
        ap = new AudioPlayer("", context);

//      Sending the audioplayer to ListSongsFragment
        Intent intent = new Intent(MainActivity.this , ListSongsFragment.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("audioplayer", ap);
        intent.putExtras(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ListSongsFragment fragment = new ListSongsFragment();

        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
        final Button button_favourite = (Button) findViewById(R.id.btn_favouriteList);

        button_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FavouritesFragment favFrag = new FavouritesFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable("audioplayer", ap);
                mSongs = fragment.initFavList();
                bundle.putParcelableArrayList("list" , (ArrayList<? extends Parcelable>) mSongs);


                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                favFrag.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, favFrag).addToBackStack(null).commit();


            }
        });


    }


}
