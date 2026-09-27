package wildlife;

public class EndangeredSpeciesRescue extends RescueCase {

    public static final double SPECIALIST_TEAM_FEE = 8000.00;

    private String conservationClassification;
    private double securityCost;
    private boolean specialistTeamRequired;

    public EndangeredSpeciesRescue(String rescueCaseId, String animalName, String species,
                                   String rescueLocation, String assignedRanger,
                                   int numberOfRescueDays, double dailyCareCost,
                                   String conservationClassification, double securityCost,
                                   boolean specialistTeamRequired) {
        super(rescueCaseId, animalName, species, rescueLocation, assignedRanger,
                numberOfRescueDays, dailyCareCost);
        this.conservationClassification = conservationClassification;
        this.securityCost = securityCost;
        this.specialistTeamRequired = specialistTeamRequired;
    }

    public String getConservationClassification() {
        return conservationClassification;
    }

    public double getSecurityCost() {
        return securityCost;
    }

    public boolean isSpecialistTeamRequired() {
        return specialistTeamRequired;
    }

    @Override
    public String getRescueType() {
        return "Endangered Species Rescue";
    }

    @Override
    public double calculateTotalRescueCost() {
        double total = getBaseCareCost() + securityCost;
        if (specialistTeamRequired) {
            total += SPECIALIST_TEAM_FEE;
        }
        return total;
    }

    @Override
    public String determineRescuePriority() {
        return "High";
    }

    @Override
    public String displayTypeSpecificInfo() {
        return String.format(
                "Classification     : %s%n"
                        + "Security Cost      : R %.2f%n"
                        + "Specialist Team    : %s%n",
                conservationClassification, securityCost, specialistTeamRequired ? "Yes" : "No");
    }
}
