package vn.edu.csc.demozingmp3.zingmp3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vn.edu.csc.demozingmp3.R;

public class MainActivity extends AppCompatActivity {

    final String path = "/storage/emulated/0/Download/Con-Trai-Cung-K-ICM-B-Ray.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
            } else {
                startService();
            }
        } else {
            startService();
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();

                // cung cap action
                intent.setAction("phatnhac11");

                // cung cap du lieu
                intent.putExtra("path111", path);

                // broadcast 1 thong tin
                sendBroadcast(intent);
            }
        });
    }

    private void startService() {
        // thuc thi  service
        startService(new Intent(this, MyService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Xin quyen doc file thanh cong!", Toast.LENGTH_LONG).show();
            startService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
