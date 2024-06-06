package com.nutriia.nutriia.interfaces;

import com.nutriia.nutriia.Day;

import java.util.Map;
import java.util.Set;

public interface OnValidateDay {
    void onValidateDayButtonClick( Map<String, Set<String>> userInput);
    void onValidateDayResponse(Day day);
}
