package com.rahurkar.mymemo.pojo;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

public class ActivityModel implements Parcelable
{
    private static final String TAG = ActivityModel.class.getSimpleName();
    boolean isStudyClass;
    String name;
    int credits;
    List<TimeModel> timeModelList;
    boolean isComplete;

    public ActivityModel()
    {
        isStudyClass = true;
        isComplete = false;
    }


    protected ActivityModel(Parcel in) {
        isStudyClass = in.readByte() != 0;
        name = in.readString();
        credits = in.readInt();
        timeModelList = in.createTypedArrayList(TimeModel.CREATOR);
        isComplete = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeByte((byte) (isStudyClass ? 1 : 0));
        dest.writeString(name);
        dest.writeInt(credits);
        dest.writeTypedList(timeModelList);
        dest.writeByte((byte) (isComplete ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActivityModel> CREATOR = new Creator<ActivityModel>() {
        @Override
        public ActivityModel createFromParcel(Parcel in) {
            return new ActivityModel(in);
        }

        @Override
        public ActivityModel[] newArray(int size) {
            return new ActivityModel[size];
        }
    };

    public boolean isStudyClass() {
        return isStudyClass;
    }

    public void setStudyClass(boolean studyClass) {
        isStudyClass = studyClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<TimeModel> getTimeModelList() {
        return timeModelList;
    }

    public void setTimeModelList(List<TimeModel> timeModelList) {
        this.timeModelList = timeModelList;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }



    @Override
    public String toString() {
        return "ActivityModel{" +
                "isStudyClass=" + isStudyClass +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", timeModelList=" + timeModelList +
                ", isComplete=" + isComplete +
                '}';
    }

}
