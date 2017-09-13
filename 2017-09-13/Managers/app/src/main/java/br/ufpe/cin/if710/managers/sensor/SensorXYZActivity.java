package br.ufpe.cin.if710.managers.sensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.ufpe.cin.if710.managers.R;

public class SensorXYZActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor = null;
    TextView sensorName, xValue, yValue, zValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        setContentView(R.layout.activity_sensor_xyz);
        sensorName = (TextView) findViewById(R.id.sensorName);
        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        Intent i = getIntent();
        String sName = i.getStringExtra(SensorListActivity.SENSOR_NAME);
        if (sName == null) {
            finish();
        }

        int sType = i.getIntExtra(SensorListActivity.SENSOR_TYPE,Sensor.TYPE_ALL);

        for (Sensor s : sensorManager.getSensorList(sType)) {
            if (s.getName().equals(sName)) {
                sensor = s;
            }
        }

        if (sensor == null) {
            finish();
        }
        else {
            sensorName.setText(sensor.getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //SensorManager.SENSOR_DELAY_UI
        //SensorManager.SENSOR_DELAY_GAME
        //SensorManager.SENSOR_DELAY_FASTEST
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        //deixar de remover o registro é prejudicial, pois continua consumindo eventos do sensor
        //ignora activity lifecycle, então é similar a um memory leak
        //battery drain!
        sensorManager.unregisterListener(this);
        super.onPause();
    }


    //recebe objeto SensorEvent representando leitura do Sensor
    //objeto vem de um object pool e é reusado frequentemente
    // não devemos nos prender a esta instância do objeto (vide comentário abaixo)
    @Override
    public void onSensorChanged(SensorEvent event) {
        Float[] values = new Float[3];

        values[0] = event.values[0];
        values[1] = event.values.length>1 ? event.values[1] : 0.0f;
        values[2] = event.values.length>2 ? event.values[2] : 0.0f;

        updateValues(values);

        //event.accuracy
        //event.sensor
        //event.timestamp

        //Don't block the onSensorChanged() method
        /*
        Sensor data can change at a high rate, which means the system may call the
        onSensorChanged(SensorEvent) method quite often. As a best practice, you
        should do as little as possible within the onSensorChanged(SensorEvent) method
        so you don't block it. If your application requires you to do any data filtering
        or reduction of sensor data, you should perform that work outside of the
        onSensorChanged(SensorEvent) method.
        */

    }

    //mudança na precisão das leituras do sensor
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //nao estamos utilizando...
    }

    private void updateValues(Float[] values) {
        //xValue.setText("X: " + values[0]);
        xValue.setText(values[0].toString());
        yValue.setText(values[1].toString());
        zValue.setText(values[2].toString());
    }
}
