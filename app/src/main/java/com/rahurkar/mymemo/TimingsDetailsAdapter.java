package com.rahurkar.mymemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TimingsDetailsAdapter extends RecyclerView.Adapter<TimingsDetailsAdapter.TimingsDetailsViewHolder> {
    private static final String TAG = TimingsDetailsAdapter.class.getSimpleName();
    List<TimingsDetails> mTimingsDetailsList;
    Context mContext;

    public TimingsDetailsAdapter(List<TimingsDetails> timingsDetailsList, Context context) {
        this.mTimingsDetailsList = timingsDetailsList;
        mContext = context;
    }

    @Override
    public TimingsDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timings_info_row, parent, false);
        Log.d(TAG, "onCreateViewHolder: " + mTimingsDetailsList.size());
        return new TimingsDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimingsDetailsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        TimingsDetails timingsDetails;
        if (mTimingsDetailsList.size() == 0 || mTimingsDetailsList.get(position).fromHour == 0) {
            holder.classTimeTextView.setVisibility(View.GONE);
        } else {
            timingsDetails  = mTimingsDetailsList.get(position);
            Log.d(TAG, "onBindViewHolder: " + timingsDetails);
            holder.addTimeImageView.setVisibility(View.GONE);
            holder.classTimeTextView.setVisibility(View.VISIBLE);
            String time = Util.getWeek(timingsDetails.week, mContext) + " " + timingsDetails.fromHour + ":" + timingsDetails.fromMinutes + " - " + timingsDetails.endHour + ":" + timingsDetails.endMinutes;
            holder.classTimeTextView.setText(time);
        }

    }

    @Override
    public int getItemCount() {
        return mTimingsDetailsList.size();
    }

    class TimingsDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView classTimeTextView;
        ImageView addTimeImageView;

        public TimingsDetailsViewHolder(View itemView) {
            super(itemView);
            classTimeTextView = itemView.findViewById(R.id.timings_row_text_view);
            addTimeImageView = itemView.findViewById(R.id.add_time_image);
            addTimeImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == addTimeImageView.getId()){
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
