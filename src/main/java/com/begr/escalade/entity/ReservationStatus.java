package com.begr.escalade.entity;

public enum ReservationStatus {
    EN_ATTENTE("En attente"),
    EN_COURS("En cours"),
    TERMINE("Terminé");

    private final String displayValue;

    ReservationStatus(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
