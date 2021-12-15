package com.example.meg.multiplechoicequiz;

import android.content.ContextWrapper;

import java.io.File;

public class Utilities {

    /**
     *
     * @param wrapper
     * @param fname
     * @return
     */
    public static boolean privateFileExists(ContextWrapper wrapper, String fname){
        File file = wrapper.getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    /**
     *
     * @param wrapper is a context wrapper for the cactivity
     * @param fname the String file name
     * @return the absolute path on the filesystem where a file created with openFileOutput(String, int) is stored.
     */
    public static File getPrivateFile(ContextWrapper wrapper, String fname){
        return wrapper.getBaseContext().getFileStreamPath(fname);
    }
}
