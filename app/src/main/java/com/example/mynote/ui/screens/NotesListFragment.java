package com.example.mynote.ui.screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynote.App;
import com.example.mynote.R;
import com.example.mynote.model.entities.Note;
import com.example.mynote.ui.NotesAdapter;
import com.example.mynote.ui.OnItemClickListener;

public class NotesListFragment extends Fragment {
    protected App app;
    private RecyclerView recyclerView;

    private Controller controller;

    private final NotesAdapter adapter = new NotesAdapter();

    interface Controller {
        void openNoteEditScreen(Note item);
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
        app = (App) requireActivity().getApplication();

        initRecyclerView(view);
        getArgs();
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
        adapter.setData(app.notesRepository.getNotes());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Note item, int position) {
                controller.openNoteEditScreen(item);
            }

            @Override
            public void onItemLongClick(Note item, View view, int position) {
                showNotePopupMenu(item, view, position);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void showNotePopupMenu(Note item, View view, int position) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view, Gravity.END);
        popupMenu.inflate(R.menu.note_item_popup_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.edit_popup_menu_item:
                    controller.openNoteEditScreen(item);
                    break;
                case R.id.delete_popup_menu_item:
                    deleteItem(item, position);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void deleteItem(Note item, int position) {
        if (app.notesRepository.deleteNote(item.getUid())) {
            Toast.makeText(requireActivity(),
                    getString(R.string.successfully_deleted) + item.getTitle(),
                    Toast.LENGTH_SHORT).show();
            adapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(requireActivity(),
                    R.string.fail_to_delete,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getArgs() {
        Bundle data = getArguments();
        if (data != null) {
            Note note = data.getParcelable(NoteEditFragment.NOTE_ARGS_KEY);
            if (note != null) {
                if (note.getUid() == null) {
                    app.notesRepository.createNote(note);
                } else {
                    app.notesRepository.updateNote(note.getUid(), note);
                }
            }
        }
    }
}