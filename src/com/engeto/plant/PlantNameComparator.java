package com.engeto.plant;

import java.util.Comparator;

public class PlantNameComparator implements Comparator<Plant> {
    @Override
    public int compare(Plant plant1, Plant plant2) {
        return plant1.getName().compareTo(plant2.getName());
    }
}
