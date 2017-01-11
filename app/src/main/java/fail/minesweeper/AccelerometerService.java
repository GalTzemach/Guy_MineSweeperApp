package fail.minesweeper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import AppLogic.GameLogic.AccelerometerListener;

public class AccelerometerService extends Service implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor sensor;
    private IBinder sensorBinder = new SensorServiceBinder();
    private AccelerometerListener listener;
    private float firstSample;
    private boolean isFirstSample = false;
    private static final double THRASHHOLD = 1.5;
    private int warningCounter = 0;


    public AccelerometerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        return sensorBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float value = event.values[1];
        //Log.v("Guy",""+value);
        if(!isFirstSample){
            firstSample = value;
            isFirstSample = true;
        }
        if(value > firstSample+THRASHHOLD || value < firstSample-THRASHHOLD){
            warningCounter++;
            if(warningCounter == 6) {
                listener.accelerometerChanged();
                warningCounter = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        return;
    }

    public void setListener(AccelerometerListener listener){
        this.listener = listener;
    }

    public AccelerometerService getSelf(){
        return this;
    }

    class SensorServiceBinder extends Binder {
        AccelerometerService getService() {
            return AccelerometerService.this.getSelf();
        }
    }
}
