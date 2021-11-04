package com.example.mynote.ui.pages.List;

import android.view.View;

import com.example.mynote.model.entities.NoteEntity;

public interface OnItemClickListener {

    void onItemClick(NoteEntity item, int position);
    void onItemLongClick(NoteEntity item, View itemView, int position);
}