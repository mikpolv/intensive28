package com.mikpolv.intensive28.homework.arraylist;

import java.util.Collection;
import java.util.Comparator;

/**
 * Resizable-arraylist with sort.
 *
 * @param <E> – the type of elements in this list
 */
public interface CustomSortableList<E> {
  /**
   * @return the number of elements in this list.
   */
  int size();

  /**
   * Inserts the element at the specified position of the list.
   *
   * @param index – position of element to insert
   * @param e – element to insert
   */
  void add(int index, E e);

  /**
   * Insert the element to the end of the list
   *
   * @param e – the element to insert
   * @return true
   */
  boolean add(E e);

  /**
   * Insert elements of the Collection to the end of the list.
   *
   * @param c – The collection with elements to insert
   * @return true if all elements added
   */
  boolean add(Collection<? extends E> c);

  /** Removes all elements from the list. */
  void clear();

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index – position to return
   * @return the element of the specified position
   */
  E get(int index);

  /**
   * Returns true if this list contains no elements.
   *
   * @return true if this list contains no elements.
   */
  boolean isEmpty();

  /**
   * Removes the element on specified position.
   *
   * @param index – specified position of element to remove
   * @return removed element
   */
  E remove(int index);

  /**
   * Removes the first occurrence of the specified element from this list.
   *
   * @param o – Element to remove
   * @return true if this list contained the specified element
   */
  boolean remove(Object o);

  /**
   * Sorts this list with specified comparator.
   *
   * @param comparator – comparator for element in the list.
   */
  void sort(Comparator<? super E> comparator);
}
