package com.example.mynote.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import com.example.mynote.R;
import com.example.mynote.domain.Note;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteEditFragment.Controller {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawer();
        initNavigationView();
        initDefaultFragment(savedInstanceState);
    }

    private void initDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, new NotesListFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.drawer_item_notes_list);
        }
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
            openNoteEditScreen(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void openNoteEditScreen(@Nullable Note item){
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, NoteEditFragment.newInstance(item))
                .commit();
        navigationView.setCheckedItem(R.id.drawer_item_note_edit);
    }

    @Override
    public void openNotesListScreen(Note item) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, NotesListFragment.newInstance(item))
                .commit();
        navigationView.setCheckedItem(R.id.drawer_item_notes_list);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        drawer = findViewById(R.id.navigation_drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
    @SuppressLint("NonConstantResourceId")
    private void initNavigationView() {
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.drawer_item_notes_list:
                    openNotesListScreen(null);
                    break;
                case R.id.drawer_item_note_edit:
                    openNoteEditScreen(null);
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}