package model;

public class MyFile {
    private final String name;
    private final String volume;
    private final String dateModified;

    public MyFile(String name, String volume, String dateModified) {
        this.name = name;
        this.volume = volume;
        this.dateModified = dateModified;
    }

    public String getName() {
        return name;
    }

    public String getVolume() {
        return volume;
    }

    public String getDateModified() {
        return dateModified;
    }
}