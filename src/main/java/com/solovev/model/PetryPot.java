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
    private final LinkedList<Address> freeAddresses = new LinkedList<>();

    /**
     * Inner class that represent Address in the bacterias
     *
     * @param y array in the bacterias
     * @param x inner index of this array in bacterias
     */
    private record Address(int y, int x) {
    }

    /**
     * Class represent response of the calculation
     *
     * @param days         days needed to fill all the pot
     * @param deadBacteria dead bacteria occurred in the process
     */
    public record Response(long days, long deadBacteria) {
    }

    public PetryPot() {
        this(0);
    }

    /**
     * Creates N*N field of empty addresses
     *
     * @param size dimensions of the field, must be from 0 to 12_113
     */
    public PetryPot(int size) {
        if (size > 12_113) {
            throw new IllegalArgumentException("Size must be < 12_113");
        }
        this.size = size;
        bacterias = new Bacteria[size][size];

        //free addresses initialisation and shuffling
        for (int i = 0; i < size; i++) {
            for(int j =0; j< size; j++){
                freeAddresses.add(new Address(i,j));
            }
        }
        Collections.shuffle(freeAddresses);
    }

    /**
     * Calculates days needed to fill all field with bacterias
     *
     * @param conf configuration of the bacteria behavior
     * @return days and dead bacteria needed to fill all pot depending on the conf; -1 if it will never be filled
     */
    public Response calculateDays(ConfigurationOfBacteriaBehavior conf) {
        long days = 0;
        long deadBacteria = 0;
        if (conf.toNumber() == 0) {
            return new Response(-1, 0);
        }

        return new Response(days, deadBacteria);
    }

    /**
     * Puts random number of bacterias to the pot
     */
    private void putBacterias(ConfigurationOfBacteriaBehavior conf) {
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
                if (!(i == height && j == width)) //checks it's not the same address
                {
                    putBacteria(i,j,bacteriaSupplier.get());
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
     *
     * @param height        first part of address
     * @param width         second part of address
     * @param bacteriaToPut bacteria to put to this place
     * @return true if bacteria was put, false if the place was already not empty
     */
    public boolean putBacteria(int height, int width, Bacteria bacteriaToPut) {
        boolean placeEmpty = bacterias[height][width] == null;
        if (placeEmpty) {
            bacterias[height][width] = bacteriaToPut;
            freeAddresses.remove(new Address(height,width));
        }
        return placeEmpty;
    }

}
