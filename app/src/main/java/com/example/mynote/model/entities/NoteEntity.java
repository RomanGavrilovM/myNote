package com.example.mynote.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NoteEntity implements Parcelable{

    @Nullable
    private String uid;
    private String title;
    private String detail;
    private long creationDate;

    public NoteEntity(String title, String detail) {
        this.title = title;
        this.detail = detail;
        this.creationDate = Calendar.getInstance().getTimeInMillis();
    }

    protected NoteEntity(Parcel in) {
        uid = in.readString();
        title = in.readString();
        detail = in.readString();
        creationDate = in.readLong();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    @Nullable
    public String getUid() {
        return uid;
    }

    public void setUid(@Nullable String uid) {
        this.uid = uid;
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
        parcel.writeString(uid);
        parcel.writeString(title);
        parcel.writeString(detail);
        parcel.writeLong(creationDate);
    }
}