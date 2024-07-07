package com.example.firstandroidproject.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstandroidproject.R;
import com.example.firstandroidproject.Utilities.Score;
import com.example.firstandroidproject.interfaces.Callback_ListItemClicked;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<Score> scoreList;
    private Callback_ListItemClicked callbackListItemClicked;

    public ScoreAdapter(List<Score> scoreList, Callback_ListItemClicked callbackListItemClicked) {
        this.scoreList = scoreList;
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = scoreList.get(position);
        holder.scoreTextView.setText(String.valueOf(score.getScore()));
        holder.locationTextView.setText(score.getLat() + ", " + score.getLon());
        holder.itemView.setOnClickListener(v -> callbackListItemClicked.listItemClicked(score.getLat(), score.getLon()));
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView scoreTextView;
        TextView locationTextView;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            scoreTextView = itemView.findViewById(R.id.score_text_view);
            locationTextView = itemView.findViewById(R.id.location_text_view);
        }
    }
}
