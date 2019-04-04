package com.example.recyclerview;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventsViewHolder> {
    private List<Event> eventItems;
    private EventHandler eventHandler;    

    public EventAdapter(List<Event> eventItems, EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.eventItems = eventItems;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event, viewGroup, false);
        return new EventsViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final EventsViewHolder eventsViewHolder, int i) {
        eventsViewHolder.priorityLevel.setOnSeekBarChangeListener(null);
        eventsViewHolder.enable.setOnCheckedChangeListener(null);
        eventsViewHolder.accountImage.setImageResource(R.mipmap.ic_launcher_account);
        eventsViewHolder.taskTitle.setText(eventItems.get(i).getTitle());
        eventsViewHolder.taskDescription.setText(eventItems.get(i).getNote());
        eventsViewHolder.taskDueDate.setText(getDateString(eventItems.get(i).getDate()));
        eventsViewHolder.priorityLevel.setProgress(eventItems.get(i).getPriority());
        eventsViewHolder.enable.setChecked(eventItems.get(i).getStatus());
        eventsViewHolder.delete.setImageResource(R.drawable.ic_delete);

        final int j = i;
        eventsViewHolder.priorityLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newPriority = eventsViewHolder.priorityLevel.getProgress();
                eventItems.get(j).setPriority(newPriority);
                eventHandler.onItemClick(j);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        eventsViewHolder.enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean newStatus = eventsViewHolder.enable.isChecked();
                eventItems.get(j).setStatus(newStatus);
                eventHandler.onItemClick(j);
            }
        });


        eventsViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventItems.remove(j);
                eventHandler.onItemClick(j);
            }
        });
    }

    private String getDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        return dateFormat.format(date);
    }

    @Override
    public int getItemCount() { return eventItems.size(); }

    static class EventsViewHolder extends RecyclerView.ViewHolder {
        private ImageButton delete;
        private ImageView accountImage;
        private SeekBar priorityLevel;
        private Switch enable;
        private TextView taskTitle, taskDescription, taskDueDate;

        public EventsViewHolder(View rowView) {
            super(rowView);

            accountImage = rowView.findViewById(R.id.accountImage);
            taskTitle = rowView.findViewById(R.id.taskTitle);
            taskDescription = rowView.findViewById(R.id.taskDescription);
            taskDueDate = rowView.findViewById(R.id.taskDueDate);
            priorityLevel = rowView.findViewById(R.id.priorityLevel);
            priorityLevel.setMax(4);
            enable = rowView.findViewById(R.id.enable);
            delete = rowView.findViewById(R.id.delete);
        }
    }

    // TODO: Check Warnings
    public String parseDate(String time) {
        String  input = "dd/MM/yyyy";
        String output = "dd MMMM";
        SimpleDateFormat  inputFormat = new SimpleDateFormat(input);
        SimpleDateFormat outputFormat = new SimpleDateFormat(output);
        String str = "";
        try {
            Date date = inputFormat.parse(time);
            str = outputFormat.format(date);
        }
        catch (ParseException err) {
            err.printStackTrace();
        }
        return str;
    }

    interface EventHandler {
        void onItemClick(int index);
        // void delete(int index);
        // void seekBar(int j);
    }
}
