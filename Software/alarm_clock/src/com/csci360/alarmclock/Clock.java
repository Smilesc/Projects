package com.csci360.alarmclock;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Clock {

    public Timer ticker = new Timer();
    public Time currentTime;
    public int format = 12;
    public boolean ampm = false;

    public Alarm Alarm1 = new Alarm();
    public Alarm Alarm2 = new Alarm();
    public boolean isSounding = false;
    public boolean stopSounding = true;
    public boolean AM = true;
    public boolean alarmsUnset;

    public Clock() {
        currentTime = new Time();

    }

    public Clock(int hours, int minutes) {
        currentTime = new Time(hours, minutes);
    }
    public void changeAMPM(){
        ampm = !ampm;
    }
    public boolean compareHours() {
        if (Alarm1.hours == currentTime.hours || Alarm2.hours == currentTime.hours) {
            return true;
        } else {
            return false;
        }
    }

    public boolean compareMinutes() {
        if (Alarm1.minutes == currentTime.minutes || Alarm2.minutes == currentTime.minutes) {
            return true;
        } else {
            return false;
        }
    }

    public void startTime() {
        ticker.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                currentTime.tick();
//                if(compareHours()){
//                    if(compareMinutes()){
//                        isSounding = true;
//                    }
//                    if(stopSounding){
//                        isSounding = false;
//                    }
//                }

            }
        }, 1000, 1000);
    }


    
    public void setClockTime(int hours, int minutes) {
        currentTime.setHours(hours);
        currentTime.setMinutes(minutes);
        currentTime.setSeconds();
    }

    public void setHours(int hours) {
        currentTime.hours = hours;
    }

    public void setMinutes(int minutes) {
        currentTime.minutes = minutes;
    }

    public void endTime() {
        ticker.cancel();
        ticker.purge();
    }

    public void changeFormat(int num) {
        if (num == 24) {
            format = 24;
        } else if (num == 12) {
            format = 12;
        }
    }

    public String outputTime() {
        if (format == 24) {
            return currentTime.getTime()[0] + ":" + currentTime.getTime()[1] + ":" + currentTime.getTime()[2];
        } else {
            if (currentTime.getTime()[0] > 12) {
                return (currentTime.getTime()[0] - 12) + ":" + currentTime.getTime()[1] + ":" + currentTime.getTime()[2] + " AM";
            } else {
                return currentTime.getTime()[0] + ":" + currentTime.getTime()[1] + ":" + currentTime.getTime()[2] + " PM";
            }

        }
    }
    
    

    public void setAlarm(int num, int hours, int minutes) {
        if (num == 1) {
            //alarm1IsSet = true;
            Alarm1.setAlarm(hours, minutes);
        } else if (num == 2) {
            //alarm2IsSet = true;
            Alarm2.setAlarm(hours, minutes);
        }

    }

    public void unsetAlarm(int num) {
        if (num == 1) {
            Alarm1.unSetAlarm();
        } else if (num == 2) {
            Alarm2.unSetAlarm();
        }
    }

    public String outputAlarmTime(int num) {
        if (num == 1) {
            return Alarm1.getTime()[0] + ":" + Alarm1.getTime()[1];
        } else if (num == 2) {
            return Alarm2.getTime()[0] + ":" + Alarm2.getTime()[1];
        } else {
            return "";
        }
    }

    /*
    public static void main(String[] args) {
        Clock x = new Clock();
        int i = 0;
        x.startTime();
        while(true){
            out.println(x.outputTime());
        }
    }
     */
}
