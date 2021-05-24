package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Builder;

@Builder
public class Note {
    private int noteid;
    private String notetitle;
    private String notedescription;

    public Note() {
    }

    public Note(int noteid, String notetitle, String notedescription) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
    }

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
}