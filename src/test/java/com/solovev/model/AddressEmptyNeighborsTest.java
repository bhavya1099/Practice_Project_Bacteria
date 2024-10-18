
package com.solovev.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.solovev.model.PetriDish.Address

public class AddressEmptyNeighborsTest {

	@Test
	@Tag("boundary")
	public void checkNeighborsForCenterPosition() {
		// Arrange
		PetriDish dish = new PetriDish(5);
		PetriDish.Address address = dish.new Address(2, 2);
		// Act
		Collection<PetriDish.Address> result = address.emptyNeighbors();
		// Assert
		assertEquals(8, result.size());
	}

	@Test
	@Tag("boundary")
	public void checkNeighborsForEdgePosition() {
		// Arrange
		PetriDish dish = new PetriDish(3);
		PetriDish.Address address = dish.new Address(0, 1);
		// Act
		Collection<PetriDish.Address> result = address.emptyNeighbors();
		// Assert
		assertEquals(5, result.size());
	}

	@Test
	@Tag("boundary")
	public void checkNeighborsForCornerPosition() {
		// Arrange
		PetriDish dish = new PetriDish(3);
		PetriDish.Address address = dish.new Address(0, 0);
		// Act
		Collection<PetriDish.Address> result = address.emptyNeighbors();
		// Assert
		assertEquals(3, result.size());
	}

	@Test
	@Tag("valid")
	public void ignoreNonEmptyNeighbors() {
		// Arrange
		PetriDish dish = new PetriDish(4);
		PetriDish.Address address = dish.new Address(2, 2);
		dish.addresses.put(dish.new Address(2, 1), new Bacteria(new ConfigurationOfBacteriaBehavior(0.5, 0.1, 14, 7)));
		// Act
		Collection<PetriDish.Address> result = address.emptyNeighbors();
		// Assert
		assertFalse(result.contains(dish.new Address(2, 1)));
		assertTrue(result.size() < 8 && result.size() >= 6); // Considering one neighbor
																// is non-empty
	}

}