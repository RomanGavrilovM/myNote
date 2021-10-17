package com.example.mynote.domain;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * CRUD - Create Read Update Delete
 */

public interface NotesRepository {

    List<Note> getNotes();

    @Nullable
    Integer createNote(Note note);

    boolean deleteNote(int id);

    boolean updateNote(int id, Note note);
}
