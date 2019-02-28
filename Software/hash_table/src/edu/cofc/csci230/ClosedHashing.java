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
		int hashNum = calcHash(key);
		int lastElementIndex = hash_table.length - 1;
		
		boolean elementFound = false;
		int numLazies = 0;
		String wordOnly = "";
		
		char[] collisionWord = new char[0];
		
			//check for null element @ hashNum
			if(hash_table[hashNum] == null) {
				
				throw new HashTableKeyException("The key does not exist in the table");				
			}
			
			
			//if element @ hashNum is not equal to the key
			if(hash_table[hashNum] != key) {
				
				//iterates from hashNum to end of list - second half below
				for(int i = hashNum; i <= lastElementIndex && elementFound == false; i++) {
					
					probes++;
					
					if(!(hash_table[i] == null)) {
						
						collisionWord = hash_table[i].toCharArray();
						
						//find out how many carrots
						for(int k = 0; k < collisionWord.length; k++) {

							if(collisionWord[k] == '^') {
								
								numLazies++;
								
							}
						}
						
						//set a variable to the string at position i without the carrots
						wordOnly = hash_table[i].substring(numLazies);
						
						numLazies = 0;
						collisionWord = null;
						
						//compare the pure string at position i to the key
						if(wordOnly.compareTo(key) == 0) {
							elementFound = true;
							
						}
					}
				}
				
				//iterates back from the beginning of the list - first half above
				for(int j = 0; j <= hashNum - 1 && elementFound == false; j++) {
					
					probes++;
					
					if(!(hash_table[j] == null)) {
						
						collisionWord = hash_table[j].toCharArray();
						
						//find out how many carrots
						for(int k = 0; k < collisionWord.length; k++) {

							if(collisionWord[k] == '^') {
								
								numLazies++;
								
							}
						}
						
						//set a variable to the string at position j without the carrots
						wordOnly = hash_table[j].substring(numLazies);
						
						numLazies = 0;
						collisionWord = null;
						
						//compare the pure string at position j to the key
						if(wordOnly.compareTo(key) == 0) {
							
							elementFound = true;
							
						}
					}
					
				}
				
			}
			
			else {
				
				probes++;
			}
			
			if(!elementFound) {
				
				throw new HashTableKeyException("The key does not exist in the table");	
			}
					
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

		int hashKey = calcHash(key);
		int lastElementIndex = hash_table.length - 1;
		boolean elementInserted = false;
		
		//if the element at the hash key index is null, insert the key
		if(hash_table[hashKey] == null) {
			
			hash_table[hashKey] = key;
			
		}
		
		//if the hash key element isn't null,
		else {
			
			//add a ^ to the string there
			hash_table[hashKey] = "^" + hash_table[hashKey];

			//if element at the hash number is not equal to the key
			if(hash_table[hashKey] != key) {
				
				//iterate through the hash table, from the hashkey + 1 to end
				for(int i = hashKey; i <= lastElementIndex && elementInserted == false; i++) {

					//if the current element isn't null,
					if(!(hash_table[i] == null)) {
						
						//and the element is equal to the key, throw exception
						if(hash_table[i].compareTo(key) == 0) {
							
							throw new HashTableKeyException("You cannot insert duplicate elements");
							
						}
					}
					
					//if current element is null, insert!
					else {
						
						hash_table[i] = key;
						elementInserted = true;
						
					}
				}//end first search for a null
				
				//iterate through the hash table, from the beginning to hashkey - 1
				for(int j = 0; j <= hashKey - 1 && elementInserted == false; j++) {
					
					//if the current element isn't null,
					if(!(hash_table[j] == null)) {
						
						//and the element is equal to the key, throw exception
						if(hash_table[j].compareTo(key) == 0) {
							
							throw new HashTableKeyException("You cannot insert duplicate elements");
							
						}
						
					}
					
					//if current element is null, insert!
					else {

						hash_table[j] = key;
						elementInserted = true;

					}
					
				}
			}
			
			else {
				
				throw new HashTableKeyException("You cannot insert duplicate elements");
			}
		}
		
		
		
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
		int hashKey = calcHash(key);
		int currentIndex = 0;;
		int numLazies = 0;
		int lastElementIndex = hash_table.length - 1;
		
		char[] collisionWord = new char[0];

		
		//if no carrots, add 1 to probes and set value to null
		if(hash_table[hashKey] != null && !(hash_table[hashKey].contains("^"))){
			
			probes++;
			hash_table[hashKey] = null;
			
		}
		
		//if there are carrots
		else {			
			
			//search from hashKey to last element - second part below
			for(int i = hashKey; i < lastElementIndex; i++) {
				
				if(hash_table[i] != null) {
					collisionWord = hash_table[i].toCharArray();
					
					//find out how many carrots
					for(int k = 0; k < collisionWord.length; k++) {
						
						if(collisionWord[k] == '^') {
							
							numLazies++;
							
						}//end if
					}// end for
					
					//set a variable to the string at position i without the carrots
					String wordOnly = hash_table[i].substring(numLazies);
					
					numLazies = 0;
					
					collisionWord = null;
					
					//compare the pure string at position i to the key
					if(wordOnly.compareTo(key) == 0) {
						
						//should take one ^ off of the end with substring method
						hash_table[i] = hash_table[i].substring(1);
						
					}//end if
					
					probes++;
					currentIndex = i;
				}
				
				hash_table[currentIndex] = null;
			}
			
			//search from beginning to hashKey - first part above
			for(int i = 0; i < hashKey; i++) {
				
				if(hash_table[i] != null) {
					collisionWord = hash_table[i].toCharArray();
					
					//find out how many carrots
					for(int k = 0; k < collisionWord.length; k++) {
						
						if(collisionWord[k] == '^') {
							
							numLazies++;
							
						}//end if
					}// end for
					
					//set a variable to the string at position i without the carrots
					String wordOnly = hash_table[i].substring(numLazies);
					
					numLazies = 0;
					
					collisionWord = null;
					
					//compare the pure string at position i to the key
					if(wordOnly.compareTo(key) == 0) {
						
						//should take one ^ off of the end with substring method
						hash_table[i] = hash_table[i].substring(1);
						
					}//end if
					
					probes++;
					currentIndex = i;
				}
				
				hash_table[currentIndex] = null;
			}
		}
		
	      
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
		
		
		int m = hash_table.length;
		int n = 0;
		double mCheck = 0;
		
		for(int i = 0; i < hash_table.length; i++) {
			
			if(hash_table[i] != null) {
				n++;
				
			}
		}
		
		return n/m;
				
	} // end loadFactor() method
	
	
	/**
	 * See equation (7.5) on page 273 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double successfulSearches() {
		
		return (1/2) * ( 1 + ( 1 / ( 1 - loadFactor() ) ) );
				
	} // end successfulSearches() method
	
	/**
	 * See equation (7.5) on page 273 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double unsuccessfulSearches() {
		
		return (1/2) * ( 1 + ( 1 / ( (1 - loadFactor() ) * (1 - loadFactor() ) ) ) );
				
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

			try {
				// 1) Insert each word into hash data structure
				for(int i = 0; i < words.size(); i++) {
					
					try {
					
						hashDS.insert(words.get(i));
					
					}
					
					catch(HashTableKeyException e) {
						
						//do nothing
						
					}
				}
						
						
				// 2) Calculate load factor value and print
						System.out.println("Load factor: " + hashDS.loadFactor());
						
						
				// 3) Calculate successful searches value and print
						System.out.println("Sucessful searches: " + hashDS.successfulSearches());
						
						
				// 4) Calculate unsuccessful searches value and print
						System.out.println("Unsucessful searches: " + hashDS.unsuccessfulSearches());
						
						
				// 5) For each word in words list, search in the hashDS, and print mean probe value
						int numWords = 0;
						int totalProbe = 0;
						
						for(int i = 0; i < words.size(); i++) {
							
							try {
								
								totalProbe = totalProbe + hashDS.search(words.get(i));
								numWords++;
								
							}
							
							catch(HashTableKeyException e) {
								
								System.out.println("Key value not found" + words.get(i));
							}
							
						}
						
						System.out.println("Mean probe value(search): " + totalProbe/numWords);
						
						
				// 6) For a word that does not exist in hashDS, search in  the hashDS, print the probe value
						try {
							
							System.out.println("Mean probe value(non-existant search): " + hashDS.search("supercalifragilistic"));
						}
						
						catch(HashTableKeyException e) {
							
							//do nothing
						}
						
				// 7) For each word in words list, delete word in the hashDS, and print mean probe value
						totalProbe = 0;
						
						for(int i = 0; i < words.size(); i++) {
							
							totalProbe = totalProbe + hashDS.delete(words.get(i));
							numWords++;
						}
						
						System.out.println("Mean probe value(delete): " + totalProbe/numWords);
						
						
				// 8) For a word that does not exist in hashDS, delete in the hashDS, print the probe value
						System.out.println("Mean probe value(non-existant delete): " + hashDS.delete("supercalifragilistic"));
						
					}//end try
					
					catch(HashTableKeyException hE) {
						
						hE.getMessage();
						
					}
					
				} 
			
		 else {
			
			System.out.println("Failed to load words from text file" );
		}
		
		// ------------------------------------------------
		// Repeat for second hash function
		
		System.out.printf("\n%s ----------------------------------\n", "Closed Hashing: FUNC2" );
		hashDS = new ClosedHashing( HashTable.HASH_FUNC2 );
		
		if ( hashDS.loadWords() ) {
			
			hashDS.initialize();
			
			System.out.printf( "Number of words in list = %d\n", words.size() );
			
			try {
				// 1) Insert each word into hash data structure
				for(int i = 0; i < words.size(); i++) {
					try {
					
						hashDS.insert(words.get(i));
					
					}
					
					catch(HashTableKeyException e) {
						
						//do nothing
						
					}
			}	
						
				// 2) Calculate load factor value and print
						System.out.println("Load factor: " + hashDS.loadFactor());
						
						
				// 3) Calculate successful searches value and print
						System.out.println("Sucessful searches: " + hashDS.successfulSearches());
						
						
				// 4) Calculate unsuccessful searches value and print
						System.out.println("Unsucessful searches: " + hashDS.unsuccessfulSearches());
						
						
				// 5) For each word in words list, search in the hashDS, and print mean probe value
						int numWords = 0;
						int totalProbe = 0;
						
						for(int i = 0; i < words.size(); i++) {
							try {
								
								totalProbe = totalProbe + hashDS.search(words.get(i));
								numWords++;
								
							}
							
							catch(HashTableKeyException e) {
								//do nothing
							}
							
						}
						
						System.out.println("Mean probe value(search): " + totalProbe/numWords);
						
						
				// 6) For a word that does not exist in hashDS, search in  the hashDS, print the probe value
						try {
							
							System.out.println("Mean probe value(non-existant search): " + hashDS.search("supercalifragilistic"));
							
						}
						
						catch(HashTableKeyException e) {
							
							//do nothing
						}
						
						
				// 7) For each word in words list, delete word in the hashDS, and print mean probe value
						totalProbe = 0;
						
						for(int i = 0; i < words.size(); i++) {
							
							try {
								
							totalProbe = totalProbe + hashDS.delete(words.get(i));
							numWords++;
							
							}
							
							catch(Exception e) {
								
								//do nothing
							}

						}
						
						System.out.println("Mean probe value(delete): " + totalProbe/numWords);
						
						
				// 8) For a word that does not exist in hashDS, delete in the hashDS, print the probe value
						System.out.println("Mean probe value(non-existant delete): " + hashDS.delete("supercalifragilistic"));
						
					}//end try
					
					catch(HashTableKeyException hE) {
						
						hE.getMessage();
						
					}
					
				} 
			
		 else {
			
			System.out.println("Failed to load words from text file" );
		}
		
    } // end main() method
	
} // end ClosedHashing class definition