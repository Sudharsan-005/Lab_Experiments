package com.example.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private final Context context;
    private List<Employee> employees;

    public EmployeeAdapter(Context context, List<Employee> employees) {
        this.context   = context;
        this.employees = employees;
    }

    public void updateList(List<Employee> newList) {
        this.employees = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee emp = employees.get(position);
        holder.tvName.setText(emp.getName());
        holder.tvDesignation.setText(emp.getDesignation());
        holder.tvDept.setText(emp.getDepartment());
        holder.tvEmail.setText(emp.getEmail());
        holder.tvSalary.setText(String.format(Locale.getDefault(), "₹%,.0f / yr", emp.getSalary()));

        // Avatar: first letter of name
        String initial = emp.getName().isEmpty() ? "?" : String.valueOf(emp.getName().charAt(0));
        holder.tvAvatar.setText(initial);

        // Set department badge colour
        int color = getDeptColor(emp.getDepartment());
        holder.tvDept.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color));
        holder.tvAvatar.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color));

        // Alternating card background
        holder.itemView.setAlpha(position % 2 == 0 ? 1f : 0.97f);
    }

    private int getDeptColor(String dept) {
        switch (dept) {
            case "Engineering": return 0xFF1565C0;
            case "Marketing":   return 0xFF6A1B9A;
            case "HR":          return 0xFF00695C;
            case "Finance":     return 0xFFE65100;
            case "Sales":       return 0xFFC62828;
            case "Operations":  return 0xFF37474F;
            default:            return 0xFF455A64;
        }
    }

    @Override
    public int getItemCount() { return employees.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesignation, tvDept, tvEmail, tvSalary, tvAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName        = itemView.findViewById(R.id.tvName);
            tvDesignation = itemView.findViewById(R.id.tvDesignation);
            tvDept        = itemView.findViewById(R.id.tvDept);
            tvEmail       = itemView.findViewById(R.id.tvEmail);
            tvSalary      = itemView.findViewById(R.id.tvSalary);
            tvAvatar      = itemView.findViewById(R.id.tvAvatar);
        }
    }
}
