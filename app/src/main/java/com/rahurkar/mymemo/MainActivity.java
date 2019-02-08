package com.rahurkar.mymemo;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.rahurkar.mymemo.adapter.ClassDetailsAdapter;
import com.rahurkar.mymemo.pojo.ActivityModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MODEL_LIST = "model_list";
    public RecyclerView mClassRecyclerView;
    ArrayList<ActivityModel> mActivityModelList;
    ClassDetailsAdapter mClassDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView addClassImage = findViewById(R.id.add_class_image);
        mClassRecyclerView = findViewById(R.id.class_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mClassRecyclerView.setLayoutManager(layoutManager);

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                intent.putParcelableArrayListExtra(MODEL_LIST, mActivityModelList);
                MainActivity.this.startActivity(intent);
            }
        });

        addClassImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "MainActivity onClick: ");
                if (mActivityModelList == null || mActivityModelList.size() == 0) {
                    mActivityModelList = new ArrayList<>();
                    mActivityModelList.add(new ActivityModel());
                    mClassDetailsAdapter = new ClassDetailsAdapter(mActivityModelList, MainActivity.this);
                    mClassRecyclerView.setAdapter(mClassDetailsAdapter);
                    mClassRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mActivityModelList.add(new ActivityModel());
                    mClassDetailsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}

