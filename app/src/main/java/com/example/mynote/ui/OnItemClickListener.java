package com.example.mynote.ui;

import android.view.View;

import com.example.mynote.model.entities.Note;

public interface OnItemClickListener {

    void onItemClick(Note item, int position);
    void onItemLongClick(Note item, View itemView, int position);
}