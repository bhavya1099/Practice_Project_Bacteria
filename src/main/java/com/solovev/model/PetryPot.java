package com.solovev.model;

import com.solovev.Main;

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
     * Goes through EMPTY neighbors of the given cell and fills it with the bacteriaSupplier based on the given supplier
     *
     * @param height           to check neighbors
     * @param width            to check neighbors
     * @param bacteriaSupplier providing bacteria
     */
    private void fillNotEmptyNeighbors(int height, int width, Supplier<Bacteria> bacteriaSupplier) {
        int startHeight = Math.max(0, height - 1);
        int endHeight = Math.min(height + 1, size - 1);
        int startWidth = Math.max(0, width - 1);
        int endWidth = Math.min(width + 1, size - 1);

        for (int i = startHeight; i <= endHeight; i++) {
            for (int j = startWidth; j <= endWidth; j++) {
                if (!(i == height && j == width) //checks it's not the same address
                        && bacterias[i][j] == null) { //also checks for only empty addresses
                    bacterias[i][j] = bacteriaSupplier.get();
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Bacteria[][] getBacterias() {
        return bacterias;
    }

    /**
     * gets bacteria of the given height and width
     *
     * @param height first part of address
     * @param width  second part of address
     * @return bacteria on this address or null if empty
     */
    public Bacteria getBacteria(int height, int width) {
        return bacterias[height][width];
    }

    /**
     * Puts bacteria in the given place if it is not already filled
     * @param height first part of address
     * @param width  second part of address
     * @param bacteriaToPut bacteria to put to this place
     * @return true if bacteria was put, false if the place was already not empty
     */
    public boolean putBacteria(int height, int width, Bacteria bacteriaToPut){
        boolean placeEmpty = bacterias[height][width] == null;
        if(placeEmpty) { bacterias[height][width] = bacteriaToPut;}
        return placeEmpty;
    }

}
