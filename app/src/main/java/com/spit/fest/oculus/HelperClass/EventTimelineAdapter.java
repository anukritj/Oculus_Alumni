package com.spit.fest.oculus.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spit.fest.oculus.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventTimelineAdapter extends RecyclerView.Adapter<EventTimelineAdapter.EventTimelineViewHolder>
{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<EventTimeline> events;

    public EventTimelineAdapter(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        events = new ArrayList<>();
    }

    public void addEventsToTimeline(EventTimeline eventTimeline)
    {
        events.add(eventTimeline);
    }

    @NonNull
    @Override
    public EventTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EventTimelineViewHolder(layoutInflater.inflate(R.layout.item_timeline, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventTimelineViewHolder eventTimelineViewHolder, int i) {
        eventTimelineViewHolder.setDate(events.get(i).getDate());
        eventTimelineViewHolder.setEvents(events.get(i).getTimings());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventTimelineViewHolder extends RecyclerView.ViewHolder
    {

        private TextView day;
        private TextView month;
        private TextView date;
        private RecyclerView recyclerView;

        EventTimelineViewHolder(@NonNull View itemView)
        {
            super(itemView);
            day = itemView.findViewById(R.id.event_day_textview);
            date = itemView.findViewById(R.id.event_date_textview);
            month = itemView.findViewById(R.id.event_month_textview);
            recyclerView = itemView.findViewById(R.id.event_recyclerview);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        public void setDate(String dateString)
        {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date eventDate = null;
            try {
                eventDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eventDate);
            String dayOfWeek[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
            String monthsOfYear[]= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            String day = dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK)-1];
            String month = monthsOfYear[calendar.get(Calendar.MONTH)];
            this.day.setText(day);
            this.month.setText(month);
            String date = String.valueOf(calendar.get(Calendar.DATE));
            this.date.setText(date);
            Date dateObj = new Date();
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.constraintLayout);
            ImageView imageView = itemView.findViewById(R.id.check);
            if (dateFormat.format(dateObj).equals(dateString))
                constraintLayout.setBackgroundResource(R.drawable.selected_date_bg);
            else
            {
                constraintLayout.setBackgroundResource(R.drawable.date_bg);
                assert eventDate != null;
                if (eventDate.before(dateObj))
                {
                    imageView.setVisibility(View.VISIBLE);
                    this.date.setVisibility(View.GONE);
                    this.month.setVisibility(View.GONE);
                }
                else
                {
                    imageView.setVisibility(View.GONE);
                    this.date.setVisibility(View.VISIBLE);
                    this.month.setVisibility(View.VISIBLE);
                }
            }
        }

        void setEvents(ArrayList<Timing> timings)
        {
            TimingAdapter timingAdapter = new TimingAdapter(context);
            timingAdapter.addEventsToTimeline(timings);
            recyclerView.setAdapter(timingAdapter);
        }
    }
}
