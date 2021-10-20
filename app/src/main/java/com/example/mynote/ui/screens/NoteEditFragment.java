package com.example.mynote.ui.screens;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.mynote.R;
import com.example.mynote.model.entities.Note;

public class NoteEditFragment extends Fragment {
    public static final String NOTE_ARGS_KEY = "NOTE_ARGS_KEY";

    private Controller controller;

    private EditText titleEditText;
    private EditText detailEditText;
    private EditText dateTimeEditText;
    private Button saveNoteButton;

    private Note note;
    private long creationDate = 0;
    private Object Note;

    interface Controller {
        void openNotesListScreen(Note item);
    }

    public static NoteEditFragment newInstance(Note item) {
        NoteEditFragment noteEditFragment = new NoteEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_ARGS_KEY, item);
        noteEditFragment.setArguments(bundle);
        return noteEditFragment;
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
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupListeners();
        getArgs();
        fillViews();
    }

    @Override
    public void onDestroy() {
        controller = null;
        super.onDestroy();
    }

    private void initViews(View view) {
        titleEditText = view.findViewById(R.id.title_edit_text);
        detailEditText = view.findViewById(R.id.detail_edit_text);
        dateTimeEditText = view.findViewById(R.id.date_time_edit_text);
        dateTimeEditText.setInputType(InputType.TYPE_NULL);
        saveNoteButton = view.findViewById(R.id.save_note_button);
    }

    private void setupListeners() {
        saveNoteButton.setOnClickListener(v -> {
            createOrEditNote();
            controller.openNotesListScreen(note);
        });

        dateTimeEditText.setOnClickListener(view -> showDateTimeDialog(dateTimeEditText));
    }

    private void createOrEditNote() {
        if (note == null) {
            note = new Note(
                    titleEditText.getText().toString(),
                    detailEditText.getText().toString()
            );
        } else {
            note.setTitle(titleEditText.getText().toString());
            note.setDetail(detailEditText.getText().toString());
        }
        if (creationDate != 0) {
            note.setCreationDate(creationDate);
        }
    }

    private void showDateTimeDialog(EditText dateTimeEditText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                creationDate = calendar.getTimeInMillis();
                Date date = new Date(creationDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y HH:mm", Locale.getDefault());
                dateTimeEditText.setText(dateFormat.format(date));
            };

            new TimePickerDialog(getContext(),
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true).show();
        };

        new DatePickerDialog(getContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void getArgs() {
        Bundle data = getArguments();
        if (data != null) note = data.getParcelable(NOTE_ARGS_KEY);

    }

    private void fillViews() {
        if (note != null) {
            titleEditText.setText(note.getTitle());
            detailEditText.setText(note.getDetail());
            dateTimeEditText.setText(note.getCreationDate());
        }
    }
}
