package com.example.firstandroidproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstandroidproject.R;
import com.example.firstandroidproject.Utilities.Score;
import com.example.firstandroidproject.fragments.ScoreAdapter;
import com.example.firstandroidproject.interfaces.Callback_ListItemClicked;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScoreAdapter scoreAdapter;
    private List<Score> scoreList;
    private Callback_ListItemClicked callbackListItemClicked;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setCallbackListItemClicked(Callback_ListItemClicked callbackListItemClicked) {
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initRecyclerView();
        loadSampleData();
        return v;
    }

    private void findViews(View v) {
        recyclerView = v.findViewById(R.id.score_list);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scoreList = new ArrayList<>();
        scoreAdapter = new ScoreAdapter(scoreList, callbackListItemClicked);
        recyclerView.setAdapter(scoreAdapter);
    }

    private void loadSampleData() {
        // Add sample scores
        scoreList.add(new Score(32.1129923, 34.8172147, 230));
        scoreList.add(new Score(32.1129923, 34.8182147, 210));
        scoreList.add(new Score(32.1129923, 34.8182147, 190));
        scoreList.add(new Score(32.1129923, 34.8182147, 170));
        scoreList.add(new Score(32.1129923, 34.8182147, 170));
        scoreList.add(new Score(32.1129923, 34.8182147, 150));
        scoreList.add(new Score(32.1129923, 34.8182147, 130));
        scoreList.add(new Score(32.1129923, 34.8182147, 120));
        scoreList.add(new Score(32.1129923, 34.8182147, 120));
        scoreList.add(new Score(32.1129923, 34.8182147, 120));

        scoreAdapter.notifyDataSetChanged();
    }
}
