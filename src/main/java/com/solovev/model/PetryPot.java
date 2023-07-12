package com.solovev.model;

import java.util.*;

/**
 * Class represents a bunch of addresses, and bacteria behaviour in the pot
 */
public class PetryPot {
    private final Collection<Address> addresses = new ArrayList<>();
    private final int size;

    public PetryPot() {
        this(0);
    }

    /**
     * Creates N*N field of empty addresses
     *
     * @param size dimensions of the field
     * @throws IndexOutOfBoundsException if size is < 0
     */
    public PetryPot(int size) {
        if (size < 0) {
            throw new IndexOutOfBoundsException("Size must be >0");
        }
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                addresses.add(new Address(i, j));
            }
        }
    }

    /**
     * Returns empty neighbors of this address.
     * For example for address 0,0 -> 01,10,11; for 11 in 3x3 pot-> all other addresses
     *
     * @param address whose neighbors to return
     * @return collection of empty (not containing bacteria) neighbors
     */
    public Collection<Address> getEmptyNeighbors(Address address) {
        Collection<Address> neighbors = new ArrayList<>();
        int initialHeight = Math.max(address.getHeight() - 1, 0);
        int initialWidth = Math.max(address.getWidth() - 1, 0);
        int endHeight = Math.min(address.getHeight() + 1, size - 1);
        int endWidth = Math.min(address.getWidth() + 1, size - 1);

        for (int i = initialHeight; i <= endHeight; i++) {
           // neighbors
        }
        return null;
    }


    public Collection<Address> getAddresses() {
        return addresses;
    }
    public Address get
}
