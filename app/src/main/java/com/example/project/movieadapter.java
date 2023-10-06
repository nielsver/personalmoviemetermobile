package com.example.project;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class movieadapter extends RecyclerView.Adapter<movieadapter.MovieViewHolder> {

    Cursor mMoviedata;

    private final ListItemClickListener mItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int position, RecyclerView.ViewHolder itemView);
    }

    public movieadapter(Cursor data, ListItemClickListener listener) {
        mMoviedata = data;
        mItemClickListener = listener;
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.gridlayout, parent, false);

        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    // Involves populating data into the item through holder²
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        mMoviedata.moveToPosition(position);
        holder.nameTextView.setTag(mMoviedata.getColumnIndex(database.Feedentry._ID));
        holder.nameTextView.setText(mMoviedata.getString(mMoviedata.getColumnIndexOrThrow(database.Feedentry.COLUMN_NAME_TITLE)));
        holder.messageRating.setText(String.valueOf(mMoviedata.getInt(mMoviedata.getColumnIndexOrThrow(database.Feedentry.COLUMN_NAME_RATING))) + " stars");


    }



    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if(mMoviedata != null) {
            return mMoviedata.getCount();
        } else {
            return 0;
        }
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_name)
        TextView nameTextView;

        @BindView(R.id.movie_Rating)
        TextView messageRating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        public void onClick(View view) {
            Log.d("clicked", "clicked");
            mItemClickListener.onListItemClick(getAdapterPosition(), this);
        }
        public void MovieDataChanged(Cursor newData) {
            if(mMoviedata != null) {
                mMoviedata.close();
                mMoviedata = newData;
            }
            notifyDataSetChanged();
        }
    }

}
