package com.example.persistent_storage;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReminderViewModel extends AndroidViewModel {
    private LiveData<List<Reminder>> liveDataOrderPriority;
    private LiveData<List<Reminder>> liveDataOrderDate;
    private MediatorLiveData<List<Reminder>> mediatorLiveData;
    private ReminderDatabase database;
    private ExecutorService executor;
    private enum Order{PRIORITY, DATE}
    private Order currentOrder = Order.PRIORITY;

    public ReminderViewModel(Application application) {
        super(application);
        executor = Executors.newSingleThreadExecutor();
        database = ReminderDatabase.getInstance(getApplication().getApplicationContext());
        liveDataOrderPriority =  database.getReminderDao().orderByPriority();
        liveDataOrderDate = database.getReminderDao().orderByDate();
        mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.setValue(new ArrayList<Reminder>());
        setObservers();
        sortByPriority();
    }


    public void populateDatabase() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
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
                        database.getReminderDao().deleteAll();
                        database.getReminderDao().insertReminders(reminders);
                    }
                });
            }
        });
        setObservers();
    }

    private void setObservers() {
        mediatorLiveData.removeSource(liveDataOrderPriority);
        mediatorLiveData.removeSource(liveDataOrderDate);
        mediatorLiveData.addSource(liveDataOrderPriority, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                if (currentOrder == Order.PRIORITY) { mediatorLiveData.setValue(reminders); }
            }
        });
        mediatorLiveData.addSource(liveDataOrderDate, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                if (currentOrder == Order.DATE) { mediatorLiveData.setValue(reminders); }
            }
        });
    }
    public void deleteAllDatabase() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getReminderDao().deleteAll();
            }
        });
        liveDataOrderPriority =  database.getReminderDao().orderByPriority();
        liveDataOrderDate = database.getReminderDao().orderByDate();
    }
    public LiveData<List<Reminder>> getLiveReminderList() {
        return mediatorLiveData;
    }
    public List<Reminder> getReminderList() {
        return mediatorLiveData.getValue();
    }

    /* Sort Methods */
    public void sortByDate() {
        currentOrder = Order.DATE;
        mediatorLiveData.setValue(liveDataOrderDate.getValue());
    }
    public void sortByPriority() {
        currentOrder = Order.PRIORITY;
        mediatorLiveData.setValue(liveDataOrderPriority.getValue());
    }

    /* Setters */
    public void remove(int index) {
        List<Reminder> list = getReminderList();
        final int rid = list.get(index).getRid();
        list.remove(index);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getReminderDao().deleteByID(rid);
            }
        });

    }
    public void setPriority(int priority, int index) {
        Reminder reminder = getReminderList().get(index);
        reminder.setPriority(priority);
        updateSingleDatabaseItem(reminder);
    }
    public void switchEnabled(int index) {
        Reminder reminder = getReminderList().get(index);
        reminder.setStatus(! reminder.getStatus());
        updateSingleDatabaseItem(reminder);
    }
    public void updateReminder(int index, String title, String note, int priority, Long time) {
        List <Reminder> list = getReminderList();
        Reminder reminder = list.get(index);
        reminder.setTitle(title);
        reminder.setNote(note);
        reminder.setTime(time);
        reminder.setPriority(priority);
        updateSingleDatabaseItem(reminder);
    }
    public void updateDatabase(final List<Reminder> reminderList) {
        if(reminderList != null) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Reminder[] reminders = reminderList.toArray(new Reminder[]{});
                    database.getReminderDao().updateReminders(reminders);
                }
            });
        }
    }
    public void updateSingleDatabaseItem(final Reminder reminder) {
        if (reminder != null) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    database.getReminderDao().updateSingleReminder(reminder);
                }
            });
        }
    }
}