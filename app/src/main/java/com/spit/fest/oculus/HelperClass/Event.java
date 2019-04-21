package com.spit.fest.oculus.HelperClass;

import java.util.ArrayList;

public class Event
{
    private String category;
    private ArrayList<String> date;
    private String timing;
    private String description;
    private String imageApp;
    private Pocs pocs;
    private String prizes;
    private String registration_fee;
    private String venue;
    private double avgRating;
    private String title;
    private String email;
    private int totRegistrations;
    private int peopleRated;
    private String eventUrl;
    private int id;

    public Event() {
    }

    public Event(String category, ArrayList<String> date, String timing, String description, String imageApp, Pocs pocs, String prizes, String registration_fee, String venue, double avgRating, String title, String email, int totRegistrations, int peopleRated, String eventUrl, int id) {
        this.category = category;
        this.date = date;
        this.timing = timing;
        this.description = description;
        this.imageApp = imageApp;
//        this.imageWebsite = imageWebsite;
        this.pocs = pocs;
        this.prizes = prizes;
        this.registration_fee = registration_fee;
        this.venue = venue;
        this.avgRating = avgRating;
        this.title = title;
        this.email = email;
        this.totRegistrations = totRegistrations;
        this.peopleRated = peopleRated;
        this.eventUrl = eventUrl;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageApp() {
        return imageApp;
    }

    public void setImageApp(String imageApp) {
        this.imageApp = imageApp;
    }

    public Pocs getPocs() {
        return pocs;
    }

    public void setPocs(Pocs pocs) {
        this.pocs = pocs;
    }

    public String getPrizes() {
        return prizes;
    }

    public void setPrizes(String prizes) {
        this.prizes = prizes;
    }

    public String getRegistration_fee() {
        return registration_fee;
    }

    public void setRegistration_fee(String registration_fee) {
        this.registration_fee = registration_fee;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotRegistrations() {
        return totRegistrations;
    }

    public void setTotRegistrations(int totRegistrations) {
        this.totRegistrations = totRegistrations;
    }

    public int getPeopleRated() {
        return peopleRated;
    }

    public void setPeopleRated(int peopleRated) {
        this.peopleRated = peopleRated;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
