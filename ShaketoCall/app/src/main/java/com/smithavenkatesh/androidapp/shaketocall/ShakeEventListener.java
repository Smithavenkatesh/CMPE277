package com.smithavenkatesh.androidapp.shaketocall;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


/**
 * Created by smithavenkatesh on 10/20/17.
 */

public class ShakeEventListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    private static final int MOV_COUNTS = 5;
    private static final int MOV_THRESHOLD = 5;
    private static final float ALPHA = 0.8F;
    private static final int SHAKE_WINDOW_TIME_INTERVAL = 1000; // milliseconds

    private float gravity[] = new float[3];

    private int counter;
    private long firstMovTime;

    private ShakeListener listener;

    public ShakeEventListener() {
    }

    public void setListener(ShakeListener listener) {
        this.listener = listener;
    }


    public void init(Context ctx) {
        mSensorManager = (SensorManager)  ctx.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        register();
    }

    public void register() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void deregister()  {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float maxAcc = calcMaxAcceleration(sensorEvent);
        if (maxAcc >= MOV_THRESHOLD) {
            if (counter == 0) {
                counter++;
                firstMovTime = System.currentTimeMillis();
                Log.d("SwA", "First mov..");
            } else {
                long now = System.currentTimeMillis();
                if ((now - firstMovTime) < SHAKE_WINDOW_TIME_INTERVAL)
                    counter++;
                else {
                    resetAllData();
                    counter++;
                    return;
                }
                Log.d("SwA", "Mov counter ["+counter+"]");

                if (counter >= MOV_COUNTS)
                {
                    System.out.println("Moved the phone");
                    listener.onShake();
                }
            }
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private float calcMaxAcceleration(SensorEvent event) {
        //Get the values of each axis
        gravity[0] = calcGravityForce(event.values[0], 0);
        gravity[1] = calcGravityForce(event.values[1], 1);
        gravity[2] = calcGravityForce(event.values[2], 2);

        float accX = event.values[0] - gravity[0];
        float accY = event.values[1] - gravity[1];
        float accZ = event.values[2] - gravity[2];

        float max1 = Math.max(accX, accY);
        return Math.max(max1, accZ);
    }

    // Low pass filter
    private float calcGravityForce(float currentVal, int index) {

        return  ALPHA * gravity[index] + (1 - ALPHA) * currentVal;
    }


    private void resetAllData() {
        Log.d("SwA", "Reset all data");
        counter = 0;
        firstMovTime = System.currentTimeMillis();
    }

    public interface ShakeListener {
        void onShake();
    }

}
