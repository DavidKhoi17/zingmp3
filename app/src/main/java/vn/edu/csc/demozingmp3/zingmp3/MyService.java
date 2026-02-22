package vn.edu.csc.demozingmp3.zingmp3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;

import vn.edu.csc.demozingmp3.R;

public class MyService extends Service {
    private BroadcastReceiver broadcastReceiver;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void register() {
        // dinh nghia noi nhan du lieu
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "phatnhac11":
                        String path = intent.getStringExtra("path111");
                        Log.e("TQKy", path);
                        play(path);
                        showNotification(path);
                        break;
                    case "start11":
                        if (mediaPlayer != null) {
                            if (!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                            }
                        }

                        break;
                    case "pause11":
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                            }
                        }
                        break;
                    case "stop11":
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                        stopForeground(true);
                        break;
                }
            }
        };

        // dang ky cac action
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("phatnhac11");
        intentFilter.addAction("start11");
        intentFilter.addAction("pause11");
        intentFilter.addAction("stop11");

        // dang ky nhan du lieu lieu
        registerReceiver(broadcastReceiver, intentFilter);

    }

    private void play(String path) {
        try {
            // cung cap tai nguyen cho doi tuong phat nhac
            mediaPlayer.setDataSource(path);

            // load tai nguyen
            mediaPlayer.prepare();

            // phat nhac
            mediaPlayer.start();

            // tam dung
            //mediaPlayer.pause();

            //mediaPlayer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showNotification(String path) {
        // khoi tao 1 notification
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        // cung cap small icon
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // khoi tao doi tuong chua layout minh thiet ke
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_layout);

        // thiet lap hinh anh cho ImageView
        remoteView.setImageViewResource(R.id.imageView, R.mipmap.ic_launcher);

        // thiet lap noi dung cho textView
        remoteView.setTextViewText(R.id.textView, path);


        // dang ky su kien click va sendBroadcast
        Intent intentPlay = new Intent("start11");
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(
                getApplicationContext(),
                111,
                intentPlay,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteView.setOnClickPendingIntent(R.id.buttonPlay, pendingIntentPlay);

        // dang ky su kien click va sendBroadcast
        Intent intentPause = new Intent("pause11");
        PendingIntent pendingIntentPause = PendingIntent.getBroadcast(
                getApplicationContext(),
                111,
                intentPause,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteView.setOnClickPendingIntent(R.id.buttonPause, pendingIntentPause);

        // dang ky su kien click va sendBroadcast
        Intent intentStop = new Intent("stop11");
        PendingIntent pendingIntentStop = PendingIntent.getBroadcast(
                getApplicationContext(),
                111,
                intentStop,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteView.setOnClickPendingIntent(R.id.buttonStop, pendingIntentStop);


        // thiet lap vo cho 1 notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setCustomContentView(remoteView);
        } else {
            builder.setContent(remoteView);
        }

        // hien thi notification:
        startForeground(1, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        register();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
