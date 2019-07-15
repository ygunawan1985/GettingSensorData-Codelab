package com.example.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    // Individual light and proximity sensors.
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    // TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);

//        List<Sensor> sensorList  =
//                mSensorManager.getSensorList(Sensor.TYPE_ALL);
//
//        StringBuilder sensorText = new StringBuilder();
//
//        for (Sensor currentSensor : sensorList ) {
//            sensorText.append(currentSensor.getName()).append(
//                    System.getProperty("line.separator"));
//        }

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        imageView = findViewById(R.id.image_view);

        mSensorProximity =
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }

        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                // Handle light sensor
                mTextSensorLight.setText(getResources().getString(
                        R.string.label_light, currentValue));

                if(currentValue >= 10000 && currentValue < 20000){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                } else if(currentValue >= 20000 && currentValue < 30000) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                } else if(currentValue >= 30000 && currentValue <= 40000) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
                //getWindow().getDecorView().setBackgroundColor();
                break;

            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(
                        R.string.label_proximity, currentValue));

//                if(currentValue <= 5) {
//                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(200, 200));
//                } else if(currentValue > 5) {
//
//                }

                break;
            default:
                // do nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
