package com.solovev.model;

public record ConfigurationOfBacteriaBehavior
        (int fromNumber, int toNumber, double probabilityDead, double probabilityDivision) {
    /**
     * Constructor of bacteria; all numbers must be >=0
     *
     * @param fromNumber          number of min bacteria to add every day
     * @param toNumber            number of max bacteria to add every day. Cannot be < then from number
     * @param probabilityDead     probability that after bays before bacteria will die(stop dividing) can be from 0.0 to 1
     * @param probabilityDivision probability that bacteria will divide, become 0 after daysBeforeSterile or if bacteria is dead
     */
    public ConfigurationOfBacteriaBehavior {
        if (fromNumber < 0
                || toNumber < 0
                || probabilityDead < 0
                || probabilityDivision < 0){
            throw new IllegalArgumentException("All values must be > 0");
        }
        if(fromNumber > toNumber){
            throw new IllegalArgumentException("From value must be < then to value");
        }

        if(probabilityDead > 1) {
            throw new IllegalArgumentException("probabilityDead must be < 1");
        }
        if(probabilityDivision > 1) {
            throw new IllegalArgumentException("probability division must be < 1");
        }
    }


}
