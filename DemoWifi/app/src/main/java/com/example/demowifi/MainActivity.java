package com.example.demowifi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WIFI = 1;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView networkStatusTextView = findViewById(R.id.networkStatusTextView);

        //Tạo connectivity manager và quản lý kết nối mạng đang hoạt động
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                networkStatusTextView.setText((String) "Connected");
            } else {
                networkStatusTextView.setText((String) "Not Connected");
            }

        } else {
            networkStatusTextView.setText((String) "Not Connected");
        }

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        // Kiểm tra nếu Wi-Fi bị vô hiệu hóa, thông báo cho người dùng
        if (!wifi.isWifiEnabled()) {
            Toast.makeText(this, "Wi-Fi is disabled. Enabling...", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }

        //Kiểm tra quyền truy cập của ứng dụng
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_WIFI);
            return;
        }
        // Lấy danh sách các mạng Wi-Fi đã cấu hình
        List<WifiConfiguration> configurations = wifi.getConfiguredNetworks();

        // Kích hoạt mạng Wi-Fi đầu tiên trong danh sách (nếu có)
        if (configurations != null && configurations.size() > 0) {
            int netID = configurations.get(0).networkId;
            boolean disableAllOthers = true;
            wifi.enableNetwork(netID, disableAllOthers);
            Toast.makeText(this, "Switched to first configured network", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No configured networks found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_WIFI) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
            } else {
                // Permission was denied.
                Toast.makeText(this, "Permission denied by user", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
