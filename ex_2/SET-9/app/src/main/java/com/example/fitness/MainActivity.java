package com.example.fitness;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private TextView exerciseDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exerciseDisplay = findViewById(R.id.exerciseDisplay);
        Button btnRunning = findViewById(R.id.btnRunning);
        Button btnCycling = findViewById(R.id.btnCycling);
        Button btnSwimming = findViewById(R.id.btnSwimming);
        Button btnYoga = findViewById(R.id.btnYoga);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String exercise = clickedButton.getText().toString();
                exerciseDisplay.setText("Selected Exercise: " + exercise);
                Toast.makeText(MainActivity.this, "Started: " + exercise, Toast.LENGTH_SHORT).show();
            }
        };
        btnRunning.setOnClickListener(listener);
        btnCycling.setOnClickListener(listener);
        btnSwimming.setOnClickListener(listener);
        btnYoga.setOnClickListener(listener);
    }
}