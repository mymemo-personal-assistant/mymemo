package com.rahurkar.mymemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rahurkar.mymemo.adapter.ClassDetailsAdapter;
import com.rahurkar.mymemo.pojo.ActivityModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MODEL_LIST = "model_list";
    public RecyclerView mClassRecyclerView;
    ArrayList<ActivityModel> mActivityModelList;
    ClassDetailsAdapter mClassDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView addClassImage = findViewById(R.id.add_class_image);
        mClassRecyclerView = findViewById(R.id.class_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mClassRecyclerView.setLayoutManager(layoutManager);

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                if( mActivityModelList == null || mActivityModelList.size() == 0)
                {
                    Toast.makeText(MainActivity.this, "No data entered for the timetable", Toast.LENGTH_SHORT).show();
                    Log.e("No_data", ""+mActivityModelList);
                    Log.e("new", "new");
                }
                else {
                    ArrayList<ActivityModel> activityModelValid = isActivityModelValid(mActivityModelList);
                    Log.d(TAG, "onClick: " + activityModelValid.size());
                    Log.d(TAG, "onClick: " + mActivityModelList.size());

                    if (activityModelValid.size() == 0) {
                        Toast.makeText(MainActivity.this, R.string.add_valid_data, Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (activityModelValid.size() != mActivityModelList.size()) {
                        Toast.makeText(MainActivity.this, R.string.change_in_model, Toast.LENGTH_LONG).show();
                    }

                    mActivityModelList = activityModelValid;
                    intent.putParcelableArrayListExtra(MODEL_LIST, mActivityModelList);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        addClassImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "MainActivity onClick: ");
                if (mActivityModelList == null || mActivityModelList.size() == 0)
                {
                    mActivityModelList = new ArrayList<>();

                    mActivityModelList.add(new ActivityModel());
                    mClassDetailsAdapter = new ClassDetailsAdapter(mActivityModelList, MainActivity.this);
                    mClassRecyclerView.setAdapter(mClassDetailsAdapter);
                    mClassRecyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    mActivityModelList.add(new ActivityModel());
                    mClassDetailsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private ArrayList<ActivityModel> isActivityModelValid(ArrayList<ActivityModel> mActivityModelList) {
        ArrayList<ActivityModel> finalList = new ArrayList<>();
        for (ActivityModel am : mActivityModelList) {
            if (!TextUtils.isEmpty(am.getName()) && am.getTimeModelList() != null && am.getTimeModelList().size() > 0) {
                finalList.add(am);
            }
        }
        return finalList;
    }


}

