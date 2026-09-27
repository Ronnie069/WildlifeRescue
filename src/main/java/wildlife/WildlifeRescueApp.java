package wildlife;

import java.util.Scanner;

public class WildlifeRescueApp {

    private final RescueCaseManager manager;
    private final Scanner scanner;

    public WildlifeRescueApp() {
        this.manager = new RescueCaseManager();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new WildlifeRescueApp().run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Select an option: ");
            switch (choice) {
                case "1":
                    createRescueCase();
                    break;
                case "2":
                    searchRescueCase();
                    break;
                case "3":
                    updateRescueStatus();
                    break;
                case "4":
                    displayAllCases();
                    break;
                case "5":
                    System.out.println(manager.generateReport());
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting Wildlife Rescue Operations System. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid menu selection. Please choose 0-5.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("WILDLIFE RESCUE OPERATIONS SYSTEM");
        System.out.println("====================================");
        System.out.println("1. Create Rescue Case");
        System.out.println("2. Search Rescue Case");
        System.out.println("3. Update Rescue Status");
        System.out.println("4. Display All Rescue Cases");
        System.out.println("5. Rescue Report");
        System.out.println("0. Exit");
    }

    private void createRescueCase() {
        System.out.println("\n--- Create Rescue Case ---");
        System.out.println("1. Injured Animal Rescue");
        System.out.println("2. Orphaned Animal Rescue");
        System.out.println("3. Endangered Species Rescue");

        String typeChoice = readLine("Select rescue type: ");
        if (!typeChoice.equals("1") && !typeChoice.equals("2") && !typeChoice.equals("3")) {
            System.out.println("Invalid rescue type selection.");
            return;
        }

        String id = readRequiredText("Rescue Case ID: ");
        if (manager.isDuplicateId(id)) {
            System.out.println("A rescue case with this ID already exists. Case not created.");
            return;
        }

        String animalName = readRequiredText("Animal Name: ");
        String species = readRequiredText("Species: ");
        String location = readRequiredText("Rescue Location: ");
        String ranger = readRequiredText("Assigned Ranger: ");
        int days = readPositiveInt("Number of Rescue Days: ");
        double dailyCost = readPositiveDouble("Daily Care Cost: ");

        RescueCase created = null;
        try {
            if (typeChoice.equals("1")) {
                String injury = readRequiredText("Injury Description: ");
                double vetCost = readPositiveDouble("Veterinary Treatment Cost: ");
                boolean surgery = readYesNo("Surgery required (Y/N): ");
                created = new InjuredAnimalRescue(id, animalName, species, location, ranger,
                        days, dailyCost, injury, vetCost, surgery);
            } else if (typeChoice.equals("2")) {
                int age = readPositiveInt("Estimated Age in Months: ");
                double feeding = readPositiveDouble("Feeding Cost: ");
                boolean foster = readYesNo("Foster care required (Y/N): ");
                created = new OrphanedAnimalRescue(id, animalName, species, location, ranger,
                        days, dailyCost, age, feeding, foster);
            } else {
                String classification = readRequiredText("Conservation Classification: ");
                double security = readPositiveDouble("Security Cost: ");
                boolean specialist = readYesNo("Specialist team required (Y/N): ");
                created = new EndangeredSpeciesRescue(id, animalName, species, location, ranger,
                        days, dailyCost, classification, security, specialist);
            }
            manager.addCase(created);
            System.out.println("Rescue case created successfully.");
            System.out.println(created.generateRescueSummary());
        } catch (IllegalArgumentException ex) {
            System.out.println("Could not create case: " + ex.getMessage());
        }
    }

    private void searchRescueCase() {
        System.out.println("\n--- Search Rescue Case ---");
        String id = readRequiredText("Enter Rescue Case ID: ");
        RescueCase found = manager.findById(id);
        if (found == null) {
            System.out.println("No rescue case found with ID " + id + ".");
        } else {
            System.out.println(found);
            System.out.println(found.generateRescueSummary());
        }
    }

    private void updateRescueStatus() {
        System.out.println("\n--- Update Rescue Status ---");
        String id = readRequiredText("Enter Rescue Case ID: ");
        RescueCase found = manager.findById(id);
        if (found == null) {
            System.out.println("No rescue case found with ID " + id + ".");
            return;
        }

        System.out.println("Current status: " + found.getCurrentStatus());
        System.out.println("1. Start rescue operation");
        System.out.println("2. Complete rescue operation");
        String action = readLine("Select an action: ");
        try {
            if (action.equals("1")) {
                found.startRescue();
                System.out.println("Rescue started. Status is now " + found.getCurrentStatus() + ".");
            } else if (action.equals("2")) {
                found.completeRescue();
                System.out.println("Rescue completed. Status is now " + found.getCurrentStatus() + ".");
            } else {
                System.out.println("Invalid action selected.");
                return;
            }
            System.out.println(found.generateRescueSummary());
        } catch (IllegalStateException ex) {
            System.out.println("Status was not updated: " + ex.getMessage());
        }
    }

    private void displayAllCases() {
        System.out.println("\n--- All Rescue Cases ---");
        if (manager.getCaseCount() == 0) {
            System.out.println("No rescue cases have been recorded.");
            return;
        }
        int index = 1;
        for (RescueCase rescueCase : manager.getAllCases()) {
            System.out.println("\n***** Case " + index++ + " *****");
            System.out.println(rescueCase);
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String readRequiredText(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!InputValidator.isBlank(value)) {
                return value;
            }
            System.out.println("This field cannot be blank. Please try again.");
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            String raw = readLine(prompt);
            try {
                int value = Integer.parseInt(raw);
                if (InputValidator.isPositiveInt(value)) {
                    return value;
                }
                System.out.println("Value must be greater than zero.");
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a whole number greater than zero.");
            }
        }
    }

    private double readPositiveDouble(String prompt) {
        while (true) {
            String raw = readLine(prompt);
            try {
                double value = Double.parseDouble(raw);
                if (InputValidator.isPositiveDouble(value)) {
                    return value;
                }
                System.out.println("Value must be greater than zero.");
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a numeric value greater than zero.");
            }
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            String raw = readLine(prompt).toUpperCase();
            if (raw.equals("Y") || raw.equals("YES")) {
                return true;
            }
            if (raw.equals("N") || raw.equals("NO")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }
}
