package com.nutriia.nutriiaemf.resources;

import java.util.HashMap;
import java.util.Map;

public class Translator {
    public enum LANGUAGES {
        FR_fr,
    }
    private static final Map<String, Map<LANGUAGES, String>> dictionnary;

     static {
        dictionnary = new HashMap<>();
        dictionnary.put("calories", Map.of(LANGUAGES.FR_fr, "Calories"));
        dictionnary.put("proteins", Map.of(LANGUAGES.FR_fr, "Protéines"));
        dictionnary.put("carbohydrates", Map.of(LANGUAGES.FR_fr, "Glucides"));
        dictionnary.put("lipids", Map.of(LANGUAGES.FR_fr, "Lipides"));
        dictionnary.put("fibers", Map.of(LANGUAGES.FR_fr, "Fibres"));
        dictionnary.put("sugars", Map.of(LANGUAGES.FR_fr, "sucres"));
        dictionnary.put("vitamin_a", Map.of(LANGUAGES.FR_fr, "Vitamine A"));
        dictionnary.put("vitamin_b1", Map.of(LANGUAGES.FR_fr, "Vitamine B1"));
        dictionnary.put("vitamin_b2", Map.of(LANGUAGES.FR_fr, "Vitamine B2"));
        dictionnary.put("vitamin_b3", Map.of(LANGUAGES.FR_fr, "Vitamine B3"));
        dictionnary.put("vitamin_b4", Map.of(LANGUAGES.FR_fr, "Vitamine B4"));
        dictionnary.put("vitamin_b5", Map.of(LANGUAGES.FR_fr, "Vitamine B5"));
        dictionnary.put("vitamin_b6", Map.of(LANGUAGES.FR_fr, "Vitamine B6"));
        dictionnary.put("vitamin_b7", Map.of(LANGUAGES.FR_fr, "Vitamine B7"));
        dictionnary.put("vitamin_b9", Map.of(LANGUAGES.FR_fr, "Vitamine B9"));
        dictionnary.put("vitamin_b12", Map.of(LANGUAGES.FR_fr, "Vitamine B12"));
        dictionnary.put("vitamin_c", Map.of(LANGUAGES.FR_fr, "Vitamine C"));
        dictionnary.put("vitamin_d", Map.of(LANGUAGES.FR_fr, "Vitamine D"));
        dictionnary.put("vitamin_e", Map.of(LANGUAGES.FR_fr, "Vitamine E"));
        dictionnary.put("vitamin_k", Map.of(LANGUAGES.FR_fr, "Vitamine K"));
        dictionnary.put("calcium", Map.of(LANGUAGES.FR_fr, "Calcium"));
        dictionnary.put("copper", Map.of(LANGUAGES.FR_fr, "Cuivre"));
        dictionnary.put("iron", Map.of(LANGUAGES.FR_fr, "Fer"));
        dictionnary.put("magnesium", Map.of(LANGUAGES.FR_fr, "Magnésium"));
        dictionnary.put("manganese", Map.of(LANGUAGES.FR_fr, "Manganèse"));
        dictionnary.put("phosphorus", Map.of(LANGUAGES.FR_fr, "Phosphore"));
        dictionnary.put("potassium", Map.of(LANGUAGES.FR_fr, "Potassium"));
        dictionnary.put("zinc", Map.of(LANGUAGES.FR_fr, "Zinc"));
        dictionnary.put("iodine", Map.of(LANGUAGES.FR_fr, "Iode"));
        dictionnary.put("selenium", Map.of(LANGUAGES.FR_fr, "Sélénium"));
        dictionnary.put("sodium", Map.of(LANGUAGES.FR_fr, "Sodium"));
        dictionnary.put("fluoride", Map.of(LANGUAGES.FR_fr, "Fluor"));
        dictionnary.put("breakfast", Map.of(LANGUAGES.FR_fr, "Petit déjeuner"));
        dictionnary.put("lunch", Map.of(LANGUAGES.FR_fr, "Déjeuner"));
        dictionnary.put("dinner", Map.of(LANGUAGES.FR_fr, "Diner"));
        dictionnary.put("snack", Map.of(LANGUAGES.FR_fr, "Collations"));
    }
    public static String translate(String text) {
        return translate(text, LANGUAGES.FR_fr);
    }

    public static String translate(String text, LANGUAGES language) {
         if(!dictionnary.containsKey(text.toLowerCase())) return text;
         if(!dictionnary.get(text.toLowerCase()).containsKey(language)) return text;
        return dictionnary.get(text.toLowerCase()).get(language);
    }
}
