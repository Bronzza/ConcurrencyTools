package com.company;

public enum Presentations {
    SEMAFOR("semafor"),
    BARIER("barier"),
    PHASER("phaser"),
    LIST("list"),
    MAP("map"),
    LOCK("lock"),
    INTEGER("atomic"),
    QUEUE("queue");

    private String description;

    Presentations(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
