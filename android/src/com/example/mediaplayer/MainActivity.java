package com.example.mediaplayer;

import android.os.Bundle;
import android.util.Log;

import org.qtproject.qt.android.bindings.QtActivity;

import androidx.annotation.NonNull;
import android.support.v4.media.session.MediaSessionCompat;

public class MainActivity extends QtActivity {
    private MediaSessionHandler mediaSessionHandler;
    private MediaNotificationManager mediaNotificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate called");
        mediaSessionHandler = new MediaSessionHandler(this);
        mediaNotificationManager = new MediaNotificationManager(this, mediaSessionHandler.getMediaSession());
    }

    // Method to be called from QML/C++
    public void updateMediaSessionState(boolean isPlaying) {
        if (mediaSessionHandler != null) {
            mediaSessionHandler.updatePlaybackState(isPlaying);
            mediaNotificationManager.updateNotification(isPlaying);
        } else {
            Log.e("MainActivity", "mediaSessionHandler is null");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaSessionHandler != null) {
            mediaSessionHandler.release();
        }
        if (mediaNotificationManager != null) {
            mediaNotificationManager.cancelNotification();
        }
    }
}
