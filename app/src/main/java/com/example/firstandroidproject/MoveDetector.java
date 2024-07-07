package com.example.firstandroidproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.firstandroidproject.interfaces.MoveCallBack;

public class MoveDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private long timeStamp = 0l;
    private MoveCallBack moveCallBack;

    public MoveDetector(Context context, MoveCallBack moveCallBack) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallBack = moveCallBack;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateMove(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    private void calculateMove(float x) {
        if(System.currentTimeMillis() - timeStamp > 500){
            timeStamp = System.currentTimeMillis();
            if(x > 0.0){
                if(moveCallBack != null){
                    moveCallBack.moveXLeft();
                }
            }
            if(x < 0.0){
                if(moveCallBack != null){
                    moveCallBack.moveXRight();
                }
            }

        }
    }

    public void start(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void stop(){
    sensorManager.unregisterListener(sensorEventListener, sensor);
    }
}
