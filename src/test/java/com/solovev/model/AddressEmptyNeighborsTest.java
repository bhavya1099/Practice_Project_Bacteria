
package com.solovev.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.solovev.model.PetriDish.Address

public class AddressEmptyNeighborsTest {

	private Map<Address, Bacteria> addresses;

	@BeforeEach
	public void setUp() {
		addresses = new HashMap<>();
	}

	private Address createAddress(int x, int y) {
		Address address = new Address(x, y);
		address.addresses = this.addresses; // Associate the map with the address instance
		return address;
	}

	@Test
	@Tag("boundary")
	public void checkCenterPositionEmptyNeighbors() {
		// Arrange
		int size = 3; // Assuming grid size is 3 for simplicity
		Address center = createAddress(1, 1);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i != 1 || j != 1) {
					addresses.put(createAddress(i, j), null);
				}
			}
		}
		// Act
		Collection<Address> result = center.emptyNeighbors();
		// Assert
		Set<Address> expected = new HashSet<>();
		expected.add(createAddress(0, 0));
		expected.add(createAddress(0, 1));
		expected.add(createAddress(0, 2));
		expected.add(createAddress(1, 0));
		expected.add(createAddress(1, 2));
		expected.add(createAddress(2, 0));
		expected.add(createAddress(2, 1));
		expected.add(createAddress(2, 2));
		assertEquals(expected, new HashSet<>(result));
	}

	@Test
	@Tag("boundary")
	public void checkCornerPositionEmptyNeighbors() {
		// Arrange
		Address corner = createAddress(0, 0);
		addresses.put(createAddress(0, 1), null);
		addresses.put(createAddress(1, 0), null);
		addresses.put(createAddress(1, 1), null);
		// Act
		Collection<Address> result = corner.emptyNeighbors();
		// Assert
		Set<Address> expected = new HashSet<>();
		expected.add(createAddress(0, 1));
		expected.add(createAddress(1, 0));
		expected.add(createAddress(1, 1));
		assertEquals(expected, new HashSet<>(result));
	}

	@Test
	@Tag("boundary")
	public void checkEdgePositionEmptyNeighbors() {
		// Arrange
		Address edge = createAddress(0, 1);
		addresses.put(createAddress(0, 0), null);
		addresses.put(createAddress(0, 2), null);
		addresses.put(createAddress(1, 0), null);
		addresses.put(createAddress(1, 1), null);
		addresses.put(createAddress(1, 2), null);
		// Act
		Collection<Address> result = edge.emptyNeighbors();
		// Assert
		Set<Address> expected = new HashSet<>();
		expected.add(createAddress(0, 0));
		expected.add(createAddress(0, 2));
		expected.add(createAddress(1, 0));
		expected.add(createAddress(1, 1));
		expected.add(createAddress(1, 2));
		assertEquals(expected, new HashSet<>(result));
	}

	@Test
	@Tag("valid")
	public void checkNoEmptyNeighborsWhenAllOccupied() {
		// Arrange
		Address pos = createAddress(1, 1);
		Bacteria bacteria = new Bacteria(new ConfigurationOfBacteriaBehavior());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!(i == 1 && j == 1)) {
					addresses.put(createAddress(i, j), bacteria);
				}
			}
		}
		// Act
		Collection<Address> result = pos.emptyNeighbors();
		// Assert
		assertTrue(result.isEmpty());
	}

	@Test
	@Tag("invalid")
	public void checkWrapAroundHandlingEmptyNeighbors() {
		// This test assumes hypothetical wrap around
		// Arrange
		int size = 3;
		Address edge = createAddress(size - 1, size - 1);
		addresses.put(createAddress(0, 0), null); // Mock wrapping behavior as if (size-1,
													// size-1) wraps to (0, 0)
		addresses.put(createAddress(size - 2, size - 1), null);
		addresses.put(createAddress(size - 1, size - 2), null);
		// Act
		Collection<Address> result = edge.emptyNeighbors();
		// Assert
		Set<Address> expected = new HashSet<>();
		expected.add(createAddress(0, 0)); // Would need actual wrap-around logic in
											// emptyNeighbors to pass
		expected.add(createAddress(size - 2, size - 1));
		expected.add(createAddress(size - 1, size - 2));
		assertEquals(expected, new HashSet<>(result));
	}

}