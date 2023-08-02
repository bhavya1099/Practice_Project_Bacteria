package com.solovev.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BacteriaTest {

    @Test
    public void growOldOneDay() {
        Bacteria bacteria = new Bacteria();

        assertFalse(bacteria.growOld());
        assertEquals(2, bacteria.getAge());
        assertFalse(bacteria.isSterile());
        assertTrue(bacteria.isAlive());
    }

    @Test
    public void growOldBecomeSterile() {
        Bacteria bacteria = new Bacteria(neverDie);
        while (bacteria.getAge() < Bacteria.STERILE_DAYS) {
            assertFalse(bacteria.growOld());
        }
        assertEquals(Bacteria.STERILE_DAYS, bacteria.getAge());
        assertTrue(bacteria.isSterile());
        assertTrue(bacteria.isAlive());
    }

    @Test
    public void growOldDead() {
        Bacteria bacteria = new Bacteria(instantDie);
        while (bacteria.getAge() < Bacteria.DEAD_DAYS - 1) {
            assertFalse(bacteria.growOld());
        }
        assertTrue(bacteria.growOld());

        assertEquals(Bacteria.DEAD_DAYS, bacteria.getAge());
        assertTrue(bacteria.isSterile());
        assertFalse(bacteria.isAlive());
        assertFalse(bacteria.growOld());
    }

    @Test
    void divisionStrategySterile() {
        Bacteria bacteria = new Bacteria();
        bacteria.setSterile(true);
        assertNull(bacteria.divisionStrategy().get());
    }
    @Test
    void divisionStrategyInstantDivision() {
        Bacteria bacteria = new Bacteria(instantDivision);
        assertEquals(new Bacteria(instantDivision),bacteria.divisionStrategy().get());
    }
    @Test
    void divisionStrategyNeverDivision() {
        Bacteria bacteria = new Bacteria(neverDivision);
        assertNull(bacteria.divisionStrategy().get());
    }

    ConfigurationOfBacteriaBehavior neverDie = new ConfigurationOfBacteriaBehavior(1, 1, 0, 0);
    ConfigurationOfBacteriaBehavior instantDie = new ConfigurationOfBacteriaBehavior(1, 1, 1, 0);
    ConfigurationOfBacteriaBehavior neverDivision = new ConfigurationOfBacteriaBehavior(1, 1, 0, 0);
    ConfigurationOfBacteriaBehavior instantDivision = new ConfigurationOfBacteriaBehavior(1, 1, 0, 1);
}