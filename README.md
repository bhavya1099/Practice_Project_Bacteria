# Practice_Project_Bacteria
Project about Juint testing and inner classes

Java Bacterial Life Simulation Project

This repository showcases a Java application that simulates the life cycle of bacteria within a given field. This project is aiming to demonstrate my programming skills and my approach to simulationg and testing.
Project Overview

The application models the behavior of bacteria on an N by N field over a specified period. It calculates the time it takes for the bacteria to fill the field and determines the number of bacteria that die during this timeframe.
Features

    Bacteria Generation: Simulates the appearance of a random number of bacteria on the field each day for the initial 7 days.
    Bacteria Reproduction: Models the reproduction of bacteria in neighboring cells with a probability based on P+.
    Bacteria Lifespan: Tracks the lifespan of bacteria and ensures they stop reproducing after 14 days.
    Bacteria Mortality: Implements the death of bacteria with a probability based on P- for bacteria older than 7 days.

How to Run

    Clone the repository to your local machine.
    Open the project in your preferred Java IDE.
    Navigate to the main simulation class.
    Customize the input values for N, K1, K2, P-, and P+.
    Run the application to observe the simulation results.

Dependencies

This project uses JUnit 5 for testing purposes. The simulation behavior can be thoroughly tested using predefined test cases.
Testing

During testing, the project considers values such as N < 100, P+ = 5%, and P- = 2% to ensure the correctness of the simulation algorithm.

Feel free to explore the code, understand the simulation logic, and experiment with different input values to observe varying outcomes.

For any inquiries or collaborations, feel free to contact me via [email address or preferred contact method].

Happy simulating and coding!
