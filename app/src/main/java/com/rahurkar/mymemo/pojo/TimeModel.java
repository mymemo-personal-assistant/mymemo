package com.rahurkar.mymemo.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeModel implements Parcelable {
    int dayOfWeek;
    int startHour;
    int startMinutes;

    int endHour;
    int endMinutes;

    public TimeModel() {
    }


    protected TimeModel(Parcel in) {
        dayOfWeek = in.readInt();
        startHour = in.readInt();
        startMinutes = in.readInt();
        endHour = in.readInt();
        endMinutes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dayOfWeek);
        dest.writeInt(startHour);
        dest.writeInt(startMinutes);
        dest.writeInt(endHour);
        dest.writeInt(endMinutes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeModel> CREATOR = new Creator<TimeModel>() {
        @Override
        public TimeModel createFromParcel(Parcel in) {
            return new TimeModel(in);
        }

        @Override
        public TimeModel[] newArray(int size) {
            return new TimeModel[size];
        }
    };

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
    }

    @Override
    public String toString() {
        return "TimeModel{" +
                "dayOfWeek=" + dayOfWeek +
                ", startHour=" + startHour +
                ", startMinutes=" + startMinutes +
                ", endHour=" + endHour +
                ", endMinutes=" + endMinutes +
                '}';
    }

    public void setValueFromObject(TimeModel timeModel) {
        this.setDayOfWeek(timeModel.getDayOfWeek());
        this.setStartHour(timeModel.getStartHour());
        this.setStartMinutes(timeModel.getStartMinutes());
        this.setEndHour(timeModel.getEndHour());
        this.setEndMinutes(timeModel.getEndMinutes());
    }

}
