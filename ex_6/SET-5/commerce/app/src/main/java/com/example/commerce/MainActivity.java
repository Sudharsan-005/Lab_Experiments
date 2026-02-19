package com.example.commerce;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "cart_channel";
    private int itemsInCart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        // Add to Cart Button Logic
        findViewById(R.id.btnAddProduct).setOnClickListener(v -> {
            itemsInCart++;
            showCartNotification();
        });

        // Cart Icon Logic (Opens Purchase Dialog)
        findViewById(R.id.btnCartIcon).setOnClickListener(v -> showPurchaseDialog());
    }

    private void showCartNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_input_add)
                .setContentTitle("Cart Updated")
                .setContentText("You have added an item. Total items: " + itemsInCart)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.notify(1, builder.build());
    }

    private void showPurchaseDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Purchase Confirmation")
                .setMessage("You have " + itemsInCart + " items in your cart. Proceed to checkout?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    itemsInCart = 0; // Reset cart after purchase
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Cart Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }
}