package com.csci360.AnsweringMachine;

public class Message {
	private String message = null;
	
	public Message(String message) {
		this.message = message;
	}
        
        public void setMessage(String message){
            this.message = message;
        }

	public String getMessage() {
		return message;
	}
}