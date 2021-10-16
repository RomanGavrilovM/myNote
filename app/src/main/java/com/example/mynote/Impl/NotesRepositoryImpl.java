package com.example.mynote.Impl;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.example.mynote.domain.Note;
import com.example.mynote.domain.NotesRepository;

public class NotesRepositoryImpl implements NotesRepository {
    private final ArrayList<Note> cache = new ArrayList<>();
    private int counter = 0;

    @Override
    public List<Note> getNotes() {
        return new ArrayList<>(cache);
    }

    @Nullable
    @Override
    public Integer createNote(Note note) {
        int newId = ++counter;
        note.setId(newId);
        cache.add(note);
        return newId;
    }

    @Override
    public boolean deleteNote(int id) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == id) {
                cache.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateNote(int id, Note note) {
        deleteNote(id);
        note.setId(id);
        cache.add(id - 1, note);
        return true;
    }
}