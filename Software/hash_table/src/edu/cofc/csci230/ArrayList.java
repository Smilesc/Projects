package edu.cofc.csci230;

/**
 * ArrayList Data Structure
 * 
 * @author CSCI 230: Data Structures and Algorithms Fall 2017
 *
 * @param <AnyType>
 */
public class ArrayList<AnyType extends Comparable<AnyType>> implements List<AnyType> {
    
    // instance variables
    private AnyType[] array;
    private int size = 0;
    private final static int INITIAL_CAPACITY = 10;
    private int capacity = INITIAL_CAPACITY;
    
    /**
     * Constructs an empty list with an initial capacity
     * (for this assignment initial capacity is 10 - see 
     * constant instance variable)
     * 
     */
    public ArrayList() {
        
        array = (AnyType[]) new Comparable[ capacity ];
        
    } // end constructor
    
    /**
     * Appends the specified element to the end of this list.
     * 
     * @param t
     */
    public void add( AnyType t) {
        
        if ( size >= capacity )
            grow();
        
        array[size]=t;
        size++;
        
    } // end add() method
    
    /**
     * Inserts the specified element at the specified position in this list.
     * 
     * @param index
     * @param t
     * @throws IndexOutOfBoundsException
     */
    public void add(int index, AnyType t) throws IndexOutOfBoundsException {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         */
        
        if ( index < 0 || index > size )
            throw new IndexOutOfBoundsException();
        
        if ( size >= capacity )
            grow();
        
        for ( int i=size; i>index; i-- ) {
            
            array[i] = array[i-1];
            
        }
        
        array[index] = t;
        
        size++;
        
    } // end add() method
    
    /**
     * Replaces the element at the specified position in this list with the specified element.
     * 
     * @param index
     * @param t
     * @throws IndexOutOfBoundsException
     */
    public void set(int index, AnyType t) throws IndexOutOfBoundsException {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         */
        
        if ( index < 0 || index >= size )
            throw new IndexOutOfBoundsException();
        
        array[index] = t;
        
    } // end set() method
    
    /**
     * Removes the element at the specified position in this list.
     * 
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public AnyType remove( int index ) throws IndexOutOfBoundsException {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         * Requirement - you must use loops (i.e. may not use
         * System.arraycopy, or any other array copy operation 
         * available in the Java API) to perform left or right
         * shift operations
         * 
         */
        
        AnyType element = get( index );
        
        for ( int i=index; i<size-1; i++ ) {
            
            array[i] = array[i+1];
        }
        
        size--;
        
        if ( size < ( capacity/2 ) && capacity > INITIAL_CAPACITY )
            shrink();
        
        return element;
        
    } // end remove() method
    
    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public AnyType get(int index) throws IndexOutOfBoundsException {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         */
        
        if ( index < 0 || index >= size )
            throw new IndexOutOfBoundsException();
        
        return array[ index ];
        
        
    } // end get() method
    
    /**
     * Returns the number of elements in this list. 
     * 
     * @return
     */
    public int size() {
        
        return size;
        
    } // end size() method
    
    /**
     * Returns true if this list contains no elements.
     * 
     * @return
     */
    public Boolean isEmpty() {
        
        return ( size == 0 );
        
    } // end isEmpty() method
    
    
    /**
     * Removes all of the elements from this list.
     * and the initial capacity is set back to 10
     * 
     */
    public void clear() {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         */
        
        array = (AnyType[]) new Comparable[ INITIAL_CAPACITY ];
        capacity = INITIAL_CAPACITY;
        size=0;
        
        
    } // end clear method
    
    /**
     * Double the capacity of the array
     * 
     */
    private void grow() {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         * Requirement - you must use loops (i.e. may not use
         * System.arraycopy, or any other array copy operation 
         * available in the Java API)
         * 
         */
        
        AnyType[] array_dbl_cap = (AnyType[]) new Comparable[ capacity*=2 ];
        
        for ( int i=0; i<array.length; i++ ) {
            
            array_dbl_cap[i] = array[i];
            
        }
        
        array = array_dbl_cap;
        
        // System.out.printf( "grow operation (%d,%d)\n", size, capacity );
        
    } // end grow() method
    
    
    /**
     * Half the capacity of the array
     * 
     */
    private void shrink() {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         * Requirement - you must use loops (i.e. may not use
         * System.arraycopy, or any other array copy operation 
         * available in the Java API)
         * 
         */
        
        AnyType[] array_half_cap = (AnyType[]) new Comparable[ capacity/=2 ];
        
        for ( int i=0; i<array_half_cap.length; i++ ) {
            
            array_half_cap[i] = array[i];
            
        }
        
        array = array_half_cap;
        
        // System.out.printf( "shrink operation (%d,%d)\n", size, capacity );
        
    } // end shrink() method
    
    /**
     * swaps two elements in a list at index 
     * position i and j.
     * 
     * For example, 
     *  if A = 1,2,3,4,5 and swap( 1, 3 ) is executed
     *  then A = 1,4,3,2,5
     *
     * @param i
     * @param j
     * @throws IndexOutOfBoundsException
     */
    public void swap( int i, int j )  throws IndexOutOfBoundsException {
        
        /**
         * -------------------------------------------
         * TODO: You fully implement this method
         * 
         */
        
        if ( i != j ) {
            
            AnyType data = get( i );
            
            array[i] = get(j);
            array[j] = data;
            
        }
        
        
    } // end swap() method
    
    /**
     * For debugging purposes :)
     * 
     * 
     */
    public String toString() {
        
        StringBuffer buffer = new StringBuffer();
        
        buffer.append( String.format( "Size=%d, A = [ ", size ) );
        
        if ( !isEmpty() ) {
            
            for ( int i=0; i<size-1; i++ ) {
                buffer.append( String.format( "%d, ", array[i] ) );    
            }
            
            buffer.append( String.format( "%d ]", array[size-1] ) );
            
        } else {
            
            buffer.append( "] " );
        }
        
        return buffer.toString();
        
    } // end toString()
    
    
    /**
     * 
     * @param args
     */
    public static void main( String[] args ) {
        
        /**
         * -------------------------------------------
         * TODO: Put your test cases here
         * 
         */
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for (int i=0; i<5; i++ )
            list.add( i, i+1 );
        
        System.out.println( list );
        
        System.out.printf( "Removed A[%d] = %d\n", list.size()-1, list.remove( list.size()-1 ) );
        
        System.out.println( list );
        
        list.set( 0, 99 );
        
        System.out.println( list );
        
        for (int i=0; i<10; i++ )
            list.add( i+1 );
        
        System.out.println( list );
        
        for ( int i=0; i<11; i++)
            list.remove(0);
        
        System.out.println( list );
        
        list.add( 1, -1 );
        
        System.out.println( list );
        
        list.clear();
        
        System.out.println( list );
        
    } // end main() method
    
} // end ArrayList class definition
