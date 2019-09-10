package com.example.mediaplayer2;
import java.io.IOException;
import java.io.Serializable;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioPlayer implements Serializable {

    private String fileName;
    private Context contex;
    private MediaPlayer mp;

    //Constructor
    public AudioPlayer(String name, Context context) {
        fileName = name;
        contex = context;

    }

    public void checkMediaPlayer(final String songName){

        if (mp == null){
            playAudio(songName);
        }

        else{
            mp.release();
            mp = null;
            playAudio(songName);
        }

    }

    public void checkMediaPlayerStop(){
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


}
