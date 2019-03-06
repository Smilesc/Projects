package edu.cofc.csci360;
import java.util.ArrayList;
import java.util.List;

public class Subject {

	public List<Observer> observerList = new ArrayList<Observer>();
	private int state;
	
	
	/**
	 * Retrieves subject's state
	 * @return int state
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Updates subject's state
	 * @param newState
	 */
	public void setState(int newState) {
		state = newState;
		notifyObservers();
	}
	
	/**
	 * Pushes update to subscribed observers
	 */
	public void notifyObservers() {
		for(int i = 0; i < observerList.size(); i++)
			observerList.get(i).update();			
	}
	
	/**
	 * Subscribes an observer to updates
	 * @param observer
	 */
	public void attach(Observer observer) {
		observerList.add(observer);
	}
	
	/**
	 * Unsubscribes an observer from updates
	 * @param observer
	 */
	public void detach(Observer observer) {
		observerList.remove(observer);
	}
}
	