package com.example.mynote.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Note implements Parcelable {

    @Nullable
    private Integer id;
    private String title;
    private String detail;
    private long creationDate;

    public Note(String title, String detail) {
        this.title = title;
        this.detail = detail;
        this.creationDate = Calendar.getInstance().getTimeInMillis();
    }

    protected Note(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        detail = in.readString();
        creationDate = in.readLong();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreationDate() {
        Date date = new Date(creationDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(title);
        parcel.writeString(detail);
        parcel.writeLong(creationDate);
    }

}