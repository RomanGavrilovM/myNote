package com.example.mynote.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mynote.R;
import com.example.mynote.domain.Note;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteEditFragment.Controller {
    private Toolbar toolbar;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initNotesListFragment();
    }

    private void initNotesListFragment() {
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, new NotesListFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_note_menu) {
            openNoteScreen(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openNoteScreen(@Nullable Note item) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, NoteEditFragment.newInstance(item))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openNotesListScreen(Note item) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, NotesListFragment.newInstance(item))
                .addToBackStack(null)
                .commit();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}