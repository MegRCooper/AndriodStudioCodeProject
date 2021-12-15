package com.example.meg.multiplechoicequiz;

abstract public class QuizWord {
    /**
     * Sets the instance text quizWord text
     * @param text points to the current class instance
     */
    public QuizWord(String text){
        this.text = text;
    }

    abstract public QuizWord  makeNewWord ();
    abstract public QuizWord[]  makeNewWords (int count);

    protected String text;

    /**
     * Gets the text
     * @return returns a string
     */
    public String getText() {
        return text;
    }


    public void run (){
        return;
    }

}
