package com.example.meg.multiplechoicequiz;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Random;
import static java.lang.Integer.min;

public class SubstQuizWord extends QuizWord {

    /**
     * This creates the new word
     * It also creates the scrambled words using the max and min length and changes the number of
     * swaps dependent upon the number of letters in the word
     * @return null if no word is found
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public QuizWord  makeNewWord(){
        boolean changed = false;
        String newWordText = "";
        Random random = new Random();
        int srcLen = 1;
        int minLen = table.getMinReplaceLength();
        int maxLen = table.getMaxReplaceLength();
        for (int i = 0; i < text.length(); i+=srcLen){
            boolean copy = true;
            if (i+minLen <= text.length()){
                int maxLenTwo = min(maxLen, text.length() - i);
                srcLen = random.nextInt (maxLenTwo-minLen + 1) + minLen;
                SubstEntry entry = table .getEntry(text.substring(i, i+srcLen));
                if (entry != null ){
                    int count = entry.getCount() - 1;
                    int newEntry;
                    if(count == 1) {
                        if(entry.getReplaceEntry() == 0) {
                            newEntry = 1;
                        }else {
                            newEntry = 0;
                        }
                    }else {
                        newEntry = random.nextInt(count);
                        if(newEntry >= entry.getReplaceEntry()){
                            newEntry++;

                        }
                    }
                    newWordText += entry.getItem(newEntry);
                    copy = false;
                    changed = true;
                }
                if (copy){
                    newWordText += text.charAt(i);
                    srcLen = 1;
                }
            }

        }
        if (changed){
            return new SubstQuizWord (newWordText, table);
        }else {
            return null;
        }
    }

    /**
     * makes a new word and then finds the next word using the random function
     * @param count integer that keeps track of the entry
     * @return the variable returnValue
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public QuizWord[] makeNewWords (int count){
        QuizWord[] returnValue = new QuizWord[count];
        Random random = new Random();
        returnValue[random.nextInt(count)] = this;
        for (int i = 0; i < count; i++){
            if(returnValue[i] != null) {
                continue;
            }
            boolean duplicate;
            int retries = 0;
            do{
                duplicate = false;
                QuizWord newWord = makeNewWord();
                if (newWord == null){
                    return null;
                }
                returnValue [i] = newWord;
                for (int j = 0; j < i; j++){
                    if (returnValue[j].getText().equals(newWord.getText())){
                        duplicate = true;
                        retries ++;
                        break;
                    }
                }
            } while (duplicate && retries < 5);
            if (duplicate) {
                return null;
            }
        }
        return returnValue;
    }
    SubstTable table;

    /**
     * Links the table of values of the pairs of letters that need scrambling.
     * @param text String passed into the class
     * @param table passes the table through to the class
     */
    public SubstQuizWord(String text, SubstTable table) {
        super(text);
        this.table = table;
    }
}
