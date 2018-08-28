

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.csci360.alarmclock.AlarmClockSystem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 *
 */
public class AlarmClockUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public AlarmClockSystem testSys = new AlarmClockSystem();
    public Text clockMinutes = new Text();
    public Text clockHours = new Text();
    public Button timeFormatButton = new Button();
    public Text amPmButton = new Text();
    public Text alarm1Time = new Text();
    public Text alarm2Time = new Text();
    public ToggleButton setTimeButton = new ToggleButton();
    public ToggleButton setAlarm1Button = new ToggleButton();
    public ToggleButton setAlarm2Button = new ToggleButton();
    public ToggleButton hoursButton = new ToggleButton();
    public ToggleButton minutesButton = new ToggleButton();
    public Button plusButton = new Button();
    public Button minusButton = new Button();
    public Button unsetAlarmsButton = new Button();
    public Slider radioSlider = new Slider();
    public ToggleButton radioButton = new ToggleButton();
    public ToggleButton silenceButton = new ToggleButton();
    public Text alarm1SetLabel = new Text();
    public Text alarm2SetLabel = new Text();
    public Rectangle topSetRect1 = new Rectangle();
    public Rectangle topSetRect2 = new Rectangle();
    public boolean onOff = false;
    public ToggleButton amButton = new ToggleButton();
    public ToggleButton pmButton = new ToggleButton();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testSys.setClock(8, 30);
        amPmButton.setText(testSys.getAmPm());
        testSys.setClockAlarm(1, 9, 35);
        testSys.setClockAlarm(2, 11, 40);
        Timer timer = new Timer();

        clockMinutes.setText(testSys.getMinutes());
        clockHours.setText(testSys.getHours());

        timer.scheduleAtFixedRate(new TimerTask() {
           public void run() {
             clockMinutes.setText(testSys.getMinutes());
             clockHours.setText(testSys.getHours());
             amPmButton.setText(testSys.getAmPm());
             alarm1Time.setText(testSys.getAlarmTime(1));
             alarm2Time.setText(testSys.getAlarmTime(2));
             if(testSys.compareAlarmHours())
                 if(testSys.compareAlarmMinutes())
                     if(!silenceButton.isSelected())
                         //System.out.println("Alarm Sounding");
                         testSys.ring();
                     else 
                         testSys.stopRing();
                   
             
           }
        }, 1000, 1000);
    }

    /**
     * Adjusts displayed time format and 12hour/24hour indicator
     */

    public void changeFormat() {
        testSys.changeFormat();
        String clockFormat = Integer.toString(testSys.masterClock.format);
        timeFormatButton.setText(clockFormat + "hr");

        if (testSys.masterClock.format == 24) {
            amPmButton.setOpacity(0);
            //update displayed format

        } else {
            amPmButton.setOpacity(1);
            //clockHours.setText(testSys.formatHours(testSys.getClockHours())); //this should format the time correctly when the format is switched. Also is not

        }
    }

    /**
     * this should function so that when the setTimeButton is deselected, the
     * system backs out of time-setting mode. 
     *
     */
    public void setTimeButtonControl() {
        if (setTimeButton.isSelected()) {

            plusButton.disableProperty();
            minusButton.disableProperty();

        }
    }

    public void increment() {
        if (setAlarm1Button.isSelected()) {
            if (hoursButton.isSelected()&&(testSys.masterClock.Alarm1.alarmTime[0] < 24)) {

            if(testSys.masterClock.alarmsUnset){
                testSys.masterClock.setAlarm(1, 0, 0);
            }
            if (hoursButton.isSelected() && (testSys.masterClock.Alarm1.alarmTime[0] < 23)) {
                testSys.masterClock.alarmsUnset = false;

                //get current Alarm1 hours
                int myHours = testSys.masterClock.Alarm1.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm1.alarmTime[1];

                //increment Alarm1 hours
                testSys.masterClock.setAlarm(1, myHours + 1, myMinutes);
                
            } else if (minutesButton.isSelected()&&(testSys.masterClock.Alarm1.alarmTime[1] <= 60)) {


                //get current Alarm1 minutes
                int myHours = testSys.masterClock.Alarm1.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm1.alarmTime[1];

                //increment Alarm1 minutes
                testSys.masterClock.setAlarm(1, myHours, myMinutes + 1);
            }
            
        }
        }

        if (setAlarm2Button.isSelected()) {
            if(testSys.masterClock.alarmsUnset){
                testSys.masterClock.setAlarm(2, 0, 0);
            }
            if (hoursButton.isSelected() && (testSys.masterClock.Alarm2.alarmTime[0] < 23)) {
                testSys.masterClock.alarmsUnset = false;

                //get current Alarm2 hours
                int myHours = testSys.masterClock.Alarm2.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm2.alarmTime[1];

                //increment Alarm2 hours
                testSys.masterClock.setAlarm(2, myHours + 1, myMinutes);

                
            } else if (minutesButton.isSelected()&&(testSys.masterClock.Alarm2.alarmTime[1] <=60)) {


                //get current Alarm2 minutes
                int myHours = testSys.masterClock.Alarm2.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm2.alarmTime[1];

                //increment Alarm2 minutes
                testSys.masterClock.setAlarm(2, myHours, myMinutes + 1);
            }
        }

        if (setTimeButton.isSelected()) {
            if (hoursButton.isSelected()) {

                //increment time hours
                testSys.setClockHours(testSys.getClockHours() + 1);
            }

            if (minutesButton.isSelected()) {

                //increment time minutes
                testSys.setClockMinutes(testSys.getClockMinutes() + 1);
            }
        }

    }
   
    public void decrement(){
        if (setAlarm1Button.isSelected()) {
            if (hoursButton.isSelected() && (testSys.masterClock.Alarm1.alarmTime[0] != 0)) {

                //get current Alarm1 hours
                int myHours = testSys.masterClock.Alarm1.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm1.alarmTime[1];
                //decrement Alarm1 hours
                testSys.masterClock.setAlarm(1, myHours - 1, myMinutes);

            } else if (minutesButton.isSelected() && (testSys.masterClock.Alarm1.alarmTime[1] != 0)) {

                //get current Alarm1 minutes
                int myHours = testSys.masterClock.Alarm1.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm1.alarmTime[1];
                //decrement Alarm1 minutes
                testSys.masterClock.setAlarm(1, myHours, myMinutes - 1);
            }
        }

        if (setAlarm2Button.isSelected()) {
            if (hoursButton.isSelected() && (testSys.masterClock.Alarm2.alarmTime[0] != 0)) {

                //get current Alarm2 hours
                int myHours = testSys.masterClock.Alarm2.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm2.alarmTime[1];
                //decrement Alarm2 hours
                testSys.masterClock.setAlarm(2, myHours - 1, myMinutes);

            } else if (minutesButton.isSelected() && (testSys.masterClock.Alarm2.alarmTime[1] != 0)) {

                //get current Alarm2 minutes
                int myHours = testSys.masterClock.Alarm2.alarmTime[0];
                int myMinutes = testSys.masterClock.Alarm2.alarmTime[1];
                //decrement Alarm2 minutes
                testSys.masterClock.setAlarm(2, myHours, myMinutes - 1);
            }
        }

        if (setTimeButton.isSelected()) {
            if (hoursButton.isSelected() && (testSys.getClockHours() != 0)) {

                //decrement time hours
                testSys.setClockHours(testSys.getClockHours() - 1);
            }

            if (minutesButton.isSelected() && (testSys.getClockMinutes() != 0)) {
                //decrement time minutes
                testSys.setClockMinutes(testSys.getClockMinutes() - 1);
            }
        }
    }

    public void unsetAlarms() {
        testSys.masterClock.unsetAlarm(1);
        testSys.masterClock.unsetAlarm(2);
        //alarm1Time.setText("");
        //alarm2Time.setText("");
        //alarm1SetLabel.setText("");
        //alarm2SetLabel.setText("");
        //topSetRect1.setOpacity(0);
        //topSetRect2.setOpacity(0);

    }

    public void tuneRadio() {
        if(!radioButton.isSelected()){
            onOff = !onOff;
            if(onOff){
                System.out.println("Radio is on");
                Double station = radioSlider.getValue();
                System.out.println("Station : " + station);
                if(amButton.isSelected())
                    System.out.println("Modulation: AM");
                else
                    System.out.println("Modulation: FM");
            }
            else
            System.out.println("Radio off");
            
        }
        
    }
    public void getStation(){
        Double station = radioSlider.getValue();
        System.out.println("Station: " + station);
        if(amButton.isSelected())
                    System.out.println("Modulation: AM");
                else
                    System.out.println("Modulation: FM");
    }

}
