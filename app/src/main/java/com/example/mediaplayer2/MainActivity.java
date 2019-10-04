package com.example.mediaplayer2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Context context;
    AudioPlayer audioPlayer;
    ArrayList<Song> mSongs = new ArrayList<>();
    ArrayList<String> mNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        context = getApplicationContext();
        audioPlayer = new AudioPlayer("", context);

//      Packing the audioplayer to bundle for the ListSongsFragment
        Intent intent = new Intent(MainActivity.this , ListSongsFragment.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("audioplayer", audioPlayer);
        intent.putExtras(bundle);

        //Creates and starts ListSongsFragment as start page then sends the bundle
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ListSongsFragment fragment = new ListSongsFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();

        //Button for the favorite fragment
        final Button button_favourite = (Button) findViewById(R.id.btn_favouriteList);
        button_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouritesFragment favFrag = new FavouritesFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("audioplayer", audioPlayer);
                mSongs = fragment.initFavList();
                bundle.putParcelableArrayList("list" , (ArrayList<? extends Parcelable>) mSongs);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                favFrag.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, favFrag).addToBackStack(null).commit();
            }
        });

        //Button for the regular song list fragment
        final Button button_songList = (Button) findViewById(R.id.btn_songList);
        button_songList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("audioplayer", audioPlayer);
                bundle.putParcelableArrayList("list" , (ArrayList<? extends Parcelable>) mSongs);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }
}
