package com.example.meg.multiplechoicequiz;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;

public class DictionaryLoader {
    private Context context;
    private String zipFileName;
    private File targetDirectory;
    private File dictDirectory;
    private Dictionary instance;

    // Loads the dictionary from the .zip file

    /**
     * Loads the dictionary from the .zip file
     * @param context gets context of the in activity class
     * @param zipFileName passes the string file name to the class
     * @param targetSubFolder passes the class the sub folders target location
     * @param externalStorage whether or not a the external storage is available.
     *
     */
    public DictionaryLoader (Context context, String zipFileName, String targetSubFolder, boolean externalStorage){
        File base = externalStorage ? context.getExternalFilesDir(null): context.getFilesDir();
        targetDirectory = new File(base, targetSubFolder);
        dictDirectory = new File(targetDirectory, "dict");
        this.context = context;
        this.zipFileName = zipFileName;
    }

    /**
     * Loads the dictionary with context, file name and the target sub folder
     * @param context gets context of the in activity class
     * @param zipFileName passes the string file name to the class
     * @param targetSubFolder passes the class the sub folders target location
     */
    public DictionaryLoader (Context context, String zipFileName, String targetSubFolder){
        this(context, zipFileName, targetSubFolder, false);
    }

    /**
     * Loads the dictionary with both context and file name
     * @param context context gets context of the in activity class
     * @param zipFileName passes the string file name to the class
     */
    public DictionaryLoader (Context context, String zipFileName){
        this(context, zipFileName, "", false);
    }

    /**
     * Loads the dictionary with just context
     * @param context gets context of the in activity class
     */
    public DictionaryLoader (Context context) {
        this(context, "dict.zip", "",false);
    }

    // Checks if file exits

    /**
     * Checks to see if the file exits
     * @return the dictionary as a list which must be greater than 0.
     */
    public boolean exists(){
        return dictDirectory.isDirectory() && dictDirectory.list().length > 0;
    }

    /**
     * Installs the dictionary from the zip file stored in assets to the
     * desired folder of private storage
     * @throws IOException
     * */
    public void install() throws IOException{
        // Used to read (decompress) the data from zip files.
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(context.getAssets().open(zipFileName, AssetManager.ACCESS_RANDOM)));
        try {
            ZipEntry ze;
            int count;
            byte [] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null){
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                }
                finally {
                    fout.close();
                }
            }
        }
        finally {
            zis.close();
        }

    }

    /**
     * creates the instance of IDictionary
     * @throws IOException
     * */
    public IDictionary createInstance(){
        instance = null;
        try {
            instance = new Dictionary(new URL("file", null, dictDirectory.toString()));
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * gets the IDictionary instance
     * @return the instance
     */
    public IDictionary getInstance (){
        return instance;
    }
}
