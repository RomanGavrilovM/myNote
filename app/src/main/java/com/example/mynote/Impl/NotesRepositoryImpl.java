package com.example.mynote.Impl;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.example.mynote.model.entities.Note;
import com.example.mynote.model.repos.NotesRepository;
public class NotesRepositoryImpl implements NotesRepository {
    private final ArrayList<Note> cache = new ArrayList<>();

    @Override
    public List<Note> getNotes() {
        return new ArrayList<>(cache);
    }

    @Nullable
    @Override
    public String createNote(Note note) {
        String newId = UUID.randomUUID().toString();
        note.setUid(newId);
        cache.add(note);
        return newId;
    }

    @Override
    public boolean deleteNote(String uid){
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getUid().equals(uid)) {
                cache.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateNote(String uid, Note note) {
        deleteNote(uid);
        note.setUid(uid);
        cache.add(note);
        return true;
    }
}