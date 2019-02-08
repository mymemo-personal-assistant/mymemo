package com.rahurkar.mymemo.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahurkar.mymemo.EnterTimingDialogFragment;
import com.rahurkar.mymemo.MainActivity;
import com.rahurkar.mymemo.R;
import com.rahurkar.mymemo.pojo.ActivityModel;
import com.rahurkar.mymemo.pojo.TimeModel;
import com.rahurkar.mymemo.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ClassDetailsAdapter extends RecyclerView.Adapter<ClassDetailsAdapter.ClassDetailsViewHolder> implements Util.OnDialogComplete {
    private static final String TAG = ClassDetailsAdapter.class.getSimpleName();
    List<ActivityModel> mActivityModelList;
    MainActivity mMainActivity;

    public ClassDetailsAdapter(List<ActivityModel> activityModelList, MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
        mActivityModelList = activityModelList;
        setHasStableIds(true);

    }

    @Override
    public ClassDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_info_row, parent, false);
        Log.d(TAG, "onCreateViewHolder: " + mActivityModelList.size());
        return new ClassDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClassDetailsViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        holder.timingsRecyclerView.setLayoutManager(new LinearLayoutManager(mMainActivity));
        holder.timingsRecyclerView.setHasFixedSize(true);

//        holder.activityModel = mActivityModelList.get(position);
        final ActivityModel activityModel1 = mActivityModelList.get(position);

        Log.d(TAG, "onBindViewHolder: Position : " + position + " ActivityModel : " + activityModel1);
        if(!activityModel1.isComplete()) {
            holder.addTimingImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EnterTimingDialogFragment enterTimingDialogFragment = EnterTimingDialogFragment.newInstance(ClassDetailsAdapter.this, position);
                    enterTimingDialogFragment.show(mMainActivity.getSupportFragmentManager(), "YO");
                }
            });

            holder.isClassCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (!isChecked) {
                        holder.creditEditText.setVisibility(View.GONE);
                    } else {
                        holder.creditEditText.setVisibility(View.VISIBLE);
                    }

                }
            });
            if (activityModel1.getTimeModelList() != null && activityModel1.getTimeModelList().size() > 0) {
                Log.d(TAG, "onBindViewHolder: Setting new times");
                TimingsDetailsAdapter timingsDetailsAdapter  = new TimingsDetailsAdapter(activityModel1.getTimeModelList(), mMainActivity);
                holder.timingsRecyclerView.setAdapter(timingsDetailsAdapter);
            }

            holder.doneImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO When you click on add timings you first create a dummy timing and then send to the adapter

                    if (TextUtils.isEmpty(holder.classNameEditText.getText().toString())) {
                        String message = mMainActivity.getResources().getString(R.string.enter_valid_string) + " activity.";
                        Toast.makeText(mMainActivity, message, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (activityModel1.getTimeModelList() == null || activityModel1.getTimeModelList().size() == 0) {
                        String message = mMainActivity.getResources().getString(R.string.enter_valid_string) + " timing.";
                        Toast.makeText(mMainActivity, message, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (holder.isClassCheckBox.isChecked()) {
                        if (TextUtils.isEmpty(holder.creditEditText.getText().toString()) || !TextUtils.isDigitsOnly(holder.creditEditText.getText().toString())) {
                            String message = mMainActivity.getResources().getString(R.string.enter_valid_string) + " credit hour";
                            Toast.makeText(mMainActivity, message, Toast.LENGTH_LONG).show();
                            return;
                        }
                        String creditString = holder.creditEditText.getText().toString();
                        activityModel1.setStudyClass(true);
                        activityModel1.setCredits(Integer.valueOf(creditString));
                        holder.creditEditText.setVisibility(View.GONE);
                        holder.creditTextView.setVisibility(View.VISIBLE);
                        holder.creditTextView.setText("Credits : " + creditString);
                    } else {
                        activityModel1.setStudyClass(false);
                    }

                    activityModel1.setComplete(true);
                    String name = holder.classNameEditText.getText().toString();
                    activityModel1.setName(name);


                    holder.classNameEditText.setVisibility(View.GONE);
                    holder.classNameTextView.setVisibility(View.VISIBLE);
                    holder.classNameTextView.setText(name);
                    holder.isClassCheckBox.setVisibility(View.GONE);

                    holder.addTimingImageView.setVisibility(View.GONE);
                    holder.doneImageView.setVisibility(View.GONE);
                    ClassDetailsAdapter.this.notifyDataSetChanged();

                }
            });

        } else {
            holder.doneImageView.setVisibility(View.GONE);
            holder.classNameEditText.setVisibility(View.GONE);
            holder.isClassCheckBox.setVisibility(View.GONE);
            holder.creditEditText.setVisibility(View.GONE);
            holder.addTimingImageView.setVisibility(View.GONE);

//            ActivityModel activityModel = holder.activityModel;
            if (activityModel1.isStudyClass()) {
                holder.creditTextView.setVisibility(View.VISIBLE);
                holder.creditTextView.setText("Credits : " + activityModel1.getCredits());
            }
            holder.classNameTextView.setVisibility(View.VISIBLE);
            holder.classNameTextView.setText(activityModel1.getName());
            TimingsDetailsAdapter timingsDetailsAdapter  = new TimingsDetailsAdapter(activityModel1.getTimeModelList(), mMainActivity);
            holder.timingsRecyclerView.setAdapter(timingsDetailsAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return mActivityModelList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onDialogComplete(List<TimeModel> timeModelList, int mPosition) {
        Log.d(TAG, "onDialogComplete: " + timeModelList);
        ActivityModel activityModel = mActivityModelList.get(mPosition);
        activityModel.setTimeModelList(timeModelList);

        this.notifyDataSetChanged();
    }

    class ClassDetailsViewHolder extends RecyclerView.ViewHolder{
        EditText classNameEditText;
        CheckBox isClassCheckBox;
        EditText creditEditText;
        TextView classNameTextView;
        RecyclerView timingsRecyclerView;
        ImageView doneImageView;
        TextView creditTextView;
        ImageView addTimingImageView;

        public ClassDetailsViewHolder(View itemView) {
            super(itemView);
            isClassCheckBox = itemView.findViewById(R.id.type_checkbox);
            creditTextView = itemView.findViewById(R.id.credit_text_view);
            creditEditText = itemView.findViewById(R.id.credit_edit_text);
            classNameEditText = itemView.findViewById(R.id.class_name_edit_text);
            classNameTextView = itemView.findViewById(R.id.class_name_text_view);
            timingsRecyclerView = itemView.findViewById(R.id.timings_recyclerView);
            doneImageView = itemView.findViewById(R.id.done_image_view);
            addTimingImageView = itemView.findViewById(R.id.add_time_image);
        }

    }
}
