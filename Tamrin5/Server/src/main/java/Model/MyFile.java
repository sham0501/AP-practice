package model;

import java.io.File;

public class MyFile {
    private final File file;
    private final String volume;
    private final String  dateModified;

    public MyFile(File file, String dateModified) {
        this.file = file;
        this.volume = file.length() + "";
        this.dateModified = dateModified;
    }

    public File getFile() {
        return file;
    }

    public String getVolume() {
        return volume;
    }

    public String getDateModified() {
        return dateModified;
    }
}