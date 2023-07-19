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
import java.util.HashMap;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class PetryPotTest {
    @Test
    public void creationMaxSize(){
        int size = PetryPot.MAX_SIZE;
        assertTrue(new PetryPot(size).getBacterias().containsKey(new PetryPot.Address(size -1,size-1)));
        assertFalse(new PetryPot(size).getBacterias().containsKey(new PetryPot.Address(size,size)));
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 2, 3, 5})
    public void creationTestNormal(int size) {
        assertTrue(new PetryPot(size).getBacterias().containsKey(new PetryPot.Address(size -1,size-1)));
        assertFalse(new PetryPot(size).getBacterias().containsKey(new PetryPot.Address(size,size)));
    }

    @Test
    public void creationEmptyTest() {
        assertEquals(new HashMap<>(), new PetryPot().getBacterias());
    }

    @Test
    public void creationTestThrows() {
        assertThrows(IllegalArgumentException.class, () -> new PetryPot(-1));
        assertThrows(IllegalArgumentException.class, () -> new PetryPot(PetryPot.MAX_SIZE +1));
    }
    @Nested
    class oneDayBacteria {
        private ConfigurationOfBacteriaBehavior oneBacteriaDay = new ConfigurationOfBacteriaBehavior(1, 1, 0, 0);
        @Test
        public void calculateDaysOneBacteriaPot3x3() {
            assertEquals(new PetryPot.Response(9, 0), new PetryPot(3).calculateDays(oneBacteriaDay));
            assertEquals(new PetryPot.Response(1, 0), new PetryPot(3).calculateDays(new ConfigurationOfBacteriaBehavior(9, 9, 0, 0)));
            assertEquals(new PetryPot.Response(1, 0), new PetryPot(3).calculateDays(new ConfigurationOfBacteriaBehavior(50, 50, 0, 0)));

            assertEquals(new PetryPot.Response(-1, 0), new PetryPot(3).calculateDays(new ConfigurationOfBacteriaBehavior(0, 0, 0, 0)));
        }
        @Test
        public void calculateDaysOneBacteriaPotMax() {
            assertEquals(new PetryPot.Response(PetryPot.MAX_SIZE * PetryPot.MAX_SIZE, 0), new PetryPot(PetryPot.MAX_SIZE).calculateDays(oneBacteriaDay));
            assertEquals(new PetryPot.Response(PetryPot.MAX_SIZE, 0), new PetryPot(PetryPot.MAX_SIZE).calculateDays(new ConfigurationOfBacteriaBehavior(PetryPot.MAX_SIZE, PetryPot.MAX_SIZE, 0, 0)));
            assertAll(() -> new PetryPot(PetryPot.MAX_SIZE).calculateDays(new ConfigurationOfBacteriaBehavior(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0)));
        }
        @Test
        public void calculateDaysOneBacteriaPotEmpty() {
            assertEquals(new PetryPot.Response(0, 0), emptyPot.calculateDays(oneBacteriaDay));
            assertEquals(new PetryPot.Response(-1, 0), emptyPot.calculateDays(new ConfigurationOfBacteriaBehavior(0, 0, 0, 0)));
        }
    }
    @Nested
    class NeighborsTests {
        private static Method fillNeighborsPot3x3;
        private static Supplier<Bacteria> bacteriaSupplier;
        private static Bacteria bacteriaToFill = new Bacteria();

        @BeforeAll
        private static void getMethod() throws NoSuchMethodException {
            fillNeighborsPot3x3 = pot3x3.getClass().getDeclaredMethod("fillNotEmptyNeighbors", int.class, int.class, Supplier.class);
            fillNeighborsPot3x3.setAccessible(true);
            bacteriaSupplier = () -> bacteriaToFill;
        }

        @Test
        public void neighborsNoNeighbors() throws InvocationTargetException, IllegalAccessException {
            fillNeighborsPot3x3.invoke(emptyPot, 0, 0, bacteriaSupplier);
            assertEquals(new HashMap<>(), emptyPot.getBacterias());

            PetryPot onePot = new PetryPot(1);
            assertNull(onePot.getBacteria(0,0));
        }

        @Test
        public void neighbors00AddressAllAreEmpty() throws InvocationTargetException, IllegalAccessException {
            // Address 0 0
            fillNeighborsPot3x3.invoke(pot3x3, 0, 0, bacteriaSupplier);

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
            fillNeighborsPot3x3.invoke(pot3x3, 2, 2, bacteriaSupplier);

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
            fillNeighborsPot3x3.invoke(pot3x3, 1, 1, bacteriaSupplier);

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
            fillNeighborsPot3x3.invoke(pot3x3, 1, 1, bacteriaSupplier);

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
            fillNeighborsPot3x3.invoke(pot3x3, 1, 1, bacteriaSupplier);

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
            fillNeighborsPot3x3.invoke(pot3x3, 1, 0, bacteriaSupplier);

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
            fillNeighborsPot3x3.invoke(pot3x3, 0, 1, bacteriaSupplier);

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