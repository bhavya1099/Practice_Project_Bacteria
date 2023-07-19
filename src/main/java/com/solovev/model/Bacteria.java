package com.solovev.model;

import java.lang.module.Configuration;

/**
 * Class represents bacteria and its live cycle
 */
public class Bacteria {
    private ConfigurationOfBacteriaBehavior config;

    public Bacteria() {
    }

    public Bacteria(ConfigurationOfBacteriaBehavior config) {
        this.config = config;
    }
}
