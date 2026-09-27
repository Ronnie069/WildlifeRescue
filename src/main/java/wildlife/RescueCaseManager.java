package wildlife;

import java.util.ArrayList;

public class RescueCaseManager {

    private final ArrayList<RescueCase> cases = new ArrayList<RescueCase>();

    public ArrayList<RescueCase> getAllCases() {
        return cases;
    }

    public int getCaseCount() {
        return cases.size();
    }

    public boolean isDuplicateId(String rescueCaseId) {
        return findById(rescueCaseId) != null;
    }

    public void addCase(RescueCase rescueCase) {
        if (rescueCase == null) {
            throw new IllegalArgumentException("Rescue case cannot be null.");
        }
        if (isDuplicateId(rescueCase.getRescueCaseId())) {
            throw new IllegalArgumentException("Rescue Case ID already exists: "
                    + rescueCase.getRescueCaseId());
        }
        cases.add(rescueCase);
    }

    public RescueCase findById(String rescueCaseId) {
        if (rescueCaseId == null) {
            return null;
        }
        for (RescueCase rescueCase : cases) {
            if (rescueCase.getRescueCaseId().equalsIgnoreCase(rescueCaseId.trim())) {
                return rescueCase;
            }
        }
        return null;
    }

    public double getTotalEstimatedCost() {
        double total = 0;
        for (RescueCase rescueCase : cases) {
            total += rescueCase.calculateTotalRescueCost();
        }
        return total;
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("==============================================\n");
        report.append("           WILDLIFE RESCUE REPORT\n");
        report.append("==============================================\n");

        if (cases.isEmpty()) {
            report.append("No rescue cases have been recorded.\n");
        } else {
            int index = 1;
            for (RescueCase rescueCase : cases) {
                report.append(String.format("%n--- Case %d ---%n", index++));
                report.append(String.format("Rescue Case ID   : %s%n", rescueCase.getRescueCaseId()));
                report.append(String.format("Rescue Type      : %s%n", rescueCase.getRescueType()));
                report.append(String.format("Species          : %s%n", rescueCase.getSpecies()));
                report.append(String.format("Rescue Location  : %s%n", rescueCase.getRescueLocation()));
                report.append(String.format("Assigned Ranger  : %s%n", rescueCase.getAssignedRanger()));
                report.append(String.format("Rescue Priority  : %s%n", rescueCase.determineRescuePriority()));
                report.append(String.format("Current Status   : %s%n", rescueCase.getCurrentStatus()));
                report.append(String.format("Total Rescue Cost: R %.2f%n",
                        rescueCase.calculateTotalRescueCost()));
            }
        }

        report.append("\n----------------------------------------------\n");
        report.append(String.format("Total number of rescue cases : %d%n", getCaseCount()));
        report.append(String.format("Total estimated rescue cost  : R %.2f%n", getTotalEstimatedCost()));
        report.append("==============================================\n");
        return report.toString();
    }
}
