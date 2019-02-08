package com.rahurkar.mymemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahurkar.mymemo.R;
import com.rahurkar.mymemo.pojo.TimeModel;
import com.rahurkar.mymemo.util.Util;

import java.util.List;

public class TimingsDetailsAdapter extends RecyclerView.Adapter<TimingsDetailsAdapter.TimingsDetailsViewHolder> {
    private static final String TAG = TimingsDetailsAdapter.class.getSimpleName();
    List<TimeModel> mTimeModelList;
    Context mContext;

    public TimingsDetailsAdapter(List<TimeModel> timeModelList, Context context) {
        this.mTimeModelList = timeModelList;
        mContext = context;
    }

    @Override
    public TimingsDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timings_info_row, parent, false);
        Log.d(TAG, "onCreateViewHolder: " + mTimeModelList.size());
        return new TimingsDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimingsDetailsViewHolder holder, int position) {
        TimeModel timeModel;

        timeModel = mTimeModelList.get(position);
        holder.weekTextView.setVisibility(View.VISIBLE);
        String time = Util.getWeek(timeModel.getDayOfWeek(), mContext);
        holder.weekTextView.setText(time);
        holder.dayTextView.setVisibility(View.VISIBLE);
        String day =  Util.getDisplayTime(timeModel.getStartHour()) + ":" + Util.getDisplayTime(timeModel.getStartMinutes()) + "  -  " + Util.getDisplayTime(timeModel.getEndHour()) + ":" + Util.getDisplayTime(timeModel.getEndMinutes());
        holder.dayTextView.setText(day);
    }

    @Override
    public int getItemCount() {
        return mTimeModelList.size();
    }

    class TimingsDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView weekTextView;
        TextView dayTextView;

        public TimingsDetailsViewHolder(View itemView) {
            super(itemView);
            weekTextView = itemView.findViewById(R.id.week_row_text_view);
            dayTextView = itemView.findViewById(R.id.time_row_text_view);

        }

    }
}
