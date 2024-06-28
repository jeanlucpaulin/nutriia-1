package com.nutriia.nutriiaemf.resources;

/**
 * Class for storing settings
 */
public class Settings {
    /**
     * @return maximum number of generation of dish suggestions
     */
    public static int getMaxDishSuggestions() {
        return 5;
    }

    /**
     * @return maximum number of displayed items in the list
     */
    public static int getMaxDisplayedItems() {
        return 8;
    }

    /**
     * @return authorization to swipe on activity to navigate
     */
    public static boolean authorizeSwipeOnActivity() {
        return false;
    }

    /**
     * @return minimum age for the user in the Spinner
     */
    public static int getMinimumAge() {
        return 18;
    }

    /**
     * @return maximum age for the user in the Spinner
     */
    public static int getMaximumAge() {
        return 100;
    }
}
