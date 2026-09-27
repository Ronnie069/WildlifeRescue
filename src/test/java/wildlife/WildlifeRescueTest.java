package wildlife;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * JUnit tests for core rescue-case behaviour.
 */
public class WildlifeRescueTest {

    private RescueCaseManager manager;
    private InjuredAnimalRescue injured;
    private OrphanedAnimalRescue orphaned;
    private EndangeredSpeciesRescue endangered;

    @Before
    public void setUp() {
        manager = new RescueCaseManager();
        injured = new InjuredAnimalRescue(
                "RC001", "Thandi", "Cheetah", "Kruger North", "Ranger Molefe",
                4, 350.00, "Broken hind leg", 2200.00, true);
        orphaned = new OrphanedAnimalRescue(
                "RC002", "Kabelo", "African Penguin", "Betty's Bay", "Ranger Naidoo",
                10, 120.00, 3, 800.00, true);
        endangered = new EndangeredSpeciesRescue(
                "RC003", "Naledi", "African Wild Dog", "Madikwe", "Ranger Dlamini",
                6, 500.00, "Endangered", 1500.00, true);
    }

    @Test
    public void injuredCostIncludesVeterinaryAndSurgeryFee() {
        // (4 * 350) + 2200 + 5000 = 8600
        assertEquals(8600.00, injured.calculateTotalRescueCost(), 0.001);
    }

    @Test
    public void injuredCostWithoutSurgeryExcludesSurgeryFee() {
        InjuredAnimalRescue noSurgery = new InjuredAnimalRescue(
                "RC010", "Luna", "Jackal", "Karoo", "Ranger Adams",
                2, 100.00, "Minor laceration", 400.00, false);
        // (2 * 100) + 400 = 600
        assertEquals(600.00, noSurgery.calculateTotalRescueCost(), 0.001);
    }

    @Test
    public void orphanedCostIncludesFeedingAndFosterFee() {
        // (10 * 120) + 800 + 2500 = 4500
        assertEquals(4500.00, orphaned.calculateTotalRescueCost(), 0.001);
    }

    @Test
    public void endangeredCostIncludesSecurityAndSpecialistFee() {
        // (6 * 500) + 1500 + 8000 = 12500
        assertEquals(12500.00, endangered.calculateTotalRescueCost(), 0.001);
    }

    @Test
    public void injuredPriorityIsHighWhenSurgeryRequired() {
        assertEquals("High", injured.determineRescuePriority());
    }

    @Test
    public void injuredPriorityIsMediumWithoutSurgery() {
        InjuredAnimalRescue noSurgery = new InjuredAnimalRescue(
                "RC011", "Luna", "Jackal", "Karoo", "Ranger Adams",
                2, 100.00, "Minor laceration", 400.00, false);
        assertEquals("Medium", noSurgery.determineRescuePriority());
    }

    @Test
    public void orphanedPriorityIsHighForYoungOrFoster() {
        assertEquals("High", orphaned.determineRescuePriority());
    }

    @Test
    public void endangeredPriorityIsAlwaysHigh() {
        assertEquals("High", endangered.determineRescuePriority());
    }

    @Test
    public void startRescueChangesStatusToInProgress() {
        assertEquals(RescueCase.STATUS_REGISTERED, injured.getCurrentStatus());
        injured.startRescue();
        assertEquals(RescueCase.STATUS_IN_PROGRESS, injured.getCurrentStatus());
    }

    @Test
    public void completeRescueChangesStatusToCompleted() {
        injured.startRescue();
        injured.completeRescue();
        assertEquals(RescueCase.STATUS_COMPLETED, injured.getCurrentStatus());
    }

    @Test
    public void cannotCompleteBeforeStart() {
        try {
            injured.completeRescue();
            fail("Completing a registered case should fail.");
        } catch (IllegalStateException expected) {
            assertEquals(RescueCase.STATUS_REGISTERED, injured.getCurrentStatus());
        }
    }

    @Test
    public void searchFindsExistingCase() {
        manager.addCase(injured);
        manager.addCase(orphaned);
        RescueCase found = manager.findById("RC002");
        assertNotNull(found);
        assertEquals("Kabelo", found.getAnimalName());
    }

    @Test
    public void searchReturnsNullForUnknownId() {
        manager.addCase(injured);
        assertNull(manager.findById("UNKNOWN"));
    }

    @Test
    public void duplicateIdsAreRejected() {
        manager.addCase(injured);
        assertTrue(manager.isDuplicateId("RC001"));
        try {
            manager.addCase(new InjuredAnimalRescue(
                    "RC001", "Other", "Lion", "Limpopo", "Ranger X",
                    1, 100.00, "Wound", 50.00, false));
            fail("Duplicate ID should be rejected.");
        } catch (IllegalArgumentException expected) {
            assertEquals(1, manager.getCaseCount());
        }
    }

    @Test
    public void blankCheckWorks() {
        assertTrue(InputValidator.isBlank(""));
        assertTrue(InputValidator.isBlank("   "));
        assertFalse(InputValidator.isBlank("RC001"));
    }
}
