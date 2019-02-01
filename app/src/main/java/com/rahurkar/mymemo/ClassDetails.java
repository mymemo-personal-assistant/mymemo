package com.rahurkar.mymemo;

import java.util.List;

public class ClassDetails {
    String className;
//    int credits;
    List<TimingsDetails> timings;

    public ClassDetails() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<TimingsDetails> getTimings() {
        return timings;
    }

    public void setTimings(List<TimingsDetails> timings) {
        this.timings = timings;
    }

    @Override
    public String toString() {
        return "ClassDetails{" +
                "className='" + className + '\'' +
                ", timings=" + timings +
                '}';
    }
}
