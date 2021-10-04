package com.example.mynote.Implementation;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.example.mynote.domain.Note;
import com.example.mynote.domain.NotesRepository;

public class RepositoryImplementation implements NotesRepository{
    private ArrayList<Note> notesArr = new ArrayList<>();
    private int counter = 0;

    @Override
    public List<Note> getNotes() {
        return new ArrayList<>(notesArr);
    }

    @Nullable
    @Override
    public Integer createNote(Note note) {
        note.setId(++counter);
        notesArr.add(note);
        return (Integer) counter;
    }

    @Override
    public boolean deleteNote(Integer id) {
        for (int i = 0; i < notesArr.size(); i++) {
            if (id == notesArr.get(i).getId()) {
                notesArr.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateNote(Integer id, Note note) {
        deleteNote(id);
        note.setId(id);
        notesArr.add(id - 1, note);
        return true;
    }

}
