package wildlife;

public abstract class RescueCase implements RescueOperations {

    public static final String STATUS_REGISTERED = "Registered";
    public static final String STATUS_IN_PROGRESS = "In Progress";
    public static final String STATUS_COMPLETED = "Completed";

    private String rescueCaseId;
    private String animalName;
    private String species;
    private String rescueLocation;
    private String assignedRanger;
    private int numberOfRescueDays;
    private double dailyCareCost;
    private String currentStatus;

    public RescueCase(String rescueCaseId, String animalName, String species,
                      String rescueLocation, String assignedRanger,
                      int numberOfRescueDays, double dailyCareCost) {
        this.rescueCaseId = rescueCaseId;
        this.animalName = animalName;
        this.species = species;
        this.rescueLocation = rescueLocation;
        this.assignedRanger = assignedRanger;
        this.numberOfRescueDays = numberOfRescueDays;
        this.dailyCareCost = dailyCareCost;
        this.currentStatus = STATUS_REGISTERED;
    }

    public String getRescueCaseId() {
        return rescueCaseId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getSpecies() {
        return species;
    }

    public String getRescueLocation() {
        return rescueLocation;
    }

    public String getAssignedRanger() {
        return assignedRanger;
    }

    public int getNumberOfRescueDays() {
        return numberOfRescueDays;
    }

    public double getDailyCareCost() {
        return dailyCareCost;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setNumberOfRescueDays(int numberOfRescueDays) {
        this.numberOfRescueDays = numberOfRescueDays;
    }

    public void setDailyCareCost(double dailyCareCost) {
        this.dailyCareCost = dailyCareCost;
    }

    public double getBaseCareCost() {
        return numberOfRescueDays * dailyCareCost;
    }

    public abstract String getRescueType();

    public abstract double calculateTotalRescueCost();

    public abstract String determineRescuePriority();

    public abstract String displayTypeSpecificInfo();

    @Override
    public void startRescue() {
        if (STATUS_COMPLETED.equals(currentStatus)) {
            throw new IllegalStateException("A completed rescue cannot be started again.");
        }
        currentStatus = STATUS_IN_PROGRESS;
    }

    @Override
    public void completeRescue() {
        if (STATUS_REGISTERED.equals(currentStatus)) {
            throw new IllegalStateException("A rescue that has not started cannot be completed.");
        }
        currentStatus = STATUS_COMPLETED;
    }

    @Override
    public String generateRescueSummary() {
        return String.format(
                "----- Rescue Summary -----%n"
                        + "Rescue Case ID   : %s%n"
                        + "Rescue Type      : %s%n"
                        + "Species          : %s%n"
                        + "Assigned Ranger  : %s%n"
                        + "Rescue Priority  : %s%n"
                        + "Current Status   : %s%n"
                        + "Total Rescue Cost: R %.2f%n",
                rescueCaseId,
                getRescueType(),
                species,
                assignedRanger,
                determineRescuePriority(),
                currentStatus,
                calculateTotalRescueCost());
    }

    public String displayCommonInfo() {
        return String.format(
                "Rescue Case ID     : %s%n"
                        + "Animal Name        : %s%n"
                        + "Species            : %s%n"
                        + "Rescue Location    : %s%n"
                        + "Assigned Ranger    : %s%n"
                        + "Rescue Days        : %d%n"
                        + "Daily Care Cost    : R %.2f%n"
                        + "Current Status     : %s%n"
                        + "Rescue Type        : %s%n"
                        + "Rescue Priority    : %s%n"
                        + "Total Rescue Cost  : R %.2f%n",
                rescueCaseId, animalName, species, rescueLocation, assignedRanger,
                numberOfRescueDays, dailyCareCost, currentStatus,
                getRescueType(), determineRescuePriority(), calculateTotalRescueCost());
    }

    @Override
    public String toString() {
        return displayCommonInfo() + displayTypeSpecificInfo();
    }
}
