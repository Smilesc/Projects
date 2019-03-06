package edu.cofc.csci360;

public class SquarerObserver extends Observer {
	
	public SquarerObserver(Subject s) {
		
		subject = s;
		subject.attach(this);	
		
	}
	
	/**
	 * Unsubscribes SquarerObserver from updates
	 */
	public void detach() {
		
		subject.detach(this);
		
	}
	

	@Override
	public void update() {
		
		System.out.println(">>SquarerObserver ");
		System.out.println("The squared value is: " + subject.getState()*subject.getState() + "\n");	
		
	}

}
