package com.example.firstandroidproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.firstandroidproject.Logic.GameManager;
import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView[] hearts;
    private ImageView[][] asteroids;
    private ImageView[] aliens;
    private MaterialButton GamePlayButton;
    private static final long DELAY = 1500L;
    private MaterialButton left_arrow;
    private MaterialButton right_arrow;
    private GameManager gameManager;
    private int alienLocation = 1;
    private Timer timer;
    private Timer timer2;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(hearts.length,this);
        initViews();
    }

    private void initViews() {
        left_arrow.setOnClickListener(v -> moveLeft());
        right_arrow.setOnClickListener(v -> moveRight());
        GamePlayButton.setOnClickListener(v -> startPlayTimer());
        refreshHeartsUI();
    }

    private void startPlayTimer() {
        GamePlayButton.setVisibility(View.INVISIBLE);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    int col = gameManager.randomAsteroidStartColumn();
                    startAsteroidFall(col);
                });
            }
        }, 0L, DELAY);
    }

    private void startAsteroidFall(int col) {
        for (int i = 0; i < asteroids[col].length; i++) {
            final int index = i;
            handler.postDelayed(() -> {
                if (index > 0) {
                    asteroids[col][index - 1].setVisibility(View.INVISIBLE);
                }
                asteroids[col][index].setVisibility(View.VISIBLE);

                if (index == asteroids[col].length - 1) {
                    gameManager.isCrash(col, alienLocation);
                    astroidInvisable(col,index);
                }
            }, index * DELAY);
        }
    }

    private void astroidInvisable(int col, int row){
        timer2 = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                asteroids[col][row].setVisibility(View.INVISIBLE);
            }
        },500L,DELAY);

    }

    public void tosttt(){

    }

    private void moveLeft() {
        if (alienLocation > 0) {
            aliens[alienLocation].setVisibility(View.INVISIBLE);
            alienLocation--;
            aliens[alienLocation].setVisibility(View.VISIBLE);
        }
        refreshHeartsUI();
    }

    private void moveRight() {
        if (alienLocation < aliens.length - 1) {
            aliens[alienLocation].setVisibility(View.INVISIBLE);
            alienLocation++;
            aliens[alienLocation].setVisibility(View.VISIBLE);
        }
        refreshHeartsUI();
    }

    private void refreshHeartsUI() {
        for (int i = 0; i < hearts.length; i++) {
            hearts[i].setVisibility(i < gameManager.getLife() ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void findViews() {
        hearts = new AppCompatImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        };

        asteroids = new AppCompatImageView[][]{
                {
                        findViewById(R.id.asteroid_00),
                        findViewById(R.id.asteroid_01),
                        findViewById(R.id.asteroid_02),
                        findViewById(R.id.asteroid_03),
                        findViewById(R.id.asteroid_04),
                },
                {
                        findViewById(R.id.asteroid_10),
                        findViewById(R.id.asteroid_11),
                        findViewById(R.id.asteroid_12),
                        findViewById(R.id.asteroid_13),
                        findViewById(R.id.asteroid_14),
                },
                {
                        findViewById(R.id.asteroid_20),
                        findViewById(R.id.asteroid_21),
                        findViewById(R.id.asteroid_22),
                        findViewById(R.id.asteroid_23),
                        findViewById(R.id.asteroid_24),
                }
        };
        aliens = new AppCompatImageView[]{
                findViewById(R.id.alien0),
                findViewById(R.id.alien1),
                findViewById(R.id.alien2)
        };
        GamePlayButton = findViewById(R.id.GamePlayButton);
        left_arrow = findViewById(R.id.left_arrow);
        right_arrow = findViewById(R.id.right_arrow);
    }
}
