package com.rahurkar.mymemo.util;

import android.content.Context;

import com.rahurkar.mymemo.R;
import com.rahurkar.mymemo.pojo.TimeModel;

import java.util.List;
import java.util.Random;

public class Util {

    public static String getWeek(int weekNo, Context context) {
        String[] day_array = context.getResources().getStringArray(R.array.days);
        return day_array[weekNo];
    }

    public static String getDisplayTime(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);
    }

    public interface OnDialogComplete{
        void onDialogComplete(List<TimeModel> timeModelList, int mPosition);
    }

    public static String generateRandomChars(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }
}
