package com.example.sensors;

import android.hardware.Sensor;

public class SensorHelper {

    public static final int CATEGORY_MOTION = 0;
    public static final int CATEGORY_ENVIRONMENT = 1;
    public static final int CATEGORY_POSITION = 2;
    public static final int CATEGORY_OTHER = 3;

    /**
     * Returns the category of a sensor.
     */
    public static int getCategory(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_STEP_COUNTER:
            case Sensor.TYPE_STEP_DETECTOR:
            case Sensor.TYPE_SIGNIFICANT_MOTION:
            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return CATEGORY_MOTION;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_PRESSURE:
            case Sensor.TYPE_RELATIVE_HUMIDITY:
            case Sensor.TYPE_TEMPERATURE:
                return CATEGORY_ENVIRONMENT;

            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_PROXIMITY:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return CATEGORY_POSITION;

            default:
                return CATEGORY_OTHER;
        }
    }

    /**
     * Returns a human-readable category name.
     */
    public static String getCategoryName(int category) {
        switch (category) {
            case CATEGORY_MOTION:
                return "Motion";
            case CATEGORY_ENVIRONMENT:
                return "Environment";
            case CATEGORY_POSITION:
                return "Position";
            default:
                return "Other";
        }
    }

    /**
     * Returns an emoji representing the sensor type.
     */
    public static String getSensorEmoji(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                return "🏃";
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "🔄";
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "🧲";
            case Sensor.TYPE_LIGHT:
                return "💡";
            case Sensor.TYPE_PRESSURE:
                return "🌡️";
            case Sensor.TYPE_PROXIMITY:
                return "👋";
            case Sensor.TYPE_GRAVITY:
                return "🌍";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "📐";
            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "🔁";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "🌡️";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "💧";
            case Sensor.TYPE_STEP_COUNTER:
                return "👟";
            case Sensor.TYPE_STEP_DETECTOR:
                return "🦶";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "⚡";
            default:
                return "📡";
        }
    }

    /**
     * Returns the unit of measurement for a sensor type.
     */
    public static String getSensorUnit(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                return "m/s²";
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "rad/s";
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "μT";
            case Sensor.TYPE_LIGHT:
                return "lux";
            case Sensor.TYPE_PRESSURE:
                return "hPa";
            case Sensor.TYPE_PROXIMITY:
                return "cm";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "°C";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "%";
            case Sensor.TYPE_STEP_COUNTER:
                return "steps";
            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "";
            default:
                return "";
        }
    }

    /**
     * Returns whether a sensor typically provides multi-axis (3+) values.
     */
    public static boolean isMultiAxis(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns the icon background drawable resource based on category.
     */
    public static int getIconBgRes(int category) {
        switch (category) {
            case CATEGORY_MOTION:
                return R.drawable.bg_sensor_icon_motion;
            case CATEGORY_ENVIRONMENT:
                return R.drawable.bg_sensor_icon_environment;
            case CATEGORY_POSITION:
                return R.drawable.bg_sensor_icon_position;
            default:
                return R.drawable.bg_sensor_icon_other;
        }
    }
}
