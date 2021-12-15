package com.example.meg.multiplechoicequiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;

public class mainQuiz extends AppCompatActivity {
    private DictionaryLoader dictionaryLoader;

    /**
     * gets the dictionary loader
     * @return the dictionary loader
     */
    private DictionaryLoader getDictionaryLoader() {
        return dictionaryLoader;
    }
    private IDictionary dict;
    private DictRandAccIndex dictRandAccIndex;
    private String questionText;
    private QuizWord[] quizWords;

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonuserscore;
    Button sBtn;

    private String mAnswer;
    private int mScore = 0;
    private int questionAsked = 0;

    /**
     * this save the state of the application in a bundle (typically non-persistent, dynamic data in
     * onSaveInstanceState), it can be passed back to onCreate if the activity needs to be recreated
     * (e.g., orientation change) so that it don't lose this prior information.
     * @param savedInstanceState is the bundle name.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        setupVars();
        chooseQuestion();

        mScoreView = findViewById(R.id.score);
        mQuestionView = findViewById(R.id.questions);
        mButtonChoice1 = findViewById(R.id.choice1);
        mButtonChoice2 = findViewById(R.id.choice2);
        mButtonChoice3 = findViewById(R.id.choice3);


        updateQuestion();
        //Start of Button Listener for Button1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            /**
             * Sets an on click listener on the first button so that it knows when the button is
             * pressed.
             * @param view View: The view that was clicked.
             */
            @Override
            public void onClick(View view){
                //Logic for the Button goes in here
                if (mButtonChoice1.getText().equals(mAnswer)){ //Compares the btn text to the answer
                    mScore ++; // If they match then the score increments by one
                    updateScore(); // Calls the method updateScore
                    chooseQuestion(); //Calls the method chooseQuestion
                    updateQuestion(); // Calls the method updateQuestion

                    Toast.makeText(mainQuiz.this, "correct", Toast.LENGTH_SHORT).show(); // Short pop-up that lets the user know they got the correct answer

                }
                else{
                    Toast.makeText(mainQuiz.this, "wrong", Toast.LENGTH_SHORT).show(); // Short pop-up that lets the user know they got the wrong answer
                    chooseQuestion();
                    updateQuestion();
                }
            }
        });
        //End of Button Listener for Button1
        //Start of Button Listener for Button2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            /**
             * Sets an on click listener on the second button so that it knows when the button is
             * pressed.
             * @param view View: The view that was clicked.
             */
            @Override
            public void onClick(View view){
                //Same logic as in Btn 1
                if (mButtonChoice2.getText().equals(mAnswer)){
                    mScore = mScore + 1;
                    updateScore();
                    chooseQuestion();
                    updateQuestion();

                    Toast.makeText(mainQuiz.this, "correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mainQuiz.this, "wrong", Toast.LENGTH_SHORT).show();
                    chooseQuestion();
                    updateQuestion();
                }
            }
        });
        //End of Button Listener for Button2
        //Start of Button Listener for Button3
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            /**
             * Sets an on click listener on the third button so that it knows when the button is
             * pressed.
             * @param view View: The view that was clicked.
             */
            @Override
            public void onClick(View view){
                //Same logic as in Btn 1
                if (mButtonChoice3.getText().equals(mAnswer)){
                    mScore = mScore + 1;
                    updateScore();
                    chooseQuestion();
                    updateQuestion();

                    Toast.makeText(mainQuiz.this, "correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mainQuiz.this, "wrong", Toast.LENGTH_SHORT).show();
                    chooseQuestion();
                    updateQuestion();
                }
            }
        });
        //End of Button Listener for Button3

        sBtn = findViewById(R.id.sBtn);
        sBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets the on click listener for the stopping of the program to check that the
             * button is pressed.
             *
             * @param v View: The view that was clicked.
             */
            @Override
            public void onClick(View v){
                finish(); // If the btn is pressed then finish
                System.exit(0); // Closes the app.
            }
        });
    }

    /**
     * Loads a dictionary, checks for the files and if it is exists then it loads to the phone
     */
    private void setupVars() {
        dictionaryLoader = new DictionaryLoader(getApplicationContext()); //Loads the Dictionary
        if (!dictionaryLoader.exists()) { // Checking for the file which contains the dictionary
            try { // If it exists then it tries ...
                dictionaryLoader.install(); // to install this to the dictionary to the local device
            } catch (IOException e) {
                e.printStackTrace(); // If not throw exception,
                return;
            }
        }
        dict = dictionaryLoader.createInstance(); // Loads the dictionary file into a var called dict
        try {
            dict.open(); // tries to open
        } catch (IOException ioe) {
            ioe.printStackTrace(); // Throw exception if failed
        }
        dictRandAccIndex = DictRandAccIndex.loadOrCreate(this, dict); // Links to the loadOrCreate Class
    }

    /**
     * Choose the next question ready for the user
     * Increments the number of questions asked
     *
     */
    private void chooseQuestion() {
        questionAsked ++; // Increments the number of questions been asked
        Random random = new Random();
        int wordIndex = random.nextInt(dictRandAccIndex.getCommonSize());
        IIndexWord correctIndexWord = dict.getIndexWord(dictRandAccIndex.getCommon(wordIndex));
        IWordID wordID = correctIndexWord.getWordIDs().get(0);
        IWord correctWord = dict.getWord((wordID));

        QuizWord quizWord = new SubstQuizWord (correctWord.getLemma(), SubstTable.similarSoundsTable); //Makes a QuizWord object from the "correct" word and the similar sounds f.
        quizWords = quizWord.makeNewWords(3); /* tries to make a set of three words from the correct word plus two scrambled words
        makeNewWords will return null if that process fails i.e if the correct word doesn't match with enough
        substitutions */
        while (quizWords == null) {  //loops the code until it succeeds in in making three words.
            wordIndex = random.nextInt(dictRandAccIndex.getCommonSize()); //Gets another random word index number.
            correctIndexWord = dict.getIndexWord(dictRandAccIndex.getCommon(wordIndex)); // Gets the correctIndexWord from the list of common words using the random index number.
            wordID = correctIndexWord.getWordIDs().get(0); // Gets wordID from correctIndexWord
            correctWord = dict.getWord((wordID)); // Gets the word from wordIndex
            quizWord = new SubstQuizWord (correctWord.getLemma(), SubstTable.similarSoundsTable); // Tries to make the set of three
            quizWords = quizWord.makeNewWords(3);
        }
        mAnswer = quizWord.getText(); // makes a note of the correct word out of the three possible options. stores in mAnswer.
        questionText = "Gloss = " + correctWord.getSynset().getGloss() + "\n"; // Sets the definition of the correct word up at the top of the UI.
        questionText = questionText.replace(mAnswer,"******"); // Removes the chosen word from the definition and replaces with asterisks so its harder.
    }

    // Updates the question by changing the text value on each button.

    /**
     * This updates the question required and sets the text
     * on the buttons and in the text edit accordingly.
     */
    private void updateQuestion() {
            mQuestionView.setText(questionText);
            mButtonChoice1.setText(quizWords[0].getText());
            mButtonChoice2.setText(quizWords[1].getText());
            mButtonChoice3.setText(quizWords[2].getText());
    }
    // Updates the users score.

    /**
     * This updates the score
     * and set the display to the score so the user can see it.
     */
    private void updateScore() {
        mScoreView.setText(String.valueOf(mScore) + "/" + (String.valueOf(questionAsked)));
        }

    private void UsersScore(){
    }

}
