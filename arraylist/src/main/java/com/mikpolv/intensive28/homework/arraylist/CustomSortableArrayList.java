package com.mikpolv.intensive28.homework.arraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/** Implementation of the Resizable-arraylist with sort using quicksort algorithm. */
public class CustomSortableArrayList<E> implements CustomSortableList<E> {

  /** Default initial capacity. */
  private static final int DEFAULT_CAPACITY = 10;

  /**
   * From Javadoc: A soft maximum array length imposed by array growth computations. Some JVMs (such
   * as HotSpot) have an implementation limit that will cause OutOfMemoryError("Requested array size
   * exceeds VM limit") to be thrown if a request is made to allocate an array of some length near
   * Integer.MAX_VALUE, even if there is sufficient heap available. The actual limit might depend on
   * some JVM implementation-specific characteristics such as the object header size. The soft
   * maximum value is chosen conservatively to be smaller than any implementation limit that is
   * likely to be encountered.
   */
  public static final int SOFT_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

  /**
   * The array buffer into which the elements of the ArrayList are stored. The capacity of the
   * ArrayList is the length of this array buffer.
   */
  private Object[] elementData;
  /** The number of elements the list contains. */
  private int size;

  /**
   * Constructs an empty list with the specified initial capacity.
   *
   * @param initialCapacity - the initial capacity of the list
   * @throws IllegalArgumentException if the specified initial capacity is negative
   */
  public CustomSortableArrayList(int initialCapacity) {
    if (initialCapacity >= 0) {
      this.elementData = new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }
  }

  /** Constructs an empty list with the default initial capacity. */
  public CustomSortableArrayList() {
    this.elementData = new Object[DEFAULT_CAPACITY];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void add(int index, E e) {
    rangeCheckForAdd(index);
    if (size == elementData.length) {
      elementData = enlarge(size + 1);
    }
    System.arraycopy(elementData, index, elementData, index + 1, size - index);
    elementData[index] = e;
    size++;
  }

  @Override
  public boolean add(E e) {
    if (size == elementData.length) {
      elementData = enlarge(size + 1);
    }
    elementData[size] = e;
    size++;
    return true;
  }

  /**
   * Insert elements of the Collection to the end of the list. Returns true if elements added, false
   * if collection with elements is empty
   *
   * @param c – The collection with elements to insert
   * @return true if elements added, false if collection with elements is empty
   */
  @Override
  public boolean add(Collection<? extends E> c) {
    Object[] a = c.toArray();
    int addedSize = a.length;
    if (addedSize == 0) {
      return false;
    }
    if (addedSize > (elementData.length - size)) {
      elementData = enlarge(size + addedSize);
    }
    System.arraycopy(a, 0, elementData, size, addedSize);
    size = size + addedSize;
    return true;
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      elementData[i] = null;
    }
    size = 0;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index – position to return
   * @return the element of the specified position
   */
  @Override
  public E get(int index) {
    rangeCheck(index);
    return elementData(index);
  }

  @SuppressWarnings("unchecked")
  private E elementData(int index) {
    return (E) elementData[index];
  }

  /**
   * @return true if this list contains no elements.
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Removes the element on specified position.
   *
   * @param index – specified position of element to remove
   * @return removed element
   */
  @Override
  public E remove(int index) {
    rangeCheck(index);
    E oldValue = elementData(index);
    int newSize = size - 1;
    if (newSize > index) {
      System.arraycopy(elementData, index + 1, elementData, index, newSize - index);
    }
    size = newSize;
    elementData[size] = null;
    return oldValue;
  }

  /**
   * Removes the first occurrence of the specified element from this list, if it is present. If the
   * list does not contain the element, it is unchanged and returns false.
   *
   * @param o – Element to remove
   * @return true if this list contained the specified element
   */
  @Override
  public boolean remove(Object o) {

    int index = findIndex(o);
    if (index >= 0) {
      remove(index);
      return true;
    }
    return false;
  }

  /** Private utility method to find index of the element if list contains it */
  private int findIndex(Object o) {
    if (o == null) {
      for (int i = 0; i < size; i++) {
        if (elementData[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (o.equals(elementData[i])) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * Sorts this list with specified comparator. Implemented Quicksort algorithm.
   *
   * @param comparator – comparator for element in the list.
   */
  @Override
  public void sort(Comparator<? super E> comparator) {
    sort(comparator, elementData, 0, size - 1);
  }

  /** Private utility method for sort with recursion */
  private void sort(Comparator<? super E> comparator, Object[] a, int lo, int hi) {
    if (hi <= lo) {
      return;
    }
    int j = partition(comparator, a, lo, hi);
    sort(comparator, a, lo, j - 1);
    sort(comparator, a, j + 1, hi);
  }

  /** Private splitting method for sort */
  @SuppressWarnings("unchecked")
  private int partition(Comparator<? super E> comparator, Object[] a, int lo, int hi) {
    int i = lo, j = hi + 1;
    while (true) {
      while (less(comparator, (E) a[++i], (E) a[lo])) {
        if (i == hi) {
          break;
        }
      }
      while (less(comparator, (E) a[lo], (E) a[--j])) {
        if (j == lo) {
          break;
        }
      }

      if (i >= j) {
        break;
      }
      exchange(a, i, j);
    }
    exchange(a, lo, j);
    return j;
  }

  /** Private compare method for sort. */
  private boolean less(Comparator<? super E> comparator, E v, E w) {
    return comparator.compare(v, w) < 0;
  }

  /** Private exchanging elements method for sort. */
  private void exchange(Object[] a, int i, int j) {
    Object swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  /**
   * Increases the capacity to ensure that it can hold at least the number of elements specified by
   * the minimum capacity argument.
   *
   * @param minCapacity - the desired minimum capacity
   * @return new array with increased capacity
   * @throws OutOfMemoryError if minCapacity is less than zero
   */
  private Object[] enlarge(int minCapacity) {
    int oldCapacity = elementData.length;
    if (oldCapacity > 0) {
      int minEnlarge = minCapacity - oldCapacity; // minimum
      int prefEnlarge = oldCapacity >> 1; // preferred
      int newCapacity = newLength(oldCapacity, minEnlarge, prefEnlarge);
      return elementData = Arrays.copyOf(elementData, newCapacity);
    } else {
      return elementData = new Object[DEFAULT_CAPACITY];
    }
  }

  /**
   * Computes a new array length given an array's current length, a minimum growth amount, and a
   * preferred growth amount.
   *
   * @param oldCapacity - current length of the array
   * @param minEnlarge - minimum required growth amount
   * @param prefEnlarge - preferred growth amount
   * @return the new array length
   */
  private int newLength(int oldCapacity, int minEnlarge, int prefEnlarge) {
    int prefLength = oldCapacity + Math.max(minEnlarge, prefEnlarge);
    if (prefLength > 0) {
      return prefLength;
    } else {
      int minLength = oldCapacity + minEnlarge;
      if (minLength < 0) { // overflow
        throw new OutOfMemoryError(
            "Required array length " + oldCapacity + " + " + minEnlarge + " is too large");
      } else return Math.max(minLength, SOFT_MAX_ARRAY_LENGTH);
    }
  }

  /**
   * Checks if index in bounds for add new element. For adding element to the next empty position in
   * the list
   */
  private void rangeCheckForAdd(int index) {
    if (index > size || index < 0) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
  }

  /** Checks if index in bounds */
  private void rangeCheck(int index) {
    if (index >= size || index < 0) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
  }
}
