package com.example.mediaplayer2;
import java.io.IOException;
import java.io.Serializable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.SeekBar;
import android.widget.TextView;

public class AudioPlayer implements Parcelable {

    private String fileName;
    private Context contex;
    private MediaPlayer mp;
    SeekBar positionBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    int totalTime;

    
    //Constructor
    public AudioPlayer(String name, Context context) {
        fileName = name;
        contex = context;

    }

    protected AudioPlayer(Parcel in) {
        fileName = in.readString();
    }

    public static final Creator<AudioPlayer> CREATOR = new Creator<AudioPlayer>() {
        @Override
        public AudioPlayer createFromParcel(Parcel in) {
            return new AudioPlayer(in);
        }

        @Override
        public AudioPlayer[] newArray(int size) {
            return new AudioPlayer[size];
        }
    };

    public void checkAudioPlayer(final String songName){

        if (mp == null){
            playAudio(songName);
        }

        else{
            mp.release();
            mp = null;
            playAudio(songName);
        }

    }

    public void checkAudioPlayerStop(){
        if (mp != null){
            mp.stop();
        }
        else{}

    }


    //Play Audio
    public void playAudio(final String songName) {

        mp = new MediaPlayer();
        mp.seekTo(0);


        try {
            AssetFileDescriptor descriptor;
            if (contex == null) {
                return;


            }
            descriptor = contex.getAssets()
                    .openFd(songName);
            mp.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mp.prepare();
            mp.setLooping(true);
            mp.start();
            mp.setVolume(7, 7);

        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        totalTime = mp.getDuration();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {

                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                int currentPosition = msg.what;
                positionBar.setProgress(currentPosition);
                String elapsedTime = createTimeLabel(currentPosition);
                elapsedTimeLabel.setText(elapsedTime);

                String remaingTime = createTimeLabel(totalTime - currentPosition);
                remainingTimeLabel.setText(remaingTime);
            }

    };

    public String createTimeLabel(int time){

        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;


    }

    //Stop Audio
    public void stop() {
        mp.stop();
        mp.release();
        mp = null;

    }

    public void pause(){
        mp.pause();
    }

    public void continuePlay(){
        mp.start();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fileName);
    }
}
