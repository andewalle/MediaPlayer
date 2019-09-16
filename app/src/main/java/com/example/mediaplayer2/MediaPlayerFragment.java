package com.example.mediaplayer2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class MediaPlayerFragment extends Fragment {

    AudioPlayer audioPlayer;
    int playOrStop = 0;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.mediaplayer_layout, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            audioPlayer = bundle.getParcelable("audioplayer" );
        }

//        Glide.with(getContext())
//                .asBitmap()
//                .load(mSongs.get(position).getCover())
//                .into(holder.image);



        Button button_start = (Button) view.findViewById(R.id.btn_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playOrStop == 0) {
                    audioPlayer.pause();
                    playOrStop = 1;
                }
                else{
                    audioPlayer.continuePlay();
                    playOrStop = 0;

                }

            }
        });



        Button button_exit = (Button) view.findViewById(R.id.btn_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("audioplayer", audioPlayer);


                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ListSongsFragment myFragment = new ListSongsFragment();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();

            }
        });


//        Button button_forward = (Button) view.findViewById(R.id.btn_stop);
//        button_forward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //audioPlayer.stop();
//
//            }
//        });
//        Button button_back = (Button) view.findViewById(R.id.btn_stop);
//        button_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //audioPlayer.stop();
//
//            }
//        });







        return view;
    }
}
