package com.solovev;

import com.solovev.model.ConfigurationOfBacteriaBehavior;
import com.solovev.model.PetriDish;

public class Main {
    public static void main(String[] args) {
        ConfigurationOfBacteriaBehavior conf3dayNoOther = new ConfigurationOfBacteriaBehavior(3, 3, 0, 0);
        ConfigurationOfBacteriaBehavior confMaxdayNoOther = new ConfigurationOfBacteriaBehavior(PetriDish.MAX_SIZE, PetriDish.MAX_SIZE, 0, 0);

        for (int i = 0; i < 100; i++) {
            System.out.printf("For size: %d : %s\n", i, new PetriDish(i).calculateDays(new ConfigurationOfBacteriaBehavior(0, 10, 0.02, 0.05)));
        }

    }
}