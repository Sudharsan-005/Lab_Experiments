package com.example.sensors;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class SensorDetailActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView tvDetailSensorName;
    private TextView tvDetailSensorEmoji;
    private TextView tvDetailCategory;
    private TextView tvDetailVendor;
    private TextView tvDetailVersion;
    private TextView tvDetailMaxRange;
    private TextView tvDetailResolution;
    private TextView tvDetailPower;

    private LinearLayout layoutMultiAxis;
    private LinearLayout layoutSingleValue;
    private TextView tvValueX, tvValueY, tvValueZ;
    private TextView tvSingleValue, tvSingleUnit;
    private TextView tvAllValues;

    private View viewLiveIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        int sensorType = getIntent().getIntExtra("sensor_type", -1);
        if (sensorType == -1) {
            finish();
            return;
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);

        if (sensor == null) {
            finish();
            return;
        }

        populateSensorInfo();
        setupLiveDataLayout();
        startLiveIndicatorAnimation();
    }

    private void initViews() {
        tvDetailSensorName = findViewById(R.id.tvDetailSensorName);
        tvDetailSensorEmoji = findViewById(R.id.tvDetailSensorEmoji);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailVendor = findViewById(R.id.tvDetailVendor);
        tvDetailVersion = findViewById(R.id.tvDetailVersion);
        tvDetailMaxRange = findViewById(R.id.tvDetailMaxRange);
        tvDetailResolution = findViewById(R.id.tvDetailResolution);
        tvDetailPower = findViewById(R.id.tvDetailPower);

        layoutMultiAxis = findViewById(R.id.layoutMultiAxis);
        layoutSingleValue = findViewById(R.id.layoutSingleValue);
        tvValueX = findViewById(R.id.tvValueX);
        tvValueY = findViewById(R.id.tvValueY);
        tvValueZ = findViewById(R.id.tvValueZ);
        tvSingleValue = findViewById(R.id.tvSingleValue);
        tvSingleUnit = findViewById(R.id.tvSingleUnit);
        tvAllValues = findViewById(R.id.tvAllValues);
        viewLiveIndicator = findViewById(R.id.viewLiveIndicator);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void populateSensorInfo() {
        tvDetailSensorName.setText(sensor.getName());
        tvDetailSensorEmoji.setText(SensorHelper.getSensorEmoji(sensor.getType()));

        int category = SensorHelper.getCategory(sensor.getType());
        tvDetailCategory.setText(SensorHelper.getCategoryName(category));

        tvDetailVendor.setText(sensor.getVendor());
        tvDetailVersion.setText(String.valueOf(sensor.getVersion()));
        tvDetailMaxRange.setText(String.format(Locale.US, "%.2f", sensor.getMaximumRange()));
        tvDetailResolution.setText(String.format(Locale.US, "%.6f", sensor.getResolution()));
        tvDetailPower.setText(String.format(Locale.US, "%.2f mA", sensor.getPower()));
    }

    private void setupLiveDataLayout() {
        if (SensorHelper.isMultiAxis(sensor.getType())) {
            layoutMultiAxis.setVisibility(View.VISIBLE);
            layoutSingleValue.setVisibility(View.GONE);
            tvAllValues.setVisibility(View.GONE);
        } else {
            layoutMultiAxis.setVisibility(View.GONE);
            layoutSingleValue.setVisibility(View.VISIBLE);
            tvSingleUnit.setText(SensorHelper.getSensorUnit(sensor.getType()));
            tvAllValues.setVisibility(View.GONE);
        }
    }

    private void startLiveIndicatorAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(viewLiveIndicator, "alpha", 1f, 0.3f);
        animator.setDuration(800);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (SensorHelper.isMultiAxis(event.sensor.getType())) {
            if (event.values.length >= 3) {
                tvValueX.setText(String.format(Locale.US, "%.2f", event.values[0]));
                tvValueY.setText(String.format(Locale.US, "%.2f", event.values[1]));
                tvValueZ.setText(String.format(Locale.US, "%.2f", event.values[2]));
            }
        } else {
            if (event.values.length >= 1) {
                tvSingleValue.setText(String.format(Locale.US, "%.2f", event.values[0]));
            }
        }

        // Also show all raw values if more than 3 axes
        if (event.values.length > 3) {
            tvAllValues.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder("All values:\n");
            for (int i = 0; i < event.values.length; i++) {
                sb.append(String.format(Locale.US, "  [%d] = %.4f\n", i, event.values[i]));
            }
            tvAllValues.setText(sb.toString().trim());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
