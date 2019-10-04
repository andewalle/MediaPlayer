package com.example.mediaplayer2;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Song> mSongs;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called.");
        holder.btn_fav.setImageResource(mSongs.get(position).getFavorite());
        holder.btn_fav.setTag(mSongs.get(position));

        ((ViewHolder)holder).btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mSongs.get(position).getFav()){
                mSongs.get(position).setFav(true);
                }
                else {
                    mSongs.get(position).setFav(false);
                }
                holder.btn_fav.setImageResource(mSongs.get(position).getFavorite());
            }
        });

        //Load pictures using glide
        Glide.with(mContext)
                .asBitmap()
                .load(mSongs.get(position).getCover())
                .into(holder.image);

        //Song names
        holder.songName.setText(mSongs.get(position).getTitle());
        holder.artistName.setText(mSongs.get(position).getArtist());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: "+ mSongs.get(position).getTitle());
                // Song file name

                mAudioPlayer.checkAudioPlayer(mSongs.get(position).getFileName(), mSongs);

                //Creates bundle with audioplayer for MediaPlayerFragment
                Bundle bundle = new Bundle();
                bundle.putParcelable("audioplayer", mAudioPlayer);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MediaPlayerFragment myFragment = new MediaPlayerFragment();
                myFragment.setArguments(bundle);
                myFragment.getList(mSongs, position);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();

                Toast.makeText(mContext, mSongs.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onBindViewHolder: working.");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mSongs == null)
        {return 0;}
        else{
        return mSongs.size();}
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView songName;
        TextView artistName;
        RelativeLayout parentLayout;
        ImageView btn_fav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            songName = itemView.findViewById(R.id.image_name);
            artistName = itemView.findViewById(R.id.image_artist);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            btn_fav = itemView.findViewById(R.id.btn_favourite);

        }
    }
}
