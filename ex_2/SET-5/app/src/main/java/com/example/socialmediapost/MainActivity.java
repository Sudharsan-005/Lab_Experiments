package com.example.socialmediapost;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private boolean isLiked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button likeButton = findViewById(R.id.likeButton);
        final TextView likeCounter = findViewById(R.id.likeCounter);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLiked) {
                    count++;
                    isLiked = true;
                    likeButton.setText("Liked");
                    likeCounter.setText("Likes: " + count);
                }
            }
        });
    }
}