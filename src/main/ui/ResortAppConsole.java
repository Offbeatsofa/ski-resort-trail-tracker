package ui;

import model.Resort;
import model.Trail;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
// Resort trail tracking application
// Code based on TellerApp lecture lab
public class ResortAppConsole {

    private Resort resort;
    private Trail trail;
    private Scanner input;
    private static final String DATA_PATH = "./data/";

    

    // EFFECTS: runs the resort application
    public ResortAppConsole() {
        input = new Scanner(System.in);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: displays main menu, sends input given to processMainCommand unless exit
    private void runApp() {
        while (true) {
            displayMainMenu();
            String command = input.nextLine().toLowerCase();
            if (command.equals("e")) {
                break;
            } else {
                processMainCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: resort
    // EFFECTS: creates resort object based on user input
    private Resort createResort() {
        System.out.println("Resort name: ");
        String name = input.nextLine();
        System.out.println("Region: ");
        String region = input.nextLine();
        return new Resort(name, region);
    }

    // MODIFIES: this
    // EFFECTS: processes user command in main menu
    @SuppressWarnings("methodlength")
    private void processMainCommand(String command) {
        if (command.equals("r")) {
            resort = createResort();
        } else if (command.equals("t")) {
            if (resort != null) {
                while (true) {
                    displayResortMenu();
                    String nextCommand = input.nextLine().toLowerCase();
                    if (nextCommand.equals("e")) {
                        break;
                    }
                    processResortCommand(nextCommand);
                }
            } else {
                System.out.println("Resort does not exist.");
            }
        } else if (command.equals("s")) {
            if (resort != null) {
                doSaveResort();
            } else {
                System.out.println("No resort to save.");
            }
        } else if (command.equals("l")) {
            doLoadResort();
        } else {
            System.out.println("Invalid main command.");
        }
    }
    
    // REQUIRES: resort != null
    // MODIFIES: this
    // EFFECTS: processes user command in the resort menu
    private void processResortCommand(String command) {    
        if (command.equals("a")) {
            doAddTrail();
        } else if (command.equals("s")) {
            if (!resort.getTrails().isEmpty()) {
                trail = doSelectTrail();
                while (true) {
                    displayTrailMenu();
                    String nextCommand = input.nextLine().toLowerCase();
                    if (nextCommand.equals("e")) {
                        break;
                    }
                    processTrailCommand(nextCommand);
                }
            } else { 
                System.out.println("No trails to show.");
            }
        } else {
            System.out.println("Invalid resort command.");
        }    
    }

    // MODIFIES: this
    // EFFECTS: processes user command in the trail menu
    private void processTrailCommand(String command) {
        switch (command) {
            case "f" : 
                System.out.println("Favorite set to " + String.valueOf(trail.favorite()));
                break;
            case "r" : 
                System.out.println("Ridden set to " + String.valueOf(trail.ride()));
                break;
            case "o" : 
                System.out.println("Open set to " + String.valueOf(trail.open()));
                break;
            case "a" : 
                doAddNote();
                break;
            case "n" : 
                doEditNotes();
                break;
            case "d" : 
                doDownhillTrail();
                break;
            default : 
                System.out.println("Invalid trail editing option.");
        }
    }

    // // REQUIRES: resorts is not empty
    // // EFFECTS: selects an added resort
    // private void doSelectResort() {
    //     showResorts();
    //     loopUntilCorrect:
    //     while (true) {
    //         System.out.println("Select resort: \n");
    //         String index = input.nextLine();
    //         for (int i = 0; i < resort.size(); i++) {
    //             if (index.equals(String.valueOf(i + 1))) {
    //                 resortIndex = i;
    //                 break loopUntilCorrect;
    //             }
    //         }
    //         System.out.println("Invalid resort selection");
    //     }
    // }

    // // EFFECTS: displays resort menu and processes user command  
    // private void resortMenu() {
    //     while (true) {
    //         displayResortMenu();
    //         String command = input.nextLine();
    //         command = command.toLowerCase();
    //         if (command.equals("e")) {
    //             //runApp();
    //             break;
    //         } else {
    //             processResortCommand(command);
    //         }
    //     }
    // }

    // // EFFECTS: prints out resorts to console
    // private void showResorts() {
    //     for (Resort r : resort) {
    //         System.out.println("Resort " + String.valueOf(resort.indexOf(r) + 1) + ": " + r.getName());
    //     }
    // }

    //MODIFIES: this, newTrail
    //EFFECTS: creates a new trail based on user inputs
    private void doAddTrail() {
        System.out.println("Name: \n");
        String name = input.nextLine();
        System.out.println("Difficulty: \n");
        String difficulty = input.nextLine();
        System.out.println("Location: \n");
        String location = input.nextLine();
        System.out.println("Features: \n");
        String features = input.nextLine();
        resort.addTrail(new Trail(name, difficulty, location, features));
    }

    //REQUIRES: resort's trails are not empty
    //EFFECTS: selects an added trail
    private Trail doSelectTrail() {
        showTrails(resort);
        while (true) {
            System.out.println("Select trail: \n");
            String index = input.nextLine();
            for (int i = 0; i < resort.getTrails().size(); i++) {
                if (index.equals(String.valueOf(i + 1))) {
                    return resort.getTrails().get(i);
                }
            }
            System.out.println("Invalid trail selection.");            
        }
    }
    
    // // EFFECTS: displays trail menu and processes user command
    // private void trailMenu() {
    //     while (true) {
    //         displayTrailMenu();
    //         System.out.println();
    //         String command = input.nextLine();
    //         command = command.toLowerCase();
    //         if (command.equals("e")) {
    //             break;
    //         } else {
    //             processTrailCommand(command);
    //         }
    //     }
    // }

    // EFFECTS: prints out a resort's trails to console
    private void showTrails(Resort r) {
        for (Trail t : r.getTrails()) {
            System.out.println("Trail " + String.valueOf(r.getTrails().indexOf(t) + 1) + ": " + t.getName());
        }
    }

    //MODIFIES: t
    //EFFECTS: adds user input as note to t
    private void doAddNote() {
        System.out.println("Note: \n");
        trail.addNote(input.nextLine());
    }

    //MODIFIES: t
    //EFFECTS: uses user input to modify the notes of t
    private void doEditNotes() {
        for (String n : trail.getNotes()) {
            System.out.println("Note " + String.valueOf(trail.getNotes().indexOf(n) + 1) + ": " + n);
            System.out.println("Edit note? y/n");
            if (input.nextLine().equals("y")) {
                System.out.println("New note: ");
                String newNote = input.nextLine();
                trail.editNote(trail.getNotes().indexOf(n), newNote);
                break;
            }
        }
    }

    // MODIFIES: trail at current trailIndex
    // EFFECTS: uses user input to add one trail to another
    private void doDownhillTrail() {
        System.out.println("Which trail would you like to add?");
        showTrails(resort);
        System.out.println("Trail #: ");
        String index = input.next();
        input.nextLine();
        for (int i = 0; i < resort.getTrails().size(); i++) {
            if (index.equals(String.valueOf(i + 1))) {
                try {
                    trail.addDownhillTrail(resort.getTrails().get(i));
                } catch (Exception e) {
                    System.out.println("Trail add failed.");
                }
                return;
            }
        }
        System.out.println("Invalid trail number.");
    }

    // REQUIRES: resort != null
    // EFFECTS: saves user selected resort to file
    private void doSaveResort() {
        String path = (DATA_PATH + resort.getName().replaceAll("\\s+", "") + ".json");
        JsonWriter writer = new JsonWriter(path);
        try {
            writer.open();
            writer.write(resort);
            writer.close();
            System.out.println("Saved " + resort.getName() + " to " + path);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + path);
        }
    }

    // EFFECTS: loads user selected resort from file
    @SuppressWarnings("methodlength")
    private void doLoadResort() {
        try {
            try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
                List<Path> pathList = entries.collect(Collectors.toList());
                if (pathList.isEmpty()) {
                    System.out.println("No resorts to load!");
                } else {
                    System.out.println("Which file would you like to load? \n");
                    loopUntilCorrect: while (true) {
                        displayFiles();              
                        String index = input.next();
                        input.nextLine();
                        for (int i = 0; i < pathList.size(); i++) {
                            if (index.equals(String.valueOf(i + 1))) {
                                JsonReader reader = new JsonReader(pathList.get(i).toString());
                                resort = reader.read();
                                break loopUntilCorrect;
                            }
                        }
                    }    
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception when reading file");
        }
    }
    
    // EFFECTS: prints file names in ./Data 
    private void displayFiles() throws IOException {
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            List<Path> pathList = entries.collect(Collectors.toList());
            for (Path path : pathList) {
                System.out.println("File " + (pathList.indexOf(path) + 1) + ": " + path.toString());
            }            
        }
    }
    
    // EFFECTS: displays options: add new resort, select/show resorts, or exit
    private void displayMainMenu() {
        System.out.println();
        System.out.println("r - Create new resort");
        System.out.println("t - Edit current resort");
        System.out.println("s - Save to file");
        System.out.println("l - Load from file");
        System.out.println("e - Exit application");
    }

    // EFFECTS: displays options: add trail, select/show trails, or go back
    private void displayResortMenu() {
        System.out.println();
        System.out.println("a - add new trail to resort");
        System.out.println("s - select a trail to edit");
        System.out.println("e - go back to previous menu");
    }

    // EFFECTS: displays options to modify a trail
    private void displayTrailMenu() {
        System.out.println();
        System.out.println(trail.getName());
        System.out.println("f - favorite/unfavorite trail");
        System.out.println("r - ride/unride trail");
        System.out.println("o - open/close trail");
        System.out.println("a - add note to trail");
        System.out.println("n - show/edit trail notes");
        System.out.println("d - add downhill trail");
        System.out.println("e - go back to previous menu");
    }
}
