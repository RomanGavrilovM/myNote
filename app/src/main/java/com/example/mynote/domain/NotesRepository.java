package com.example.mynote.domain;

import androidx.annotation.Nullable;

import java.util.List;

public interface NotesRepository {
    List<Note> getNotes();

    @Nullable
    Integer createNote(Note note);

    boolean updateNote(Integer id, Note note);

    boolean deleteNote(Integer id);


}
