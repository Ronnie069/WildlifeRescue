package wildlife;

public class OrphanedAnimalRescue extends RescueCase {

    public static final double FOSTER_CARE_FEE = 2500.00;

    private int estimatedAgeMonths;
    private double feedingCost;
    private boolean fosterCareRequired;

    public OrphanedAnimalRescue(String rescueCaseId, String animalName, String species,
                                String rescueLocation, String assignedRanger,
                                int numberOfRescueDays, double dailyCareCost,
                                int estimatedAgeMonths, double feedingCost,
                                boolean fosterCareRequired) {
        super(rescueCaseId, animalName, species, rescueLocation, assignedRanger,
                numberOfRescueDays, dailyCareCost);
        this.estimatedAgeMonths = estimatedAgeMonths;
        this.feedingCost = feedingCost;
        this.fosterCareRequired = fosterCareRequired;
    }

    public int getEstimatedAgeMonths() {
        return estimatedAgeMonths;
    }

    public double getFeedingCost() {
        return feedingCost;
    }

    public boolean isFosterCareRequired() {
        return fosterCareRequired;
    }

    @Override
    public String getRescueType() {
        return "Orphaned Animal Rescue";
    }

    @Override
    public double calculateTotalRescueCost() {
        double total = getBaseCareCost() + feedingCost;
        if (fosterCareRequired) {
            total += FOSTER_CARE_FEE;
        }
        return total;
    }

    @Override
    public String determineRescuePriority() {
        if (fosterCareRequired || estimatedAgeMonths < 6) {
            return "High";
        }
        return "Medium";
    }

    @Override
    public String displayTypeSpecificInfo() {
        return String.format(
                "Estimated Age      : %d months%n"
                        + "Feeding Cost       : R %.2f%n"
                        + "Foster Care Needed : %s%n",
                estimatedAgeMonths, feedingCost, fosterCareRequired ? "Yes" : "No");
    }
}
