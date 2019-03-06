package edu.cofc.csci360;

public class EvenOrOddObserver extends Observer {
	
	public EvenOrOddObserver(Subject s) {
		
		subject = s;
		subject.attach(this);
		
	}
	
	@Override
	public void update() {
		
		System.out.println(">>EvenOrOddObserver");
		
		if(subject.getState() % 2 == 0) {
			
			System.out.println("This number is even \n");
		}
		
		else {
			
			System.out.println("This number is odd \n");
			
		}
	}
}
