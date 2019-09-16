package com.example.mediaplayer2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//
    Context context; //= getApplicationContext();
    AudioPlayer ap ;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        context = getApplicationContext();
        ap = new AudioPlayer("", context);

//
        Intent intent = new Intent(MainActivity.this , ListSongsFragment.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("audioplayer", ap);
        intent.putExtras(bundle);
//
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListSongsFragment fragment = new ListSongsFragment();

        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();

    }

}
