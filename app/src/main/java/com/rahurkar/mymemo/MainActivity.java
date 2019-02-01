package com.rahurkar.mymemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView mClassRecyclerView;
    List<ClassDetails> mClassDetailsList;
    ClassDetailsAdapter mClassDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView addClassImage = findViewById(R.id.add_class_image);
        mClassRecyclerView = findViewById(R.id.class_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mClassRecyclerView.setLayoutManager(layoutManager);

        addClassImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClassDetailsList == null || mClassDetailsList.size() == 0) {
                    mClassDetailsList = initializeClassDetails();
                    mClassDetailsList.add(new ClassDetails());
                    mClassDetailsAdapter = new ClassDetailsAdapter(mClassDetailsList, MainActivity.this);
                    mClassRecyclerView.setAdapter(mClassDetailsAdapter);
                    mClassRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mClassDetailsList.add(new ClassDetails());
                    mClassDetailsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private List<ClassDetails> initializeClassDetails() {
        List<ClassDetails> classDetailsList = new ArrayList<>();
        return classDetailsList;
    }
}

