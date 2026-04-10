package com.example.sensors;

import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    public interface OnSensorClickListener {
        void onSensorClick(Sensor sensor);
    }

    private final List<Sensor> sensors;
    private final OnSensorClickListener listener;

    public SensorAdapter(List<Sensor> sensors, OnSensorClickListener listener) {
        this.sensors = sensors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sensor, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        Sensor sensor = sensors.get(position);
        int category = SensorHelper.getCategory(sensor.getType());

        holder.tvSensorName.setText(sensor.getName());
        holder.tvSensorVendor.setText(sensor.getVendor());
        holder.tvSensorEmoji.setText(SensorHelper.getSensorEmoji(sensor.getType()));
        holder.tvCategory.setText(SensorHelper.getCategoryName(category));
        holder.flSensorIcon.setBackgroundResource(SensorHelper.getIconBgRes(category));

        holder.tvMaxRange.setText(String.format(Locale.US, "Range: %.1f", sensor.getMaximumRange()));
        holder.tvPower.setText(String.format(Locale.US, "⚡ %.2f mA", sensor.getPower()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSensorClick(sensor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {
        final TextView tvSensorName;
        final TextView tvSensorVendor;
        final TextView tvSensorEmoji;
        final TextView tvCategory;
        final TextView tvMaxRange;
        final TextView tvPower;
        final FrameLayout flSensorIcon;

        SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSensorName = itemView.findViewById(R.id.tvSensorName);
            tvSensorVendor = itemView.findViewById(R.id.tvSensorVendor);
            tvSensorEmoji = itemView.findViewById(R.id.tvSensorEmoji);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvMaxRange = itemView.findViewById(R.id.tvMaxRange);
            tvPower = itemView.findViewById(R.id.tvPower);
            flSensorIcon = itemView.findViewById(R.id.flSensorIcon);
        }
    }
}
