package com.example.mynote.Impl;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

import com.example.mynote.model.entities.Note;

public class NotesDiffCallback extends DiffUtil.Callback {

    private final List<Note> oldList;
    private final List<Note> newList;

    public NotesDiffCallback(List<Note> oldList, List<Note> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition).getUid(), newList.get(newItemPosition).getUid());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition).getTitle(), newList.get(newItemPosition).getTitle())
                && Objects.equals(oldList.get(oldItemPosition).getDetail(), newList.get(newItemPosition).getDetail())
                && Objects.equals(oldList.get(oldItemPosition).getCreationDate(), newList.get(newItemPosition).getCreationDate());
    }
}
