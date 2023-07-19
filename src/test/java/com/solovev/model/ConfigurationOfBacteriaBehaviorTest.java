package com.solovev.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationOfBacteriaBehaviorTest {
    @Test
    void paramLessThen0Test() {
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0, 0, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(-1, 0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, -1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, 0, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, 0, 0, -1));
    }

    @Test
    @DisplayName("Number from must be less then number to")
    void fromNumberToNumber(){
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0,2,0,0));
        assertAll(() -> new ConfigurationOfBacteriaBehavior(Integer.MAX_VALUE,Integer.MAX_VALUE,0,0));
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0,Integer.MAX_VALUE,0,0));

        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(1, 0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(2, 1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(Integer.MAX_VALUE, Integer.MAX_VALUE -1, 0, 0));
    }
    @Test
    void probabilityRange(){
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0,0,0,0));
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0,0,1,0));
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0,0,0,1));
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0,0,1,1));
        assertAll(() -> new ConfigurationOfBacteriaBehavior(0,0,0.5,0.5));

        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, 0, 1.1, 0));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, 0, 0, 1.1));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, 0, 1.1, 1.1));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, 0, 0.5, 1.1));
        assertThrows(IllegalArgumentException.class, () -> new ConfigurationOfBacteriaBehavior(0, 0, Double.MAX_VALUE, 0));
    }

}