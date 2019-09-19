package com.example.mediaplayer2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Context context;
    AudioPlayer ap ;

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
        ListSongsFragment fragment = new ListSongsFragment();

        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
        final Button button_favourite = (Button) findViewById(R.id.btn_favouriteList);
        button_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("audioplayer", ap);


                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FavouritesFragment myFragment = new FavouritesFragment();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();


            }
        });


    }


}
