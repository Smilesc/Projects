package edu.cofc.csci360;
public class ObserverDemo {
	
	public static void main(String[] args) {
		
		Subject subject = new Subject();
		
		SquarerObserver squared = new SquarerObserver(subject);
		EvenOrOddObserver evenOdd = new EvenOrOddObserver(subject);
		LetsPlayAGameObserver game = new LetsPlayAGameObserver(subject);
		
		System.out.println("First state: 15 ( subject.setState(15) is called ) \n");
		subject.setState(15);
		System.out.println("_______________________________________");
		
		System.out.println("SquarerObserver detached itself from Subject ( squared.detach ) \n");
		squared.detach();

		System.out.println("Second state: 111 \n" );
		subject.setState(111);
		System.out.println("_______________________________________");
		
		System.out.println("Subject detached LetsPlayAGameObserver ( subject.detach(game) ) \n");
		subject.detach(game);
		
		System.out.println("Third state: 68 \n");
		subject.setState(68);
		System.out.println("_______________________________________");
		
	}

}
