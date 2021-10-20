package com.example.mynote;

import android.app.Application;
import com.example.mynote.Impl.NotesRepositoryImpl;
import com.example.mynote.model.entities.Note;
import com.example.mynote.model.repos.NotesRepository;

public class App extends Application {
    public final NotesRepository notesRepository = new NotesRepositoryImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        fillRepositoryByTestValues();
    }

    private void fillRepositoryByTestValues() {


        notesRepository.createNote(new Note("Заметка № 2", "Пойти на встречу с портнерами"));
        notesRepository.createNote(new Note("Заметка № 3", "Сходить в отпуск"));
        notesRepository.createNote(new Note("Заметка № 4", "Сходить в отпуск"));
        notesRepository.createNote(new Note("Заметка № 5", "Прочести анализ рынка"));
        notesRepository.createNote(new Note("Заметка № 6", "Пойти выучить английский"));
        notesRepository.createNote(new Note("Заметка № 7", "Сходить в отпуск"));
        notesRepository.createNote(new Note("Заметка № 8", "Найти новых партнеров"));
        notesRepository.createNote(new Note("Заметка № 9", "Прочести анализ рынка"));
        notesRepository.createNote(new Note("Заметка № 10", "Отдать ожежду на хим чистку"));
        notesRepository.createNote(new Note("Заметка № 11", "Прочести анализ рынка"));
        notesRepository.createNote(new Note("Заметка № 12", "Найти новых партнеров"));
        notesRepository.createNote(new Note("Заметка № 13", "Пойти на встречу с портнерами"));
        notesRepository.createNote(new Note("Заметка № 14", "Найти новых партнеров"));
        notesRepository.createNote(new Note("Заметка № 15", "Пойти на встречу с портнерами"));
        notesRepository.createNote(new Note("Заметка № 16", "Отдать ожежду на хим чистку"));

    }
}

