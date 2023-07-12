package com.solovev.model;

import java.util.Objects;

/**
 * Class to store the address of the filled and bacteria in it
 */
public class Address {
    private Bacteria bacteria;
    private final int height;
    private final int width;

    /**
     * Creates address
     * @param height must be > 0
     * @param width must be > 0
     * @throws IndexOutOfBoundsException if x or y is > 0
     */
    public Address(int height, int width) {
        if(height < 0 || width < 0){
            throw  new IndexOutOfBoundsException("x and y must be > 0, but x=" + height + " and y=" + width);
        }
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setBacteria(Bacteria bacteria) {
        this.bacteria = bacteria;
    }

    public Bacteria getBacteria() {
        return bacteria;
    }

    /**
     * checks if address does not contain bacteria
     * @return true if bacteria is null a false otherwise
     */
     public boolean isEmpty(){
        return bacteria == null;
     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;

        if (height != address.height) return false;
        if (width != address.width) return false;
        return Objects.equals(bacteria, address.bacteria);
    }

    @Override
    public int hashCode() {
        int result = bacteria != null ? bacteria.hashCode() : 0;
        result = 31 * result + height;
        result = 31 * result + width;
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "bacteria=" + bacteria +
                ", x=" + height +
                ", y=" + width +
                '}';
    }
}
