package com.example.firstandroidproject.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;
import java.util.Random;

public class GameManager {
    private int life;
    private Random random;
    private Context context;
    private Vibrator vibrator;

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
        return random.nextInt(3);
    }

    public void isCrash(int asteroidColumn, int currentAlienColumn) {
        if (asteroidColumn == currentAlienColumn) {
            life--;
            String crashText = "Watch out!! -❤️";
            toast(crashText);
        }
        if (life == 0) {
            life = 3;
            String newLifeText = "New Life! ❤️❤️❤️";
            toast(newLifeText);
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
}
