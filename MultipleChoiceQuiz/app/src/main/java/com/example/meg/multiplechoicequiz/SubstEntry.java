package com.example.meg.multiplechoicequiz;

public class    SubstEntry {
    private String[] data;
    private int replaceLength;
    private int replaceEntry;

    /**
     * Gets the replacment entry integer
     * @return an integer
     */
    public int getReplaceEntry() {
        return replaceEntry;
    }

    /**
     * Gets the replacement length
     * @return integer
     */
    public int getReplaceLength() {
        return replaceLength;
    }

    /**
     * Counts the data length.
     * @return text array data
     */
    public int getCount() {
        return data.length;
    }

    /**
     * Gets the data index
     * @param index is the amount of data needed
     * @return data index
     */
    public String getItem(int index) {
        return data[index];
    }

    /**
     * Creates instances for this class
     * @param data is the array of words (data)
     * @param replaceEntry this is the index for the next entry.
     */
    public SubstEntry(String[] data, int replaceEntry) {
        this.data = data;
        this.replaceLength = data[replaceEntry].length();
        this.replaceEntry = replaceEntry;
    }
}


