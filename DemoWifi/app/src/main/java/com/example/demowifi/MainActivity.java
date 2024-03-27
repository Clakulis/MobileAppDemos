package com.example.demowifi;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager connectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView networkStatusTextView = findViewById(R.id.networkStatusTextView);

        //Tạo connectivity manager và kiểm tra tình trạng mạng
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
}
