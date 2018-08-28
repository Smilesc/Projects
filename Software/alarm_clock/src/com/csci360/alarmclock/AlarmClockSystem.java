package com.csci360.alarmclock;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.File;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer; 


public class AlarmClockSystem {

    public Clock masterClock;
    public Radio masterRadio;
    public File file=new File("alarmSound.mp3");
    public Media m = new Media(file.toURI().toString());
    public MediaPlayer player = new MediaPlayer(m);

    public AlarmClockSystem() {
        masterClock = new Clock();
        masterRadio = new Radio();
        masterClock.startTime();
    }
    public void ring(){
  
        player.play();
    }
    public void stopRing(){
        player.pause();
    }

    public void tuneRadio(String mod, double freq) {
        masterRadio.tune(mod, freq);
    }

    public String getRadioStats() {

        return masterRadio.getRadio()[0] + ":" + masterRadio.getRadio()[1];
    }

    public Clock getClock() {
        return masterClock;
    }

    public String getHours() {
        if(masterClock.format == 12)
            if(masterClock.currentTime.getHours() > 12)
                return Integer.toString(masterClock.currentTime.getHours() -12);
        return Integer.toString(masterClock.currentTime.getHours());
    }

    public String getMinutes() {
        return Integer.toString(masterClock.currentTime.getMinutes());
    }

    public void setClock(int hours, int minutes) {
        //masterClock.endTime();
        masterClock.setClockTime(hours, minutes);
        //masterClock.startTime();
    }

    public void setClockHours(int hours) {
        if(masterClock.format == 12)
            if(masterClock.currentTime.hours >= 12){
                masterClock.setHours(1);
                masterClock.changeAMPM();
            }
            else
                masterClock.setHours(hours);
        if(masterClock.format ==24)
            if(masterClock.currentTime.hours >= 24)
                masterClock.setHours(0);
        masterClock.startTime();
    }
    
    public String getAmPm(){
        if(masterClock.ampm)
            return "AM";
        else
            return "PM";
    }
    public int getClockHours(){
        
        return masterClock.currentTime.getHours();
    }

    public int getClockMinutes() {
        return masterClock.currentTime.getMinutes();
    }

    public void setClockMinutes(int minutes) {
        if(masterClock.currentTime.minutes >= 60)
            masterClock.setMinutes(0);
        else
            masterClock.setMinutes(minutes);
    }

    public void changeFormat() {
        if (masterClock.format == 12) {
            masterClock.changeFormat(24);
        } else if (masterClock.format == 24) {
            masterClock.changeFormat(12);
        }
    }
    
    /* I added this method to help with displaying time in the UI.
    The old display method in Clock was designed for the system console 
    */
    public String formatHours(int hours) {
        String formattedOutput = Integer.toString(hours);

        if (masterClock.format == 12) {
            if (hours == 0) {
                hours = 12;
            } else if (hours > 12) {
                hours = (hours - 12);
            }
        } else {
            if(hours == 12 && masterClock.AM == true){
                hours = 0;
            }
            else if(masterClock.AM = false){
                hours = (hours + 12); //this is never used because AM is never set to false anywhere--
                                      //either I missed it or we left it out by accident so I added it in Clock
       
            }
        }
  
        if (hours < 10) {

            formattedOutput = "0" + hours;
        }

        return formattedOutput;
    }
    
    public String formatMinutes(int minutes){
        String formattedOutput = Integer.toString(minutes);
        if(minutes < 10){
            formattedOutput = "0" + minutes;
        }
        return formattedOutput;
    }

    public void setClockAlarm(int num, int h, int m) {
        masterClock.setAlarm(num, h, m);
    }

    public void unSetClockAlarm(int num) {
        masterClock.unsetAlarm(num);
    }
    public String getAlarmTime(int num){
        return masterClock.outputAlarmTime(num);
    }

    public boolean compareAlarmHours() {
        return masterClock.compareHours();
    }

    public boolean compareAlarmMinutes() {
        return masterClock.compareMinutes();
    }

//   public static void main(String[] args) {
//        AlarmClockSystem tester = new AlarmClockSystem();
//        //tester.getClock().setClockTime();
//        boolean looper1 = true;
//        ///*
//        while (looper1 == true) {
//
//            System.out.print("Enter Command You wish to Execute: ");
//            Scanner sc = new Scanner(System.in);
//            String command = sc.next();
//            if (command.equals("setClock")) {
//                System.out.print("\nEnter Hours: ");
//                int h = sc.nextInt();
//                System.out.print("\nEnter Minutes: ");
//                int m = sc.nextInt();
//                tester.setClock(h, m);
//                System.out.println("\nClock Updated");
//                System.out.println(tester.getClock().outputTime());
//
//            } else if (command.equals("setClockHours")) {
//                System.out.print("\nEnter Hours: ");
//                int h = sc.nextInt();
//                tester.setClockHours(h);
//                System.out.println("\nClock Updated");
//                System.out.println(tester.getClock().outputTime());
//
//            } else if (command.equals("setClockMinutes")) {
//                System.out.print("\nEnter Minutes: ");
//                int m = sc.nextInt();
//                tester.setClockMinutes(m);
//                System.out.println("\nClock Updated");
//                System.out.println(tester.getClock().outputTime());
//
//            } else if (command.equals("changeFormat")) {
//                tester.changeFormat();
//                System.out.println("\nClock Updated");
//                System.out.println(tester.getClock().outputTime());
//
//            } else if (command.equals("unSetClockAlarm")) {
//                System.out.print("\nEnter Alarm to Unset: ");
//                int num = sc.nextInt();
//                tester.unSetClockAlarm(num);
//
//            } else if (command.equals("setClockAlarm")) {
//                System.out.print("\nEnter Alarm Number");
//                int num = sc.nextInt();
//                System.out.print("\nEnter Hours: ");
//                int h = sc.nextInt();
//                System.out.print("\nEnter Minutes: ");
//                int m = sc.nextInt();
//                tester.setClockAlarm(num, h, m);
//                System.out.println("\nAlarm " + num + " Updated to....");
//                System.out.println(tester.getClock().outputAlarmTime(num));
//
//            } else if (command.equals("showTime")) {
//                System.out.println(tester.getClock().outputTime());
//            } else if (command.equals("showClock")) {
//                looper1 = false;
//
//            } else if (command.equals("tuneRadio")) {
//                System.out.print("\nTuning The Radio");
//                System.out.print("\nEnter Radio ModulationAM/FM : \n");
//                String mod = sc.next();
//                System.out.print("\nEnter Frequency: ");
//                System.out.print("\n88-108 for FM, 540-1700 for AM \n");
//                Double freq = sc.nextDouble();
//                tester.tuneRadio(mod, freq);
//                System.out.print("\nRadio Tuned current status is.... \n");
//                System.out.print(tester.getRadioStats());
//                System.out.print("\ncurrent Time is....");
//                System.out.println(tester.getClock().outputTime());
//
//            } else {
//                System.out.println("Invalid Command: Current AlarmClockSystem time is....");
//                System.out.println(tester.getClock().outputTime());
//            }
//
//        }
//        //*/
//
//        while (true) {
//            try {
//                System.out.println(tester.getClock().outputTime());
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//
//            }
//        }
//
//    }
}
