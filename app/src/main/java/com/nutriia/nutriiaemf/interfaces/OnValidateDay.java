package com.nutriia.nutriiaemf.interfaces;

import com.nutriia.nutriiaemf.models.Day;

import java.util.Map;
import java.util.Set;

public interface OnValidateDay {
    void onValidateDayButtonClick( Map<String, Set<String>> userInput);
    void onValidateDayResponse(Day day);
}
