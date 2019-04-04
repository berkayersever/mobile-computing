package com.example.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements EventAdapter.EventHandler {

    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    public void load() {
        try {
            eventList.add(new Event("CS310 Midterm1", "Midterm at LO65 from 9am", new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019"), 4, true));
            eventList.add(new Event("Room meeting", "Meet Mr. Mbape before things get worse", new SimpleDateFormat("dd/MM/yyyy").parse("07/04/2019"), 4, true));
            eventList.add(new Event("CS310 Midterm1 Objection", "Room LO65 from 10am ", new SimpleDateFormat("dd/MM/yyyy").parse("06/05/2019"), 3, false));
            eventList.add(new Event("CS308 Project presentation", "4th sprint user-inteface design", new SimpleDateFormat("dd/MM/yyyy").parse("09/04/2019"), 4, true));
            eventList.add(new Event("Judiy's Funeral", "buy flowers", new SimpleDateFormat("dd/MM/yyyy").parse("31/03/2019"), 3, false));
            eventList.add(new Event("Avengers release", "Invite roommates to watch it", new SimpleDateFormat("dd/MM/yyyy").parse("02/04/2019"), 2, true));
            eventList.add(new Event("Job application", "Ware the black suit", new SimpleDateFormat("dd/MM/yyyy").parse("05/05/2019"), 4, false));
            eventList.add(new Event("Google's interview", "Focus on software engineering nothing else matters", new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019"), 1, true));
            eventList.add(new Event("Summer school starts", "Nothing special", new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019"), 4, true));
            eventList.add(new Event("MS application deadline", "now or never", new SimpleDateFormat("dd/MM/yyyy").parse("08/05/2019"), 4, true));
            eventList.add(new Event("Make plane reservation", "Most preferably Turkish Airlines", new SimpleDateFormat("dd/MM/yyyy").parse("29/03/2019"), 3, true));
            eventList.add(new Event("Trip to Africa", "Dont miss your plane", new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019"), 4, false));
            eventList.add(new Event("See the dentist", "Midterm at LO65 from 9am", new SimpleDateFormat("dd/MM/yyyy").parse("06/05/2019"), 2, true));
            eventList.add(new Event("CS310 Midterm2", "Midterm at LO65 from 9am", new SimpleDateFormat("dd/MM/yyyy").parse("04/04/2019"), 4, true));
            eventList.add(new Event("Call Dad", "Remind him of fees", new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019"), 3, true));
            eventList.add(new Event("Shopping", "With loved ones", new SimpleDateFormat("dd/MM/yyyy").parse("06/05/2019"), 1, false));
            eventList.add(new Event("fundraiser", "Give whatever you have", new SimpleDateFormat("dd/MM/yyyy").parse("01/04/2019"), 4, true));
            eventList.add(new Event("CS310 Final", "All topics includes", new SimpleDateFormat("dd/MM/yyyy").parse("28/05/2019"), 1, true));
            eventList.add( new Event("Date with Juliet", "Hilton hotel, Mecidiyekoy", new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019"), 4, false));
        } catch (ParseException err) {
            err.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();
        recyclerView = findViewById(R.id.myList);
        RadioGroup radioGroup = findViewById(R.id.sort);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(eventList,this);
        recyclerView.setAdapter(eventAdapter);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.sortPriority:
                        Collections.sort(eventList, new PriorityComperator());
                        updateUI();
                        break;
                    case R.id.sortDate:
                        Collections.sort(eventList, new DateComperator());
                        updateUI();
                        break;
                }
            }
        });
    }

    public void updateUI() { eventAdapter.notifyDataSetChanged(); }

    private class PriorityComperator implements Comparator<Event> {

        public int compare(Event event1, Event event2) {
            return Integer.valueOf(event2.getPriority()).compareTo(event1.getPriority());
        }
    }

    private class DateComperator implements Comparator<Event> {

        public int compare(Event event1, Event event2) {
            return event1.getDate().compareTo(event2.getDate());
        }
    }

    @Override
    public void onItemClick(int index) { updateUI(); }
}
