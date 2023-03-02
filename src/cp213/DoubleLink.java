package cp213;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The abstract base class for doubly-linked data structures. Provides
 * attributes and implementations for getLength, isEmpty, toArray, and iterator
 * methods. The <code>front</code> attribute is the first node in any
 * doubly-linked list and <code>rear</code> the last node.
 *
 * @author David Brown
 * @version 2019-01-26
 * @param <T>
 *            data type for base data structure.
 */
public abstract class DoubleLink<T> implements Iterable<T> {

    /**
     * Creates an Iterator for the outer class. An iterator allows a program to
     * walk through the data in a data structure by using the hasNext and next
     * methods. Typical code:
     *
     * <pre>
    Iterator&lt;T&gt; iter = deque.iterator();

    while(iter.hasNext()){
        T data = iter.next();
        ...
    }
     * </pre>
     *
     * It also allows the user of the enhanced for loop:
     *
     * <pre>
    for(T data : deque){
        ...
    }
     * </pre>
     *
     * (Replace T with a concrete class such as String or Integer.)
     */
    private class DoubleLinkIterator implements Iterator<T> {
	// current is initialized to beginning of linked deque.
	private DoubleNode<T> current = DoubleLink.this.front;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
	    return this.current != null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next() {
	    T result = null;

	    if (this.current == null) {
		throw new NoSuchElementException();
	    } else {
		result = this.current.getValue();
		this.current = this.current.getNext();
	    }
	    return result;
	}
    }

    // First node of linked deque.
    protected DoubleNode<T> front = null;

    // Number of elements currently stored in linked deque.
    protected int length = 0;
    // Last node of linked deque.
    protected DoubleNode<T> rear = null;

    /**
     * Returns the current number of elements in the linked structure.
     *
     * @return the value of length.
     */
    public final int getLength() {
	return this.length;
    }

    /**
     * Determines whether the deque is empty or not.
     *
     * @return true if deque is empty, false otherwise.
     */
    public final boolean isEmpty() {
	return this.front == null;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public final Iterator<T> iterator() {
	return new DoubleLinkIterator();
    }

    /**
     * Returns an array of data from a deque. Not thread safe as it assumes
     * contents of deque are not changed by an external thread during the copy
     * loop. If data elements are added or removed by an external thread while
     * the data is being copied to the array, then the declared array length may
     * no longer be valid.
     *
     * @return an array of data of type T. Returns null if the deque is empty.
     */
    @SuppressWarnings("unchecked")
    public final T[] toArray() {
	T[] a = null;

	if (this.front != null) {
	    // Create an array of data based upon the class of the head data.
	    a = (T[]) Array.newInstance(this.front.getValue().getClass(),
		    this.length);
	    final Iterator<T> iter = this.iterator();

	    for (int i = 0; i < this.length; i++) {
		a[i] = iter.next();
	    }
	}
	return a;
    }
}