package com.example.mediaplayer2;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Song> mSongs = new ArrayList<>();
    private Context mContext;
    private AudioPlayer mAudioPlayer;


    public RecyclerViewAdapter(Context context, ArrayList<Song> songs, final AudioPlayer audioPlayer){
        mContext = context;
        mSongs = songs;
        mAudioPlayer = audioPlayer;

    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mSongs.get(position).getCover())
                .into(holder.image);

        //Song names
        holder.songName.setText(mSongs.get(position).getTitle());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: "+ mSongs.get(position).getTitle());
                // Song file name
                mAudioPlayer.checkMediaPlayer(mSongs.get(position).getFileName());

                Toast.makeText(mContext, mSongs.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        AppCompatActivity activity = (AppCompatActivity) mContext;
        Fragment newFragment = new MediaPlayer();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.recyclerv_view, newFragment)
                .addToBackStack(null).commit();
        Log.d(TAG, "onBindViewHolder: working.");


    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView image;
        TextView songName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            songName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
