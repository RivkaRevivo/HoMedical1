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
        textView = (TextView) findViewById(R.id.textView13);
//        Bundle b = new Bundle();
//        b = getIntent().getExtras();
//        String name = b.getString("tip");
        //textView.setText(MangerActivity.mes);
    }
}