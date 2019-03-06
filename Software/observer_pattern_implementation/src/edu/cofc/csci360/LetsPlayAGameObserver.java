package edu.cofc.csci360;
import java.util.Scanner;

public class LetsPlayAGameObserver extends Observer {
	
	private int myNumber = 0;
	private int theNumber = 1;
	private Scanner keyboard = new Scanner(System.in);
	
	public LetsPlayAGameObserver(Subject s) {
		subject = s;
		subject.attach(this);		
	}
	
	
	/**
	 * Prompts user to guess a number and gives feedback on how close the user is
	 * @param tries: Number of attempts player has to guess correct number
	 */
	public void guesser(int tries) {
		
		int i = 0;
			while(myNumber != theNumber && i <= tries) {
				
				System.out.println("Tries left: " + (tries - i));
				System.out.print("Guess an integer: " );
				myNumber = keyboard.nextInt();
						
				if(theNumber >= 0) {
					System.out.print("You are: ");
					if(myNumber <= 0) {
						System.out.println("You'll never win with guesses like that");
					}
					if(myNumber < (theNumber *.10) || myNumber > (theNumber *1.90)) {
						System.out.println("Not even close \n");
					}
					else if(myNumber < (theNumber*.25) || myNumber > (theNumber *1.75)) {
						System.out.println("Cool \n");
					}
					else if(myNumber < (theNumber*.5) || myNumber > (theNumber *1.5)) {
						System.out.println("Lukewarm \n");
					}
					else if(myNumber < (theNumber*.75) || myNumber > (theNumber *1.25)) {
						System.out.println("Warm \n");
					}
					else if(myNumber < (theNumber *.90) || myNumber > (theNumber *1.10)) {
						System.out.println("Hot \n");
					}
					else if(myNumber < (theNumber *.95) || myNumber > (theNumber*1.05)) {
						System.out.println("Super Hot \n");
					}
					else if(myNumber < (theNumber *.99) || myNumber > (theNumber *1.01)) {
						System.out.println("On Fire!! \n");
					}
				}
			
				else {
					System.out.println("Stay positive please");
				}
				
				i++;
			}	
		
			if(myNumber == theNumber) {
				System.out.println("A winner!");
			}
			else if(tries == 0) {
			System.out.println("No more tries. You lose");
		}
	}
	
	public void update() {
		theNumber = subject.getState();
		System.out.println(">>LetsPlayAGameObserver");
		guesser(5);		
		
	}

}
