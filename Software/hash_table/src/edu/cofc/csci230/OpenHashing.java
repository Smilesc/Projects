package edu.cofc.csci230;

/**
 * 
 * Open hashing data structure
 * 
 * @author CSCI 230: Data Structures and Algorithms Fall 2017
 *
 */
public class OpenHashing extends HashTable { 
	
	/* private instance variables */
	private ArrayList<String>[] hash_table;
	
	/**
	 * Constructor
	 */
    public OpenHashing( int hash_function ) {
    	
    	this.hash_function = hash_function;
    	
    } // end constructor
    
    /**
     * Initialize the hash table
     * 
     * The number of elements in the hash table (m) is equal 97.
     * 
     */
    public void initialize() {
    	
    	hash_table = (ArrayList<String>[]) new ArrayList[ HashTable.M ];
    	
    	for ( int i=0; i<hash_table.length; i++ ) {
    		
    		hash_table[i] = new ArrayList<String>();
    		
    	}
    	
    } // end initialize() method
    
    
    /**
     * Search for key in the hash table
     * 
     * Exceptions: If the key does not exist in the hash table, the throw
     * 		       a HashTableKeyException
     * 
     * return: The number of list probes needed to find the key in the hash 
     * 		   table, e.g. 1 if the key was the first element in the list, n 
     * 	       if the key was the very last element in the list (where n is 
     *         the number of elements in the list).
     * 
     * @param key
     * @return
     */
	public int search( String key ) throws HashTableKeyException {
		
		int probes = 1;

		//iterate through hash table
		for(int i = 0; i < hash_table.length; i++) {
			
			//iterate through the list at each element of the hash table
			for(int j = 0; j < hash_table[i].size(); j++) {
				
				//while key is not equal to the element j in the current list, increment probes
				if(key.compareTo(hash_table[i].get(j)) != 0) {
					
					probes++;
					
					//if the end of the list of the last element in the hash table is reached, throw exception "not found"
					if(i == hash_table.length - 1 && j == hash_table[i].size() - 1) {
						
						throw new HashTableKeyException("The key was not found");
					}
				}
				
				else {
					
					return probes;
					
				}
			}
		}
		
	return probes;
		
	} // end search() method
	
	/**
	 * Insert key into hash table
	 * 
	 * Exceptions: Duplicate keys are not allowed, e.g., if "dog" is already exists
	 * 			   in the hash table, then another "dog" key cannot be inserted. In 
	 * 			   this instance, throw a HashTableKeyException.
	 * 
	 * @param key
	 */
	public void insert( String key ) throws HashTableKeyException {

		int hashValue = calcHash(key);
		boolean hasBeenInserted = false;
		
		//iterates through the hash table, as long as the element has not yet been inserted
		for(int i = 0; i < hash_table.length && hasBeenInserted == false; i++) {
			
			//if the current index is the hash value,
			if(i == hashValue) {			
				
				//and the list at hash table index i is not empty, 
				if(!(hash_table[i].isEmpty())) {
					
					//iterate through the list
					for(int j = 0; j < hash_table[i].size(); j++) {
						
						//if the current list position is a duplicate of key, throw an exception
						if(hash_table[i].get(j).compareTo(key) == 0) {

							throw new HashTableKeyException("Duplicate keys not permitted");
							
						}
					}
				}
					hash_table[i].add(key);
					hasBeenInserted = true;
			}
		}
		
	} // end insert() method
	
	/**
	 * Delete a key from the hash table
	 * 
	 * Exceptions: if they key does not exist in the hash table, then throw
	 * 			   a HashTableKeyException
	 * 
	 * return: The number of probes needed to find the key in the hash table,
     *         e.g. 1 if the key was the first element in the list, n if it 
     *         was the very last element in the list, where n is the size 
     *         of the list.
	 * 
	 * @param key
	 * @return
	 */
	public int delete ( String key ) throws HashTableKeyException {
		
		int probes = 0;

		//iterates through hash table, adding a probe for each new element in the hash table
		for(int i = 0;i < hash_table.length; i++) {
			
			probes++;
			
			//if the list at hash table index i is not empty,
			if(!(hash_table[i].isEmpty())){
				
				//iterate through that list, and,
				for(int j = 0; j < hash_table[i].size(); j++) {
					
					//while the current list position is not equal to the key, increment probes
					if(hash_table[i].get(j).compareTo(key) != 0) {
						
						probes++;
						
						//if the end of the list of the last element in the hash table is reached, throw exception "not found"
						if(i == hash_table.length - 1 && j == hash_table[i].size() - 1) {
							
							throw new HashTableKeyException("The key was not found");
						}
					}
					
					else {
						
						hash_table[i].remove(j);
						return probes;

					}
				}
			}
		}
		
	return probes;

	} // end delete() method
	
	/**
	 * See page 271 in supplemental course textbook (chapter is provided as PDF 
	 * on OAKS in content section).
	 * 
	 * Also, refer to your lecture notes.
	 * 
	 * @return
	 */
	public double loadFactor() {

		double m = 0;
		double n = 0;
		double mCheck = 0;
			
		for(int i = 0; i < hash_table.length; i++) {
			
			if(!(hash_table[i].isEmpty())) {
				
				m++;
				
				n += hash_table[i].size();
					
				}
		}

		return n/m;
				
	} // end loadFactor() method
	
	
	/**
	 * See equation (7.4) on page 271 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double successfulSearches() {
		
		return 1 + loadFactor()/2;
				
	} // end successfulSearches() method
	
	/**
	 * See equation (7.4) on page 271 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double unsuccessfulSearches() {

		return loadFactor();
				
	} // end unsuccessfulSearches() method
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
        
		OpenHashing hashDS = new OpenHashing( HashTable.HASH_FUNC1 );
		System.out.printf("\n%s ----------------------------------\n", "Open Hashing: FUNC1" );
		
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
					
					totalProbe = totalProbe + hashDS.search(words.get(i));
					numWords++;
					
				}
				
				System.out.println("Mean probe value(search): " + totalProbe/numWords);
				
				
		// 6) For a word that does not exist in hashDS, search in  the hashDS, print the probe value
				System.out.println("Mean probe value(non-existant search): " + hashDS.search("supercalifragilistic"));
				
				
		// 7) For each word in words list, delete word in the hashDS, and print mean probe value
				totalProbe = 0;
				
				for(int i = 0; i < words.size(); i++) {
					try {
					
						totalProbe = totalProbe + hashDS.delete(words.get(i));
						numWords++;
					}
					
					catch(Exception e) {
						
					}
				}
				
				System.out.println("Mean probe value(delete): " + totalProbe/numWords);
				
				
		// 8) For a word that does not exist in hashDS, delete in the hashDS, print the probe value
				System.out.println("Mean probe value(non-existant delete): " + hashDS.delete("supercalifragilistic"));
				
			}
			
			catch(HashTableKeyException hE) {
				
				hE.getMessage();
				
			}
			
		}//end try
		
		else {
			
			System.out.println("Failed to load words from text file" );
		}
		
		// ------------------------------------------------
		// Repeat for second hash function
		
		hashDS = new OpenHashing( HashTable.HASH_FUNC2 );
		System.out.printf("\n%s ----------------------------------\n", "Open Hashing: FUNC2" );
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
					
					totalProbe = totalProbe + hashDS.search(words.get(i));
					numWords++;
					
				}
				
				System.out.println("Mean probe value(search): " + totalProbe/numWords);
				
				
		// 6) For a word that does not exist in hashDS, search in  the hashDS, print the probe value
				System.out.println("Mean probe value(non-existant search): " + hashDS.search("supercalifragilistic"));
				
				
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
		
    } // end main() method
	
} // end OpenHashing class definition
