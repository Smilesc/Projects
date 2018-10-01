/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.csci360.AnsweringMachine.AnsweringMachineSystem;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author zaefi
 */
public class AnsweringMachineUIController implements Initializable{
    
    public Button listenToGreetingButton = new Button();
    public Button setGreetingButton = new Button();
    private final Scanner keyboardIn = new Scanner(System.in);
    public AnsweringMachineSystem testSys = new AnsweringMachineSystem();
    public Button playButton = new Button();
    public Text UINumUnheard = new Text();
    public Text UICurrentMessage = new Text();
    public TextField setGreetingTextField = new TextField();

	
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testSys.leaveMessage("Hey what's up");
        testSys.leaveMessage("Where are you?");
        testSys.leaveMessage("Call me back");
        
        UINumUnheard.setText(Integer.toString(testSys.numUnheardMessages()));
      
    }    
    //public void showTextField(){
     //   setGreetingTextField.setOpacity(1.0);
        
    //}
    public void setGreeting() {     
        System.out.println("Enter a greeting: ");
        String userInput = keyboardIn.nextLine();
        testSys.setGreeting(userInput);
    }    
    
    public void listenToGreeting(){
        System.out.println(testSys.getGreeting());
        UICurrentMessage.setText(testSys.getGreeting());
    }
    
    public void deleteMessage(){
        
        testSys.selectDelete(testSys.currentMessage);
    }
    
    public void replayMessage(){
        System.out.println(testSys.selectReplay(testSys.currentMessage).getMessage());
    }
    
    public void playMessage(){
        testSys.currentMessage = testSys.listenToUnheardMessage(0);
        System.out.println(testSys.currentMessage.getMessage());  
        UINumUnheard.setText(Integer.toString(testSys.numUnheardMessages()));
        UICurrentMessage.setText(testSys.currentMessage.getMessage());
    }

}
