package com.example.firstandroidproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.firstandroidproject.Logic.GameManager;
import com.example.firstandroidproject.Utilities.SecondActivity;
import com.example.firstandroidproject.interfaces.MoveCallBack;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MoveCallBack {
    private boolean isGamePaused = false;
    private long delay;
    private AppCompatImageView[] hearts;
    private MaterialTextView score;
    private ImageView[][] asteroids;
    private ImageView[][] coins;
    private ImageView[] aliens;

    private Switch speed_switch;
    private Switch sensors_switch;
    private MaterialButton GamePlayButton;
    private static final long FAST_DELAY = 500L;
    private static final long SLOW_DELAY = 1500L;
    private MaterialButton left_arrow;
    private MaterialButton right_arrow;
    private GameManager gameManager;
    private int alienLocation = 2;
    private Timer timer;
    private Handler handler = new Handler();

    private MoveDetector moveDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(hearts.length, this);
        initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGamePaused) {
            resumeGame();
        }
    }

    private void pauseGame() {
        isGamePaused = true;
        if (timer != null) {
            timer.cancel();
        }
        if (moveDetector != null) {
            moveDetector.stop();
        }
    }

    private void resumeGame() {
        isGamePaused = false;
        if (sensors_switch.isChecked()) {
            moveDetector.start();
        }
        startPlayTimer(delay);
    }

    private void initMoveDetector() {
        moveDetector = new MoveDetector(this, this);
        right_arrow.setVisibility(View.INVISIBLE);
        left_arrow.setVisibility(View.INVISIBLE);
    }

    private void initViews() {
        score.setText(String.valueOf(gameManager.getScore()));
        left_arrow.setOnClickListener(v -> moveLeft());
        right_arrow.setOnClickListener(v -> moveRight());
        GamePlayButton.setOnClickListener(v -> startTheGame());
        refreshUI();
    }

    private void startTheGame() {
        GamePlayButton.setVisibility(View.INVISIBLE);
        sensors_switch.setVisibility(View.INVISIBLE);
        speed_switch.setVisibility(View.INVISIBLE);

        if (sensors_switch.isChecked()) {
            initMoveDetector();
            moveDetector.start();
        }

        delay = speed_switch.isChecked() ? FAST_DELAY : SLOW_DELAY;
        isGamePaused = false;
        startPlayTimer(delay);
    }

    private void startPlayTimer(long delay) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    int col1 = gameManager.randomAsteroidStartColumn();
                    startAsteroidFall(col1, delay);
                    int col2 = gameManager.randomCoinColumn();
                    startCoinFall(col2, delay);
                });
            }
        }, 0L, delay);
    }

    private void startAsteroidFall(int col, long delay) {
        for (int i = 0; i < asteroids[col].length; i++) {
            final int index = i;
            handler.postDelayed(() -> {
                if (index > 0) {
                    asteroids[col][index - 1].setVisibility(View.INVISIBLE);
                }
                asteroids[col][index].setVisibility(View.VISIBLE);

                if (index == asteroids[col].length - 1) {
                    if (gameManager.isCrash(col, alienLocation)) {
                        gameOver();
                    } else {
                        asteroidInvisible(col, index, delay);
                    }
                }
            }, index * delay);
        }
    }

    private void gameOver() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent the user from going back
    }

    private void startCoinFall(int col, long delay) {
        for (int i = 0; i < coins[col].length; i++) {
            final int index = i;
            handler.postDelayed(() -> {
                if (index > 0) {
                    coins[col][index - 1].setVisibility(View.INVISIBLE);
                }
                coins[col][index].setVisibility(View.VISIBLE);

                if (index == coins[col].length - 1) {
                    gameManager.isCoin(col, alienLocation);
                    coinInvisible(col, index, delay);
                }
            }, index * delay);
        }
    }

    private void asteroidInvisible(int col, int row, long delay) {
        handler.postDelayed(() -> asteroids[col][row].setVisibility(View.INVISIBLE), delay);
    }

    private void coinInvisible(int col, int row, long delay) {
        handler.postDelayed(() -> coins[col][row].setVisibility(View.INVISIBLE), delay);
    }

    @Override
    public void moveXLeft() {
        moveLeft();
    }

    @Override
    public void moveXRight() {
        moveRight();
    }

    private void moveLeft() {
        if (alienLocation > 0) {
            aliens[alienLocation].setVisibility(View.INVISIBLE);
            alienLocation--;
            aliens[alienLocation].setVisibility(View.VISIBLE);
        }
        refreshUI();
    }

    private void moveRight() {
        if (alienLocation < aliens.length - 1) {
            aliens[alienLocation].setVisibility(View.INVISIBLE);
            alienLocation++;
            aliens[alienLocation].setVisibility(View.VISIBLE);
        }
        refreshUI();
    }

    private void refreshUI() {
        for (int i = 0; i < hearts.length; i++) {
            hearts[i].setVisibility(i < gameManager.getLife() ? View.VISIBLE : View.INVISIBLE);
        }
        score.setText(String.valueOf(gameManager.getScore()));
    }

    private void findViews() {
        hearts = new AppCompatImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        };

        score = findViewById(R.id.score);

        sensors_switch = findViewById(R.id.sensors_switch);

        speed_switch = findViewById(R.id.speed_switch);

        GamePlayButton = findViewById(R.id.GamePlayButton);

        asteroids = new AppCompatImageView[][]{
                {
                        findViewById(R.id.asteroid_00),
                        findViewById(R.id.asteroid_01),
                        findViewById(R.id.asteroid_02),
                        findViewById(R.id.asteroid_03),
                        findViewById(R.id.asteroid_04),
                        findViewById(R.id.asteroid_05),
                        findViewById(R.id.asteroid_06),
                },
                {
                        findViewById(R.id.asteroid_10),
                        findViewById(R.id.asteroid_11),
                        findViewById(R.id.asteroid_12),
                        findViewById(R.id.asteroid_13),
                        findViewById(R.id.asteroid_14),
                        findViewById(R.id.asteroid_15),
                        findViewById(R.id.asteroid_16),
                },
                {
                        findViewById(R.id.asteroid_20),
                        findViewById(R.id.asteroid_21),
                        findViewById(R.id.asteroid_22),
                        findViewById(R.id.asteroid_23),
                        findViewById(R.id.asteroid_24),
                        findViewById(R.id.asteroid_25),
                        findViewById(R.id.asteroid_26),
                },
                {
                        findViewById(R.id.asteroid_30),
                        findViewById(R.id.asteroid_31),
                        findViewById(R.id.asteroid_32),
                        findViewById(R.id.asteroid_33),
                        findViewById(R.id.asteroid_34),
                        findViewById(R.id.asteroid_35),
                        findViewById(R.id.asteroid_36),
                },
                {
                        findViewById(R.id.asteroid_40),
                        findViewById(R.id.asteroid_41),
                        findViewById(R.id.asteroid_42),
                        findViewById(R.id.asteroid_43),
                        findViewById(R.id.asteroid_44),
                        findViewById(R.id.asteroid_45),
                        findViewById(R.id.asteroid_46),
                }
        };

        coins = new AppCompatImageView[][]{
                {
                        findViewById(R.id.coin_00),
                        findViewById(R.id.coin_01),
                        findViewById(R.id.coin_02),
                        findViewById(R.id.coin_03),
                        findViewById(R.id.coin_04),
                        findViewById(R.id.coin_05),
                        findViewById(R.id.coin_06),
                },
                {
                        findViewById(R.id.coin_10),
                        findViewById(R.id.coin_11),
                        findViewById(R.id.coin_12),
                        findViewById(R.id.coin_13),
                        findViewById(R.id.coin_14),
                        findViewById(R.id.coin_15),
                        findViewById(R.id.coin_16),
                },
                {
                        findViewById(R.id.coin_20),
                        findViewById(R.id.coin_21),
                        findViewById(R.id.coin_22),
                        findViewById(R.id.coin_23),
                        findViewById(R.id.coin_24),
                        findViewById(R.id.coin_25),
                        findViewById(R.id.coin_26),
                },
                {
                        findViewById(R.id.coin_30),
                        findViewById(R.id.coin_31),
                        findViewById(R.id.coin_32),
                        findViewById(R.id.coin_33),
                        findViewById(R.id.coin_34),
                        findViewById(R.id.coin_35),
                        findViewById(R.id.coin_36),
                },
                {
                        findViewById(R.id.coin_40),
                        findViewById(R.id.coin_41),
                        findViewById(R.id.coin_42),
                        findViewById(R.id.coin_43),
                        findViewById(R.id.coin_44),
                        findViewById(R.id.coin_45),
                        findViewById(R.id.coin_46),
                }
        };
        aliens = new AppCompatImageView[]{
                findViewById(R.id.alien0),
                findViewById(R.id.alien1),
                findViewById(R.id.alien2),
                findViewById(R.id.alien3),
                findViewById(R.id.alien4),
        };
        left_arrow = findViewById(R.id.left_arrow);
        right_arrow = findViewById(R.id.right_arrow);
    }
}
