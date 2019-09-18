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


    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Song> mSongs = new ArrayList<>();
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

//        View fragmentView = inflater.inflate(R.layout.activity_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerv_view);

        //Hämta audioplayer från bundle. inte skapa ny

        Bundle bundle = this.getArguments();
        if (bundle != null){
            audioPlayer = bundle.getParcelable("audioplayer" );
        }

        Log.d(TAG, "initRecyclerView: init recyclerview.");
        recyclerView =  view.findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mSongs, audioPlayer);
        recyclerView.setAdapter(adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            initSongList();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }
//  Initiating the song list and song filename list
    private void initSongList() throws IOException {

        AssetManager assetManager = getContext().getAssets();
        String[] files = assetManager.list("");

        if (files == null){
            return;
        }

        List<String> it = new LinkedList<String>(Arrays.asList(files));
        for (int i = 0; i <it.size(); i++) {
            if (it.get(i).endsWith(".mp3")){
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
            byte cover [] = mediaMetadataRetriever.getEmbeddedPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
            if (cover != null) {

                bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
            }
            Song song = new Song(artist, duration, title, fileName, album, bitmap);
            mSongs.add(song);

        }
        Log.d("artist123", mSongs.get(0).getArtist());
        Log.d("duration", mSongs.get(0).getDuration());
    }
}
