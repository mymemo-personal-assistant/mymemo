package com.rahurkar.mymemo.pojo;

public class Cell {
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "data='" + data + '\'' +
                '}';
    }
}
