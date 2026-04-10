package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private RecyclerView rvSensors;
    private LinearLayout layoutEmpty;

    private SensorManager sensorManager;
    private SensorGridAdapter adapter;
    private List<SensorItem> sensorItems;
    private final List<Sensor> registeredSensors = new ArrayList<>();

    /**
     * The curated list of important sensors to showcase.
     * Each entry: { sensorType, displayName, emoji, categoryLabel, unit, isMultiAxis }
     */
    private static final Object[][] IMPORTANT_SENSORS = {
            {Sensor.TYPE_ACCELEROMETER,        "Accelerometer",        "🏃", "Motion Sensor",      "m/s²",  true},
            {Sensor.TYPE_GYROSCOPE,            "Gyroscope",            "🔄", "Motion Sensor",      "rad/s", true},
            {Sensor.TYPE_MAGNETIC_FIELD,        "Magnetometer",         "🧲", "Position Sensor",    "μT",    true},
            {Sensor.TYPE_LIGHT,                "Light",                "💡", "Environment Sensor", "lux",   false},
            {Sensor.TYPE_PROXIMITY,            "Proximity",            "👋", "Position Sensor",    "cm",    false},
            {Sensor.TYPE_PRESSURE,             "Barometer",            "🌡️","Environment Sensor", "hPa",   false},
            {Sensor.TYPE_GRAVITY,              "Gravity",              "🌍", "Motion Sensor",      "m/s²",  true},
            {Sensor.TYPE_LINEAR_ACCELERATION,  "Linear Acceleration",  "📐", "Motion Sensor",      "m/s²",  true},
            {Sensor.TYPE_ROTATION_VECTOR,      "Rotation Vector",      "🔁", "Motion Sensor",      "",      true},
            {Sensor.TYPE_RELATIVE_HUMIDITY,    "Humidity",             "💧", "Environment Sensor", "%",     false},
            {Sensor.TYPE_AMBIENT_TEMPERATURE,  "Temperature",          "🌡️","Environment Sensor", "°C",    false},
            {Sensor.TYPE_STEP_COUNTER,         "Step Counter",         "👟", "Motion Sensor",      "steps", false},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvSensors = findViewById(R.id.rvSensors);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        buildSensorList();
        setupGrid();
    }

    /**
     * Builds the curated list of SensorItems, checking device availability for each.
     */
    private void buildSensorList() {
        sensorItems = new ArrayList<>();

        for (Object[] entry : IMPORTANT_SENSORS) {
            int type = (int) entry[0];
            String name = (String) entry[1];
            String emoji = (String) entry[2];
            String catLabel = (String) entry[3];
            String unit = (String) entry[4];
            boolean multi = (boolean) entry[5];
            int category = SensorHelper.getCategory(type);

            Sensor sensor = sensorManager.getDefaultSensor(type);
            sensorItems.add(new SensorItem(type, name, emoji, catLabel, unit, multi, category, sensor));
        }
    }

    private void setupGrid() {
        if (sensorItems.isEmpty()) {
            rvSensors.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
            return;
        }

        rvSensors.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);

        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        rvSensors.setLayoutManager(gridManager);

        adapter = new SensorGridAdapter(sensorItems);
        rvSensors.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registeredSensors.clear();
        for (SensorItem item : sensorItems) {
            if (item.isAvailable()) {
                sensorManager.registerListener(this, item.getSensor(),
                        SensorManager.SENSOR_DELAY_UI);
                registeredSensors.add(item.getSensor());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        registeredSensors.clear();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (adapter != null) {
            adapter.updateSensorValues(event.sensor.getType(), event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}