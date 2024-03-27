package com.example.demosensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        // Lấy SensorManager để tạo và quản lý đối tượng Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Kiểm tra xem đối tượng quản Manager có tồn tại không
        if (sensorManager != null) {
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            // Kiểm tra xem đối tượng Sensor có tồn tại không
            if (magnetometer != null) {
                sensorManager.registerListener((SensorEventListener) this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(this, "Magnetometer khả dụng!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Magnetometer không khả dụng.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "SensorManager không khả dụng.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Xử lý các thay đổi về độ chính xác
        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            switch (accuracy) {
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    Toast.makeText(this, "Độ chính xác của magnetometer thấp.", Toast.LENGTH_SHORT).show();
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    // Có thể thông báo cho người dùng rằng độ chính xác đang ổn nhưng không tốt nhất
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    // Không cần hành động nào, cảm biến đang hoạt động tốt
                    break;
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    Toast.makeText(this, "Dữ liệu của magnetometer không đáng tin cậy.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Xử lý dữ liệu cảm biến ở đây
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        String magnetometerData = "X: " + x + "\nY: " + y + "\nZ: " + z;
        textView.setText(magnetometerData);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}