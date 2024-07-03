package com.example.mediaplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import android.support.v4.media.session.MediaSessionCompat;

public class MediaNotificationManager {
    private static final String CHANNEL_ID = "media_playback_channel";
    private static final int NOTIFICATION_ID = 1;

    private Context context;
    private NotificationManager notificationManager;
    private MediaSessionCompat mediaSession;

    public MediaNotificationManager(Context context, MediaSessionCompat mediaSession) {
        this.context = context;
        this.mediaSession = mediaSession;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Media Playback",
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void updateNotification(boolean isPlaying) {
        NotificationCompat.Action playPauseAction;
        if (isPlaying) {
            playPauseAction = new NotificationCompat.Action(
                    android.R.drawable.ic_media_pause,
                    "Pause",
                    getPendingIntent("ACTION_PAUSE")
            );
        } else {
            playPauseAction = new NotificationCompat.Action(
                    android.R.drawable.ic_media_play,
                    "Play",
                    getPendingIntent("ACTION_PLAY")
            );
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setStyle(new MediaStyle().setMediaSession(mediaSession.getSessionToken()))
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Media Player")
                .setContentText("Playing audio")
                .addAction(playPauseAction)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.setAction(action);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
