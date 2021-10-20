package com.example.mynote.ui.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.mynote.R;

import com.example.mynote.model.entities.Note;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteEditFragment.Controller {

    private final Map<Integer, Fragment> fragments = createFragments();
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private static Map<Integer, Fragment> createFragments() {
        Map<Integer, Fragment> newFragmentsMap = new HashMap<>();

        newFragmentsMap.put(R.id.drawer_item_notes_list, new NotesListFragment());
        newFragmentsMap.put(R.id.drawer_item_settings, new SettingsFragment());
        newFragmentsMap.put(R.id.drawer_item_about_app, new AboutAppFragment());

        return newFragmentsMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawerLayout();
        initNavigationView();
        openDefaultFragment(savedInstanceState);
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void openNoteEditScreen(@Nullable Note item) {
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.land_fragment_container, NoteEditFragment.newInstance(item))
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, NoteEditFragment.newInstance(item))
                    .commit();
        }
    }

    @Override
    public void openNotesListScreen(Note item) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, NotesListFragment.newInstance(item))
                .commit();
        navigationView.setCheckedItem(R.id.drawer_item_notes_list);
    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.main_fragment_container, new NotesListFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.drawer_item_notes_list);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void initDrawerLayout() {
        drawer = findViewById(R.id.navigation_drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initNavigationView() {
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, Objects.requireNonNull(fragments.get(item.getItemId())))
                    .commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}