package br.ufpe.cin.if710.managers.sensor;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.if710.managers.R;

public class SensorListActivity extends ListActivity {
    public static final String SENSOR_ID = "SENSOR_ID";
    public static final String SENSOR_TYPE = "SENSOR_TYPE";
    public static final String SENSOR_NAME = "SENSOR_NAME";

    ArrayAdapter<Sensor> adapter;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //assim como em LocationManager a gente pega um tipo de provider (GPS_PROVIDER)
        //com sensores, não há APIs dedicadas para sensores, usamos o SensorManager
        //os sensores são identificados por nomes (TYPE_LINEAR_ACCELERATION)

        //existem sensores e tipos de sensores
        //abaixo, estamos pedindo todos os sensores, de todos os tipos
        List<Sensor> allSensors = new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));
        List<Sensor> sensors = new ArrayList<>();

        //removendo trigger sensors, que entregam apenas uma única leitura
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (Sensor s : allSensors) {
                boolean triggerSensor = s.getType() == Sensor.TYPE_SIGNIFICANT_MOTION ||
                        s.getType() == Sensor.TYPE_STEP_COUNTER ||
                        s.getType() == Sensor.TYPE_STEP_DETECTOR;

                if (!triggerSensor) {
                    sensors.add(s);
                }
            }
        }
        else {
            sensors = allSensors;
        }

        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sensors);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Sensor s = (Sensor) l.getAdapter().getItem(position);
        //s.getPower();//power in mA used by this sensor while in use
        //s.getMaxDelay();//The max delay for this sensor in microseconds.
        //s.getMinDelay();//The min delay for this sensor in microseconds.

        Toast.makeText(this, s.getName(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent();
        if (isXYZ(s)) {
            i = new Intent(this, SensorXYZActivity.class);
        }
        else {
            i = new Intent(this, SensorSingleValueActivity.class);
        }
        //i.putExtra(SENSOR_ID,s.getId());
        i.putExtra(SENSOR_TYPE,s.getType());
        i.putExtra(SENSOR_NAME,s.getName());

        startActivity(i);
    }

    //separando sensores que levam em conta 3 eixos (acelerometro, gravidade, giroscopio...)
    //de sensores que usam apenas um valor (pressao, luz...)
    private boolean isXYZ(Sensor s) {
        switch (s.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_ROTATION_VECTOR:
                return true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (s.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR
                    || s.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED
                    || s.getType() == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
                return true;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (s.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
                return true;
            }
        }

        return false;
    }
}
