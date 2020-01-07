package com.example.homedical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class GetDesc extends Activity {

    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_desc);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int hight = dm.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(hight *.5));


        desc = (TextView) findViewById(R.id.textView6);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String name = b.getString("desc");
        desc.setText(name);
    }
}
