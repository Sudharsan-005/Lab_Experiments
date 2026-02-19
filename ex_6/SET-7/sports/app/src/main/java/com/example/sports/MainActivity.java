package com.example.sports;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private TextView newsContent;
    private String CHANNEL_ID = "breaking_news_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsContent = findViewById(R.id.newsContent);
        categorySpinner = findViewById(R.id.categorySpinner);

        // 1. Setup Spinner
        String[] categories = {"Sports", "Technology", "World", "Business"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = categories[position];
                newsContent.setText("Showing latest news for: " + selected);
                // Trigger a notification when a category is selected (simulation)
                showNotification(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 2. Setup Long Click to Show Share Dialog
        newsContent.setOnLongClickListener(v -> {
            showActionDialog();
            return true;
        });
    }

    // 3. Sharing/Saving Dialog
    private void showActionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Article Options")
                .setItems(new String[]{"Share Article", "Save for Later"}, (dialog, which) -> {
                    if (which == 0) {
                        Toast.makeText(this, "Sharing...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    // 4. Breaking News Notification
    private void showNotification(String category) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "News", NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Breaking: " + category)
                .setContentText("Tap to read the full story.")
                .setAutoCancel(true)
                .setContentIntent(pi);

        nm.notify(1, builder.build());
    }
}