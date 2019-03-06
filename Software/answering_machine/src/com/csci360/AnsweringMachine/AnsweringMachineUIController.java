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
    
    private final Scanner keyboardIn = new Scanner(System.in);
    public AnsweringMachineSystem testSys = new AnsweringMachineSystem();
    public Text UINumUnheard = new Text();
    public Text UICurrentMessage = new Text();
    public TextField setGreetingTextField = new TextField();

	
  
    /**
     * Initialize the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testSys.leaveMessage("Hey what's up");
        testSys.leaveMessage("Where are you?");
        testSys.leaveMessage("Call me back");
        
        UINumUnheard.setText(Integer.toString(testSys.numUnheardMessages()));
      
    }    
    
    /**
     * Change greeting
     */
    public void setGreeting() {     
        System.out.println("Enter a greeting: ");
        String userInput = keyboardIn.nextLine();
        testSys.setGreeting(userInput);
    }    
    
    /**
     * Display greeting
     */
    public void listenToGreeting(){
        System.out.println(testSys.getGreeting());
        UICurrentMessage.setText(testSys.getGreeting());
    }
    
    /**
     * Delete current message
     */
    public void deleteMessage(){
        
        testSys.selectDelete(testSys.getCurrentMessage());
    }
    
    /**
     * replay message
     */
    public void replayMessage(){
        System.out.println(testSys.selectReplay());
    }
    
    /**
     * Display next unheard message
     */
    public void playMessage(){
        testSys.setCurrentMessage(testSys.listenToUnheardMessage());
        System.out.println(testSys.getCurrentMessageText());  
        UINumUnheard.setText(Integer.toString(testSys.numUnheardMessages()));
        UICurrentMessage.setText(testSys.getCurrentMessageText());
    }

}
