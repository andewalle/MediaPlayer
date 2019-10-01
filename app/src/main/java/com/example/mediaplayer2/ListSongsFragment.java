package com.example.mediaplayer2;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListSongsFragment extends Fragment {

    private static final String TAG = "ListSongsFragment";

    private ArrayList<Song> mSongs = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Song> mFavList = new ArrayList<>();

    public void setmSongs(ArrayList<Song> mSongs) {
        this.mSongs = mSongs;
    }

    private RecyclerView recyclerView;

    AudioPlayer audioPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listsongsfragment_layout, container, false);

        recyclerView = view.findViewById(R.id.recyclerv_view);

        //Hämta audioplayer från bundle. inte skapa ny

        Bundle bundle = this.getArguments();
        if (bundle != null){
            audioPlayer = bundle.getParcelable("audioplayer" );
        }

        try {
            initSongList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        recyclerView =  view.findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mSongs, audioPlayer);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }
    public ArrayList<Song> initFavList(){

        int size = mFavList.size();

        for (int j = size -1; j >= 0 ; j--) {
            if (!mFavList.get(j).getFav()) {
                mFavList.remove(j);
            }
        }

        for (int i = 0; i < mSongs.size(); i++) {
            if (mSongs.get(i).getFav()){
                if (mFavList.size() == 0){
                    mFavList.add(mSongs.get(i));
                }
                else{
                    boolean found = false;
                    for (int j = 0; j < mFavList.size() ; j++) {
                        if (mSongs.get(i).getFileName().equals(mFavList.get(j).getFileName())){
                            found = true;
                            break;
                        }
                    }
                    if (found){
                        found = false;
                    }
                    else{ mFavList.add(mSongs.get(i));}
                }
            }
        }

        return mFavList;
    }
//  Initiating the song list and song filename list
    public void initSongList() throws IOException {


        if (mSongs.size() < 1) {
            AssetManager assetManager = getContext().getAssets();
            String[] files = assetManager.list("");

            if (files == null) {
                return;
            }

            List<String> it = new LinkedList<String>(Arrays.asList(files));
            for (int i = 0; i < it.size(); i++) {
                if (it.get(i).endsWith(".mp3")) {
                    mNames.add(it.get(i));
                }
            }

            Log.d("list", it.toString());
            Log.d("list", mNames.toString());
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();


            for (int i = 0; i < mNames.size(); i++) {

                AssetFileDescriptor fileDescriptor = getContext().getAssets().openFd(mNames.get(i));
                mediaMetadataRetriever.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                String title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                String duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                String album = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                String fileName = mNames.get(i);
                byte cover[] = mediaMetadataRetriever.getEmbeddedPicture();
                Boolean fav = false;
                Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
                if (cover != null) {
                    bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
                }
                Song song = new Song(artist, duration, title, fileName, album, bitmap, fav);
                mSongs.add(song);

            }
            Log.d("artist123", mSongs.get(0).getArtist());
            Log.d("duration", mSongs.get(0).getDuration());
        }
        else return;
    }
}
