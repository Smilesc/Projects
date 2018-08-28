package com.csci360.alarmclock;

//import com.csci360.alarmclock.AlarmClockSystem;
import static org.junit.Assert.*;

public class SystemTester {
    AlarmClockSystem testSys = new AlarmClockSystem();

    @org.junit.Test
    public void setClockAlarmTest(){
        testSys.setClockAlarm(1,8,00);

        assertEquals("8:0", testSys.getClock().outputAlarmTime(1));

    }

    @org.junit.Test
    public void unSetClockAlarmTest(){
        testSys.unSetClockAlarm(1);

        assertEquals("25:70", testSys.getClock().outputAlarmTime(1));

    }

    @org.junit.Test
    public void compareAlarmHoursTest(){
        testSys.setClock(5,20);
        testSys.setClockAlarm(1,5,20);
        assertTrue(testSys.compareAlarmHours());

    }

    @org.junit.Test
    public void compareAlarmMinutesTest(){
        testSys.setClock(5,20);
        testSys.setClockAlarm(1,5,20);
        assertTrue(testSys.compareAlarmMinutes());

    }
    @org.junit.Test
    public void tuneRadioTest(){
        testSys.tuneRadio("FM",90.0);
        assertEquals("FM:90.0", testSys.getRadioStats());

    }

    @org.junit.Test
    public void tuneRadioTest2(){
        testSys.tuneRadio("Nill",0.0);
        assertEquals("FM:88.0", testSys.getRadioStats());
    }

    @org.junit.Test
    public void tuneRadioTest3(){
        testSys.tuneRadio("FM",0.0);
        assertEquals("FM:88.0", testSys.getRadioStats());

    }
    @org.junit.Test
    public void tuneRadioTest4(){
        testSys.tuneRadio("AM",90.0);
        assertEquals("AM:540.0", testSys.getRadioStats());

    }

    @org.junit.Test
    public void setClockTest(){
        testSys.setClock(23,59);
        assertEquals("23:59:1", testSys.getClock().outputTime());

    }
    @org.junit.Test
    public void setClockHoursTest(){
        testSys.setClockHours(21);
        assertEquals("21:0:1", testSys.getClock().outputTime());

    }
    @org.junit.Test
    public void setClockMinutesTest(){
        testSys.setClockMinutes(50);
        assertEquals("0:50:1", testSys.getClock().outputTime());

    }






}