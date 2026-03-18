package com.example.employee;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EmployeeAdapter adapter;

    private Spinner spinnerDept;
    private EditText etMinSalary, etMaxSalary;
    private TextView tvQuery, tvResultCount;
    private RecyclerView recyclerView;

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

        dbHelper = new DatabaseHelper(this);

        // UI references
        spinnerDept   = findViewById(R.id.spinnerDept);
        etMinSalary   = findViewById(R.id.etMinSalary);
        etMaxSalary   = findViewById(R.id.etMaxSalary);
        tvQuery       = findViewById(R.id.tvQuery);
        tvResultCount = findViewById(R.id.tvResultCount);
        recyclerView  = findViewById(R.id.recyclerView);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnReset  = findViewById(R.id.btnReset);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate department spinner
        List<String> depts = dbHelper.getDepartments();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, depts);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDept.setAdapter(spinnerAdapter);

        // Load all on start
        loadAll();

        btnSearch.setOnClickListener(v -> performSearch());
        btnReset.setOnClickListener(v -> {
            spinnerDept.setSelection(0);
            etMinSalary.setText("");
            etMaxSalary.setText("");
            loadAll();
        });
    }

    private void loadAll() {
        StringBuilder query = new StringBuilder();
        List<Employee> list = dbHelper.getAllEmployees(query);
        showResults(list, query.toString());
    }

    private void performSearch() {
        String dept      = spinnerDept.getSelectedItem().toString();
        String minStr    = etMinSalary.getText().toString().trim();
        String maxStr    = etMaxSalary.getText().toString().trim();

        boolean hasDept   = !dept.equals("All Departments");
        boolean hasSalary = !TextUtils.isEmpty(minStr) || !TextUtils.isEmpty(maxStr);

        // Validate salary inputs if provided
        double minSalary = 0, maxSalary = Double.MAX_VALUE;
        if (hasSalary) {
            if (TextUtils.isEmpty(minStr)) minStr = "0";
            if (TextUtils.isEmpty(maxStr)) maxStr = "9999999";
            try {
                minSalary = Double.parseDouble(minStr);
                maxSalary = Double.parseDouble(maxStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid salary values", Toast.LENGTH_SHORT).show();
                return;
            }
            if (minSalary > maxSalary) {
                Toast.makeText(this, "Min salary cannot exceed max salary", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        StringBuilder query = new StringBuilder();
        List<Employee> list;

        if (hasDept && hasSalary) {
            list = dbHelper.getByDeptAndSalary(dept, minSalary, maxSalary, query);
        } else if (hasDept) {
            list = dbHelper.getByDepartment(dept, query);
        } else if (hasSalary) {
            list = dbHelper.getBySalaryRange(minSalary, maxSalary, query);
        } else {
            list = dbHelper.getAllEmployees(query);
        }

        showResults(list, query.toString());
    }

    private void showResults(List<Employee> list, String sql) {
        // Show the SQL query
        tvQuery.setText(sql);

        // Show result count
        int count = list.size();
        tvResultCount.setText(count + " employee" + (count != 1 ? "s" : "") + " found");

        // Update RecyclerView
        if (adapter == null) {
            adapter = new EmployeeAdapter(this, list);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(list);
        }
    }
}