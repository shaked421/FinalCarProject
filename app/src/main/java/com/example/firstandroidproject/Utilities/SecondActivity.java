package com.example.firstandroidproject.Utilities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstandroidproject.R;
import com.example.firstandroidproject.fragments.ListFragment;
import com.example.firstandroidproject.fragments.MapFragment;
import com.example.firstandroidproject.interfaces.Callback_ListItemClicked;

public class SecondActivity extends AppCompatActivity {
    private FrameLayout score_list;
    private FrameLayout map;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViews();
        initViews();
    }

    private void initViews() {
        listFragment = new ListFragment();
        listFragment.setCallbackListItemClicked(new Callback_ListItemClicked() {
            @Override
            public void listItemClicked(double lat, double lon) {
                mapFragment.zoom(lat, lon);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.score_list, listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
    }

    private void findViews() {
        score_list = findViewById(R.id.score_list);
        map = findViewById(R.id.map);
    }
}
