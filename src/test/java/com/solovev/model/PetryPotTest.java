package com.solovev.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PetryPotTest {

    @ParameterizedTest()
    @ValueSource(ints = {0, 1, 2, 3, 5, 1000}) //to do n = 12113 java heap space problem
    public void creationTestNormal(int size) {
        assertEquals(size * size, new PetryPot(size).getAddresses().size());
    }
    @Test
    public void creationTestThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> new PetryPot(-1));
    }
    @Test
    public void neighborsNoNeighbors(){
        assertTrue(emptyPot.getEmptyNeighbors(new Address(0,0)).isEmpty());
        assertTrue(new PetryPot(1).getEmptyNeighbors(new Address(0,0)).isEmpty());
    }
    @Test
    public void neighborsAllEmpty() {
        //first
        Map<Address, Collection<Address>> neighbors3x3For00 = Map.of(
                new Address(0, 0),
                List.of(new Address(0, 1), new Address(1, 1), new Address(1, 0))
        );
        assertEquals(neighbors3x3For00.get(new Address(0, 0)), pot3x3.getEmptyNeighbors(new Address(0, 0)));

        //last
        Address address22 = new Address(2, 2);
        Collection<Address> forAddress22 = List.of(new Address(2, 1), new Address(1, 1), new Address(1, 2));
        assertEquals(forAddress22, pot3x3.getEmptyNeighbors(address22));

        //middle
        Address address11 = new Address(1, 1);
        Collection<Address> forAddress11 = pot3x3.getAddresses();
        forAddress11.remove(address11);
        assertEquals(forAddress11, pot3x3.getEmptyNeighbors(address11));

        //upside
        Address address01 = new Address(0, 1);
        Collection<Address> forAddress01 = List.of(new Address(0, 0),
                new Address(1, 0),
                address11,
                new Address(1, 2),
                new Address(0, 2));
        assertEquals(forAddress01,pot3x3.getEmptyNeighbors(address01));

        //left side
        Address address10 = new Address(0,1);
        Collection<Address> forAddress10 = List.of( new Address(0,0),
                address01,
                address11,
                new Address(2,1),
                new Address(2,0));
        assertEquals(forAddress10, pot3x3.getEmptyNeighbors(address10));

        // Address (2, 0)
        Address address20 = new Address(2, 0);
        Collection<Address> forAddress20 = List.of(new Address(1, 0), new Address(1, 1), new Address(2, 1));
        assertEquals(forAddress20, pot3x3.getEmptyNeighbors(address20));

        // Address (0, 2)
        Address address02 = new Address(0, 2);
        Collection<Address> forAddress02 = List.of(new Address(0, 1), new Address(1, 1), new Address(1, 2));
        assertEquals(forAddress02, pot3x3.getEmptyNeighbors(address02));
    }
    private PetryPot emptyPot;
    private PetryPot pot3x3 = new PetryPot(3);

    /**
     * Reloads all pots to the initial state
     */
    @BeforeEach
    private void reloadPots(){
        emptyPot = new PetryPot();
        pot3x3 = new PetryPot(3);
    }


}