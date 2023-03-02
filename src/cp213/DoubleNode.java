package cp213;

/**
 * The individual node of a doubly-linked structure that stores <code>T</code>
 * objects. This is a doubly-linked node. The node link can be updated, but not
 * the node data, in order to avoid moving data between nodes. Data structures
 * must be reordered by moving nodes.
 *
 * @author David Brown
 * @version 2019-01-26
 */
public final class DoubleNode<T> {

    // Link to the next DoubleNode.
    private DoubleNode<T> next = null;
    // Link to the previous DoubleNode.
    private DoubleNode<T> prev = null;
    // The T data.
    private T value = null;

    /**
     * Creates a new node with data and link to the previous and next nodes. Not
     * copy safe as it accepts a reference to the data rather than a copy of the
     * data.
     *
     * @param value
     *            the data to store in the node.
     * @param next
     *            the previous node to link to.
     * @param next
     *            the next node to link to.
     */
    public DoubleNode(final T value, final DoubleNode<T> prev,
	    final DoubleNode<T> next) {
	this.value = value;
	this.prev = prev;
	this.next = next;
    }

    /**
     * Returns the next node in the linked structure.
     *
     * @return The node that follows this node.
     */
    public final DoubleNode<T> getNext() {
	return this.next;
    }

    /**
     * Returns the previous node in the linked structure.
     *
     * @return The node that precedes this node.
     */
    public final DoubleNode<T> getPrev() {
	return this.prev;
    }

    /**
     * Returns the node data. Not copy safe as it returns a reference to the
     * data, not a copy of the data.
     *
     * @return The data portion of the node.
     */
    public final T getValue() {
	return this.value;
    }

    /**
     * Links this node to the next node.
     *
     * @param next
     *            The new node to link to.
     */
    public final void setNext(final DoubleNode<T> next) {
	this.next = next;
    }

    /**
     * Links this node to the previous node.
     *
     * @param prev
     *            The new node to link to.
     */
    public final void setPrev(final DoubleNode<T> prev) {
	this.prev = prev;
    }
}