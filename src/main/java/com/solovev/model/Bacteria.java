package com.solovev.model;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Class represents bacteria and its live cycle
 */
public class Bacteria {
    public static final int STERILE_DAYS = 14; // after this age bacteria can not divide
    public static final int DEAD_DAYS = 7; // after this age bacteria gets probability to die
    private long age = 1;
    private boolean isAlive = true;
    private boolean isSterile;
    private final ConfigurationOfBacteriaBehavior config;

    public Bacteria() {
        config = new ConfigurationOfBacteriaBehavior(0,0,0,0);
    }

    public Bacteria(ConfigurationOfBacteriaBehavior config) {
        this.config = config;
    }

    /**
     * Increments bacteria age and defines if bacteria is dead or sterile
     *
     * @return true if bacteria is dead after this method, false otherwise
     */
    public boolean growOld() {
        boolean died = false;
        age++;
        if (isAlive && age >= DEAD_DAYS) {
            died = new Random().nextDouble() < config.probabilityDead();
            if (died) {
                isSterile = true;
                isAlive = false;
            }
        }
        if (!isSterile && age >= STERILE_DAYS) {
            isSterile = true;
        }
        return died;
    }

    /**
     * method to define how bacteria will reproduce
     *
     * @return Supplier for dead and sterile bacterias always return null fro normal bacterias can return null, can return new bacteria with same config
     */
    public Supplier<Bacteria> divisionStrategy() {
        return isSterile ?
                () -> null
                : () -> new Random().nextDouble() <= config.probabilityDivision() ? new Bacteria(config) : null;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isSterile() {
        return isSterile;
    }

    public void setSterile(boolean sterile) {
        isSterile = sterile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bacteria bacteria = (Bacteria) o;
        return age == bacteria.age && isAlive == bacteria.isAlive && isSterile == bacteria.isSterile && Objects.equals(config, bacteria.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, isAlive, isSterile, config);
    }
}
