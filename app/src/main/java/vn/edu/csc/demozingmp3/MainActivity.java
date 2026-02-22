package vn.edu.csc.demozingmp3;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    Button buttonStartService, buttonStopService, buttonSendBroadcast,
            buttonShowNotification, buttonShowCustomNotification;
    EditText editText;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        buttonStartService = findViewById(R.id.buttonStartService);
        buttonStopService = findViewById(R.id.buttonStopService);
        buttonShowNotification = findViewById(R.id.buttonShowNotification);
        buttonShowCustomNotification = findViewById(R.id.buttonShowCustomNotification);
        buttonSendBroadcast = findViewById(R.id.buttonSend);
        editText = findViewById(R.id.editText);

        buttonStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // khoi intent
                intent = new Intent(getApplication(), MyService.class);

                // them du lieu
                intent.putExtra("fileName", "baihat1.mp3");

                // khoi chay service
                startService(intent);
            }
        });
        buttonStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent != null) {
                    stopService(intent);
                }
            }
        });

        buttonSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                // cung cap action
                intent.setAction("gui_so_dem");

                // cung cap du lieu gui di
                int num = Integer.parseInt(editText.getText().toString());
                intent.putExtra("so", num);

                // tung goi tin cho he thong
                sendBroadcast(intent);
            }
        });

        buttonShowNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // khoi tao 1 notification
                Notification.Builder builder = new Notification.Builder(getApplicationContext());

                // cung cap small icon
                builder.setSmallIcon(R.mipmap.ic_launcher);

                // cung cap tieu de
                builder.setContentTitle("Demo Notification");

                // cung cap noi dung
                builder.setContentText("Hello");

                // get doi tuong gui thong tin cho Notification Service
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                // thuc hien hien thi thong tin vua thiet ke
                notificationManager.notify(111, builder.build());
            }
        });

        buttonShowCustomNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // khoi tao 1 notification
                Notification.Builder builder = new Notification.Builder(getApplicationContext());

                // cung cap small icon
                builder.setSmallIcon(R.mipmap.ic_launcher);

                // giu lai notification khong cho nguoi dung huy
                builder.setOngoing(true);

                // khoi tao doi tuong chua layout minh thiet ke
                RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_layout);

                // thiet lap hinh anh cho ImageView
                remoteView.setImageViewResource(R.id.imageView, R.mipmap.ic_launcher);

                // thiet lap vo cho 1 notification
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setCustomContentView(remoteView);
                } else {
                    builder.setContent(remoteView);
                }

                // get doi tuong gui thong tin cho Notification Service
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                // thuc hien hien thi thong tin vua thiet ke
                notificationManager.notify(111, builder.build());
            }
        });

    }
}
