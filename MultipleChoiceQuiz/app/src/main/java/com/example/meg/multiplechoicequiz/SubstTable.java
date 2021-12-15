package com.example.meg.multiplechoicequiz;

import java.util.HashMap;


public class SubstTable {

    /**
     * Sets up the new table with the data from the Similar sounds data
     */
    public static final SubstTable similarSoundsTable = new SubstTable (LetterData.SIMILAR_SOUNDS);
    //public static final SubstTable dylexiaPairsTable = new SubstTable (LetterData.DYSLEXIA_PAIRS);

    /**
     *
     * @param partWord this is the smaller sections of the words
     * @return the hashmap part of the word
     */
    public SubstEntry getEntry (String partWord){
        return hashMap.get(partWord);
    }

    private HashMap <String, SubstEntry> hashMap;
    private int minReplaceLength;
    private int maxReplaceLength;

    /**
     * gets the minimum length of the words that can be swapped
     * @return minReplaceLength
     */
    public int getMinReplaceLength() {
        return minReplaceLength;
    }

    /**
     * gets the maximum length of the words that can be swapped
     * @return maxReplacedLength
     */
    public int getMaxReplaceLength() {
        return maxReplaceLength;
    }

    /**
     * This is the letter scrambler class
     * @param letterData An array of letters that can be replaced.
     */
    SubstTable (String[][] letterData){
        maxReplaceLength = 0;
        minReplaceLength = 9999;
        hashMap = new HashMap<>();
        for(int i = 0; i < letterData.length; i++){
            for (int j = 0; j < letterData[i].length; j++){
                int length = letterData[i][j].length();
                if (length < minReplaceLength){
                    minReplaceLength = length;
                }
                if (length > maxReplaceLength){
                    maxReplaceLength = length;
                }
                SubstEntry entry = new SubstEntry(letterData[i], j);
                hashMap.put(letterData[i][j], entry);
            }
        }

    }
}
