package com.example.mynote.ui;

import static com.example.mynote.consatant.Constant.EDIT_NOTE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynote.Implementation.RepositoryImplementation;
import com.example.mynote.R;
import com.example.mynote.domain.Note;
import com.example.mynote.domain.NotesRepository;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NoteListActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private Note noteNull = new Note();
    private Note noteNew = new Note();
    private NotesRepository notesRepository = new RepositoryImplementation();
    private NoteScreenActivity adapter = new NoteScreenActivity();
    private static final int REQUEST_CODE_NOTE_EDIT_ACTIVITY = 88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        createTestNotesRepository();
        initToolBar();
        initRecyclerView();
        createDecoration();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_note_item: {
                noteNull.setDate("");
                openNoteScreen(noteNull);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void openNoteScreen(Note note) {
        Intent intent = new Intent(this, NoteEditActivity.class);
        intent.putExtra(EDIT_NOTE, note);
        startActivityForResult(intent, REQUEST_CODE_NOTE_EDIT_ACTIVITY);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_NOTE_EDIT_ACTIVITY) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == RESULT_OK) {

            noteNew = data.getParcelableExtra(EDIT_NOTE);
            if (noteNew.getId() == null) {
                notesRepository.createNote(new Note(noteNew.getHead(), noteNew.getDescription(), noteNew.getDate()));
            } else {
                notesRepository.updateNote(noteNew.getId(), noteNew);
            }
            initRecyclerView();
        }

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

    private void initToolBar() {
        toolbar = (MaterialToolbar) findViewById(R.id.note_list_toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(Note item) {
        openNoteScreen(item);
        //  writeFile();
        // readFile();
    }

    private void readFile() {
        File  myFile = new File("txt5.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader( new FileReader(myFile));
            String str="";
            Note noteWrite =new Note();
            int i=1;
            while ((str=br.readLine()) !=null){
                if(i==1){
                    Integer strInt = Integer.valueOf(str);
                    noteWrite.setId(strInt);
                } else if (i==2){
                    noteWrite.setHead(str);
                } else if (i==3){
                    noteWrite.setDescription(str);
                }else if (i==4){
                    noteWrite.setDate(str);
                    i=0;
                }
                if (i==0){
                    Integer j = noteWrite.getId();
                    notesRepository.updateNote(noteWrite.getId() ,noteWrite);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile() {
        File  myFile = new File("txt5.txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter( new FileWriter(myFile));
            for (int i = 0; i <notesRepository.getNotes().size() ; i++) {
                bw.write(notesRepository.getNotes().get(i).getId()+"");
                bw.write("\n");
                bw.write(notesRepository.getNotes().get(i).getHead());
                bw.write("\n");
                bw.write(notesRepository.getNotes().get(i).getDescription());
                bw.write("\n");
                bw.write(notesRepository.getNotes().get(i).getDate());
                bw.write("\n");
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDecoration() {
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
    }

}