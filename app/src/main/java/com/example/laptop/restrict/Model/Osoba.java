package com.example.laptop.restrict.Model;

/**
 * Created by durma on 5.2.18..
 */

public class Osoba {

    private String name;
    private String title;
    private String eMail;
    private int number;

    public Osoba(String name, String title, String eMail, int number) {
        this.name = name;
        this.title = title;
        this.eMail = eMail;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
