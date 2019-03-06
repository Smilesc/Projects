package com.csci360.AnsweringMachine;

public class Message {
	private String message = null;
	
        /**
         * 
         * @param message 
         */
	public Message(String message) {
		this.message = message;
	}
        
        /**
         * 
         * @param message 
         */
        public void setMessage(String message){
            this.message = message;
        }

        /**
         * 
         * @return String
         */
	public String getMessage() {
		return message;
	}
}