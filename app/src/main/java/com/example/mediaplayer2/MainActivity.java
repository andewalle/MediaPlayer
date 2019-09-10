package com.example.mediaplayer2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    String name;
    Context context = getApplicationContext();
    AudioPlayer ap;


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
        startAudio(name);
    }

    public void startAudio(String name){
        Intent intent = new Intent(this , ListSongsFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("audioplayer", ap);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}
