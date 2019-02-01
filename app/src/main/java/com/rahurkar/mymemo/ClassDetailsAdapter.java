package com.rahurkar.mymemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClassDetailsAdapter extends RecyclerView.Adapter<ClassDetailsAdapter.ClassDetailsViewHolder> {
    private static final String TAG = ClassDetailsAdapter.class.getSimpleName();
    List<ClassDetails> mClassDetailsList;
    Context mContext;

    public ClassDetailsAdapter(List<ClassDetails> classDetailsList, MainActivity mainActivity) {
        this.mClassDetailsList = classDetailsList;
        this.mContext = mainActivity;
    }

    @Override
    public ClassDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_info_row, parent, false);
        Log.d(TAG, "onCreateViewHolder: " + mClassDetailsList.size());
        return new ClassDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClassDetailsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        final List<TimingsDetails> timingsDetailsList;
        holder.timingsRecyclerView.setVisibility(View.VISIBLE);
        holder.timingsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if(mClassDetailsList.size() == 0 || TextUtils.isEmpty(mClassDetailsList.get(position).className)) {
            timingsDetailsList = new ArrayList<>();
            final TimingsDetails timingsDetails = new TimingsDetails();
            timingsDetailsList.add(timingsDetails);
            final TimingsDetails timingsDetails1 = new TimingsDetails();

            final ClassDetails classDetails = mClassDetailsList.get(position);
            final TimingsDetailsAdapter timingsDetailsAdapter  = new TimingsDetailsAdapter(timingsDetailsList, mContext);
            holder.doneImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO When you click on add timings you first create a dummy timing and then send to the adapter
                    String name = holder.classNameEditText.getText().toString();
                    classDetails.setClassName(name);
                    holder.classNameEditText.setVisibility(View.GONE);
                    holder.classNameTextView.setVisibility(View.VISIBLE);
                    holder.classNameTextView.setText(name);
                    timingsDetails.week = 1;
                    timingsDetails.fromHour = 10;
                    timingsDetails.fromMinutes = 00;
                    timingsDetails.endHour = 12;
                    timingsDetails.endMinutes = 00;

//                    final TimingsDetails timingsDetails1 = new TimingsDetails();
//                    timingsDetails1.week = 1;
//                    timingsDetails1.fromHour = 10;
//                    timingsDetails1.fromMinutes = 00;
//                    timingsDetails1.endHour = 12;
//                    timingsDetails1.endMinutes = 00;
//                    timingsDetailsList.add(timingsDetails1);

                    timingsDetailsList.add(timingsDetails);
                    classDetails.setTimings(timingsDetailsList);

                    holder.doneImageView.setVisibility(View.GONE);
                    timingsDetailsAdapter.notifyDataSetChanged();
                    ClassDetailsAdapter.this.notifyDataSetChanged();

                }
            });
            holder.timingsRecyclerView.setAdapter(timingsDetailsAdapter);

        } else {
            holder.doneImageView.setVisibility(View.GONE);
            holder.classNameEditText.setVisibility(View.GONE);
            holder.classNameTextView.setText(mClassDetailsList.get(position).className);
            timingsDetailsList = mClassDetailsList.get(position).getTimings();
            final TimingsDetailsAdapter timingsDetailsAdapter  = new TimingsDetailsAdapter(timingsDetailsList, mContext);
            holder.timingsRecyclerView.setAdapter(timingsDetailsAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return mClassDetailsList.size();
    }

    class ClassDetailsViewHolder extends RecyclerView.ViewHolder{
        EditText classNameEditText;
        TextView classNameTextView;
        RecyclerView timingsRecyclerView;
        ImageView doneImageView;

        public ClassDetailsViewHolder(View itemView) {
            super(itemView);
            classNameEditText = itemView.findViewById(R.id.class_name_edit_text);
            classNameTextView = itemView.findViewById(R.id.class_name_text_view);
            timingsRecyclerView = itemView.findViewById(R.id.timings_recyclerView);
            doneImageView = itemView.findViewById(R.id.done_image_view);
        }

    }
}
