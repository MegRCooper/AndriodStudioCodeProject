package com.example.meg.multiplechoicequiz;

/**
 *
 */
public class LetterData {

    /**
     * This is a 2D array of strings which has the grouped similar sounding letter pairs
     * which will be used for the letter scrambling.
     */
       public static final String[][] SIMILAR_SOUNDS = {{"p", "pp"},{"v", "ve"},{"b","bb"},{"ch", "tch"},
            {"w", "wh"},{"d", "dd","ed"},{"s","ss","se","c","ce","st"},{"g","gg","gh"},{"f","ff","ph"
            ,"gh","ffe"},{"m","mm","mb","mn"},{"ng","in"},{"s","sh","ch"},{"l","ll","le","el","il","al"},
            {"r","rr","wr"},{"j","g","ge","dge"},{"z","zz","s","se","ze"},{"t","tt","ed","te"},
            {"oa", "ow", "o", "oe"},{"o", "a", "ou"},{"e", "y", "i", "ie"},{"ee", "ea", "e", "y",
            "ey", "ie"},{"a", "ai", "ea", "ay", "ey"},{"e", "ea", "ai", "ie"},{"er", "ur", "ir", "or"},
            {"oo", "oul", "u"},{"i", "y"},{"u", "o", "ou"},{"ar", "la", "al"},{"ow", "ou"},{"oi", "oy"},
            {"u", "ew", "ue"},{"oo", "ue", "ew", "o", "u", "ou"}};

    //public static final String[][] DYSLEXIA_PAIRS = {{"d","b"},{"c","s"},{"e","o"},{"p","q"}};


}

// {"oa", "ow", "o", "oe"}
// {"ear", "eer", "ere"}
// {"o", "a", "ou"}
// {"igh", "e", "y", "i", "ie"}
// {"ee", "ea", "e", "y", "ey", "ie"}
// {"a", "ai", "ea", "ay", "ey", "eigh"}
// {"or", "our", "a", "al", "ore", "oor", "aw", "au"}
// {"e", "ea", "ai", "ie"}
// {"er", "ur", "ir", "or", "ear"}
// {"oo", "oul", "u"}
// {"i", "y"}
// {"u", "o", "ou"}
// {"ar", "la", "al"}
// {"ow", "ou"}
// {"oi", "oy"}
// {"ule", "u", "ew", "ue"}
// {"air", "are", "ear", "eir", "ere"}
// {"oo", "ue", "ew", "o", "u", "ou"}