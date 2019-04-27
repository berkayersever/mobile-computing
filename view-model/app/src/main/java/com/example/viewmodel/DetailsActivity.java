package com.example.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class DetailsActivity extends AppCompatActivity {

    private int index;
    private Button saveButton;
    private CalendarView calendarView;
    private DetailsViewModel viewModel;
    private EditText titleEdit, descriptionEdit;
    private SeekBar priorityBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        index = getIntent().getIntExtra(RemindersActivity.INDEX_KEY, 0);
        if (savedInstanceState != null) {
            viewModel.readFromBundle(savedInstanceState);
        }
        else {
            viewModel.setTime(getIntent().getLongExtra(RemindersActivity.TIME_KEY, 0));
            viewModel.setPriority(getIntent().getIntExtra(RemindersActivity.PRIORITY_KEY, 0));
            viewModel.setNote(getIntent().getStringExtra(RemindersActivity.NOTE_KEY));
            viewModel.setName(getIntent().getStringExtra(RemindersActivity.NAME_KEY));
        }
        calendarView = findViewById(R.id.calendarView);
        saveButton = findViewById(R.id.saveButton);
        titleEdit = findViewById(R.id.titleEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        priorityBar = findViewById(R.id.detailPriorityBar);
        titleEdit.setText(viewModel.getLiveName().getValue());
        descriptionEdit.setText(viewModel.getLiveNote().getValue());
        calendarView.setDate(viewModel.getLiveTime().getValue(), false, true);
        priorityBar.setProgress(viewModel.getLivePriority().getValue());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(DetailsActivity.this, RemindersActivity.class);
                resultIntent.putExtra(RemindersActivity.NAME_KEY, viewModel.getLiveName().getValue());
                resultIntent.putExtra(RemindersActivity.NOTE_KEY, viewModel.getLiveNote().getValue());
                resultIntent.putExtra(RemindersActivity.PRIORITY_KEY, viewModel.getLivePriority().getValue());
                resultIntent.putExtra(RemindersActivity.TIME_KEY, viewModel.getLiveTime().getValue());
                resultIntent.putExtra(RemindersActivity.INDEX_KEY, index);
                resultIntent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(resultIntent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                viewModel.setTime(calendar.getTimeInMillis());
            }
        });

        priorityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) { viewModel.setPriority(progress); }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) { viewModel.setName(s.toString()); }
        });

        descriptionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) { viewModel.setNote(s.toString()); }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.writeToBundle(outState);
    }
}