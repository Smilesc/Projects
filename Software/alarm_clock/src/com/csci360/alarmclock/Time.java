package com.csci360.alarmclock;


public class Time{

    public int seconds;
    public int minutes;
    public int hours;
    public int[] displayTime = new int[3];

    public int getSeconds(){
        return seconds;
    }

    public int getMinutes(){
        return minutes;
    }
    public int getHours(){
        return hours;
    }

    public void setSeconds(){
        seconds = 0;
    }
    public void setMinutes( int inMin){
        minutes = inMin;
    }
    public void setHours(int inHour){
        hours = inHour;
    }

    public int[] getTime(){
        displayTime[0] = hours;
        displayTime[1] = minutes;
        displayTime[2] = seconds;
        return displayTime;
    }

    public Time(){
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;

    }
    public Time(int initializedHour, int initializedMinutes){
        this.minutes = initializedMinutes;
        this.hours = initializedHour;
        this.seconds = 0;
    }

    public void tick(){

        this.seconds++;
        if(this.seconds == 60) {
            this.minutes++;
            this.seconds = 0;
            if (this.minutes == 60) {
                this.hours++;
                this.minutes = 0;
                if(this.hours == 24){
                    this.hours = 0;
                }
            }
        }
    }


}