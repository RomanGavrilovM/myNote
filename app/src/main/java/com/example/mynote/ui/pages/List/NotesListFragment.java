package com.example.mynote.ui.pages.List;

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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynote.App;
import com.example.mynote.R;
import com.example.mynote.Impl.NotesDiffCallback;
import com.example.mynote.model.entities.NoteEntity;
import com.example.mynote.model.repos.NotesRepository;

@SuppressWarnings("FieldCanBeLocal")
public class NotesListFragment extends Fragment {
    private final NotesAdapter adapter = new NotesAdapter();
    private final Subscriber subscriber = this::onNoteSaved;

    private App app;
    private NotesRepository repository;

    private RecyclerView recyclerView;
    private Controller controller;

    public interface Controller {
        void openNoteEditScreen(NoteEntity item);

        void subscribe(Subscriber subscriber);

        void unsubscribe(Subscriber subscriber);
    }

    public interface Subscriber {
        void onNoteSaved(NoteEntity note);
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

        controller.subscribe(subscriber);
        initRepository();
        initRecyclerView(view);
    }

    @Override
    public void onDestroy() {
        controller.unsubscribe(subscriber);
        controller = null;
        super.onDestroy();
    }

    private void initRepository() {
        app = (App) requireActivity().getApplication();
        repository = app.getNotesRepository();
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setData(repository.getNotes());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(NoteEntity item, int position) {
                controller.openNoteEditScreen(item);
            }

            @Override
            public void onItemLongClick(NoteEntity item, View view, int position) {
                showNotePopupMenu(item, view);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void showNotePopupMenu(NoteEntity item, View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view, Gravity.END);
        popupMenu.inflate(R.menu.note_item_popup_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.edit_popup_menu_item:
                    controller.openNoteEditScreen(item);
                    break;
                case R.id.delete_popup_menu_item:
                    deleteItem(item);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void deleteItem(NoteEntity item) {
        if (repository.deleteNote(item.getUid())) {
            Toast.makeText(requireActivity(),
                    getString(R.string.successfully_deleted) + item.getTitle(),
                    Toast.LENGTH_SHORT).show();
            checkDiffRepo();
        } else {
            Toast.makeText(requireActivity(),
                    R.string.fail_to_delete,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onNoteSaved(NoteEntity note) {
        if (note != null) {
            if (note.getUid() == null) {
                repository.createNote(note);
            } else {
                repository.updateNote(note.getUid(), note);
            }
            checkDiffRepo();
        }
    }

    public void checkDiffRepo() {
        NotesDiffCallback notesDiffCallback = new NotesDiffCallback(adapter.getData(), repository.getNotes());
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(notesDiffCallback, true);
        adapter.setData(repository.getNotes());
        result.dispatchUpdatesTo(adapter);
    }
}