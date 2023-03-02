package cp213;

/**
 * A simple linked sorted list structure of <code>T</code> objects. Only the
 * <code>T</code> value contained in the sorted list is visible through the
 * standard sorted list methods. Extends the <code>DoubleLink</code> class,
 * which already defines the front node, rear node, length, isEmpty, and
 * iterator.
 *
 * @author - your name here - Sahil Lalani
 * @author David Brown
 * @version 2018-07-12
 *
 * @param <T> this value structure value type.
 */
public class DoubleSortedList<T extends Comparable<T>> extends DoubleLink<T> {

    /**
     * Removes duplicates from this List. The list contains one and only one of
     * each value formerly present in this List. The first occurrence of each
     * value is preserved.
     */
    public void clean() {
	DoubleNode<T> keyNode = this.front;
	DoubleNode<T> current = null;

	while (keyNode != null) {
	    // Loop through every node - compare each node with the rest
	    current = keyNode.getNext();

	    while (current != null
		    && current.getValue().compareTo(keyNode.getValue()) == 0) {
		// Remove the current node by connecting the node before it
		// to the node after it.
		this.length--;
		keyNode.setNext(current.getNext());

		if (current.getNext() != null) {
		    current.getNext().setPrev(keyNode);
		}
		// Move to the _next node.
		current = current.getNext();
	    }
	    if (current == null) {
		this.rear = keyNode;
	    }
	    // Check for duplicates of the _next remaining node in this
	    // SortedList
	    keyNode = keyNode.getNext();
	}
	return;
    }

    /**
     * Combines contents of two lists into a third. Values are alternated from
     * the source lists into this List, but the sorted order is preserved. The
     * source lists are empty when finished. NOTE: value must not be moved, only
     * nodes.
     *
     * @param source1 The first list to combine with this List.
     * @param source2 The second list to combine with this List.
     */
    public void combine(final DoubleSortedList<T> source1,
	    final DoubleSortedList<T> source2) {

	while (source1.front != null && source2.front != null) {

	    if (source1.front.getValue()
		    .compareTo(source2.front.getValue()) <= 0) {
		this.moveFrontToRear(source1);
	    } else {
		this.moveFrontToRear(source2);
	    }
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
     * Determines if this SortedList contains key.
     *
     * @param key The key value to look for.
     * @return true if key is in this SortedList, false otherwise.
     */
    public boolean contains(final T key) {
	return this.linearSearch(key) != null;
    }

    /**
     * Finds the number of times key appears in list.
     *
     * @param key The value to look for.
     * @return The number of times key appears in this SortedList.
     */
    public int count(final T key) {
	int n = 0;
	DoubleNode<T> current = this.linearSearch(key);

	while (current != null && current.getValue().compareTo(key) == 0) {
	    n++;
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
     * Get the nth item in this SortedList.
     *
     * @param n The index of the item to return.
     * @return The nth item in this SortedList.
     * @throws ArrayIndexOutOfBoundsException if n is not a valid index.
     */
    public T get(final int n) throws ArrayIndexOutOfBoundsException {
	T value = null;
	DoubleNode<T> current = this.front;
	int index = 0;

	for (index = 0; index < n && current != null; index++) {
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
     * Finds the location of a value by key in list.
     *
     * @param key The value to search for.
     * @return The index of key in this SortedList, -1 otherwise.
     */
    public int index(final T key) {
	int i = 0;
	DoubleNode<T> current = this.front;

	for (i = 0; current != null
		&& current.getValue().compareTo(key) < 0; i++) {
	    current = current.getNext();
	}
	if (current == null || current.getValue().compareTo(key) != 0) {
	    i = -1;
	}
	return i;
    }

    /**
     * Inserts value into this SortedList.
     *
     * @param value The new value to insert into this SortedList.
     */
    public void insert(final T value) {
	DoubleNode<T> current = this.front;

	// Loop through the linked list to find the proper position for value.
	while (current != null && value.compareTo(current.getValue()) > 0) {
	    current = current.getNext();
	}
	// Create the new node and link it to current.
	if (current == null) {
	    // Add to the rear of the list
	    final DoubleNode<T> node = new DoubleNode<>(value, this.rear, null);

	    if (this.rear == null) {
		this.front = node;
	    } else {
		this.rear.setNext(node);
	    }
	    this.rear = node;
	} else if (current.getPrev() == null) {
	    // The new node is the first node in the linked list.
	    final DoubleNode<T> node = new DoubleNode<>(value, null,
		    this.front);

	    this.front.setPrev(node);
	    this.front = node;
	} else {
	    final DoubleNode<T> node = new DoubleNode<>(value,
		    current.getPrev(), current);
	    current.getPrev().setNext(node);
	    current.setPrev(node);
	}
	// Increment the list length.
	this.length++;
	return;
    }

    /**
     * Creates an intersection of two other Lists into this SortedList. Copies
     * value to this SortedList. left and right Lists are unchanged.
     *
     * @param left  The first List to create an intersection from.
     * @param right The second List to create an intersection from.
     */
    public void intersection(final DoubleSortedList<T> left,
	    final DoubleSortedList<T> right) {
	// Start with contents of left List.
	DoubleNode<T> tempLeft = left.front;
	DoubleNode<T> tempRight = right.front;

	while (tempLeft != null && tempRight != null) {
	    final int result = tempLeft.getValue()
		    .compareTo(tempRight.getValue());

	    if (result < 0) {
		// Data does not match - move to next node in left
		tempLeft = tempLeft.getNext();
	    } else if (result > 0) {
		// Data does not match - move to next node in right
		tempRight = tempRight.getNext();
	    } else {
		// Value exists in both lists
		if (this.front == null) {
		    // this list is empty
		    this.insert(tempLeft.getValue());
		} else if (this.rear.getValue()
			.compareTo(tempLeft.getValue()) < 0) {
		    this.rear.setNext(new DoubleNode<>(tempLeft.getValue(),
			    this.rear, null));
		    this.rear = this.rear.getNext();
		    this.length++;
		} else {
		    tempLeft = tempLeft.getNext();
		    tempRight = tempRight.getNext();
		}
	    }
	}
	return;
    }

    /**
     * Determines whether two lists are identical.
     *
     * @param that The list to compare against this SortedList.
     * @return true if this SortedList contains the same values in the same
     *         order as that, false otherwise.
     */
    public boolean isIdentical(final DoubleSortedList<T> that) {
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
     * Finds the maximum value in this SortedList.
     *
     * @return The maximum value.
     */
    public T max() {
	final T value = this.rear.getValue();
	return value;
    }

    /**
     * Finds the minimum value in this SortedList.
     *
     * @return The minimum value.
     */
    public T min() {
	final T value = this.front.getValue();
	return value;
    }

    /**
     * Finds, removes, and returns the value in this SortedList that matches
     * key.
     *
     * @param key The value to search for.
     * @return The value matching key, null otherwise.
     */
    public T remove(final T key) {
	T value = null;
	// search list for key.
	final DoubleNode<T> current = this.linearSearch(key);

	if (current != null) {
	    value = this.removeNode(current);
	}
	return value;
    }

    /**
     * Removes and returns the value at the front of the list.
     *
     * @return the value that has been removed.
     */
    public T removeFront() {
	return this.removeNode(this.front);
    }

    /**
     * Finds and removes all values in this SortedList that match key.
     *
     * @param key The value to search for.
     */
    public void removeMany(final T key) {
	DoubleNode<T> temp = null;
	DoubleNode<T> current = this.linearSearch(key);

	while (current != null && current.getValue().compareTo(key) == 0) {
	    temp = current.getNext();
	    this.removeNode(current);
	    current = temp;
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
    public void split(final DoubleSortedList<T> target1,
	    final DoubleSortedList<T> target2) {

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
    public void splitAlternate(final DoubleSortedList<T> target1,
	    final DoubleSortedList<T> target2) {
	boolean moveleft = true;

	while (this.front != null) {

	    if (moveleft) {
		target1.moveFrontToRear(this);
	    } else {
		target2.moveFrontToRear(this);
	    }
	    moveleft = !moveleft;
	}
	return;
    }

    /**
     * Creates a union of two other Lists into this SortedList. Copies value to
     * this list. left and right Lists are unchanged.
     *
     * @param left  The first List to create a union from.
     * @param right The second List to create a union from.
     */
    public void union(final DoubleSortedList<T> left,
	    final DoubleSortedList<T> right) {
	DoubleNode<T> tempLeft = left.front;
	DoubleNode<T> tempRight = right.front;
	DoubleNode<T> source = null;
	T newValue = null;

	while (tempLeft != null && tempRight != null) {
	    final int result = tempLeft.getValue()
		    .compareTo(tempRight.getValue());

	    if (result < 0) {
		newValue = tempLeft.getValue();
		tempLeft = tempLeft.getNext();
	    } else if (result > 0) {
		newValue = tempRight.getValue();
		tempRight = tempRight.getNext();
	    } else {
		// Value exists in both lists
		newValue = tempLeft.getValue();
		tempLeft = tempLeft.getNext();
		tempRight = tempRight.getNext();
	    }

	    if (this.front == null) {
		// this list is empty
		this.insert(newValue);
	    } else if (this.rear.getValue().compareTo(newValue) < 0) {
		this.rear.setNext(new DoubleNode<>(newValue, this.rear, null));
		this.rear = this.rear.getNext();
		this.length++;
	    }
	}
	// Determine which source list still has data, if any
	if (tempLeft != null) {
	    source = tempLeft;
	} else if (tempRight != null) {
	    source = tempRight;
	}
	if (this.front == null && source != null) {
	    this.insert(source.getValue());
	    source = source.getNext();
	}
	while (source != null) {
	    if (this.rear.getValue().compareTo(source.getValue()) < 0) {
		this.rear.setNext(
			new DoubleNode<>(source.getValue(), this.rear, null));
		this.rear = this.rear.getNext();
		this.length++;
	    }
	    source = source.getNext();
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

	while (current != null && current.getValue().compareTo(key) < 0) {
	    current = current.getNext();
	}
	// Does the current node contain the matching value?
	if (current != null && current.getValue().compareTo(key) != 0) {
	    current = null;
	}
	return current;
    }

    /**
     * @param that
     */
    private void moveFrontToRear(final DoubleSortedList<T> that) {
	// Move the front node from that to the rear of this
	final DoubleNode<T> node = that.front;
	// Update the source list
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
     * Removes a value from anywhere in a sorted list. The node holding it is
     * also removed, and the nodes linked to it are updated appropriately.
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
