
package com.solovev.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.solovev.model.PetriDish.Address

@ExtendWith(MockitoExtension.class)
public class AddressEmptyNeighborsTest {

	private PetriDish.Address address;

	private Map<PetriDish.Address, Bacteria> addresses;

	private static final int TEST_GRID_SIZE = 10; // Define grid size for test

	@BeforeEach
	void setUp() {
		addresses = new HashMap<>();
		for (int i = 0; i < TEST_GRID_SIZE; i++) {
			for (int j = 0; j < TEST_GRID_SIZE; j++) {
				addresses.put(new PetriDish.Address(i, j), null); // Initialize all
																	// addresses as empty
			}
		}
		address = new PetriDish.Address(5, 5); // Central address
		address.addresses = addresses;
		address.size = TEST_GRID_SIZE;
	}

	@Test
	@Tag("valid")
	public void checkNeighborsForCenterPosition() {
		addresses.put(new PetriDish.Address(6, 5), new Bacteria()); // Set one neighbor as
																	// occupied
		Collection<PetriDish.Address> expect = new HashSet<>();
		// Expected empty neighbors
		expect.add(new PetriDish.Address(4, 4));
		expect.add(new PetriDish.Address(4, 5));
		expect.add(new PetriDish.Address(4, 6));
		expect.add(new PetriDish.Address(5, 4));
		expect.add(new PetriDish.Address(5, 6));
		expect.add(new PetriDish.Address(6, 4));
		expect.add(new PetriDish.Address(6, 6));
		Collection<PetriDish.Address> actual = address.emptyNeighbors();
		assertEquals(expect, actual);
	}

	@Test
	@Tag("boundary")
	public void checkNoNeighborsAtGridBoundary() {
		address = new PetriDish.Address(0, 0);
		address.addresses = addresses;
		address.size = 1; // Minimal grid
		Collection<PetriDish.Address> actual = address.emptyNeighbors();
		assertEquals(0, actual.size());
	}

	@Test
	@Tag("valid")
	public void partiallyFilledNeighborsDetection() {
		address = new PetriDish.Address(3, 3);
		address.addresses = addresses;
		address.size = TEST_GRID_SIZE;
		addresses.put(new PetriDish.Address(2, 3), new Bacteria()); // Set one neighbor as
																	// occupied
		Collection<PetriDish.Address> expect = new HashSet<>();
		expect.add(new PetriDish.Address(2, 2));
		expect.add(new PetriDish.Address(2, 4));
		expect.add(new PetriDish.Address(3, 2));
		expect.add(new PetriDish.Address(3, 4));
		expect.add(new PetriDish.Address(4, 2));
		expect.add(new PetriDish.Address(4, 3));
		expect.add(new PetriDish.Address(4, 4));
		Collection<PetriDish.Address> actual = address.emptyNeighbors();
		assertEquals(expect, actual);
	}

	@Test
	@Tag("boundary")
	public void singleElementGridNeighborCheck() {
		address = new PetriDish.Address(0, 0);
		address.addresses = addresses;
		address.size = 1; // Single element grid
		Collection<PetriDish.Address> actual = address.emptyNeighbors();
		assertEquals(0, actual.size());
	}

	@Test
	@Tag("boundary")
	public void fullBoundaryPositionCheck() {
		address.size = TEST_GRID_SIZE;
		address.addresses.put(new PetriDish.Address(0, 1), null); // Set adjacent to
																	// boundary empty
		address = new PetriDish.Address(0, 0);
		address.addresses = this.addresses; // Make accessible
		Collection<PetriDish.Address> actual = address.emptyNeighbors();
		Collection<PetriDish.Address> expect = new HashSet<>();
		expect.add(new PetriDish.Address(0, 1));
		expect.add(new PetriDish.Address(1, 0));
		expect.add(new PetriDish.Address(1, 1));
		assertEquals(expect, actual);
	}

}