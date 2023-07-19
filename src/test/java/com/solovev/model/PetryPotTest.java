package com.solovev.model;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class PetryPotTest {

    @ParameterizedTest()
    @ValueSource(ints = {1, 2, 3, 5, 12113})
    public void creationTestNormal(int size) {
        assertEquals(size, new PetryPot(size).getBacterias().length);
        assertEquals(size, new PetryPot(size).getBacterias()[0].length);
    }

    @Test
    public void creationEmptyTest() {
        assertEquals(0, new PetryPot().getBacterias().length);
    }

    @Test
    public void creationTestThrows() {
        assertThrows(NegativeArraySizeException.class, () -> new PetryPot(-1));
    }

    @Nested
    class NeighborsTests {
        private static Method fillNeighborsPot3x3;
        private static Supplier<Bacteria> bacteriaSupplier;
        private static Bacteria bacteriaToFill = new Bacteria();

        @BeforeAll
        private static void getMethod() throws NoSuchMethodException {
            fillNeighborsPot3x3=pot3x3.getClass().getDeclaredMethod("fillNotEmptyNeighbors", int.class, int.class, Supplier.class);
            fillNeighborsPot3x3.setAccessible(true);
            bacteriaSupplier = () -> bacteriaToFill;
        }
        @Test
        public void neighborsNoNeighbors() throws InvocationTargetException, IllegalAccessException {
        fillNeighborsPot3x3.invoke(emptyPot,0, 0, bacteriaSupplier);
            assertArrayEquals(new Bacteria[0][0], emptyPot.getBacterias());

            PetryPot onePot = new PetryPot(1);
            assertNull(onePot.getBacterias()[0][0]);
        }

        @Test
        public void neighbors00AddressAllAreEmpty() throws InvocationTargetException, IllegalAccessException {
            // Address 0 0
            fillNeighborsPot3x3.invoke(pot3x3,0,0,bacteriaSupplier);

            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 0));

            assertNull(pot3x3.getBacteria(0, 2));
            assertNull(pot3x3.getBacteria(1, 2));
            assertNull(pot3x3.getBacteria(2, 0));
            assertNull(pot3x3.getBacteria(2, 1));
            assertNull(pot3x3.getBacteria(2, 2));
        }

        @Test
        public void neighbors22AddressAllAreEmpty() throws InvocationTargetException, IllegalAccessException {
            fillNeighborsPot3x3.invoke(pot3x3,2,2,bacteriaSupplier);

            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 2));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(2, 1));

            assertNull(pot3x3.getBacteria(0, 0));
            assertNull(pot3x3.getBacteria(0, 1));
            assertNull(pot3x3.getBacteria(0, 2));
            assertNull(pot3x3.getBacteria(1, 0));
            assertNull(pot3x3.getBacteria(2, 0));
        }

        @Test
        public void neighbors11AddressAllAreEmpty() throws InvocationTargetException, IllegalAccessException {
            fillNeighborsPot3x3.invoke(pot3x3,1,1,bacteriaSupplier);

            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 0));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 2));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 0));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 2));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(2, 0));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(2, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(2, 2));

            assertNull(pot3x3.getBacteria(1, 1));
        }

        @Test
        public void neighbors11AddressAllFilled() throws InvocationTargetException, IllegalAccessException {
            Bacteria bacteriaToFill = new Bacteria();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    pot3x3.putBacteria(i, j, new Bacteria());
                }
            }
            fillNeighborsPot3x3.invoke(pot3x3,1,1,bacteriaSupplier);

            assertNotSame(bacteriaToFill, pot3x3.getBacteria(0, 0));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(0, 1));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(0, 2));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(1, 0));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(1, 2));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(2, 0));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(2, 1));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(2, 2));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(1, 1));
        }

        @Test
        public void neighbors11AddressSomeFilled() throws InvocationTargetException, IllegalAccessException {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    pot3x3.putBacteria(i, j, new Bacteria());
                }
            }
            fillNeighborsPot3x3.invoke(pot3x3,1,1,bacteriaSupplier);

            assertNotSame(bacteriaToFill, pot3x3.getBacteria(0, 0));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(0, 1));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(1, 0));
            assertNotSame(bacteriaToFill, pot3x3.getBacteria(1, 1));

            assertSame(bacteriaToFill, pot3x3.getBacteria(0, 2));
            assertSame(bacteriaToFill, pot3x3.getBacteria(1, 2));
            assertSame(bacteriaToFill, pot3x3.getBacteria(2, 0));
            assertSame(bacteriaToFill, pot3x3.getBacteria(2, 1));
            assertSame(bacteriaToFill, pot3x3.getBacteria(2, 2));


        }

        @Test
        public void neighbors10AddressAllAreEmpty() throws InvocationTargetException, IllegalAccessException {
            fillNeighborsPot3x3.invoke(pot3x3,1,0,bacteriaSupplier);

            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 0));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(2, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(2, 0));

            assertNull(pot3x3.getBacteria(0, 2));
            assertNull(pot3x3.getBacteria(1, 2));
            assertNull(pot3x3.getBacteria(2, 2));
            assertNull(pot3x3.getBacteria(1, 0));
        }

        @Test
        public void neighbors01AddressAllAreEmpty() throws InvocationTargetException, IllegalAccessException {
            fillNeighborsPot3x3.invoke(pot3x3,0,1,bacteriaSupplier);

            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 0));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 0));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 1));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(1, 2));
            assertEquals(bacteriaToFill, pot3x3.getBacteria(0, 2));

            assertNull(pot3x3.getBacteria(2, 0));
            assertNull(pot3x3.getBacteria(2, 1));
            assertNull(pot3x3.getBacteria(2, 2));
            assertNull(pot3x3.getBacteria(0, 1));
        }
    }

    private static PetryPot emptyPot;
    private static PetryPot pot3x3 = new PetryPot(3);

    /**
     * Reloads all pots to the initial state
     */
    @BeforeEach
    private void reloadPots() {
        emptyPot = new PetryPot();
        pot3x3 = new PetryPot(3);
    }


}