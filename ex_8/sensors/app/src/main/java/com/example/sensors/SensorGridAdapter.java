package com.example.sensors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class SensorGridAdapter extends RecyclerView.Adapter<SensorGridAdapter.GridViewHolder> {

    private final List<SensorItem> items;

    public SensorGridAdapter(List<SensorItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sensor_grid, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        SensorItem item = items.get(position);

        holder.tvSensorEmoji.setText(item.getEmoji());
        holder.tvSensorName.setText(item.getDisplayName());
        holder.tvCategory.setText(item.getCategoryLabel());
        holder.flSensorIcon.setBackgroundResource(SensorHelper.getIconBgRes(item.getCategory()));

        if (item.isAvailable()) {
            holder.viewAvailability.setBackgroundResource(R.drawable.bg_available_dot);
            holder.tvUnavailable.setVisibility(View.GONE);

            if (item.isMultiAxis()) {
                holder.layoutMultiAxis.setVisibility(View.VISIBLE);
                holder.layoutSingleValue.setVisibility(View.GONE);
            } else {
                holder.layoutMultiAxis.setVisibility(View.GONE);
                holder.layoutSingleValue.setVisibility(View.VISIBLE);
                holder.tvUnit.setText(item.getUnit());
            }
        } else {
            holder.viewAvailability.setBackgroundResource(R.drawable.bg_unavailable_dot);
            holder.layoutMultiAxis.setVisibility(View.GONE);
            holder.layoutSingleValue.setVisibility(View.GONE);
            holder.tvUnavailable.setVisibility(View.VISIBLE);
        }

        holder.itemView.setAlpha(item.isAvailable() ? 1.0f : 0.5f);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Updates the live values for a specific sensor type.
     * Called from the Activity's SensorEventListener.
     */
    public void updateSensorValues(int sensorType, float[] values) {
        for (int i = 0; i < items.size(); i++) {
            SensorItem item = items.get(i);
            if (item.getSensorType() == sensorType && item.isAvailable()) {
                notifyItemChanged(i, values);
                break;
            }
        }
    }

    /**
     * Partial bind — only update values, not the full card.
     */
    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty() && payloads.get(0) instanceof float[]) {
            float[] values = (float[]) payloads.get(0);
            SensorItem item = items.get(position);

            if (item.isMultiAxis() && values.length >= 3) {
                holder.tvValueX.setText(String.format(Locale.US, "%.2f", values[0]));
                holder.tvValueY.setText(String.format(Locale.US, "%.2f", values[1]));
                holder.tvValueZ.setText(String.format(Locale.US, "%.2f", values[2]));
            } else if (!item.isMultiAxis() && values.length >= 1) {
                holder.tvSingleValue.setText(String.format(Locale.US, "%.1f", values[0]));
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {
        final FrameLayout flSensorIcon;
        final TextView tvSensorEmoji;
        final View viewAvailability;
        final TextView tvSensorName;
        final TextView tvCategory;
        final LinearLayout layoutMultiAxis;
        final TextView tvValueX, tvValueY, tvValueZ;
        final LinearLayout layoutSingleValue;
        final TextView tvSingleValue;
        final TextView tvUnit;
        final TextView tvUnavailable;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            flSensorIcon = itemView.findViewById(R.id.flSensorIcon);
            tvSensorEmoji = itemView.findViewById(R.id.tvSensorEmoji);
            viewAvailability = itemView.findViewById(R.id.viewAvailability);
            tvSensorName = itemView.findViewById(R.id.tvSensorName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            layoutMultiAxis = itemView.findViewById(R.id.layoutMultiAxis);
            tvValueX = itemView.findViewById(R.id.tvValueX);
            tvValueY = itemView.findViewById(R.id.tvValueY);
            tvValueZ = itemView.findViewById(R.id.tvValueZ);
            layoutSingleValue = itemView.findViewById(R.id.layoutSingleValue);
            tvSingleValue = itemView.findViewById(R.id.tvSingleValue);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvUnavailable = itemView.findViewById(R.id.tvUnavailable);
        }
    }
}
