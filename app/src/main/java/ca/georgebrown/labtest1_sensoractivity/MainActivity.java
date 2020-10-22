package ca.georgebrown.labtest1_sensoractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private MenuItem item1;

    private Sensor lightSensor;
    private SensorManager sensorManager;
    private TextView textView3;
    private View root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item1=(MenuItem) findViewById(R.id.item1);
        textView3 = (TextView) findViewById(R.id.textView3);
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        root = findViewById(R.id.root);

        String sensor_error = getResources().getString(R.string.textView3);
        if (lightSensor == null) { textView3.setText(sensor_error); }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sensor_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent =new Intent(getApplicationContext(),SensorActivity.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent mainA =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainA);
                return true;
            default:
                return super.onOptionsItemSelected(item1);
        }
    }

    public void getLight(SensorEvent event){
        float[] values = event.values;
        float x=values[0];
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textView3.setText(event.values[0]+ " Light ");
        getLight(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}