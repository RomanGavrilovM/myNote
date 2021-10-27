package com.example.mynote.model.repos;

import androidx.annotation.Nullable;

import java.util.List;

import com.example.mynote.model.entities.NoteEntity;

/**
 * CRUD - Create Read Update Delete
 */

public interface NotesRepository {

    List<NoteEntity> getNotes();

    @Nullable
    String createNote(NoteEntity note);

    boolean deleteNote(String uid);

    boolean updateNote(String uid, NoteEntity note);
}