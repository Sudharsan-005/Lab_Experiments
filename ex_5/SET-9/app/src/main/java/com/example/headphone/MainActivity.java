package com.example.headphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView statusTextView;
    private HeadsetReceiver headsetReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusTextView = findViewById(R.id.statusTextView);
        headsetReceiver = new HeadsetReceiver();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Register the receiver to listen for headphone jack changes
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetReceiver, filter);
    }
    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(headsetReceiver);
    }
    private class HeadsetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        statusTextView.setText("Headphones Disconnected");
                        Toast.makeText(context, "Headset Unplugged", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        statusTextView.setText("Headphones Connected");
                        Toast.makeText(context, "Headset Plugged In", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        statusTextView.setText("Unknown State");
                }
            }
        }
    }
}