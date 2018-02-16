/*************************************************************************
 *  Compilation:  javac Bag.java
 *  Execution:    java Bag
 *
 *  A generic bag or multiset, implemented using a linked list.
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The <tt>Bag</tt> class represents a bag (or multiset) of
 *  generic items. It supports insertion and iterating over the
 *  items in arbitrary order.
 *  <p>
 *  The <em>add</em>, <em>isEmpty</em>, and <em>size</em>  operation
 *  take constant time. Iteration takes time proportional to the number of items.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

//Edits made by Evan Gutman---erg53@pitt.edu


public class Bag<Item> implements Iterable<Item> {
    private int N;         // number of elements in bag
    private Node first;    // beginning of bag

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    /**
     * Create an empty stack.
     */
    public Bag() {
        first = null;
        N = 0;
    }

    /**
     * Is the BAG empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Return the number of items in the bag.
     */
    public int size() {
        return N;
    }

    /**
     * Add the item to the bag.
     */
    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public void remove(Item item){
        Node current=first;
        Node previous=null;
        while(!item.equals(current.item)){
            if(current.next==null){
                System.out.println("Edge does not exist: no action completed");
                return;
            }else{
                previous=current;
                current=current.next;
            }
        }
        if(previous==null){
            first=current.next;
        }else{
            previous.next=current.next;
        }
        N-=1;

    }


    /**
     * Return an iterator that iterates over the items in the bag.
     */
    public Iterator<Item> iterator()  {
        return new LIFOIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LIFOIterator implements Iterator<Item> {
        private Node current = first;
        private Node previous=null;

        public boolean hasNext()  { return current != null;}
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            previous=current;
            current = current.next;
            return item;
        }
    }
}