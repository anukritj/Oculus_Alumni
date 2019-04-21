package com.spit.fest.oculus;

public class Friends {
    private String members, groupName;
    private int imageResourceId;
    private boolean join;

    public Friends() {
    }

    public Friends(String groupName, String members, int imageResourceId) {
        this.members = members;
        this.groupName = groupName;
        this.imageResourceId = imageResourceId;
    }

    public String getMembers() {
        return members;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }
}
