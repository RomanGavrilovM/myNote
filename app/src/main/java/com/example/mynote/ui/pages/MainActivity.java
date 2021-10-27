package com.example.mynote.ui.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.mynote.R;
import com.example.mynote.model.entities.NoteEntity;
import com.example.mynote.ui.pages.about.AboutAppFragment;
import com.example.mynote.ui.pages.Edit.NoteEditFragment;
import com.example.mynote.ui.pages.List.NotesListFragment;
import com.example.mynote.ui.pages.Settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteEditFragment.Controller {
    private final static String NOTES_LIST_TAG = "LIST";
    private final static String NOTE_EDIT_TAG = "EDIT";

    private final Map<Integer, Fragment> drawerFragmentsMap = createFragments();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final List<NotesListFragment.Subscriber> subscribers = new ArrayList<>();

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
        } else if (fragmentManager.getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.exit_dialog_title)
                    .setPositiveButton("Да", (dialogInterface, i) -> {
                        MainActivity.super.onBackPressed();
                        Toast.makeText(MainActivity.this, R.string.exit_dialog_toast, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Нет", (dialogInterface, i) -> dialogInterface.cancel())
                    .setCancelable(false)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void subscribe(NotesListFragment.Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(NotesListFragment.Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(NoteEntity note) {
        for (NotesListFragment.Subscriber subscriber : subscribers) {
            subscriber.onNoteSaved(note);
        }
    }

    @Override
    public void openNotesListScreen(NoteEntity item) {
        fragmentManager
                .beginTransaction()
                .remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(NOTE_EDIT_TAG)))
                .show(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(NOTES_LIST_TAG)))
                .commit();
        notifySubscribers(item);
    }

    @Override
    public void openNoteEditScreen(@Nullable NoteEntity item) {
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.land_fragment_container, NoteEditFragment.newInstance(item))
                    .addToBackStack(null)
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .hide(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(NOTES_LIST_TAG)))
                    .add(R.id.main_fragment_container, NoteEditFragment.newInstance(item), NOTE_EDIT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.main_fragment_container, new NotesListFragment(), NOTES_LIST_TAG)
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
                    .replace(R.id.main_fragment_container, Objects.requireNonNull(drawerFragmentsMap.get(item.getItemId())))
                    .commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}