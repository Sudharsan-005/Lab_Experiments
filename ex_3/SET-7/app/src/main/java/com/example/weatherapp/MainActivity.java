package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout inputLayout = findViewById(R.id.inputLayout);
        final LinearLayout displayLayout = findViewById(R.id.displayLayout);
        final EditText cityInput = findViewById(R.id.cityEditText);
        final TextView weatherDisplay = findViewById(R.id.weatherDisplay);
        Button submitBtn = findViewById(R.id.viewWeatherBtn);
        Button backBtn = findViewById(R.id.backBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityInput.getText().toString();
                if (!cityName.isEmpty()) {
                    weatherDisplay.setText("City: " + cityName + "\nWeather: 22°C, Cloudy");
                    inputLayout.setVisibility(View.GONE);
                    displayLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLayout.setVisibility(View.GONE);
                inputLayout.setVisibility(View.VISIBLE);
                cityInput.setText("");
            }
        });
    }
}