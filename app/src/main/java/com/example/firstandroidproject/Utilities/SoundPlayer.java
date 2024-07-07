package com.example.firstandroidproject.Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.ContactsContract;

import com.example.firstandroidproject.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class SoundPlayer {
    private Context context;
    private Executor executor;
    private MediaPlayer mediaPlayer;

    public SoundPlayer(Context context){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playSound(){
        //if(mediaPlayer == null){
            executor.execute(() ->{
                mediaPlayer = MediaPlayer.create(context, R.raw.failure);
                mediaPlayer.setVolume(1.0f,1.0f);
                mediaPlayer.start();
            });
        //}
    }
}
