package cp213;

/**
 * A simple linked deque structure of <code>T</code> objects. Only the
 * <code>T</code> value contained in the deque is visible through the standard
 * deque methods. Extends the <code>DoubleLink</code> class, which already
 * defines the front node, rear node, length, isEmpty, and iterator.
 *
 * @author - your name here -Sahil Lalani
 * @author David Brown
 * @version 2019-01-26
 *
 * @param <T>
 *            this data structure value type.
 */
public class DoubleDeque<T> extends DoubleLink<T> {

    /**
     * Adds a value to the front of a deque.
     *
     * @param value
     *            value to add to the front of the deque.
     */
    public void addFront(final T value) {
	// your code here
    	DoubleNode <T> node= new DoubleNode <T>(value,null,null);
    	if (this.front==null) {
    		this.front= node;
    		this.rear=node;
    	}
    	else {
    	node.setNext(this.front);
    	this.front.setPrev(node);
    	this.front= this.front.getPrev();
    	this.length+=1;
    	}
    	return;
    }

    /**
     * Adds a value to the rear of a deque.
     *
     * @param value
     *            value to add to the rear of the deque.
     */
    public void addRear(final T value) {
	// your code here
    	DoubleNode <T> node= new DoubleNode <T>(value,null,null);
    	if (this.front==null) {
    		this.front= node;
    		this.rear=node;
    	}
    	else {
    	node.setPrev(this.rear);
    	this.rear.setNext(node);
    	this.rear= this.rear.getNext();
    	this.length+=1;
    	}
	return;
    }

    /**
     * Returns the value at the front of a deque.
     *
     * @return the value at the front of the deque.
     */
    public T peekFront() {
	// your code here
    	T node=null;
    	if(this.front!=null) {
    	node= this.front.getValue();
    	}
    	return node;
    }

    /**
     * Returns the value at the rear of a deque.
     *
     * @return the value at the rear of the deque.
     */
    public T peekRear() {
	// your code here
    	T node=null;
    	if(this.rear!=null) {
    	node= this.rear.getValue();
    	}
    	return node;
    }
    /**
     * Removes and returns the value at the front of a deque.
     *
     * @return the value that has been removed.
     */
    public T removeFront() {
	// your code here
    	T node= this.peekFront();
    	if(this.front!=null) {
    		if (this.front.getNext()!=null) {
    			this.front= this.front.getNext();
    			this.front.setPrev(null);
    		}
    		else{
    			this.front=null;
    			this.rear=this.front;
    		}
    		this.length--;
    	}
    	return node;		
    }

    /**
     * Removes and returns the value at the rear of a deque.
     *
     * @return the value that has been removed.
     */
    public T removeRear() {
	// your code here
    	T node = this.peekRear();
    	if(this.front!=null) {
	    	if (this.rear.getPrev()!=null) {
	    		this.rear=this.rear.getPrev();
	    		this.rear.setNext(null);
	    	}
	    	else {
	    		this.front=null;
	    		this.rear=this.front;
	    	}
	    	this.length--;
    	}
    	return node;
    }
    public static void main(String[] args) {
    	Integer[] values = { 9,8,7,6};
    	DoubleDeque<Integer> deque = new DoubleDeque<>();
    	// deque.addValues(values);

    	for (Integer v : values) {
    	    deque.addFront(v);
    	}

    	Integer v = deque.removeFront();
    	System.out.println(v);
    	v = deque.removeRear();
    	System.out.println(v);
        }
    }