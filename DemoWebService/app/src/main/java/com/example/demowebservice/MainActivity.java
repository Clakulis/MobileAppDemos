package com.example.demowebservice;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        //Lấy nút và set onclick event cho nút
        Button fetchBtn = findViewById(R.id.fetchbtn);
        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi chạy AsyncTask để tải dữ liệu
                new FetchDataTask().execute("https://jsonplaceholder.typicode.com/todos/1");
            }
        });
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String resultJson = ""; // Chuỗi để lưu kết quả JSON

            try {
                URL url = new URL(urls[0]); // Tạo URL từ chuỗi đầu vào
                urlConnection = (HttpURLConnection) url.openConnection(); // Mở kết nối
                urlConnection.setRequestMethod("GET"); // Đặt phương thức là GET
                urlConnection.connect(); // Kết nối đến web service

                // Đọc dữ liệu từ luồng nhập
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                // Đọc từng dòng dữ liệu và thêm vào buffer
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Nếu không có dữ liệu, trả về null
                    return null;
                }

                resultJson = buffer.toString(); // Chuyển buffer thành chuỗi
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return resultJson; // Trả về chuỗi JSON
        }

        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result); // Cập nhật UI với kết quả JSON
        }
    }
}

