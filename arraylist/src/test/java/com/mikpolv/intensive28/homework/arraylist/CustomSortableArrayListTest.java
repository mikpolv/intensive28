package com.mikpolv.intensive28.homework.arraylist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomSortableArrayListTest {
  private CustomSortableArrayList<Object> testInstance;
  private final Object DEFAULT_VALUE_TO_ADD = new Object();
  private final Object ANOTHER_DEFAULT_VALUE_TO_ADD = new Object();
  private final Collection<Object> COLLECTION_TO_ADD =
      new ArrayList<>(Arrays.asList(ANOTHER_DEFAULT_VALUE_TO_ADD, ANOTHER_DEFAULT_VALUE_TO_ADD));

  @BeforeEach
  void setUp() {
    testInstance = new CustomSortableArrayList<>();
  }

  @Test
  void shouldIncreaseSizeByAdding() {
    int numOfAdds = 10;
    // add 10 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
      assertEquals(testInstance.size(), i + 1);
    }
  }

  @Test
  void shouldAddNewElementAndReturn() {
    testInstance.add(DEFAULT_VALUE_TO_ADD);
    assertEquals(testInstance.get(0), DEFAULT_VALUE_TO_ADD);
  }

  @Test
  void shouldThrowExceptionIfIndexOutOfBoundaries() {
    testInstance.add(DEFAULT_VALUE_TO_ADD);
    // get index 10
    assertThrows(IndexOutOfBoundsException.class, () -> testInstance.get(10));
    // get index equals size
    assertThrows(IndexOutOfBoundsException.class, () -> testInstance.get(1));
    // remove index equals size
    assertThrows(IndexOutOfBoundsException.class, () -> testInstance.remove(1));
  }

  @Test
  void shouldAddWithIndexEqualsSize() {
    testInstance.add(DEFAULT_VALUE_TO_ADD);
    testInstance.add(DEFAULT_VALUE_TO_ADD);
    testInstance.add(testInstance.size(), DEFAULT_VALUE_TO_ADD);
    assertEquals(testInstance.get(2), DEFAULT_VALUE_TO_ADD);
  }

  @Test
  void shouldAddMoreThanTenItems() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    assertEquals(testInstance.size(), numOfAdds);
  }

  @Test
  void shouldAddByIndex() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // add by index 10
    testInstance.add(10, ANOTHER_DEFAULT_VALUE_TO_ADD);
    assertEquals(testInstance.get(10), ANOTHER_DEFAULT_VALUE_TO_ADD);
  }

  @Test
  void shouldAddCollection() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // add collection
    testInstance.add(COLLECTION_TO_ADD);
    assertEquals(testInstance.get(20), ANOTHER_DEFAULT_VALUE_TO_ADD);
    assertEquals(testInstance.get(21), ANOTHER_DEFAULT_VALUE_TO_ADD);
  }

  @Test
  void shouldClear() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    testInstance.clear();
    assertEquals(0, testInstance.size());
  }

  @Test
  void shouldReturnTrueIfIsEmpty() {
    assertTrue(testInstance.isEmpty());
  }

  @Test
  void shouldReturnFalseIfIsEmpty() {
    testInstance.add(DEFAULT_VALUE_TO_ADD);
    assertFalse(testInstance.isEmpty());
  }

  @Test
  void shouldRemoveElement() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // add by index 10 another element
    testInstance.add(10, ANOTHER_DEFAULT_VALUE_TO_ADD);
    // remove index 10
    Object removedObject = testInstance.remove(10);
    // should return deleted value
    assertEquals(removedObject, ANOTHER_DEFAULT_VALUE_TO_ADD);
    assertEquals(testInstance.get(10), DEFAULT_VALUE_TO_ADD);
  }

  @Test
  void shouldRemoveByIndexAndDecreaseSize() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // remove index 10
    testInstance.remove(10);
    assertEquals(numOfAdds - 1, testInstance.size());
  }

  @Test
  void shouldRemoveByElementAndDecreaseSize() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // remove element
    testInstance.remove(DEFAULT_VALUE_TO_ADD);
    assertEquals(numOfAdds - 1, testInstance.size());
  }

  @Test
  void shouldNotRemoveByElementAndDecreaseSize() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // remove not contained element
    boolean ifRemoved = testInstance.remove(ANOTHER_DEFAULT_VALUE_TO_ADD);
    // should be false if removed
    assertFalse(ifRemoved);
    assertEquals(numOfAdds, testInstance.size());
  }

  @Test
  void shouldRemoveByObject() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // add by index 10 another element
    testInstance.add(10, ANOTHER_DEFAULT_VALUE_TO_ADD);
    // remove element
    boolean ifRemoved = testInstance.remove(ANOTHER_DEFAULT_VALUE_TO_ADD);
    // should be true if removed
    assertTrue(ifRemoved);
    assertEquals(testInstance.get(10), DEFAULT_VALUE_TO_ADD);
  }

  @Test
  void shouldRemoveNull() {
    int numOfAdds = 20;
    // add 20 items
    for (int i = 0; i < numOfAdds; i++) {
      testInstance.add(DEFAULT_VALUE_TO_ADD);
    }
    // add by index 10 null
    testInstance.add(10, null);
    // remove null
    testInstance.remove(null);
    assertEquals(testInstance.get(10), DEFAULT_VALUE_TO_ADD);
  }

  @Test
  void shouldSort() {
    CustomSortableArrayList<Integer> testInstanceForSort = new CustomSortableArrayList<>();
    // create unsorted list
    Integer[] unsortedList = {12, 6, 12, 0, 5, 4, 4, 10, 9, 2};
    // create sorted list
    Integer[] sortedList = {0, 2, 4, 4, 5, 6, 9, 10, 12, 12};
    testInstanceForSort.add(Arrays.asList(unsortedList));
    testInstanceForSort.sort(Integer::compare);
    for (int i = 0; i < testInstanceForSort.size(); i++) {
      assertEquals(sortedList[i], testInstanceForSort.get(i));
    }
  }

  @Test
  void shouldSortEmptyArrayList() {
    CustomSortableArrayList<Integer> testInstanceForSort = new CustomSortableArrayList<>();
    assertDoesNotThrow(() -> testInstanceForSort.sort(Integer::compare));
  }
}
