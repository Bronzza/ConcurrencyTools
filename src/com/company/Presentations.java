package com.company;

public enum Presentations {
    SEMAFOR ("semafor"),
    BARIER("barier"),
    PHASER("phaser");

    private String description;

    Presentations(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
