package com.example.flightbooking;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        TextView tvDisplay = findViewById(R.id.tvDisplay);

        // getIntent() works now because we extended AppCompatActivity
        String destination = getIntent().getStringExtra("DEST_KEY");

        if (destination != null && !destination.isEmpty()) {
            tvDisplay.setText("Flight Booked to: " + destination);
        } else {
            tvDisplay.setText("No destination selected.");
        }
    }
}