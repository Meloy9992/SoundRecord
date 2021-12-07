package com.example.mynotebook;

import android.os.Parcel;
import android.os.Parcelable;

public class RecordingTime implements Parcelable {

    private String recName;
    private String filePath;
    private int id;
    private int timeLength;
    private long dateTime;

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    protected RecordingTime(Parcel in) {
        recName = in.readString();
        filePath = in.readString();
        id = in.readInt();
        timeLength = in.readInt();
        dateTime = in.readLong();
    }

    public static final Creator<RecordingTime> CREATOR = new Creator<RecordingTime>() {
        @Override
        public RecordingTime createFromParcel(Parcel in) {
            return new RecordingTime(in);
        }

        @Override
        public RecordingTime[] newArray(int size) {
            return new RecordingTime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recName);
        dest.writeString(filePath);
        dest.writeInt(id);
        dest.writeInt(timeLength);
        dest.writeLong(dateTime);
    }
}
