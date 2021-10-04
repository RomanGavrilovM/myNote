package com.example.mynote.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Calendar;
import java.util.regex.Pattern;

import com.example.mynote.R;
import com.example.mynote.domain.Note;

import static com.example.mynote.consatant.Constant.EDIT_NOTE;

public class NoteEditActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private EditText headEditText;
    private EditText descriptionEditText;
    private TextView dataTextView;
    private TextView dataYearTextView ;
    private Note noteStructure;
    private DatePicker datePicker;
    private String dataSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        initDataPicker();
        initToolBar();
        getData();
        initEditText();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note_item: {
                passDataBack();
                finish();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
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
        String s = Pattern.compile(R.string.data_text+"").toString();

        return convertWriteDayAndMonthData(day) + "." + convertWriteDayAndMonthData(month) + "." + year +  (String) dataYearTextView.getText();
    }

    private String convertWriteDayAndMonthData(int day) {
        if (day < 10) {
            return "0" + day;
        } else {
            return "" + day;
        }
    }

    private void passDataBack() {
        Intent intentResult = new Intent();
        intentResult.putExtra(EDIT_NOTE, createNote());
        setResult(RESULT_OK, intentResult);
    }

    private Note createNote() {
        Note noteNew = new Note(headEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                dataSave);
        noteNew.setId(noteStructure.getId());
        return noteNew;
    }

    private void initToolBar() {
        toolbar = (MaterialToolbar) findViewById(R.id.note_list_toolbar);
        setSupportActionBar(toolbar);
    }

    private void getData() {
        noteStructure = getIntent().getExtras().getParcelable(EDIT_NOTE);
    }

    private void initEditText() {
        headEditText = findViewById(R.id.head_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        dataTextView =findViewById(R.id.data_text_view_create_note);
        setTextInEditText();
    }

    private void setTextInEditText() {
        headEditText.setText(noteStructure.getHead());
        descriptionEditText.setText(noteStructure.getDescription());
        dataTextView.setText(dataTextView.getText()+noteStructure.getDate());
    }
}
