package com.engeto.plant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant {
    private static int nextId = 1;
    private int id;
    private String name, notes;
    private LocalDate planted, watering;
    private int frequencyOfWatering; //in days


    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        validateFrequencyOfWatering(frequencyOfWatering);
        validatePlantingAndWateringDates(planted, watering);

        this.id = nextId++;
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.watering = watering;
        this.frequencyOfWatering = frequencyOfWatering;
    }



    public Plant (String name, LocalDate planted, int frequencyOfWatering) throws PlantException {
        this(name, "", planted, LocalDate.now(), frequencyOfWatering);
    }

    public Plant (String name) throws PlantException {
        this(name, LocalDate.now(), 7);
    }

    public String getWateringInfo(){
        return String.format("%s%n Poslední zálivka: %s%n Příští zálivka: %s%n",
                name, watering.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), watering.plusDays(frequencyOfWatering).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) throws PlantException {
        validatePlantingAndWateringDates(planted, this.watering);
        this.planted = planted;
    }


    public LocalDate getWatering() {
        return watering;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        validatePlantingAndWateringDates(this.planted, watering);
        this.watering = watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        validateFrequencyOfWatering(frequencyOfWatering);
        this.frequencyOfWatering = frequencyOfWatering;
    }

    @Override
    public String toString() {
        return String.format("Květina (%s): %s, zasazena: %s, naposledy zalita: %s, frekvence zalévání ve dnech: %s%n",
                getId(), getName(), getPlanted().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), getWatering().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), getFrequencyOfWatering());

    }
    private void validatePlantingAndWateringDates(LocalDate planted, LocalDate watering) throws PlantException {
        if (watering.isBefore(planted)) {
            throw new PlantException("Datum zálivky nesmí předcházet datu zasazení rostliny. Zadaná sadba: " + planted + ", zálivka: " + watering);
        }
    }

    private void validateFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0) {
            throw new PlantException("Frekvence zálivky nesmí být rovna nule, nebo v minusu. Zadáno: " + frequencyOfWatering);
        }
    }
}

