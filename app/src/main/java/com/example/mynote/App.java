package com.example.mynote;

import android.app.Application;
import com.example.mynote.Impl.NotesRepositoryImpl;
import com.example.mynote.model.entities.NoteEntity;
import com.example.mynote.model.repos.NotesRepository;

public class App extends Application {
    private final NotesRepository notesRepository = new NotesRepositoryImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        fillRepositoryByTestValues();
    }
    public NotesRepository getNotesRepository() {
        return notesRepository;
    }

    private void fillRepositoryByTestValues() {


        notesRepository.createNote(new NoteEntity("Заметка № 2", "Пойти на встречу с портнерами"));
        notesRepository.createNote(new NoteEntity("Заметка № 3", "Сходить в отпуск"));
        notesRepository.createNote(new NoteEntity("Заметка № 4", "Сходить в отпуск"));
        notesRepository.createNote(new NoteEntity("Заметка № 5", "Прочести анализ рынка"));
        notesRepository.createNote(new NoteEntity("Заметка № 6", "Пойти выучить английский"));
        notesRepository.createNote(new NoteEntity("Заметка № 7", "Сходить в отпуск"));
        notesRepository.createNote(new NoteEntity("Заметка № 8", "Найти новых партнеров"));
        notesRepository.createNote(new NoteEntity("Заметка № 9", "Прочести анализ рынка"));
        notesRepository.createNote(new NoteEntity("Заметка № 10", "Отдать ожежду на хим чистку"));
        notesRepository.createNote(new NoteEntity("Заметка № 11", "Прочести анализ рынка"));
        notesRepository.createNote(new NoteEntity("Заметка № 12", "Найти новых партнеров"));
        notesRepository.createNote(new NoteEntity("Заметка № 13", "Пойти на встречу с портнерами"));
        notesRepository.createNote(new NoteEntity("Заметка № 14", "Найти новых партнеров"));
        notesRepository.createNote(new NoteEntity("Заметка № 15", "Пойти на встречу с портнерами"));
        notesRepository.createNote(new NoteEntity("Заметка № 16", "Отдать ожежду на хим чистку"));

    }
}

