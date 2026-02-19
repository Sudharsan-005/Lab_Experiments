package com.example.chat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "chat_notifications";
    private TextView tvChatDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvChatDisplay = findViewById(R.id.tvChatDisplay);
        createNotificationChannel();

        findViewById(R.id.btnNewMessage).setOnClickListener(v -> showComposeDialog());

        // Handle clicking the notification (if any extra data was passed)
        if (getIntent().hasExtra("msg")) {
            displayConversation(getIntent().getStringExtra("sender"), getIntent().getStringExtra("msg"));
        }
    }

    private void showComposeDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_compose, null);
        EditText etRecipient = view.findViewById(R.id.etRecipient);
        EditText etMessage = view.findViewById(R.id.etMessage);

        new AlertDialog.Builder(this)
                .setTitle("New Message")
                .setView(view)
                .setPositiveButton("Send", (dialog, which) -> {
                    String sender = etRecipient.getText().toString();
                    String message = etMessage.getText().toString();
                    sendNotification(sender, message);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sendNotification(String sender, String message) {
        // Intent to open app back to this screen
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("sender", sender);
        intent.putExtra("msg", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(sender)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.notify(1, builder.build());
    }

    private void displayConversation(String sender, String msg) {
        tvChatDisplay.setVisibility(View.VISIBLE);
        tvChatDisplay.setText("From: " + sender + "\n\n" + msg);
        findViewById(R.id.btnNewMessage).setVisibility(View.GONE);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Chat", NotificationManager.IMPORTANCE_DEFAULT);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }
}