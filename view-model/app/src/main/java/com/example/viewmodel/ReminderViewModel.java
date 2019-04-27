package com.example.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReminderViewModel extends ViewModel {
    private MutableLiveData<List<Reminder>> liveReminderList;
    public ReminderViewModel() {
        Reminder [] reminders = {
                new Reminder("CS310 Midterm1","Midterm at LO65 from 9am", "06/04/2019", 4, 1),
                new Reminder("Room meeting","Meet Mr. Mbape before things get worse", "07/04/2019", 4, 1),
                new Reminder("CS310 Midterm1 Objection","Room LO65 from 10am ", "06/05/2019", 3, 0),
                new Reminder("CS308 Project presentation","4th sprint user-inteface design", "09/04/2019", 4, 1),
                new Reminder("Judiy's Funeral","buy flowers", "31/03/2019", 3, 0),
                new Reminder("Avengers release","Invite roommates to watch it", "02/04/2019", 2, 1),
                new Reminder("Job application","Ware the black suit", "05/05/2019", 4, 0),
                new Reminder("Google's interview","Focus on software engineering nothing else matters", "06/04/2019", 1, 1),
                new Reminder("Summer school starts","Nothing special", "06/04/2019", 4, 1),
                new Reminder("MS application deadline","now or never", "08/05/2019", 4, 1),
                new Reminder("Make plane reservation","Most preferably Turkish Airlines", "29/03/2019", 3, 1),
                new Reminder("Trip to Africa","Dont miss your plane", "06/04/2019", 4, 0),
                new Reminder("See the dentist","Midterm at LO65 from 9am", "06/05/2019", 2, 1),
                new Reminder("CS310 Midterm2","Midterm at LO65 from 9am", "04/04/2019", 4, 1),
                new Reminder("Call Dad","Remind him of fees", "06/04/2019", 3, 1),
                new Reminder("Shopping","With loved ones", "06/05/2019", 1, 0),
                new Reminder("fundraiser","Give whatever you have", "01/04/2019", 4, 1),
                new Reminder("CS310 Final","All topics includes", "28/05/2019", 1, 1),
                new Reminder("Date with Juliet","Hilton hotel, Mecidiyekoy", "06/04/2019", 4, 0),
                };
        liveReminderList = new MutableLiveData<>();
        liveReminderList.setValue(new ArrayList<Reminder>(Arrays.asList(reminders)));
        sortByPriority();
    }

    /* Getters */
    public MutableLiveData<List<Reminder>> getLiveReminderList() {
        return liveReminderList;
    }
    public List<Reminder> getReminderList() {
        return liveReminderList.getValue();
    }
    private Reminder getReminder(int index) {
        return liveReminderList.getValue().get(index);
    }

    /* Setters */
    public void remove(int index) {
        List<Reminder> list = getReminderList();
        list.remove(index);
        liveReminderList.setValue(list);
    }
    public void setPriority(int priority, int index) {
        List<Reminder> list = getReminderList();
        list.get(index).setPriority(priority);
        liveReminderList.setValue(list);
    }
    public void switchEnabled(int index) {
        List<Reminder> list = getReminderList();
        Reminder reminder = list.get(index);
        reminder.setStatus(! reminder.getStatus());
        liveReminderList.setValue(list);
    }
    public void updateReminder(int index, String name, String note, int priority, Long time) {
        List <Reminder> list = getReminderList();
        Reminder reminder = list.get(index);
        reminder.setTitle(name);
        reminder.setNote(note);
        reminder.setTime(time);
        reminder.setPriority(priority);
        liveReminderList.setValue(list);
    }

    /* Sort Methods */
    public void sortByDate() {
        List<Reminder> list = getReminderList();
        Collections.sort(list, new Reminder.SortByDate());
        liveReminderList.setValue(list);
    }
    public void sortByPriority() {
        List<Reminder> list = getReminderList();
        Collections.sort(list, new Reminder.SortByPriority());
        liveReminderList.setValue(list);

    }
}
