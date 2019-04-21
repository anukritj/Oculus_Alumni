package com.spit.fest.oculus.HelperClass;

public class Pocs
{
    private long contact_no;
    private String name;

    public Pocs() {
    }

    public long getContact_no() {
        return contact_no;
    }

    public void setContact_no(long contact_no) {
        this.contact_no = contact_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pocs(long contact_no, String name)
    {
        this.contact_no = contact_no;
        this.name = name;
    }
}
