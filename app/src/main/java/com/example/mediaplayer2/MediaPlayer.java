package com.example.mediaplayer2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MediaPlayer extends Fragment {

    AudioPlayer audioPlayer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.listsongsfragment_layout, container, false);

        Button button_start = (Button) view.findViewById(R.id.btn_stop);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.stop();

            }
        });

        Button button_forward = (Button) view.findViewById(R.id.btn_stop);
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //audioPlayer.stop();

            }
        });
        Button button_back = (Button) view.findViewById(R.id.btn_stop);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //audioPlayer.stop();

            }
        });







        return view;
    }
}
