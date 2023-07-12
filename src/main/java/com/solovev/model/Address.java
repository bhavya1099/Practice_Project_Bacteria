package com.solovev.model;

import java.util.Collection;

/**
 * Class to store the address of the filled and bacteria in it
 */
public class Address {
    private Bacteria bacteria;
    private final int x;
    private final int y;
    /**
     * max value (exclusive) for both x and y, min is always 0
     */
    private final int limit;

    public Address(int x, int y, int limit) {
        this.x = x;
        this.y = y;
        this.limit = limit;
    }

    public void setBacteria(Bacteria bacteria) {
        this.bacteria = bacteria;
    }

    public Bacteria getBacteria() {
        return bacteria;
    }

    /**
     * Returns empty neighbors of this address.
     * For example for address 0,0 -> 01,10,11; for 11 in 3x3 fir->
     * @return
     */
    public Collection<Address> getEmptyNeighbors(){

    }
}
