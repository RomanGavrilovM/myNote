package com.example.mynote.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynote.domain.Note;

import com.example.mynote.R;
import com.example.mynote.domain.NotesRepository;
import com.example.mynote.Impl.NotesRepositoryImpl;

public class NotesListFragment extends Fragment {
    private RecyclerView recyclerView;
    private Controller controller;

    private final NotesRepository notesRepository = new NotesRepositoryImpl();
    private final NotesAdapter adapter = new NotesAdapter();

    interface Controller {
        void openNoteScreen(Note item);
    }

    public static NotesListFragment newInstance(Note item) {
        NotesListFragment notesListFragment = new NotesListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteEditFragment.NOTE_ARGS_KEY, item);
        notesListFragment.setArguments(bundle);
        return notesListFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            controller = (Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement NotesListFragment.Controller");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillRepositoryByTestValues();
        getArgs();
        initRecyclerView(view);
    }

    @Override
    public void onDestroy() {
        controller = null;
        super.onDestroy();
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(Note item) {

        controller.openNoteScreen(item);
    }

    private void getArgs() {
        Bundle data = getArguments();
        if (data != null) {
            Note note = data.getParcelable(NoteEditFragment.NOTE_ARGS_KEY);
            if (note != null) {
                if (note.getId() == null) {
                    notesRepository.createNote(note);
                } else {
                    notesRepository.updateNote(note.getId(), note);
                }
            }
        }
    }
    private void fillRepositoryByTestValues() {
        notesRepository.createNote(new Note("Заметка № 1", "Сделать статистику"));
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