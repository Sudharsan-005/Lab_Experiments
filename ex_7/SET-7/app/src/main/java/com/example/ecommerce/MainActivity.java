package com.example.ecommerce;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.RangeSlider;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ProductAdapter adapter;
    EditText etSearch;
    Spinner spCategory;
    RangeSlider sliderPrice;
    TextView tvRange, tvCount;
    String selectedCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        etSearch = findViewById(R.id.etSearch);
        spCategory = findViewById(R.id.spCategory);
        sliderPrice = findViewById(R.id.sliderPrice);
        tvRange = findViewById(R.id.tvRange);
        tvCount = findViewById(R.id.tvCount);
        RecyclerView rv = findViewById(R.id.rvProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Category spinner
        List<String> cats = db.getCategories();
        spCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cats));
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                selectedCategory = cats.get(pos);
                filter();
            }
            public void onNothingSelected(AdapterView<?> p) {}
        });

        // Price slider
        sliderPrice.setValueFrom(0f);
        sliderPrice.setValueTo(1200f);
        sliderPrice.setValues(0f, 1200f);
        sliderPrice.setStepSize(10f);
        sliderPrice.addOnChangeListener((s, v, f) -> filter());

        // Search
        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) { filter(); }
            public void afterTextChanged(Editable s) {}
        });

        // Initial load
        List<Product> products = db.searchProducts("", "All", 0, 1200);
        adapter = new ProductAdapter(products);
        rv.setAdapter(adapter);
        tvCount.setText(products.size() + " products");
        tvRange.setText("$0 - $1200");
    }

    private void filter() {
        List<Float> vals = sliderPrice.getValues();
        float min = vals.get(0), max = vals.get(1);
        tvRange.setText(String.format(Locale.US, "$%.0f - $%.0f", min, max));

        String q = etSearch.getText().toString().trim();
        List<Product> results = db.searchProducts(q, selectedCategory, min, max);
        adapter.update(results);
        tvCount.setText(results.size() + " products");
    }
}