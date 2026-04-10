package com.example.sensors;

import android.hardware.Sensor;

/**
 * Represents an important sensor to showcase in the grid.
 * Holds the sensor type and an optional reference to the actual Sensor
 * (null if the device doesn't have it).
 */
public class SensorItem {
    private final int sensorType;
    private final String displayName;
    private final String emoji;
    private final String categoryLabel;
    private final String unit;
    private final boolean multiAxis;
    private final int category;
    private Sensor sensor; // null if unavailable

    public SensorItem(int sensorType, String displayName, String emoji,
                      String categoryLabel, String unit, boolean multiAxis,
                      int category, Sensor sensor) {
        this.sensorType = sensorType;
        this.displayName = displayName;
        this.emoji = emoji;
        this.categoryLabel = categoryLabel;
        this.unit = unit;
        this.multiAxis = multiAxis;
        this.category = category;
        this.sensor = sensor;
    }

    public int getSensorType() { return sensorType; }
    public String getDisplayName() { return displayName; }
    public String getEmoji() { return emoji; }
    public String getCategoryLabel() { return categoryLabel; }
    public String getUnit() { return unit; }
    public boolean isMultiAxis() { return multiAxis; }
    public int getCategory() { return category; }
    public Sensor getSensor() { return sensor; }
    public boolean isAvailable() { return sensor != null; }
}
