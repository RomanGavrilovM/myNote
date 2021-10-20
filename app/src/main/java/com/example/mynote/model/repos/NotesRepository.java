package com.example.mynote.model.repos;

import androidx.annotation.Nullable;

import java.util.List;

import com.example.mynote.model.entities.Note;

/**
 * CRUD - Create Read Update Delete
 */

public interface NotesRepository {

    List<Note> getNotes();

    @Nullable
    String createNote(Note note);

    boolean deleteNote(String uid);

    boolean updateNote(String uid, Note note);
}