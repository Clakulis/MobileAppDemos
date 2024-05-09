package com.example.finalferushquan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mapSuggestedRoutesButton;
    ConstraintLayout mapSavedRoutesButton;
    TextView mapSuggestedRoutesButtonText;
    TextView mapSavedRoutesButtonText;

    HorizontalScrollView mapSuggestedRoutesLayout;
    HorizontalScrollView mapSavedRoutesLayout;
    TextView menuBarHomeButton;
    TextView menuBarRoutesButton;
    TextView menuBarMapButton;
    TextView menuBarSocialButton;
    TextView menuBarProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapSuggestedRoutesButton = findViewById(R.id.mapSuggestedRoutesButton);
        mapSavedRoutesButton = findViewById(R.id.mapSavedRoutesButton);
        mapSuggestedRoutesButtonText = findViewById(R.id.mapSuggestedRoutesButtonText);
        mapSavedRoutesButtonText = findViewById(R.id.mapSavedRoutesButtonText);
        mapSuggestedRoutesLayout = findViewById(R.id.mapSuggestedRoutesLayout);
        mapSavedRoutesLayout = findViewById(R.id.mapSavedRoutesLayout);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);

        mapSuggestedRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mapSuggestedRoutesLayout.setVisibility(View.VISIBLE);
                mapSavedRoutesButton.setVisibility(View.INVISIBLE);
            }
        });

        menuBarSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SocialActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


    }
}