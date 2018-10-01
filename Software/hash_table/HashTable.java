package edu.cofc.csci230;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class HashTable {
	
	/* constants */
	public static final int HASH_FUNC1 = 1; 
	public static final int HASH_FUNC2 = 2; 
	public static final int M = 307;

	/* private instance variables */	
	protected int hash_function = HASH_FUNC1;
	
	/* public class variables */
	protected static List<String> words = new ArrayList<String>();
	
	/**
     * Calculate hash value for a word
     * 
     * @param key
     * @return
     */
    public int calcHash( String key ) {
    	
    	int h_k = 0;
        
    	switch ( hash_function ) {
    	
    		/**
    		 * Single hash function
    		 * 
    		 * h(k) = k mod m
    		 * 
    		 * k is the sum of ASCII integer values for each character 
    		 * in the string. See http://www.asciitable.com/ for character
    		 * to decimal conversions.
    		 * 
    		 * m = 307 (see M constant above)
    		 * 
    		 */
    		case HASH_FUNC1:
    			
    			/*
    			 * TODO: put solution here
    			 */
    		
    		/**
    		* Double hash function. See equation 7.6 on page 273
    		* in the supplemental course text book (chapter is 
    		* provided as PDF on OAKS in content section).
    		* 
    		* h(k) = ( l + 2*s(k) ) mod m
    		* 
    		* l is the sum of ASCII integer values for each character 
    		* in the string. See http://www.asciitable.com/ for character
    		* to decimal conversions.
    		* 
    		* s(k) = ( k mod m ) + 1
    		* 
    		* m = 307 (see M constant above)
    		* 
    		*/
    		default:
    			
    			/**
    			 * TODO: put solution here
    			 */
    			
    	}
    	
    	return h_k;

    } // end calcHash() method
    
    
    /**
	 * load words from text file into an Arraylist
	 * 
	 * 
	 */
	public Boolean loadWords() {

        Boolean pass = true;
		File file = new File("words.txt");	
		Scanner scanner = null;

        words.clear();
		
	    try {
	    	
	    	scanner = new Scanner( file );
	    	
	    	while ( scanner.hasNext() ) {
	    		
	    		/* read string and remove period if it exists */
	    		String word = scanner.next().replaceAll("\\.", "");
	    		
	    		words.add( word.toLowerCase() );
	    			
	    	}
	        
	    } catch ( FileNotFoundException file_error ) {
	    	
			file_error.printStackTrace();
			
			pass = false;
			
		} finally {
			
			scanner.close();
			
		}
	    
	    return pass;
		
	} // end loadWords() method

} // end HashTable
