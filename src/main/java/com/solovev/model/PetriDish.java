package com.solovev.model;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class represents a bunch of addresses, and bacteria behaviour in the pot
 */
public class PetriDish {
    public static final int MAX_SIZE = 10; //todo out of heap for 12_113 now 6k is max
    /**
     * dimension of the field, must be from 0 to MAX_SIZE; overall real size is size^2
     */
    private final int size;
    /**
     * counts bacteria dead and alive in the dish
     */
    private int fill;
    private final Map<Address, Bacteria> addresses = new HashMap<>();
    private long days;
    private long deadBacteria;

    public class Address {
        private final int x;
        private final int y;

        public Address(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return collection of not empty neighbors of this address
         */
        public Collection<Address> emptyNeighbors() {
            int startHeight = Math.max(0, y - 1);
            int endHeight = Math.min(y + 1, size - 1);
            int startWidth = Math.max(0, x - 1);
            int endWidth = Math.min(x + 1, size - 1);

            Collection<Address> neighbors = new HashSet<>();

            for (int yTemp = startHeight; yTemp <= endHeight; yTemp++) {
                for (int xTemp = startWidth; xTemp <= endWidth; xTemp++) {
                    Address addressToAdd = new Address(xTemp, yTemp);
                    if (!addressToAdd.equals(this)//checks it's not the same address
                            && addresses.get(addressToAdd) == null) {
                        neighbors.add(addressToAdd);
                    }
                }
            }
            return neighbors;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return x == address.x && y == address.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Address{" +
                    x +
                    "," + y +
                    '}';
        }
    }

    /**
     * Class represent response of the calculation
     *
     * @param days         days needed to fill all the pot
     * @param deadBacteria dead bacteria occurred in the process
     */
    public record Response(long days, long deadBacteria) {
    }

    public PetriDish() {
        this(0);
    }

    /**
     * Creates N*N field of empty addresses
     *
     * @param size dimensions of the field, must be from 0 to MAX_SIZE
     */
    public PetriDish(int size) {
        if (size > MAX_SIZE || size < 0) {
            throw new IllegalArgumentException("Size must be >= 0 and < " + MAX_SIZE);
        }
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                addresses.put(new Address(i, j), null);
            }
        }
    }

    /**
     * Calculates days needed to fill all field with bacterias
     *
     * @param conf configuration of the bacteria behavior
     * @return days and dead bacteria needed to fill all pot depending on the conf; -1 if it will never be filled
     */
    public Response calculateDays(ConfigurationOfBacteriaBehavior conf) {
        if (conf.toNumber() == 0) {
            return new Response(-1, 0);
        }
        //first bacteria divides if not sterile, then it can die or becomes sterile, then new bacterias are put in the dish
        while (notFull()) {
            days++;
            divideBacteria();
            putBacterias(conf);
        }
        return new Response(days, deadBacteria);
    }

    /**
     * all not dead or sterile bacterias present in the dish first divides then grows old; Bacteria can die during this method and will increment dead bacteria counter
     */
    private void divideBacteria() {
        Collection<Map.Entry<Address, Bacteria>> notEmptyNotDeadAddresses = addresses //todo this or store in memory?
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().isAlive())
                .collect(Collectors.toSet());

        for (Map.Entry<Address, Bacteria> entry : notEmptyNotDeadAddresses) {
            Bacteria bacteria = entry.getValue();
            fillNotEmptyNeighbors(entry.getKey(), bacteria.divisionStrategy());
            //after division, it grows old and can become sterile or die
            if (bacteria.growOld()) {
                deadBacteria++;
            }
        }
    }

    /**
     * Puts random number of bacterias to the pot
     *
     * @param conf config to base putting on
     */
    private void putBacterias(ConfigurationOfBacteriaBehavior conf) {
        Random rand = new Random();
        int numberOfBacterias = rand.nextInt(conf.fromNumber() - 1, conf.toNumber()) + 1;

        List<Address> emptyAddresses = addresses
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .toList();
        int emptySize = emptyAddresses.size();
        if (emptySize > numberOfBacterias) {
            for (int i = 0; i < numberOfBacterias; i++) {
                putBacteria(emptyAddresses.get(rand.nextInt(emptySize)), new Bacteria(conf));
            }
        } else {
            emptyAddresses.forEach(address -> putBacteria(address, new Bacteria(conf)));
        }
    }

    /**
     * Goes through EMPTY neighbors of the given cell and fills it with the bacteriaSupplier based on the given supplier
     *
     * @param addressToCheck   to check neighbors
     * @param bacteriaSupplier providing bacteria
     */
    private void fillNotEmptyNeighbors(Address addressToCheck, Supplier<Bacteria> bacteriaSupplier) {
        for (Address address : addressToCheck.emptyNeighbors()) {
            putBacteria(address, bacteriaSupplier.get());
        }
    }

    public int getSize() {
        return size;
    }

    /**
     * gets bacteria of the given x and y
     *
     * @param x first part of address
     * @param y second part of address
     * @return bacteria on this address or null if empty
     */
    public Bacteria getBacteria(int x, int y) {
        return addresses.get(new Address(x, y));
    }

    public Map<Address, Bacteria> getBacterias() {
        return Collections.unmodifiableMap(addresses);
    }

    /**
     * Puts bacteria in the given place if it is not already filled
     *
     * @param address       to put the bacteria
     * @param bacteriaToPut bacteria to put to this place
     * @return true if bacteria was put, false if the place was already not empty
     */
    public boolean putBacteria(Address address, Bacteria bacteriaToPut) {
        boolean result = false;
        if (bacteriaToPut != null
                && addresses.putIfAbsent(address, bacteriaToPut) == null) {
            result = true;
            fill++;
        }
        return result;
    }

    /**
     * Checks if dish is full or not
     *
     * @return true is dish is full
     */
    public boolean notFull() {
        return fill < size * size;
    }

}
