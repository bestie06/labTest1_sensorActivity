package ca.georgebrown.labtest1_sensoractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textView2;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private View root;
    private long lastUpdate;
    private boolean tempAva;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2=findViewById(R.id.textView2);
        root=findViewById(R.id.root);
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);



        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null)
        {
            tempSensor=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            tempAva =true;
        }else {
            textView2.setText("Temperature is not avaliable");
            //istempAva=false;
            tempAva=false;
        }


    }


    //private static final float celci=1.6f;
    private void getTemp(SensorEvent event){
        float x =event.values[0];

        float celci= x*SensorManager.GRAVITY_EARTH;
        long time=event.timestamp;
        if(time-lastUpdate<200){
            return;
        }
        Log.d("temp tested",""+celci);



        if(x<0){
           root.setBackgroundColor(Color.BLUE);
        }
        if(x==0 && x<=20){
            root.setBackgroundColor(Color.WHITE);
        }
        if(x>20 && x<=40){
            root.setBackgroundColor(Color.YELLOW);
        }
        if (x > 40) {

            root.setBackgroundColor(Color.RED);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        getTemp(event);
        textView2.setText(event.values[0]+" C ");

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(tempAva) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(tempAva){
            sensorManager.unregisterListener(this);
        }


    }




}