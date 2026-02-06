package com.example.countries; // Replace with YOUR package name

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView; // Using standard widget to match XML
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> adapter;


    ArrayList<String> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);


        String[] countries = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
                "Argentina", "Australia", "Brazil", "Canada", "China",
                "Denmark", "Egypt", "France", "Germany", "India",
                "Japan", "Mexico", "Norway", "United States"};
        countryList = new ArrayList<>(Arrays.asList(countries));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countryList);
        listView.setAdapter(adapter);

        // 4. Implement Search Filter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}