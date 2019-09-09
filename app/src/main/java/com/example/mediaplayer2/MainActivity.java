package com.example.mediaplayer2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Song> mSongs = new ArrayList<>();


    private SectonsStatePagerAdapter mSectionStatePagerAdapter;
    private RecyclerView recyclerView;



    public String name;

    AudioPlayer audioPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        ListSongsFragment fragment = new ListSongsFragment();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();




        recyclerView = findViewById(R.id.recyclerv_view);

        audioPlayer = new AudioPlayer(name, getApplicationContext());


        /*Button button = (Button) findViewById(R.id.btn_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.playAudio();

            }
        });*/

        Button button1 = (Button) findViewById(R.id.btn_stop);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.stop();

            }
        });

        initRecyclerView();
        try {
            initSongList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSongList() throws IOException {

        AssetManager assetManager = getAssets();
        String[] files = assetManager.list("");



        List<String> it = new LinkedList<String>(Arrays.asList(files));
        for (int i = 0; i <it.size(); i++) {
            if (it.get(i).endsWith(".mp3")){
                mNames.add(it.get(i));
            }

        }

        Log.d("list", it.toString());
        Log.d("list", mNames.toString());


        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();


        for (int i = 0; i < mNames.size(); i++) {
            AssetFileDescriptor fileDescriptor = getAssets().openFd(mNames.get(i));
            mediaMetadataRetriever.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());

            String title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            String fileName = mNames.get(i);
            byte cover [] = mediaMetadataRetriever.getEmbeddedPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
            if (cover != null) {

                bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
            }
                Song song = new Song(artist, duration, title, fileName, bitmap);
            mSongs.add(song);

        }
        Log.d("artist123", mSongs.get(0).getArtist());
        Log.d("duration", mSongs.get(0).getDuration());

        initRecyclerView();
    }


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mSongs, audioPlayer);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
