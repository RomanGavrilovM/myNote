package com.example.mynote.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Pattern;

import com.example.mynote.R;
import com.example.mynote.domain.Note;
import com.example.mynote.domain.NotesRepository;
import com.example.mynote.fragment.EditNoteFragment;
import com.example.mynote.fragment.ListNoteFragment;
import com.example.mynote.fragment.SettingFragment;
import com.example.mynote.Implementation.NotesRepositoryImplementation;

import static com.example.mynote.consatant.Constant.EDIT_NOTE;
import static com.example.mynote.consatant.Constant.positioN;


@SuppressLint("RestrictedApi")
public class MainActivity extends AppCompatActivity implements ListNoteFragment.Controller,
        EditNoteFragment.Controller, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private Note noteNull = new Note();
    private Note noteNew = new Note();
    private NotesRepository notesRepository = new NotesRepositoryImplementation();
    private NotesAdapter adapter = new NotesAdapter();
    private EditText headEditText;
    private EditText descriptionEditText;
    private TextView dataTextView;
    private TextView dataYearTextView;
    private DatePicker datePicker;
    private String dataSave;

    private int position = 0;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTestNotesRepository();

        if (savedInstanceState == null) {
            oneOpenFragment();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_note_item: {
                noteNull.setDate("");
                openNoteScreen(noteNull);
                positioN = -1;
                return true;
            }
            case R.id.save_note_item: {
                openScreenPostSave();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }


    @SuppressWarnings("StatemenWithEmplyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_setting) {
            Toast.makeText(this, "dsfg", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void openListNote() {
        initRecyclerView();
    }

    @Override
    public void openEditNote() {
        initDataPicker();
        initEditText();
    }


    private void createTestNotesRepository() {
        notesRepository.createNote(new Note("Заметка № 1", "Сделать статистику", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 2", "Пойти на встречу с портнерами", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 3", "Сходить в отпуск", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 4", "Сходить в отпуск", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 5", "Прочести анализ рынка", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 6", "Пойти выучить английский", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 7", "Сходить в отпуск", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 8", "Найти новых партнеров", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 9", "Прочести анализ рынка", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 10", "Отдать ожежду на хим чистку", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 11", "Прочести анализ рынка", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 12", "Найти новых партнеров", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 13", "Пойти на встречу с портнерами", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 14", "Найти новых партнеров", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 15", "Пойти на встречу с портнерами", "07.09.2021 года."));
        notesRepository.createNote(new Note("Заметка № 16", "Отдать ожежду на хим чистку", "07.09.2021 года."));

    }


    private void oneOpenFragment() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_edit, EditNoteFragment.newInstance(notesRepository.getNotes().get(positioN)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_list, new ListNoteFragment())
                    .commit();
        }
    }

    private void openScreenPostSave() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_list, ListNoteFragment.newInstance(createNote()))
                .commit();
    }

    private Note createNote() {
        Note noteNew = new Note(headEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                dataSave);
        noteNew.setId(positioN + 1);
        return noteNew;
    }


    public void openNoteScreen(Note note) {
        twoOpenFragment(note);
    }

    private void twoOpenFragment(Note note) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().popBackStack();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_edit, EditNoteFragment.newInstance(note))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_edit, EditNoteFragment.newInstance(note))
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }


    public void createNoteActivity(Note note) {
        notesRepository.createNote(new Note(note.getHead(), note.getDescription(), note.getDate()));

    }

    public void updateNoteActivity(Note note) {
        notesRepository.updateNote(note.getId(), note);

    }

    private void initEditText() {
        headEditText = findViewById(R.id.head_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        dataTextView = findViewById(R.id.data_text_view_create_note);
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(Note item) {
        openNoteScreen(item);
        positioN = item.getId() - 1;

    }


    private void initDataPicker() {
        datePicker = findViewById(R.id.data_picket);
        dataYearTextView = findViewById(R.id.data_text_year);

        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        dataSave = convertWriteData(day, month + 1, year);
        datePicker.init(year, month,
                day, (view, year1, monthOfYear, dayOfMonth) -> {
                    dataSave = convertWriteData(dayOfMonth, monthOfYear + 1, year1);
                });
    }

    private String convertWriteData(int day, int month, int year) {
        String s = Pattern.compile(R.string.data_text + "").toString();

        return convertWriteDayAndMonthData(day) + "." + convertWriteDayAndMonthData(month) + "." + year + (String) dataYearTextView.getText();
    }

    private String convertWriteDayAndMonthData(int day) {
        if (day < 10) {
            return "0" + day;
        } else {
            return "" + day;
        }
    }

}
