package com.rahurkar.mymemo;

import android.content.Context;

public class Util {

    public static String getWeek(int weekNo, Context context) {
        String[] day_array = context.getResources().getStringArray(R.array.days);
        return day_array[weekNo];
    }

}
