package smithavenkatesh.clapapp.cmpe277.com.clapapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximity;
    //private SensorEventListener listener;
    private static final int Sensor_Sensitivity = 4;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if sensor exits
        PackageManager PM= this.getPackageManager();
        boolean check_proximity = PM.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);
        System.out.println(check_proximity);

        //instantiate the object of SensorManager class.
        mSensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mp = MediaPlayer.create(this,R.raw.clap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //register listener for sensor
        mSensorManager.registerListener(this,mProximity,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.out.println("In The function onSensorChanged");
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] >= -Sensor_Sensitivity && sensorEvent.values[0] <= Sensor_Sensitivity) {
                //near
                mp.start();
                Toast.makeText(getApplicationContext(), "Clapped", Toast.LENGTH_SHORT).show();
                System.out.println("Clapped");
            }

        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
