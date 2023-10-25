package com.engeto.plant;

import java.util.Comparator;

public class PlantWateringComparator implements Comparator<Plant> {
    public int compare(Plant plant1, Plant plant2) {
        int wateringComparison = plant1.getWatering().compareTo(plant2.getWatering());
        if (wateringComparison != 0) {
            return wateringComparison;
        } else {
            // V případě stejného data zálivky se květiny srovnají podle abecedy.
            return plant2.getName().compareTo(plant1.getName());
        }
    }
}