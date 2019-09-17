package com.example.mediaplayer2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.Objects;

public class MediaPlayerFragment extends Fragment {

    AudioPlayer audioPlayer;
    int playOrStop = 0;

    SeekBar positionBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    int totalTime;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }



    public String createTimeLabel(int time){

        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            positionBar.setProgress(currentPosition);
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remaingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText(remaingTime);
        }
    };


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.mediaplayer_layout, container, false);

        positionBar = (SeekBar) view.findViewById(R.id.seekbar);

        remainingTimeLabel = (TextView) view.findViewById(R.id.timeleft);

        elapsedTimeLabel = (TextView) view.findViewById(R.id.elapsedtime);



        Bundle bundle = this.getArguments();
        if (bundle != null){
            audioPlayer = bundle.getParcelable("audioplayer" );
        }

        audioPlayer.mp.seekTo(0);

        totalTime = audioPlayer.mp.getDuration();

        positionBar.setMax(totalTime);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (audioPlayer.mp != null) {
                        try {
                            Message msg = new Message();
                            msg.what = audioPlayer.mp.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

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
