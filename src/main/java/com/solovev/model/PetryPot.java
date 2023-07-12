package com.solovev.model;

import java.util.*;
import java.util.function.Supplier;

/**
 * Class represents a bunch of addresses, and bacteria behaviour in the pot
 */
public class PetryPot {
    private final Bacteria[][] bacterias;
    private final int size;

    public PetryPot() {
        this(0);
    }

    /**
     * Creates N*N field of empty addresses
     *
     * @param size dimensions of the field, must be from 0 to 12113
     */
    public PetryPot(int size) {
        this.size = size;
        bacterias = new Bacteria[size][size];
    }

    /**
     * Goes through NOT EMPTY neighbors of the given cell and fills it with the bacteriaSupplier based on the given supplier
     * @param height to check neighbors
     * @param width to check neighbors
     * @param bacteriaSupplier providing bacteria
     */
    public void fillNotEmptyNeighbors(int height, int width, Supplier<Bacteria> bacteriaSupplier){

    }
    public int getSize() {
        return size;
    }
    public Bacteria[][] getBacterias() {
        return bacterias;
    }

    /**
     * gets bacteria of the given height and width
     * @param height first part of address
     * @param width second part of address
     * @return bacteria on this address or null if empty
     */
    public Bacteria getBacteria(int height, int width){
        return bacterias[height][width];
    }

}
