package com.example.mynote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

import com.example.mynote.R;
import com.example.mynote.domain.Note;
import com.example.mynote.ui.MainActivity;

public class EditNoteFragment  extends Fragment {
    private EditText headEditText;
    private EditText descriptionEditText;
    private TextView dataTextView;
    private Note note;
    private MaterialToolbar toolbar;
    private EditNoteFragment.Controller controller;
    private static final String ARG_NOTE = "NOTE";
    private Note noteNull = new Note();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListNoteFragment.Controller) {
            controller = (EditNoteFragment.Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement EditNoteFragment.Controller");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            putAndSetView(bundle);
        }
        initToolbar(view);
        controller.openEditNote();

    }

    private void initToolbar(View view) {
        toolbar = (MaterialToolbar) view.findViewById(R.id.note_edit_toolbar);
        ((MainActivity) requireActivity()).setSupportActionBar(toolbar);
    }

    private void putAndSetView(Bundle bundle) {
        note = bundle.getParcelable(ARG_NOTE);
        headEditText.setText(note.getHead());
        descriptionEditText.setText(note.getDescription());
        dataTextView.setText(dataTextView.getText() + note.getDate());
    }

    private void initView(View view) {
        headEditText = view.findViewById(R.id.head_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        dataTextView = view.findViewById(R.id.data_text_view_create_note);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_note_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private EditNoteFragment.Controller getController() {
        return (EditNoteFragment.Controller) requireActivity();
    }


    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface Controller {
        void openEditNote();
    }

    @Override
    public void onDestroy() {
        controller = null;
        super.onDestroy();
    }
}
