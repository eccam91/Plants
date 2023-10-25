import com.engeto.plant.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            PlantList listOfPlants = new PlantList();

            //Načti seznam květin ze souboru kvetiny.txt.
            listOfPlants.loadPlantsFromFile("kvetiny.txt");

            //Vyzkoušej, že se aplikace bude chovat správně při načtení vadného souboru.
            //listOfPlants.loadPlantsFromFile("kvetiny-spatne-datum.txt");
            //listOfPlants.loadPlantsFromFile("kvetiny-spatne-frekvence.txt");


            //Vypiš na obrazovku informace o zálivce pro všechny květiny.
            List<Plant> plants = listOfPlants.getAllPlants();
            for (Plant plant : plants) {
                System.out.println(plant.getWateringInfo());
            }

            //Odebrání květiny ze seznamu
            listOfPlants.removePlant(3);

            //Kontrolní výpis seznamu květin
            System.out.println(listOfPlants.getAllPlantsAsText());

            //Přidání květin do seznamu
            listOfPlants.addPlant(new Plant("Sukulent v koupelně", "Nezalévá se", LocalDate.of(2023,9,04), LocalDate.of(2023,9,04), 365));
            listOfPlants.addPlant(new Plant("Lopatkovec"));

            // Seřaď rostliny podle názvu (+výpis)
            List<Plant> plantsSortedByName = new ArrayList<>(listOfPlants.getAllPlants());
            Collections.sort(plantsSortedByName, new PlantNameComparator());

            System.out.println("Květiny seřazené podle názvu:");
            for (Plant plant : plantsSortedByName) {
                System.out.println(plant.getName());
            }
            System.out.println();

            // Seřaď rostliny podle data poslední zálivky (+výpis)
            List<Plant> plantsSortedByWateringDate = new ArrayList<>(listOfPlants.getAllPlants());
            Collections.sort(plantsSortedByWateringDate, Collections.reverseOrder(new PlantWateringComparator()));

            System.out.println("Květiny seřazené podle poslední zálivky (od nejnovější):");
            for (Plant plant : plantsSortedByWateringDate) {
                System.out.println(plant.getName() + " - Poslední zálivka: " + plant.getWatering().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
            System.out.println();

            //Ulož seznam květin do nového souboru
            listOfPlants.savePlantsToFile("kvetiny-updated.txt");

            //Kontrolní načtení a výpis nového souboru
            PlantList newListOfPlants = new PlantList();
            newListOfPlants.loadPlantsFromFile("kvetiny-updated.txt");
            System.out.println(newListOfPlants.getAllPlantsAsText());


        } catch (PlantException e) {
            System.err.println("Chyba: " + e.getLocalizedMessage());
        }

    }
}