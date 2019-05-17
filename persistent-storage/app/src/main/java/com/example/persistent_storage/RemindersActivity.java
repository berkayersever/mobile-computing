package com.example.persistent_storage;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import java.util.Calendar;
import java.util.List;

public class RemindersActivity extends AppCompatActivity implements ReminderAdapter.EventHandler{
    public static final String INDEX_KEY = "com.example.persistent_storage.index";
    public static final String TIME_KEY = "com.example.persistent_storage.time";
    public static final String PRIORITY_KEY = "com.example.persistent_storage.priority";
    public static final String NAME_KEY = "com.example.persistent_storage.name";
    public static final String NOTE_KEY = "com.example.persistent_storage.note";
    private RecyclerView recyclerView;
    private ReminderAdapter reminderAdapter;
    private RadioGroup radioGroup;
    private ReminderViewModel viewModel;
    private Button populateButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        radioGroup = findViewById(R.id.radioGroup);
        viewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        reminderAdapter = new ReminderAdapter(this, viewModel.getReminderList());
        recyclerView.setAdapter(reminderAdapter);
        viewModel.getLiveReminderList().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                if(reminders != null) {
                    reminderAdapter.setReminderList(reminders);
                    reminderAdapter.notifyDataSetChanged();
                }
            }
        });

        /* Sorting Selection */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(viewModel.getReminderList() != null) {
                    if      (checkedId == R.id.dateButton)      { viewModel.sortByDate(); }
                    else if (checkedId == R.id.priorityButton)  { viewModel.sortByPriority(); }
                    reminderAdapter.notifyDataSetChanged();
                }
            }
        });

        /* Persistent Storage */
        populateButton = findViewById(R.id.populateButton);
        deleteButton = findViewById(R.id.deleteAllButton);
        populateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.populateDatabase();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllDatabase();
            }
        });
    }

    /* Methods for ReminderAdapter */
    @Override
    public void onCancelButtonClick(int index) {
        viewModel.remove(index);
        reminderAdapter.notifyDataSetChanged();
    }
    @Override
    public void onPriorityBarChange(int index, int newValue) {
        viewModel.setPriority(newValue, index);
    }
    @Override
    public void onSwitchChange(int index) {
        viewModel.switchEnabled(index);
    }
    public void onReminderClick(int index) {
        Reminder reminder = viewModel.getReminderList().get(index);
        Intent intent = new Intent(RemindersActivity.this, DetailsActivity.class);
        intent.putExtra(INDEX_KEY,index);
        intent.putExtra(TIME_KEY,reminder.getTime());
        intent.putExtra(PRIORITY_KEY, reminder.getPriority());
        intent.putExtra(NAME_KEY, reminder.getTitle());
        intent.putExtra(NOTE_KEY, reminder.getNote());
        startActivity(intent);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String name = intent.getStringExtra(NAME_KEY);
        String note = intent.getStringExtra(NOTE_KEY);
        Long time = intent.getLongExtra(TIME_KEY, Calendar.getInstance().getTimeInMillis());
        int priority = intent.getIntExtra(PRIORITY_KEY, 0);
        int index = intent.getIntExtra(INDEX_KEY, 0);
        viewModel.updateReminder(index,name,note,priority,time);
    }
}