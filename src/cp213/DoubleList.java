package cp213;

/**
 * A simple linked list structure of <code>T</code> objects. Only the
 * <code>T</code> value contained in the list is visible through the standard
 * list methods. Extends the <code>DoubleLink</code> class, which already
 * defines the front node, rear node, length, isEmpty, and iterator.
 *
 * @author - your name here -Sahil Lalani
 * @author David Brown
 * @version 2019-03-11
 *
 * @param <T> this data structure value type.
 */
public class DoubleList<T extends Comparable<T>> extends DoubleLink<T> {

    public static void main(final String[] args) {
	final DoubleList<Integer> dl = new DoubleList<>();

	dl.insert(0, 0);
	dl.insert(0, 1);
	dl.insert(0, 2);
	dl.insert(0, 3);
	final DoubleNode<Integer> previous = dl.linearSearch(2);

	boolean thing = dl.contains(2);
	System.out.println("Contains: " + thing);

	if (previous == null) {
	    System.out.println(dl.front.getValue());
	} else {
	    System.out.println(previous.getNext().getValue());
	}

    }

    /**
     * Appends value to the rear of this List.
     *
     * @param value The value to append.
     */
    public void append(final T value) {
	final DoubleNode<T> node = new DoubleNode<>(value, this.rear, null);

	if (this.rear == null) {
	    this.front = node;
	} else {
	    this.rear.setNext(node);
	}
	this.rear = node;
	this.length++;
	return;
    }

    /**
     * Removes duplicates from this List. The list contains one and only one of
     * each value formerly present in this List. The first occurrence of each
     * value is preserved.
     */
    public void clean() {
	DoubleNode<T> keyNode = this.front;
	DoubleNode<T> current = this.front;

	while (keyNode != null) {
	    // Loop through every node - compare each node with the rest
	    current = keyNode.getNext();

	    while (current != null) {
		// Always search to the end of this List (may have > 1
		// duplicate)
		if (current.getValue().compareTo(keyNode.getValue()) == 0) {
		    this.removeNode(current);
		}
		// Move to the _next node.
		current = current.getNext();
	    }
	    // Check for duplicates of the _next remaining node in this List
	    keyNode = keyNode.getNext();
	}
	return;
    }

    /**
     * Combines contents of two lists into a third. Values are alternated from
     * the source lists into this List. The source lists are empty when
     * finished. NOTE: value must not be moved, only nodes.
     *
     * @param source1 The first list to combine with this List.
     * @param source2 The second list to combine with this List.
     */
    public void combine(final DoubleList<T> source1,
	    final DoubleList<T> source2) {

	while (source1.front != null && source2.front != null) {
	    this.moveFrontToRear(source1);
	    this.moveFrontToRear(source2);
	}
	while (source1.front != null) {
	    this.moveFrontToRear(source1);
	}
	while (source2.front != null) {
	    this.moveFrontToRear(source2);
	}
	return;
    }

    /**
     * Determines if this List contains key.
     *
     * @param key The key value to look for.
     * @return true if key is in this List, false otherwise.
     */
    public boolean contains(final T key) {
	return this.linearSearch(key) != null;
    }

    /**
     * Finds the number of times key appears in list.
     *
     * @param key The value to look for.
     * @return The number of times key appears in this List.
     */
    public int count(final T key) {
	int n = 0;
	DoubleNode<T> current = this.front;

	while (current != null) {
	    if (current.getValue().compareTo(key) == 0) {
		n++;
	    }
	    current = current.getNext();
	}
	return n;
    }

    /**
     * Finds and returns the value in list that matches key.
     *
     * @param key The value to search for.
     * @return The value that matches key, null otherwise.
     */
    public T find(final T key) {
	T value = null;
	final DoubleNode<T> node = this.linearSearch(key);

	if (node != null) {
	    value = node.getValue();
	}
	return value;
    }

    /**
     * Get the nth item in this List.
     *
     * @param n The index of the item to return.
     * @return The nth item in this List.
     * @throws ArrayIndexOutOfBoundsException if n is not a valid index.
     */
    public T get(final int n) throws ArrayIndexOutOfBoundsException {
	T value = null;
	DoubleNode<T> current = this.front;

	for (int index = 0; index < n && current != null; index++) {
	    current = current.getNext();
	}
	if (current == null) {
	    throw new ArrayIndexOutOfBoundsException("Invalid index");
	} else {
	    value = current.getValue();
	}
	return value;
    }

    /**
     * Finds the first location of a value by key in this List.
     *
     * @param key The value to search for.
     * @return The index of key in this List, -1 otherwise.
     */
    public int index(final T key) {
	int i = 0;
	DoubleNode<T> current = this.front;

	for (i = 0; current != null
		&& current.getValue().compareTo(key) != 0; i++) {
	    current = current.getNext();
	}
	if (current == null) {
	    i = -1;
	}
	return i;
    }

    /**
     * Inserts value into this List at index i. If i greater than the length of
     * this List, append value to the end of this List. Accepts negative values:
     * -1 is the rear, -2 is second from the end, etc.
     *
     * @param n     The index to insert the new value at.
     * @param value The new value to insert into this List.
     */
    public void insert(int n, final T value) {

	if (n < 0) {
	    // negative index
	    n = this.length + n;
	}
	if (n >= this.length) {
	    this.append(value);
	} else if (n == 0) {
	    this.prepend(value);
	} else {
	    DoubleNode<T> current = this.front;

	    for (int i = 0; i < n; i++) {
		current = current.getNext();
	    }
	    final DoubleNode<T> node = new DoubleNode<>(value,
		    current.getPrev(), current);
	    current.getPrev().setNext(node);
	    current.setPrev(node);
	    this.length++;
	}
	return;
    }

    /**
     * Creates an intersection of two other Lists into this List. Copies value
     * to this List. Source Lists are unchanged.
     *
     * @param source1 The first List to create an intersection from.
     * @param source2 The second List to create an intersection from.
     */
    public void intersection(final DoubleList<T> source1,
	    final DoubleList<T> source2) {
	// Start with contents of left List.
	DoubleNode<T> temp = source1.front;

	while (temp != null) {
	    if (!this.contains(temp.getValue())
		    && source2.contains(temp.getValue())) {
		this.append(temp.getValue());
	    }
	    temp = temp.getNext();
	}
	return;
    }

    /**
     * Determines whether two lists are identical.
     *
     * @param that The list to compare against this List.
     * @return true if this List contains the same values in the same order as
     *         that, false otherwise.
     */
    public boolean isIdentical(final DoubleList<T> that) {
	boolean identical = true;

	if (this.length != that.length) {
	    identical = false;
	} else {
	    DoubleNode<T> currentThis = this.front;
	    DoubleNode<T> currentThat = that.front;

	    while (currentThis != null && currentThis.getValue()
		    .compareTo(currentThat.getValue()) == 0) {
		currentThis = currentThis.getNext();
		currentThat = currentThat.getNext();
	    }
	    identical = currentThis == null;
	}
	return identical;
    }

    /**
     * Finds the maximum value in this List.
     *
     * @return The maximum value.
     */
    public T max() {
	T value = this.front.getValue();
	DoubleNode<T> current = this.front.getNext();

	while (current != null) {

	    if (current.getValue().compareTo(value) > 0) {
		value = current.getValue();
	    }
	    current = current.getNext();
	}
	return value;
    }

    /**
     * Finds the minimum value in this List.
     *
     * @return The minimum value.
     */
    public T min() {
	T value = this.front.getValue();
	DoubleNode<T> current = this.front.getNext();

	while (current != null) {

	    if (current.getValue().compareTo(value) < 0) {
		value = current.getValue();
	    }
	    current = current.getNext();
	}
	return value;
    }

    /**
     * Returns the value at the front of a list.
     *
     * @return the value at the front of the list.
     */
    public T peek() {
	return this.front.getValue();
    }

    /**
     * Adds a value to the front of this List.
     *
     * @param value The value to prepend.
     */
    public void prepend(final T value) {
	final DoubleNode<T> node = new DoubleNode<>(value, null, this.front);

	if (this.front == null) {
	    this.rear = node;
	} else {
	    this.front.setPrev(node);
	}
	this.front = node;
	this.length++;
	return;
    }

    /**
     * Finds, removes, and returns the value in this List that matches key.
     *
     * @param key The value to search for.
     * @return The value matching key, null otherwise.
     */
    public T remove(final T key) {
	T value = null;
	final DoubleNode<T> current = this.linearSearch(key);

	if (current != null) {
	    // key found
	    value = current.getValue();
	    this.length--;
	    // Track the nodes before and after current.
	    final DoubleNode<T> nodePrev = current.getPrev();
	    final DoubleNode<T> nodeNext = current.getNext();

	    if (nodePrev == null) {
		// current is the front node
		this.front = this.front.getNext();
	    } else {
		nodePrev.setNext(current.getNext());
	    }
	    if (nodeNext == null) {
		// current is the rear node
		this.rear = this.rear.getPrev();
	    } else {
		nodeNext.setPrev(current.getPrev());
	    }
	}
	return value;
    }

    /**
     * Removes the value at the front of this List.
     *
     * @return The value at the front of this List.
     */
    public T removeFront() {
	final T value = this.front.getValue();
	// Update front
	this.removeNode(this.front);
	return value;
    }

    /**
     * Finds and removes all values in this List that match key.
     *
     * @param key The value to search for.
     */
    public void removeMany(final T key) {
	DoubleNode<T> temp = null;
	DoubleNode<T> current = this.front;

	while (current != null) {

	    if (current.getValue().compareTo(key) == 0) {
		temp = current.getNext();
		this.removeNode(current);
		current = temp;
	    } else {
		current = current.getNext();
	    }
	}
	return;
    }

    /**
     * Removes and returns the value at the rear of the list.
     *
     * @return the value that has been removed.
     */
    public T removeRear() {
	return this.removeNode(this.rear);
    }

    /**
     * Reverses the order of the values in this List.
     */
    public void reverse() {
	DoubleNode<T> newfront = null;
	DoubleNode<T> oldNext = null;
	DoubleNode<T> oldPrev = null;
	this.rear = this.front;

	while (this.front != null) {
	    oldNext = this.front.getNext();
	    oldPrev = this.front.getPrev();
	    this.front.setNext(oldPrev);
	    this.front.setPrev(oldNext);
	    newfront = this.front;
	    this.front = oldNext;
	}
	this.front = newfront;
	return;
    }

    /**
     * Splits the contents of this List into the target Lists. Moves nodes only
     * - does not move value or call the high-level methods insert or remove.
     * this List is empty when done. The first half of this List is moved to
     * target1, and the last half of this List is moved to target2. If the
     * resulting lengths are not the same, target1 should have one more element
     * than target2.
     *
     * @param target1 The first List to move nodes to.
     * @param target2 The second List to move nodes to.
     */
    public void split(final DoubleList<T> target1,
	    final DoubleList<T> target2) {

	if (this.getLength() == 0) {
	    // Clear the targets - shouldn't actually be necessary.
	    target1.front = target1.rear = null;
	    target1.length = 0;
	    target2.front = target2.rear = null;
	    target2.length = 0;
	} else if (this.getLength() == 1) {
	    target1.front = target1.rear = this.front;
	    target1.length = 1;
	    target2.front = target2.rear = null;
	    target2.length = 0;
	} else {
	    final int mid = this.length / 2 + this.length % 2;
	    DoubleNode<T> current = this.front;

	    for (int i = 0; i < mid; i++) {
		current = current.getNext();
	    }
	    // Define target1
	    target1.front = this.front;
	    target1.rear = current.getPrev();
	    target1.rear.setNext(null);
	    target1.length = mid;
	    // Define target2
	    target2.front = current;
	    target2.front.setPrev(null);
	    target2.rear = this.rear;
	    target2.length = this.length - mid;
	}
	// Clean up the current list.
	this.front = this.rear = null;
	this.length = 0;
	return;
    }

    /**
     * Splits the contents of this List into the target Lists. Moves nodes only
     * - does not move value or call the high-level methods insert or remove.
     * this List is empty when done. Nodes are moved alternately from this List
     * to target1 and target2.
     *
     * @param target1 The first List to move nodes to.
     * @param target2 The second List to move nodes to.
     */
    public void splitAlternate(final DoubleList<T> target1,
	    final DoubleList<T> target2) {
	boolean moveLeft = true;

	while (this.front != null) {

	    if (moveLeft) {
		target1.moveFrontToRear(this);
	    } else {
		target2.moveFrontToRear(this);
	    }
	    moveLeft = !moveLeft;
	}
	return;
    }

    /**
     * Creates a union of two other Lists into this List. Copies value to this
     * list. source Lists are unchanged.
     *
     * @param source1 The first List to create a union from.
     * @param source2 The second List to create a union from.
     */
    public void union(final DoubleList<T> source1,
	    final DoubleList<T> source2) {
	// Copy value from left List into this List, if not already there.
	DoubleNode<T> temp = source1.front;

	while (temp != null) {

	    if (!this.contains(temp.getValue())) {
		// Value does not exist in this List.
		this.append(temp.getValue());
	    }
	    temp = temp.getNext();
	}
	// Copy value from right List into this List, if not already there.
	temp = source2.front;

	while (temp != null) {

	    if (!this.contains(temp.getValue())) {
		// Value does not exist in this List.
		this.append(temp.getValue());
	    }
	    temp = temp.getNext();
	}
	return;
    }

    /**
     * Searches for the first occurrence of key in this List. Private helper
     * methods - used only by other ADT methods.
     *
     * @param key The value to look for.
     * @return A pointer to the node containing key.
     */
    private DoubleNode<T> linearSearch(final T key) {
	DoubleNode<T> current = this.front;

	while (current != null && current.getValue().compareTo(key) != 0) {
	    current = current.getNext();
	}
	return current;
    }

    /**
     * A helper method to move the front node of that List to the rear of this
     * List.
     *
     * @param that The list to move the front of.
     */
    private void moveFrontToRear(final DoubleList<T> that) {
	// Move the front node from that to the rear of this
	final DoubleNode<T> node = that.front;
	// Update the source List.
	that.length--;
	that.front = that.front.getNext();

	if (that.front == null) {
	    that.rear = null;
	} else {
	    that.front.setPrev(null);
	}
	node.setPrev(this.rear);
	node.setNext(null);

	// Update the target list
	if (this.rear == null) {
	    this.front = node;
	} else {
	    this.rear.setNext(node);
	}
	this.rear = node;
	this.length++;
	return;
    }

    /**
     * Removes a value from anywhere in a deque. The node holding it is also
     * removed, and the nodes linked to it are updated appropriately.
     *
     * @param node the node to be removed.
     * @return the value in the node that is removed.
     */
    private T removeNode(final DoubleNode<T> node) {
	final T value = node.getValue();

	if (node.getPrev() == null) {
	    this.front = node.getNext();
	} else {
	    node.getPrev().setNext(node.getNext());
	}
	if (node.getNext() == null) {
	    this.rear = node.getPrev();
	} else {
	    node.getNext().setPrev(node.getPrev());
	}
	this.length--;
	return value;
    }
}
