package com.example.mynote.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Note implements Parcelable {
    @Nullable
    private Integer id;
    private String head;
    private String description;
    private String date;

    public Note() {
    }

    public Note(String head, String description, String date) {
        this.head = head;
        this.description = description;
        this.date = date;
    }

    protected Note(Parcel in) {
        id = getOptIn(in);
        head = in.readString();
        description = in.readString();
        date = in.readString();
    }

    private Integer getOptIn(Parcel in) {
        if (in.readByte() == 0) {
            return null;
        } else {
            return in.readInt();
        }
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        putOptInt(dest, id);
        dest.writeString(head);
        dest.writeString(description);
        dest.writeString(date);
    }

    private void putOptInt(Parcel dest, Integer id) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
    }
}