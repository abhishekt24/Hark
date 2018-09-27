package com.techiespace.projects.hark;

import java.util.HashSet;
import java.util.Set;

public class EvaluateClip {
    String originalTranscript;
    String userTranscript;
    String originalWords[];
    String userWords[];

    public EvaluateClip(String originalTranscript, String userTranscript) {
        this.originalTranscript = originalTranscript;
        this.userTranscript = userTranscript;
        originalWords = originalTranscript.split("\\s+");
        userWords = userTranscript.split("\\s+");
    }

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0; /* both strings are zero length */
        }
        /* // If you have StringUtils, you can use it to calculate the edit distance:
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                                                             (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
        // returning accuracy between 0 and 1
    }

    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    double evaluate() {
        int flag = 0;
        Set<String> missedWords = new HashSet<String>();

        originalTranscript = originalTranscript.replaceAll("[^a-zA-Z0-9]", "");
        userTranscript = userTranscript.replaceAll("[^a-zA-Z0-9]", "");

        for (String temp : originalWords) {
            flag = 0;
            for (String temp1 : userWords) {
                if (temp.equals(temp1))
                    flag = 1;
            }
            if (flag == 0) {
                missedWords.add(temp);
            }
        }

        double score = similarity(originalTranscript, userTranscript);
        return ((double) Math.round(score * 100 * 10)) / 10;
    }
}
