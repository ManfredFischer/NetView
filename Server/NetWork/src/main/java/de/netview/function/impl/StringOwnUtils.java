package de.netview.function.impl;

public class StringOwnUtils {

    public static String ersetze(String suche, String ersatz, String str) {
        int start = str.indexOf(suche);

        while (start != -1) {
            str = str.substring(0, start) + ersatz + str.substring(start + suche.length(), str.length());
            start = str.indexOf(suche, start + ersatz.length());
        }
        return (str);
    }

    public static String replaceUmlaut(String input) {

        //replace all lower Umlauts
        String output = input.replace("ü", "ue")
                .replace("ö", "oe")
                .replace("ä", "ae");

        //now replace all the other capital umlaute
        output = output.replace("Ü", "Ue")
                .replace("Ö", "Oe")
                .replace("Ä", "Ae");

        return output;
    }
}
