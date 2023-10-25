package com.engeto.plant;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlantList {
    private List<Plant> plants;

    public PlantList() {
        this.plants = new ArrayList<>();
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void removePlant(int index) throws PlantException {
        if (index >= 1 && index < plants.size() + 1) {
            plants.remove(index-1);
        } else {
            throw new PlantException("Květina se zadaným indexem neexistuje a nemůže být odstraněna. Zadaný index: "+index+", rozpětí seznamu květin: 1 - " + plants.size());
        }
    }

    public List<Plant> getAllPlants() {
        return new ArrayList<>(plants);
    }

    public String getAllPlantsAsText() {
        StringBuilder result = new StringBuilder();

        if (!plants.isEmpty()) {
            for (Plant plant : plants) {
                result.append(String.format("Květina (%s): %s, zasazena: %s, naposledy zalita: %s, frekvence zalévání ve dnech: %s.%n",
                        plant.getId(), plant.getName(), plant.getPlanted().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), plant.getWatering().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), plant.getFrequencyOfWatering()));
            }
        } else {
            result.append("Žádné květiny v seznamu");
        }

        return result.toString();
    }

    public Plant getPlantAt(int index) throws PlantException {
        if (index >= 1 && index < plants.size() + 1) {
            Plant plant = plants.get(index - 1);
            return plant;
        } else {
            throw new PlantException("Květina se zadaným indexem neexistuje. Zadaný index: "+index+", rozpětí seznamu květin: 1 - " + plants.size());
        }
    }

    public PlantList loadPlantsFromFile(String filename) throws PlantException {
        PlantList result = new PlantList();
        int lineNumber = 1;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseLine(line, result, lineNumber);
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Nepodařilo se nalézt soubor "+filename+": "+e.getLocalizedMessage());
        }

        return result;
    }

    public void savePlantsToFile(String filename) throws PlantException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Plant plant : plants) {
                String line = String.format("%s\t%s\t%d\t%s\t%s",
                        plant.getName(),
                        plant.getNotes(),
                        plant.getFrequencyOfWatering(),
                        plant.getWatering(),
                        plant.getPlanted());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PlantException("Chyba při ukládání květin do souboru: " + e.getMessage());
        }

    }

    private void parseLine(String line, PlantList plantlist, int lineNumber) throws PlantException {
        String[] blocks = line.split("\t");
        int numOfBlocks = blocks.length;
        if (numOfBlocks != 5) {
            throw new PlantException(
                    "Nesprávný počet položek na řádku: " + line +
                            "! Počet položek: " + numOfBlocks + ".");
        }

        String name = blocks[0].trim();
        String notes = blocks[1].trim();
        int frequencyOfWatering;
        try {
            frequencyOfWatering = Integer.parseInt(blocks[2].trim());
        } catch (NumberFormatException e) {
            throw new PlantException("Chybně zadaná frekvence zalévání [" + blocks[2] + "] na řádku č. " + lineNumber + ": " + line + "!");
        }


        LocalDate watering;
        try {
            watering = LocalDate.parse(blocks[3].trim());
        } catch (DateTimeParseException e) {
            throw new PlantException("Chybně zadané datum [" + blocks[3] + "] na řádku č. " + lineNumber + ": " + line + "!");
        }

        LocalDate planted;
        try {
            planted = LocalDate.parse(blocks[4].trim());
        } catch (DateTimeParseException e) {
            throw new PlantException("Chybně zadané datum [" + blocks[4] + "] na řádku č. " + lineNumber + ": " + line + "!");
        }

        Plant newPlant = new Plant(name, notes, planted, watering, frequencyOfWatering);
        addPlant(newPlant);
    }
}
