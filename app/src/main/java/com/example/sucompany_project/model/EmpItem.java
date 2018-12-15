package com.example.sucompany_project.model;

import java.util.Locale;

public class EmpItem {

    public final long _id;
    public final String name;
    public final String age;
    public final String position;
    public final String department;
    public final String tel;

    public EmpItem(long _id, String name, String age, String position , String department , String tel) {
        this._id = _id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.department = department;
        this.tel = tel;
    }

    @Override
    public String toString() {
        String msg = String.format(
                Locale.getDefault(),
                "%s (%s)",
                this.name,
                this.age
        );
        return msg;
    }
}