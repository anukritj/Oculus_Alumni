package com.spit.fest.oculus.HelperClass;

import java.util.ArrayList;

public class EventTimeline
{
    private String date;
    private ArrayList<Timing> timings;

    public EventTimeline() {
    }

    public EventTimeline(String date, ArrayList<Timing> timings) {
        this.date = date;
        this.timings = timings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Timing> getTimings() {
        return timings;
    }

    public void setTimings(ArrayList<Timing> timings) {
        this.timings = timings;
    }
}
