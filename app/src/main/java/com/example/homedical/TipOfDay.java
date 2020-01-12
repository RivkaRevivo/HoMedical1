package com.example.homedical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TipOfDay extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_of_day);
        textView = findViewById(R.id.textView13);
        String massage = getIntent().getStringExtra("massage");
        textView.setText(massage);

    }
}