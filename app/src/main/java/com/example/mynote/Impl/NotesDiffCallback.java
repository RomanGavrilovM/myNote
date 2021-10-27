package com.example.mynote.Impl;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

import com.example.mynote.model.entities.NoteEntity;

public class NotesDiffCallback extends DiffUtil.Callback {

    private final List<NoteEntity> oldList;
    private final List<NoteEntity> newList;

    public NotesDiffCallback(List<NoteEntity> oldList, List<NoteEntity> newList) {
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
