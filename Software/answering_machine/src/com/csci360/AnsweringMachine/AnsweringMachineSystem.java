package com.csci360.AnsweringMachine;

import java.util.Stack;
import java.util.ArrayList;
import static java.lang.System.out;
import javafx.fxml.FXML;

public class AnsweringMachineSystem {
	
	public Stack<Message> UnheardMessages = new Stack<Message>();
	public ArrayList<Message> allMessages = new ArrayList<Message>();
	public Message currentMessage = new Message("No current message");
	public String greeting = "The user is away. Please leave a message.";
	
	public AnsweringMachineSystem() {
		
	}
	
	public Message leaveMessage(String message) {
		Message newMessage = new Message(message);
		
		UnheardMessages.push(newMessage);
		allMessages.add(newMessage);
		
		return newMessage;
	}
	
	public void selectDelete(int messageIndex) {
		if(UnheardMessages.contains(allMessages.get(messageIndex))){
			UnheardMessages.remove(messageIndex);
		}
		
		allMessages.remove(messageIndex);
	}
        
        public void selectDelete(Message message){    
            
            allMessages.remove(message);
            System.out.println("Message deleted");
            
        }
	
	public String listenToAllMessage(int messageIndex) {
            String message;
            
            if(allMessages.isEmpty()){
                message = "No more messages";
            }
            
            else{
                message = allMessages.get(messageIndex).getMessage();
            
		//UnheardMessages.remove(allMessages.get(messageIndex));	
            }
            
            return message;
	}
        
	public Message listenToUnheardMessage(int messageIndex) {
            Message message = new Message(null);
            
            if(UnheardMessages.isEmpty()){
                message.setMessage("No more unheard messages");
            }
            
            else{
                message.setMessage(UnheardMessages.pop().getMessage());	
            }
            
            return message;
	}
        
	public Message selectReplay(int messageIndex) {	
                return allMessages.get(messageIndex);
	}
        
        public Message selectReplay(Message message){	
                return message;
        }
	
	public String getGreeting() {
		return greeting;
	}
	
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	
	public int numUnheardMessages() {		
		return UnheardMessages.size();
	}
	
	public int numallMessages() {
		return allMessages.size();
	}
		
}
