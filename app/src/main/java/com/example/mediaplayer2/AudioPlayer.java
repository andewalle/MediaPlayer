package com.example.mediaplayer2;
import java.io.IOException;
import java.io.Serializable;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Parcel;
import android.os.Parcelable;

public class AudioPlayer implements Parcelable {

    private String fileName;
    private Context contex;
    private MediaPlayer mp;

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


            try {
                AssetFileDescriptor descriptor;
                if (contex == null){
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
