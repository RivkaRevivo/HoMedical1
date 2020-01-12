package com.example.homedical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class eyes extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    Button get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_eyes);
        get = findViewById(R.id.Button);
        new FirebaseDatabasehelper().getMedical(new FirebaseDatabasehelper.DataStatus() {
            @Override
            public void DataisLoaded(List<Medical> medicals, List<String> keys) {
                List<Medical>med = new ArrayList<>();
                for (Medical m : medicals){
                    if (m.getCategory() == 2){
                        med.add(m);
                    }
                }
                new RecyclerView_Config().setConfig(mRecyclerView, eyes.this, med,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }
}
