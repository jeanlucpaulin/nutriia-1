package com.nutriia.nutriiaemf.interfaces;

import com.nutriia.nutriiaemf.models.Day;

import java.util.Map;
import java.util.Set;

/**
 * Interface for validate day
 */
public interface OnValidateDay {
    /**
     * Method called when the validate day button is clicked
     * @param userInput the user input
     */
    void onValidateDayButtonClick( Map<String, Set<String>> userInput);

    /**
     * Method called when the day is validated
     * @param day the day
     */
    void onValidateDayResponse(Day day);
}
