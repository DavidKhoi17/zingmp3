package vn.edu.csc.demozingmp3;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    BroadcastReceiver broadcastReceiver;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void dem(final int num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = num;
                while (count >= 0) {
                    count--;
                    Log.e("TQKy", String.valueOf(count));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {// loai thuc thi 1 service

        // khoi tao noi nhan du lieu
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // noi doc so tu MainActivity gui xuong
                int num = intent.getIntExtra("so", 0);
                dem(num);
            }
        };

        // khoi tac doi tuong chua cac Action
        IntentFilter intentFilter = new IntentFilter("gui_so_dem");

        // dang ky nhan
        registerReceiver(broadcastReceiver, intentFilter);

        //dem();
//        return START_CONTINUATION_MASK;
        return START_STICKY;
//        return START_NOT_STICKY;
//        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        // huy dang ky nhan
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
