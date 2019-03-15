package com.rahurkar.mymemo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.evrencoskun.tableview.TableView;
import com.rahurkar.mymemo.adapter.TableViewAdapter;
import com.rahurkar.mymemo.pojo.ActivityModel;
import com.rahurkar.mymemo.pojo.Cell;
import com.rahurkar.mymemo.pojo.TimeModel;
import com.rahurkar.mymemo.util.MyTableViewListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TableActivity extends AppCompatActivity {
    private static final String TAG = TableActivity.class.getSimpleName();
    public static final int STUDY_HOUR_PER_CREDIT = 3;
    public static final int STUDY_HOURS_PER_DAY = 2;
    public static final int START_TIME_OF_DAY = 7;
    private List<String> mRowHeaderList;
    private List<String> mColumnHeaderList;
    private List<List<Cell>> mCellList;
    private List<List<Cell>> mCellList_2;
    TableView mTableView;
    TableViewAdapter mAdapter;
    ArrayList<ActivityModel> mActivityModelsList;
    boolean mIsTable1Selected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        mActivityModelsList = this.getIntent().getParcelableArrayListExtra(MainActivity.MODEL_LIST);
        Log.d(TAG, "onCreate: Models : " + mActivityModelsList);
        mTableView = findViewById(R.id.content_container);
        ActionBar supportActionBar = getSupportActionBar();

        mColumnHeaderList = Arrays.asList(getResources().getStringArray(R.array.days));
        mRowHeaderList = generateTimeRowHeader(mRowHeaderList);
        mCellList = generateCells(mCellList, 2);
        mCellList_2 = generateCells(mCellList_2, 4);

        mAdapter = new TableViewAdapter(this);
        // Set this adapter to the our TableView
        mTableView.setAdapter(mAdapter);
        mTableView.setTableViewListener(new MyTableViewListener());
        mAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
        boolean enabled = mTableView.isEnabled();
        Log.d(TAG, "onCreate: " + enabled);
        // Let's set datas of the TableView on the Adapter

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timetables, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.time1) {
            if (mIsTable1Selected) {
                return true;
            } else {
                mAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
                mAdapter.notifyDataSetChanged();
            }

            Log.d(TAG, "onOptionsItemSelected: 1");
            return true;
        }

        if (item.getItemId() == R.id.time2)
        {
            Log.d(TAG, "onOptionsItemSelected: 2");
            mIsTable1Selected = false;
            mAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList_2);
            mAdapter.notifyDataSetChanged();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private List<List<Cell>> generateCells(List<List<Cell>> mCellList, int studyHoursPerDay)
    {
        mCellList = initializeCells();
        setActivities(mCellList);
        setGeneralRoutine(mCellList);

        for (int k = 0 ; k < mActivityModelsList.size(); k++)
        {
            ActivityModel activityModel = mActivityModelsList.get(k);
            setSubjectsWithCredits(mCellList, activityModel, studyHoursPerDay);
        }

        setPostGenFreeTime(mCellList);

        return mCellList;
    }

    private void setSubjectsWithCredits(List<List<Cell>> mCellList, ActivityModel activityModel, int studyHoursPerDay)
    {
        int credits;
        if(activityModel.getCredits() >0)
        {   credits = activityModel.getCredits();  }
        else
            {
                credits = 4;
            }
        int totalStudyTIme = credits * STUDY_HOUR_PER_CREDIT;
        int count_per_day = 0;
        int total_count = 0;
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 17; i++) {
                Cell cell = mCellList.get(i).get(j);
                if (TextUtils.isEmpty(cell.getData())) {
                    cell.setData(activityModel.getName() + "-_Study");
                    count_per_day++;
                    total_count++;
                    if (count_per_day > studyHoursPerDay - 1) {
                        count_per_day = 0;
                        break;
                    }
                    if (total_count > totalStudyTIme - 1) {
                        break;
                    }
                }

            }
            if (total_count > totalStudyTIme - 1) {
                break;
            }
        }
    }

    private List<List<Cell>> initializeCells() {
        List<List<Cell>> mCellList;
        mCellList = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                Cell cell = new Cell();

                cell.setData("");
                cellList.add(cell);
            }
            mCellList.add(cellList);
        }
        return mCellList;
    }

    private void setActivities(List<List<Cell>> mCellList) {
        for (int i = 0; i < mActivityModelsList.size(); i++) {
            ActivityModel activityModel = mActivityModelsList.get(i);
            for (TimeModel timeModel : activityModel.getTimeModelList()) {
                int x = timeModel.getStartHour() - START_TIME_OF_DAY;
                int y = timeModel.getDayOfWeek();
                mCellList.get(x).get(y).setData(activityModel.getName());

                int x1 = timeModel.getEndHour() - START_TIME_OF_DAY;
                if (x1 != x) {
                    mCellList.get(x1).get(y).setData(activityModel.getName());
                }
            }
        }
    }

    private void setGeneralRoutine(List<List<Cell>> mCellList)
    {
        for (int k = 0; k < 7; k++)
        {
            mCellList.get(0).get(k).setData("Get_Ready&_Cook");
            mCellList.get(15).get(k).setData("Cook_food");
            mCellList.get(16).get(k).setData("Sleep");
            mCellList.get(5).get(k).setData("Lunch_Time");
            mCellList.get(13).get(k).setData("Dinner_Time");

        }
    }

    private void setPostGenFreeTime(List<List<Cell>> mCellList)
    {
        //for (int k = 0; k < 17; k++)
        //{
        //    if(TextUtils.isEmpty(mCellList.get(k).get(6).getData()))
        //        mCellList.get(k).get(6).setData("Enjoy today");
        //}
        for (int i=0 ; i< 7 ; i++)
        {
            for (int k = 0; k < 17; k++)
            {
                if (TextUtils.isEmpty(mCellList.get(k).get(i).getData()))
                    mCellList.get(k).get(i).setData("Read up");
            }
        }
    }

    private List<String> generateTimeRowHeader(List<String> mRowHeaderList) {
        mRowHeaderList = new ArrayList<>();
        for (int i = 7; i < 24; i++) {
            mRowHeaderList.add(Integer.toString(i) + ":00");
//            mRowHeaderList.add(Integer.toString(i) + ":30");
        }
        Log.d(TAG, "generateTimeRowHeader: " + mRowHeaderList.size());
        return mRowHeaderList;
    }

}
