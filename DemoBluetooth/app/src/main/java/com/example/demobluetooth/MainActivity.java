package com.example.demobluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_BLUETOOTH = 100;
    //BroadcastReceiver nhận tín hiệu nếu tìm được một thiết bị Bluetooth
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            TextView textView = findViewById(R.id.text_view);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Xử lý thiết bị được tìm thấy. In địa chỉ của thiết bị ra màn hình
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceAddress = device.getAddress();
                String content = textView.getText() + "\n" + deviceAddress;
                textView.setText(content);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Đăng ký BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        //Kiểm tra quyền scan bluetooth của ứng dụng. Nếu chưa có thì request người dùng
        if (bluetooth.isEnabled()){
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.BLUETOOTH_SCAN)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BLUETOOTH_SCAN},
                        MY_PERMISSIONS_REQUEST_BLUETOOTH);
            } else {
                Toast.makeText(this, "Start Discovery", Toast.LENGTH_SHORT).show();
                bluetooth.startDiscovery();
            }
        }
    }

    //Hàm xử lý phản hồi của người dùng với request
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_BLUETOOTH) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
            } else {
                // Permission was denied.
                Toast.makeText(this, "Permission denied by user", Toast.LENGTH_SHORT).show();
            }
        }
    }
}