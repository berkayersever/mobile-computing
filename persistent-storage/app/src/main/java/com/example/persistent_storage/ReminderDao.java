package com.example.persistent_storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM reminders")
    LiveData<List<Reminder>> getAllReminders();

    @Query("SELECT * FROM reminders WHERE rid = :rid")
    LiveData<Reminder> loadReminder(int rid);

    @Query("DELETE FROM reminders")
    void deleteAll();

    @Query("DELETE FROM reminders WHERE rid = :rid")
    void deleteByID(int rid);

    @Query("SELECT * FROM reminders ORDER BY CALENDAR ASC")
    LiveData<List<Reminder>> orderByDate();

    @Query("SELECT * FROM reminders ORDER BY PRIORITY DESC")
    LiveData<List<Reminder>> orderByPriority();

    @Update
    void updateSingleReminder(Reminder reminder);

    @Update
    void updateReminders(Reminder... reminders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReminders(Reminder... reminders);
}