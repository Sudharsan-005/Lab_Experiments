package com.example.flightbooking; // Ensure this matches your project name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etDestination = findViewById(R.id.etDestination);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = etDestination.getText().toString();

                // Intent is used to move from this Activity to ConfirmationActivity
                Intent intent = new Intent(MainActivity.this, com.example.flightbooking.MainActivity.class);
                intent.putExtra("DEST_KEY", destination);
                startActivity(intent);
            }
        });
    }
}