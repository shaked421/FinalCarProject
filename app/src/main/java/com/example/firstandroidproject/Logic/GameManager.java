package com.example.firstandroidproject.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.firstandroidproject.Utilities.SoundPlayer;

import java.util.Random;

public class GameManager {
    private int life;
    private int score = 0;
    private Random random;
    private Context context;
    private Vibrator vibrator;

    public SoundPlayer soundPlayer;

    public GameManager(int life, Context context) {
        this.life = life;
        this.random = new Random();
        this.context = context;
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public int getLife() {
        return life;
    }

    public int randomAsteroidStartColumn() {
        return random.nextInt(5);
    }

    public int randomCoinColumn() {
        return random.nextInt(5);
    }

    public boolean isCrash(int asteroidColumn, int currentAlienColumn) {
        if (asteroidColumn == currentAlienColumn) {
            life--;
            String crashText = "Watch out!! -❤️";
            toast(crashText);
            playSound();
        }
        if (life == 0) {
            String gameOverText = "GAME OVER!!!!️";
            toast(gameOverText);
            return true;
        }
        return false;
    }

    public void isCoin(int coinColumn, int currentAlienColumn) {
        if (coinColumn == currentAlienColumn) {
            score += 10;
        }
    }

    private void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        vibrate();
    }

    private void vibrate() {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }

    public void playSound() {
        soundPlayer = new SoundPlayer(context);
        soundPlayer.playSound();
    }

    public int getScore() {
        return score;
    }
}
