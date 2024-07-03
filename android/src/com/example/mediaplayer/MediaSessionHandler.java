package com.example.mediaplayer;

import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

public class MediaSessionHandler {
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    public MediaSessionHandler(Context context) {
        mediaSession = new MediaSessionCompat(context, "MediaSessionHandler");
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                Log.d("MediaSessionHandler", "onPlay called");
            }

            @Override
            public void onPause() {
                super.onPause();
                Log.d("MediaSessionHandler", "onPause called");
            }
        });
    }

    public void updatePlaybackState(boolean isPlaying) {
        if (isPlaying) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1);
        } else {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
    }

    public void release() {
        if (mediaSession != null) {
            mediaSession.release();
            mediaSession = null;
        }
    }

    public MediaSessionCompat getMediaSession() {
        return  mediaSession;
    }
}
