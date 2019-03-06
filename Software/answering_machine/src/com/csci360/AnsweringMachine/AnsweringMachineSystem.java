package com.csci360.AnsweringMachine;

import java.util.Stack;
import java.util.ArrayList;

public class AnsweringMachineSystem {
	
	private final Stack<Message> UnheardMessages = new Stack<>();
	private final ArrayList<Message> allMessages = new ArrayList<>();
	private Message currentMessage = new Message("No current message");
	private String greeting = "The user is away. Please leave a message.";
	
	public AnsweringMachineSystem() {
		
	}
	
        /**
         * 
         * @param message String
         */
	public void leaveMessage(String message) {
		Message newMessage = new Message(message);
		
		UnheardMessages.push(newMessage);
		allMessages.add(newMessage);	
	}
        
        /**
         * 
         * @return Message currentMessage
         */
        public Message getCurrentMessage(){
            return this.currentMessage;
        }
        
        /**
         * 
         * @param message 
         */
        public void setCurrentMessage(Message message){
            this.currentMessage = message;
        }
        
        /**
         * 
         * @return String currentMessage's message
         */
        public String getCurrentMessageText(){
            return this.currentMessage.getMessage();
        }
        
        /**
         * 
         * @param messageIndex 
         */
	public void selectDelete(int messageIndex) {
		if(UnheardMessages.contains(allMessages.get(messageIndex))){
			UnheardMessages.remove(messageIndex);
		}
		
		allMessages.remove(messageIndex);
	}
        
        /**
         * 
         * @param message 
         */
        public void selectDelete(Message message){    
            
            allMessages.remove(message);
            System.out.println("Message deleted");
            
        }
	
        /**
         * Retrieves any message
         * @param messageIndex
         * @return String
         */
	public String listenToAllMessage(int messageIndex) {
            String message;
            
            if(allMessages.isEmpty()){
                message = "No more messages";
            }
            
            else{
                message = allMessages.get(messageIndex).getMessage();	
            }
            
            return message;
	}
        
        /**
         * Retrieves any unheard message
         * @return Message
         */
	public Message listenToUnheardMessage() {
            Message message = new Message(null);
            
            if(UnheardMessages.isEmpty()){
                message.setMessage("No more unheard messages");
            }
            
            else{
                message.setMessage(UnheardMessages.pop().getMessage());	
            }
            
            return message;
	}
        
        /**
         * 
         * @param messageIndex
         * @return Message 
         */
	public Message selectReplay(int messageIndex) {	
                return allMessages.get(messageIndex);
	}
        
        /**
         * 
         * @param message
         * @return Message
         */
        public String selectReplay(){	
                return getCurrentMessageText();
        }
	
        /**
         * 
         * @return String
         */
	public String getGreeting() {
		return greeting;
	}
	
        /**
         * 
         * @param greeting 
         */
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	
        /**
         * 
         * @return int
         */
	public int numUnheardMessages() {		
		return UnheardMessages.size();
	}
	
        /**
         * 
         * @return int 
         */
	public int numallMessages() {
		return allMessages.size();
	}
		
}
