package cp213;

/**
 * A simple linked queue structure of <code>T</code> objects. Only the
 * <code>T</code> value contained in the queue is visible through the standard
 * queue methods. Extends the <code>DoubleLink</code> class, which already
 * defines the front node, rear node, length, isEmpty, and iterator.
 *
 * @author - your name here - Sahil Lalani
 * @author David Brown
 * @version 2019-01-26
 *
 * @param <T>
 *            this data structure value type.
 */
public class DoubleQueue<T> extends DoubleLink<T> {

    /**
     * Combines the contents of the left and right Queues into the current
     * Queue. Moves nodes only - does not move value or call the high-level
     * methods insert or remove. left and right Queues are empty when done.
     * Nodes are moved alternately from left and right to this Queue.
     *
     * @param source1
     *            The front Queue to extract nodes from.
     * @param source2
     *            The second Queue to extract nodes from.
     */
    public void combine(final DoubleQueue<T> source1,
    	    final DoubleQueue<T> source2) {
    	Boolean b=false;
    	while(source1.rear!=null && source2.rear!=null){
    		if(b && source1.front!=null) {
    			source1.rear_to_rear(this);
    		}
    		else if(source2.front!=null) {
    			source2.rear_to_rear(this);
    			}
    		b=!b;
    	}
    }
    

    /**
     * Adds value to the rear of the queue. Increments the queue size.
     *
     * @param value
     *            The value to added to the rear of the queue.
     */
    public void insert(final T value) {
	// your code here
    	DoubleNode<T> prev= this.rear;
    	
    	DoubleNode<T> link= new DoubleNode<T>(value, prev,null);
    	if (link.getPrev().getValue()== null){
    		this.front= link;
    		this.rear=this.front;
    	}
    	else {
    		prev.setNext(link);
    		link.setPrev(prev);
    	}
    }

    /**
     * Returns the front value of the queue and removes that value from the
     * queue. The next node in the queue becomes the new front node. Decrements
     * the queue size.
     *
     * @return The value at the front of the queue.
     */
    public T remove() {
	// your code here
    	T v= this.rear.getValue();
    	if (!this.rear.equals(this.front)){
    		DoubleNode<T> previous= this.rear.getPrev();
    		this.rear= previous;
    		this.rear.setNext(null);
    	}
		return v;
    }

    /**
     * Splits the contents of the current Queue into the left and right Queues.
     * Moves nodes only - does not move value or call the high-level methods
     * insert or remove. this Queue is empty when done. Nodes are moved
     * alternately from this Queue to left and right.
     *
     * @param target1
     *            The first Queue to move nodes to.
     * @param target2
     *            The second Queue to move nodes to.
     */
    public void split(final DoubleQueue<T> target1,
	    final DoubleQueue<T> target2) {
	// your code here
    	boolean b=true;
    	while(this.front!=null) {
    		if(b) {
    		this.rear_to_rear(target1);
    		}
    		else  {
        		this.rear_to_rear(target2);
        		}
    	 }
    		
    	
    }
    public void rear_to_rear(DoubleQueue <T> target) {
    	DoubleNode <T> node= this.rear;
    	DoubleNode <T> node1= target.rear;
    	if(node!=null) {
	    	if(node1 !=null) {
	    		node1.setNext(node);
	    		this.rear=this.rear.getPrev();
	    		this.rear.setNext(null);
	    		node.setPrev(target.rear);
	    		target.rear.setNext(node);
	    		
	    	}
	    	else if(node1==null){
	    		this.rear=this.rear.getPrev();
	    		this.rear.setNext(null);
	    		node.setPrev(null);
	    		target.front=node;
	    		target.rear=node;
	    	}
	    	if(this.rear==null) {
	    		this.front=null;
	    	}
    	}
    }
    
}
