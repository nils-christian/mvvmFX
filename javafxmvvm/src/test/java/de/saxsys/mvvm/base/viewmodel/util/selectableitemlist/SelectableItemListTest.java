package de.saxsys.mvvm.base.viewmodel.util.selectableitemlist;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.saxsys.jfx.mvvm.base.viewmodel.util.selectableitemlist.SelectableItemList;

/**
 * Tests for {@link SelectableItemList}.
 * 
 * @author sialcasa
 * 
 */
public class SelectableItemListTest {

	private StringConverter<Integer> stringConverter;
	private ObservableList<Integer> itemList;
	private SelectableItemList<Integer> selectableItemList;
	private IntegerProperty selectedIndex;
	private ReadOnlyListProperty<String> stringList;
	private ObjectProperty<Integer> selectedItem;

	/**
	 * Prepares the test.
	 */
	@Before
	public void init() {

		// Create the items
		itemList = FXCollections.observableArrayList();
		itemList.clear();
		itemList.add(1);
		itemList.add(2);
		itemList.add(3);

		// Create the converter
		stringConverter = new StringConverter<Integer>() {
			@Override
			public Integer fromString(String arg0) {
				return Integer.parseInt(arg0);
			}

			@Override
			public String toString(Integer arg0) {
				return arg0.toString();
			}
		};

		// Convenience
		selectableItemList = new SelectableItemList<Integer>(itemList,
				stringConverter);
		selectedIndex = selectableItemList.selectedIndexProperty();
		stringList = selectableItemList.stringListProperty();
		selectedItem = selectableItemList.selectedItemProperty();
	}

	/**
	 * Check whether the internal state is correct.
	 */
	@Test
	public void testStartState() {
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(itemList.get(0), selectedItem.get());
	}

	/**
	 * Check whether the string list changes when the item list changes.
	 */
	@Test
	public void addItemToItemList() {
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(3, stringList.size());
		itemList.add(4);
		Assert.assertEquals(4, stringList.size());
	}

	/**
	 * Check whether the {@link #selectedItem} changes when the
	 * {@link #selectedIndex} was changed.
	 */
	@Test
	public void setSelectedItemByIndex() {
		selectedIndex.set(1);
		Assert.assertEquals(itemList.get(1), selectedItem.get());
	}

	/**
	 * Check whether the {@link #selectedIndex} changes when the
	 * {@link #selectedItem} was changed.
	 */
	@Test
	public void setSelectedIndexByItem() {
		selectedItem.set(3);
		Assert.assertEquals(2, selectedIndex.get());
	}

	/**
	 * Check whether changes are refused when an invalid {@link #selectedItem}
	 * was set.
	 */
	@Test
	public void setSelectedIndexWithInvalidItem() {
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(1, (int) selectedItem.get());
		selectedItem.set(100);
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(1, (int) selectedItem.get());
		selectedItem.set(2);
		Assert.assertEquals(1, selectedIndex.get());
		Assert.assertEquals(2, (int) selectedItem.get());
	}

	/**
	 * Check whether changes are refused when an invalid {@link #selectedIndex}
	 * was set.
	 */
	@Test
	public void setSelectedItemWithInvalidIndex() {
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(1, (int) selectedItem.get());
		selectedIndex.set(100);
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(1, (int) selectedItem.get());
		selectedIndex.set(1);
		Assert.assertEquals(1, selectedIndex.get());
		Assert.assertEquals(2, (int) selectedItem.get());
	}

}
