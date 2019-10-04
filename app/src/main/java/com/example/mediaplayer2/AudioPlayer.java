package com.example.mediaplayer2;
import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Parcel;
import android.os.Parcelable;

public class AudioPlayer implements Parcelable {

    private String fileName;
    private Context contex;
    public MediaPlayer mediaPlayer;
    ArrayList<Song> mSongs = new ArrayList<>();

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

    //Checks if media player is null else releases mediaPlayer
    public void checkAudioPlayer(final String songName, final ArrayList<Song> mSongs){

        if (mediaPlayer == null){
            this.mSongs = mSongs;
            playAudio(songName);
        }

        else{
            this.mSongs = mSongs;
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
            playAudio(songName);
        }
    }

    //Plays Audio
    public void playAudio(String songName) {

        mediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor descriptor;
            if (contex == null) {
                return;
            }
            descriptor = contex.getAssets()
                    .openFd(songName);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    try {
                        for (int i = 0; i <mSongs.size() ; i++) {
                            if (mSongs.get(i).getFileName().equals(fileName))
                            {
                                String song = mSongs.get(i+1).getFileName();
                                checkAudioPlayer(song, mSongs);
                            }
                        }
                        mp.setLooping(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Pause
    public void pause(){
        mediaPlayer.pause();
    }
    //Continue playing from pause
    public void continuePlay(){
        mediaPlayer.start();
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
