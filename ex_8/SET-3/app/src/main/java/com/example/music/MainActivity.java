package com.example.music;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgWorkoutTypes;
    private RadioButton rbCardio, rbStrength, rbYoga;
    private TextView tvPlaylist, tvCurrentTrack;
    private Button btnPrev, btnPlayPause, btnNext;

    private List<String> currentPlaylist = new ArrayList<>();
    private int currentTrackIndex = 0;
    private boolean isPlaying = false;
    
    // Playlists
    private final String[] cardioTracks = {"Cardio - Fast Beat (160 BPM)", "Cardio - Run Free (170 BPM)", "Cardio - Energy Max (175 BPM)"};
    private final String[] strengthTracks = {"Strength - Heavy Weights (120 BPM)", "Strength - Core Power (110 BPM)", "Strength - Iron Lifter (125 BPM)"};
    private final String[] yogaTracks = {"Yoga - Zen Flow (60 BPM)", "Yoga - Inner Peace (50 BPM)", "Yoga - Morning Sun (65 BPM)"};

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable playRunnable;
    private int mockProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgWorkoutTypes = findViewById(R.id.rgWorkoutTypes);
        rbCardio = findViewById(R.id.rbCardio);
        rbStrength = findViewById(R.id.rbStrength);
        rbYoga = findViewById(R.id.rbYoga);

        tvPlaylist = findViewById(R.id.tvPlaylist);
        tvCurrentTrack = findViewById(R.id.tvCurrentTrack);

        btnPrev = findViewById(R.id.btnPrev);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnNext = findViewById(R.id.btnNext);

        rgWorkoutTypes.setOnCheckedChangeListener((group, checkedId) -> {
            stopPlayback();
            loadPlaylist(checkedId);
        });

        btnPlayPause.setOnClickListener(v -> togglePlayPause());
        btnNext.setOnClickListener(v -> playNext());
        btnPrev.setOnClickListener(v -> playPrev());
    }

    private void loadPlaylist(int checkedId) {
        currentPlaylist.clear();
        currentTrackIndex = 0;

        StringBuilder sb = new StringBuilder("Loaded Playlist:\n");

        String[] tracks = null;
        if (checkedId == R.id.rbCardio) {
            tracks = cardioTracks;
        } else if (checkedId == R.id.rbStrength) {
            tracks = strengthTracks;
        } else if (checkedId == R.id.rbYoga) {
            tracks = yogaTracks;
        }

        if (tracks != null) {
            for (int i = 0; i < tracks.length; i++) {
                currentPlaylist.add(tracks[i]);
                sb.append((i + 1)).append(". ").append(tracks[i]).append("\n");
            }
        }

        tvPlaylist.setText(sb.toString().trim());
        updateTrackUI();
    }

    private void updateTrackUI() {
        if (currentPlaylist.isEmpty()) {
            tvCurrentTrack.setText("No Track Selected");
            return;
        }

        String trackName = currentPlaylist.get(currentTrackIndex);
        if (isPlaying) {
            tvCurrentTrack.setText("Playing: " + trackName);
            btnPlayPause.setText("Pause");
        } else {
            tvCurrentTrack.setText("Paused: " + trackName);
            btnPlayPause.setText("Play");
        }
    }

    private void togglePlayPause() {
        if (currentPlaylist.isEmpty()) {
            Toast.makeText(this, "Please select a workout first", Toast.LENGTH_SHORT).show();
            return;
        }

        isPlaying = !isPlaying;
        updateTrackUI();
        
        if (isPlaying) {
            simulatePlayback();
        } else {
            stopPlaybackRunnable();
        }
    }

    private void playNext() {
        if (currentPlaylist.isEmpty()) return;
        currentTrackIndex = (currentTrackIndex + 1) % currentPlaylist.size();
        mockProgress = 0;
        if (!isPlaying) isPlaying = true;
        updateTrackUI();
        simulatePlayback();
    }

    private void playPrev() {
        if (currentPlaylist.isEmpty()) return;
        currentTrackIndex = (currentTrackIndex - 1 + currentPlaylist.size()) % currentPlaylist.size();
        mockProgress = 0;
        if (!isPlaying) isPlaying = true;
        updateTrackUI();
        simulatePlayback();
    }

    private void simulatePlayback() {
        stopPlaybackRunnable();
        playRunnable = new Runnable() {
            @Override
            public void run() {
                if (isPlaying) {
                    mockProgress++;
                    if (mockProgress > 100) { // simulate track end
                        playNext();
                    } else {
                        // update UI if needed (e.g. progress bar)
                        tvCurrentTrack.setText("Playing: " + currentPlaylist.get(currentTrackIndex) + " (" + mockProgress + "%)");
                        handler.postDelayed(this, 1000); // 1 tick per second
                    }
                }
            }
        };
        handler.post(playRunnable);
    }

    private void stopPlaybackRunnable() {
        if (playRunnable != null) {
            handler.removeCallbacks(playRunnable);
        }
    }

    private void stopPlayback() {
        isPlaying = false;
        mockProgress = 0;
        stopPlaybackRunnable();
        updateTrackUI();
    }
}