package wildlife;

public class InjuredAnimalRescue extends RescueCase {

    public static final double SURGERY_FEE = 5000.00;

    private String injuryDescription;
    private double veterinaryTreatmentCost;
    private boolean surgeryRequired;

    public InjuredAnimalRescue(String rescueCaseId, String animalName, String species,
                               String rescueLocation, String assignedRanger,
                               int numberOfRescueDays, double dailyCareCost,
                               String injuryDescription, double veterinaryTreatmentCost,
                               boolean surgeryRequired) {
        super(rescueCaseId, animalName, species, rescueLocation, assignedRanger,
                numberOfRescueDays, dailyCareCost);
        this.injuryDescription = injuryDescription;
        this.veterinaryTreatmentCost = veterinaryTreatmentCost;
        this.surgeryRequired = surgeryRequired;
    }

    public String getInjuryDescription() {
        return injuryDescription;
    }

    public double getVeterinaryTreatmentCost() {
        return veterinaryTreatmentCost;
    }

    public boolean isSurgeryRequired() {
        return surgeryRequired;
    }

    @Override
    public String getRescueType() {
        return "Injured Animal Rescue";
    }

    @Override
    public double calculateTotalRescueCost() {
        double total = getBaseCareCost() + veterinaryTreatmentCost;
        if (surgeryRequired) {
            total += SURGERY_FEE;
        }
        return total;
    }

    @Override
    public String determineRescuePriority() {
        if (surgeryRequired) {
            return "High";
        }
        return "Medium";
    }

    @Override
    public String displayTypeSpecificInfo() {
        return String.format(
                "Injury Description : %s%n"
                        + "Veterinary Cost    : R %.2f%n"
                        + "Surgery Required   : %s%n",
                injuryDescription, veterinaryTreatmentCost, surgeryRequired ? "Yes" : "No");
    }
}
