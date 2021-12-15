package com.example.meg.multiplechoicequiz;

import android.content.ContextWrapper;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IIndexWordID;
import edu.mit.jwi.item.POS;


public class DictRandAccIndex implements Serializable {
    private ArrayList<IIndexWordID> allWords;
    private ArrayList<IIndexWordID> commonWords;
    private IDictionary dict;

    /**
     * This gets the common word
     * @param index it the number of the word I want
     * @return the index
     */
    public IIndexWordID getCommon(int index) {
        return commonWords.get(index);
    }

    /**
     * This gets all words
     * @param index  is the number of the word I want
     * @return the index
     */
    public IIndexWordID get(int index) {
        return allWords.get(index);
    }

    /**
     * Retrieves the common words size
     * @return the common words size
     */
    public int getCommonSize() {
        return commonWords.size();
    }

    /**
     * It will determined the size of the all words ArrayList
     * @return all words size
     */
    public int getSize() {
        return allWords.size();
    }





    /**
     *Creates the empty indexes and then calls the helper function to populate
     * them from the dictionary. It calls the helper four times, once for each POS. This constructor
     * is used during start-up the first time the app is run after installation. Saving of the file is
     * completed elsewhere.
     ** @param dict object of dictionary loader
     */
    public DictRandAccIndex(IDictionary dict) {
        allWords = new ArrayList<>();
        commonWords = new ArrayList<>();
        this.dict = dict;
        makeArrList(POS.NOUN);
        makeArrList(POS.VERB);
        makeArrList(POS.ADJECTIVE);
        makeArrList(POS.ADVERB);

    }

    /**
     * populates the indexes directly from an ObjectInputStream. This is used
     * during start-up on the second and successive times the app is run as part of the process of
     * loading the indexes from the dict.ser file in private storage.
     * @param dict object of dictionary loader
     * @param ois this is the object for the deserialization
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private DictRandAccIndex(IDictionary dict, ObjectInputStream ois) throws ClassNotFoundException, IOException{
        this.dict = dict;
        allWords = (ArrayList<IIndexWordID>)ois.readObject();
        commonWords = (ArrayList<IIndexWordID>)ois.readObject();
    }

    /**
     * makeArray first gets an iterator from WordNet for one of the word index lists in the WordNet
     * dictionary (Nouns, Verbs etc.). It then steps through every index entry in that list using the
     * iterator. The code then checks the word against a regular expression – a regex is a way of
     * checking whether a string matches a given pattern. In this case it looks to see if the word
     * starts with a possibly capital letter ( [A-Za-z] ), followed by between 2 and 14 characters
     * ( {2,14} ) that only contains lowercase letters ( [a-z] ) - so no spaces, punctuation or
     * capitalized abbreviations. If the word matches, then it is added to the allWords array list.
     * If it is a common word (getTagSenseCount() > 3), then it is added to the commonWords array list.
     *
     * @param pos is a part of speech, so defines the type of word.
     */
    private void  makeArrList (POS pos){ //POS = part of speech e.g. noun, verb etc.
        Iterator<IIndexWord> iter = dict.getIndexWordIterator(pos);
        String metchString = "[A-Za-z][a-z]{2,14}";
        while (iter.hasNext()) {
            IIndexWord inxWord = iter.next();
            if (inxWord.getLemma().matches(metchString)) {
                allWords.add(inxWord.getID());
                if (inxWord.getTagSenseCount() > 3)
                    commonWords.add(inxWord.getID());
            }
        }
    }

    /**
     * Sets the name of the file to dict.set
     *
     */
    static private final String FILENAME = "dict.ser";

    /**
     *
     * @param wrapper is a object that can contain an activity i.e. access to private storage
     * @param dict object of the dictionary
     * @return the new similar word index (newSwi)
     */
       static public DictRandAccIndex loadOrCreate(ContextWrapper wrapper, IDictionary dict){
        DictRandAccIndex newSwi = null; // The dictionary index is set to null
        if (Utilities.privateFileExists(wrapper, FILENAME)){ //Links to the Utilities class to check that the file dict.ser exists
            File file = Utilities.getPrivateFile(wrapper,FILENAME); // Takes to the private storage to find the file
            try
            {
                // Reading the object from a file
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fis);

                // Method for deserialization of object
                newSwi = new DictRandAccIndex(dict, in);

                in.close();
                fis.close();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
                Toast.makeText(wrapper, ex.getMessage(), Toast.LENGTH_SHORT).show(); // Shows the exception
            }
            catch(ClassNotFoundException ex){
                ex.printStackTrace();
                Toast.makeText(wrapper, ex.getMessage(), Toast.LENGTH_SHORT).show(); // Shows the exception
            }

        }
        else { // If the files doesn't exist then it makes one
            newSwi = new DictRandAccIndex(dict); // Adds the words (objects) into a sequence of bytes to be placed into a file
            File file = Utilities.getPrivateFile(wrapper,FILENAME);
            try
            {
                //Saving of object in a file
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fos);

                // Method for serialization of object
                out.writeObject(newSwi.allWords);
                out.writeObject(newSwi.commonWords);

                out.close();
                fos.close();


            }
            catch(IOException ex) {
                ex.printStackTrace();
                Toast.makeText(wrapper, ex.getMessage(), Toast.LENGTH_SHORT).show(); // Shows the exception
            }
        }
        return newSwi;
    }
}
