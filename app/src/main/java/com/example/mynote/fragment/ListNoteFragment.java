package com.example.mynote.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynote.domain.Note;
import com.google.android.material.appbar.MaterialToolbar;

import com.example.mynote.R;
import com.example.mynote.ui.MainActivity;


import com.example.mynote.domain.Note;


public class ListNoteFragment extends Fragment  {
    private Controller controller;
    private MaterialToolbar toolbar;
    private static final String ARG_NOTE_LIST = "NOTE_LIST";
    private Note note;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Controller ){
            controller = (Controller)context;
        } else{
            throw  new IllegalStateException("Activity must implement ListNoteFragment.Controller");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_note_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_notes_list,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            note = bundle.getParcelable(ARG_NOTE_LIST);
            saveNote();
        }
        initToolbar(view);
        controller.openListNote();
    }

    private void saveNote() {
        if (note.getId() == 0) {
            ((MainActivity)requireActivity()).createNoteActivity(note);
        } else {
            ((MainActivity)requireActivity()).updateNoteActivity(note);
        }
        ((MainActivity)requireActivity()).initRecyclerView();
    }

    private void initToolbar(View view) {
        toolbar = (MaterialToolbar) view.findViewById(R.id.note_list_toolbar);
        ((MainActivity)requireActivity()).setSupportActionBar(toolbar);
    }

    private  Controller getController(){
        return (Controller) requireActivity();
    }

    public interface Controller{
        void openListNote();
    }

    public static ListNoteFragment newInstance(Note note){
        ListNoteFragment fragment = new ListNoteFragment();
        Bundle  bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE_LIST,note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        controller = null;
        super.onDestroy();
    }

}
