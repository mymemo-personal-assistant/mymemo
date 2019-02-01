package com.rahurkar.mymemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TableActivity extends AppCompatActivity {
    private static final String TAG = TableActivity.class.getSimpleName();
    private List<String> mRowHeaderList;
    private List<String> mColumnHeaderList;
    private List<List<Cell>> mCellList;
    TableView mTableView;
    TableViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        mTableView = findViewById(R.id.content_container);

        mColumnHeaderList = Arrays.asList(getResources().getStringArray(R.array.days));
        mRowHeaderList = generateTimeRowHeader(mRowHeaderList);
        mCellList = initializeCells(mCellList);
        mAdapter = new TableViewAdapter(this);
        // Set this adapter to the our TableView
        mTableView.setAdapter(mAdapter);
        mTableView.setTableViewListener(new MyTableViewListener());
        mAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
        boolean enabled = mTableView.isEnabled();
        Log.d(TAG, "onCreate: " + enabled);
        // Let's set datas of the TableView on the Adapter

    }

    private List<List<Cell>> initializeCells(List<List<Cell>> mCellList) {
        mCellList = new ArrayList<>();
        for(int i = 0; i < 17; i ++) {
            List<Cell> cellList = new ArrayList<>();
            for(int j = 0; j < 7; j++) {
                Cell cell = new Cell();

                cell.setData("");
                cellList.add(cell);
            }
            mCellList.add(cellList);
        }
        mCellList.get(3).get(0).setData("CS535");
        mCellList.get(4).get(0).setData("CS535");
        mCellList.get(3).get(2).setData("CS535");
        mCellList.get(4).get(2).setData("CS535");

        mCellList.get(3).get(1).setData("CS537");
        mCellList.get(4).get(1).setData("CS537");

        mCellList.get(10).get(0).setData("CS562");
        mCellList.get(11).get(0).setData("CS562");
        mCellList.get(10).get(2).setData("CS562");
        mCellList.get(11).get(2).setData("CS562");


        return mCellList;
    }

    private List<String> generateTimeRowHeader(List<String> mRowHeaderList) {
        mRowHeaderList = new ArrayList<>();
        for(int i = 7; i < 24; i ++) {
            mRowHeaderList.add(Integer.toString(i) + ":00");
//            mRowHeaderList.add(Integer.toString(i) + ":30");
        }
        Log.d(TAG, "generateTimeRowHeader: " + mRowHeaderList.size());
        return mRowHeaderList;
    }
}
