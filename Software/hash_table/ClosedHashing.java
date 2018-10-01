package edu.cofc.csci230;

/**
 * 
 * Closed hashing data structure using linear probing.
 * 
 * @author CSCI 230: Data Structures and Algorithms Fall 2017
 *
 */
public class ClosedHashing extends HashTable { 
	
	/* private instance variables */
	private String[] hash_table;
	
	/**
	 * Constructor
	 */
    public ClosedHashing( int hash_function ) {
    	
    	this.hash_function = hash_function;
    	
    } // end constructor
    
    /**
     * Initialize the hash table
     * 
     * The number of elements in the hash table is equal to 2 x the number of words 
     * in the word list.
     * 
     */
    public void initialize() {
    	
    	hash_table = new String[ 2*words.size() ];
    	
    	for ( int i=0; i<words.size(); i++ ) {
    		
    		hash_table[i] = null;
    		
    	}
    	
    } // end initialize() method
    
    
    
    /**
     * Search for key in the hash table.
     * 
     * In this implementation, a lazy character "^" (at the beginning of the 
	 * string value) is used to indicate a collision has occurred. The number 
	 * of lazy characters indicate the number of collisions,e.g. "^^" would 
	 * indicate two collisions have occurred.
     * 
     * Exceptions: If the key does not exist in the hash table, then throw 
     *             a HashTableKeyException
     * 
     * return: The number of linear probes needed to find the key in the 
     * 		   hash table, e.g. 1 if no probing, n if probed n times to
     *         find an open location.
     * 
     * @param key
     * @return
     */
	public int search( String key ) throws HashTableKeyException {
		
		int probes = 0;
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		
		
		return probes;
	    
		
	} // end search() method
	
	/**
	 * Insert key into hash table
	 * 
	 * In this implementation, a lazy character "^" (at the beginning of the 
	 * string value) is used to indicate a collision has occurred. The number 
	 * of lazy characters indicate the number of collisions,e.g. "^^" would 
	 * indicate two collisions have occurred.
	 * 
	 * Exceptions: Duplicate key values are not allowed e.g., if "dog" is 
	 * 		       already exists in the hash table, then another 
	 * 			   "dog" key cannot be inserted. In this instance, throw a
	 * 			   HashTableKeyException.
	 * 
	 * @param key
	 */
	public void insert( String key ) throws HashTableKeyException {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		
		
		
	} // end insert() method
	
	/**
	 * Delete a key from the hash table. 
	 * 
	 * In this implementation, a lazy character "^" (at the beginning of the 
	 * string value) is used to indicate a collision has occurred. The number 
	 * of lazy characters indicate the number of collisions,e.g. "^^" would 
	 * indicate two collisions have occurred. Every successful deletion 
	 * should remove one "^" symbol. 
	 * 
	 * Exceptions: if they key does not exist in the hash table then throw
	 * 			   a HashTableKeyException
	 * 
	 * return: The number of probes needed to find the key in the hash table,
     *         e.g. 1 if the key was the first element in the list, n if it was 
     *         the very last element in the list, where n is the number of elements 
     *         in the list.
	 * 
	 * @param key
	 * @return
	 */
	public int delete ( String key ) throws HashTableKeyException {
		
		int probes = 0;
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		
	      
		return probes;
		
	} // end delete() method
	
	/**
	 * See page 271 in supplemental course textbook (chapter is provided as PDF 
	 * on OAKS in content section).
	 * 
	 * Also, refer to your lecture notes. Note, for closed hashing, m is 
	 * the number of locations in the hash table.
	 * 
	 * @return
	 */
	public double loadFactor() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		
		return 1.0;
				
	} // end loadFactor() method
	
	
	/**
	 * See equation (7.5) on page 273 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double successfulSearches() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		
		return 1.0;
				
	} // end successfulSearches() method
	
	/**
	 * See equation (7.5) on page 273 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double unsuccessfulSearches() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		
		return 1.0;
				
	} // end unsuccessfulSearches() method
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
        
		ClosedHashing hashDS = new ClosedHashing( HashTable.HASH_FUNC1 );
		System.out.printf("\n%s ----------------------------------\n", "Closed Hashing: FUNC1" );
		
		if ( hashDS.loadWords() ) {
			
			hashDS.initialize();
			
			System.out.printf( "Number of words in list = %d\n", words.size() );
			
			/* ------------------------------------------------
			 * TODO:
			 * ------------------------------------------------
			 * 1) Insert each word into hash data structure
			 * 2) Calculate load factor value and print to 
			 *    console (using System.printf or System.println)
			 * 3) Calculate successful searches value and print to 
			 *    console (using System.printf or System.println)
			 * 4) Calculate unsuccessful searches value and print to 
			 *    console (using System.printf or System.println)
			 * 5) For each word in words list, search in the hashDS, 
			 *    and print mean probe value to console (using System.printf or System.println)
			 * 6) For a word that does not exist in hashDS, search in 
			 *    the hashDS, print the probe value to console (using System.printf or System.println)
			 * 7) For each word in words list, delete word in the hashDS, 
			 *    and print mean probe value to console (using System.printf or System.println)
			 * 8) For a word that does not exist in hashDS, delete in 
			 *    the hashDS, print the probe value to console (using System.printf or System.println)
			 * 
			 */
			
		} else {
			
			System.out.println("Failed to load words from text file" );
		}
		
		// ------------------------------------------------
		// Repeat for second hash function
		
		System.out.printf("\n%s ----------------------------------\n", "Closed Hashing: FUNC2" );
		hashDS = new ClosedHashing( HashTable.HASH_FUNC2 );
		
		if ( hashDS.loadWords() ) {
			
			hashDS.initialize();
			
			System.out.printf( "Number of words in list = %d\n", words.size() );
			
			/* ------------------------------------------------
			 * TODO:
			 * ------------------------------------------------
			 * 1) Insert each word into hash data structure
			 * 2) Calculate load factor value and print to 
			 *    console (using System.printf or System.println)
			 * 3) Calculate successful searches value and print to 
			 *    console (using System.printf or System.println)
			 * 4) Calculate unsuccessful searches value and print to 
			 *    console (using System.printf or System.println)
			 * 5) For each word in words list, search in the hashDS, 
			 *    and print mean probe value to console (using System.printf or System.println)
			 * 6) For a word that does not exist in hashDS, search in 
			 *    the hashDS, print the probe value to console (using System.printf or System.println)
			 * 7) For each word in words list, delete word in the hashDS, 
			 *    and print mean probe value to console (using System.printf or System.println)
			 * 8) For a word that does not exist in hashDS, delete in 
			 *    the hashDS, print the probe value to console (using System.printf or System.println)
			 * 
			 */
			
		} else {
			
			System.out.println("Failed to load words from text file" );
		}
		
    } // end main() method
	
} // end ClosedHashing class definition