package com.example.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    private List<Product> list;

    public ProductAdapter(List<Product> list) { this.list = list; }

    public void update(List<Product> list) { this.list = list; notifyDataSetChanged(); }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Product p = list.get(pos);
        h.name.setText(p.getName());
        h.category.setText(p.getCategory());
        h.price.setText(String.format(Locale.US, "$%.2f", p.getPrice()));
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, category, price;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            category = v.findViewById(R.id.tvCategory);
            price = v.findViewById(R.id.tvPrice);
        }
    }
}
