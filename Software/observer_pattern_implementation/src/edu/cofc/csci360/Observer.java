package edu.cofc.csci360;

public abstract class Observer {
	
	public Subject subject;
	
	/**
	 * Executed when subject pushes updates to subscribed observers
	 */
	public abstract void update();

}
