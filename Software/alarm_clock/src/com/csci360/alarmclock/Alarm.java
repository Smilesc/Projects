package com.csci360.alarmclock;

public class Alarm extends Time{
    public int hours;
    public int minutes;
    public int[] alarmTime = new int[2];

    public Alarm() {
        hours = 25;
        minutes = 70;
    }

    public void setAlarm(int h, int m) {
        hours = h;
        minutes = m;
    }
    public void unSetAlarm(){
        hours = 25;
        minutes = 70;
    }
    public int[] getTime(){
        alarmTime[0] = hours;
        alarmTime[1] = minutes;
        return alarmTime;

    }

}
