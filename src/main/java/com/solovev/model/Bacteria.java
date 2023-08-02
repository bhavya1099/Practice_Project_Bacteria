package com.solovev.model;

/**
 * Class represents bacteria and its live cycle
 */
public class Bacteria {

    private int age = 1;
    private boolean isAlive;
    private boolean isSterile;

    public Bacteria() {
    }

    /**
     * Increments bacteria age and defines if bacteria is dead or sterile
     */
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isSterile() {
        return isSterile;
    }

    public void setSterile(boolean sterile) {
        isSterile = sterile;
    }
}
