package Main;

import Main.Users.Daneshjoo;

import java.util.ArrayList;

public class Dars {
    private final int code;
    private final String name;
    private final String ostad;
    private int zarfiatPorShode;
    private int zarfiatKol;
    private final int vahed;
    private final int roozEmtehan;
    private final int mahEmtehan;
    private final String zamanEmtehan;
    private final String roozClass;
    private final String zamanClass;
    private final boolean takhasosi;
    private final String daneshkade;

    public Dars(int code, String name, String ostad, int zarfiatPorShode, int zarfiatKol, int vahed,
                int roozEmtehan, int mahEmtehan, String zamanEmtehan, String roozClass, String zamanClass,
                boolean takhasosi, String daneshkade) {
        this.code = code;
        this.name = name;
        this.ostad = ostad;
        this.zarfiatPorShode = zarfiatPorShode;
        this.zarfiatKol = zarfiatKol;
        this.vahed = vahed;
        this.roozEmtehan = roozEmtehan;
        this.mahEmtehan = mahEmtehan;
        this.zamanEmtehan = zamanEmtehan;
        this.roozClass = roozClass;
        this.zamanClass = zamanClass;
        this.takhasosi = takhasosi;
        this.daneshkade = daneshkade;
    }

    //Getter and Setter
    public void setZarfiatKol(int zarfiatKol) {
        this.zarfiatKol = zarfiatKol;
    }

    public void setZarfiatPorShode(int zarfiatPorShode) {
        this.zarfiatPorShode = zarfiatPorShode;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getOstad() {
        return ostad;
    }

    public int getZarfiatKol() {
        return zarfiatKol;
    }

    public int getVahed() {
        return vahed;
    }

    public int getRoozEmtehan() {
        return roozEmtehan;
    }

    public int getMahEmtehan() {
        return mahEmtehan;
    }

    public String getZamanEmtehan() {
        return zamanEmtehan;
    }

    public String getRoozClass() {
        return roozClass;
    }

    public String getZamanClass() {
        return zamanClass;
    }

    public boolean isTakhasosi() {
        return takhasosi;
    }

    public String getDaneshkade() {
        return daneshkade;
    }

    public int getZarfiatPorShode() {
        return zarfiatPorShode;
    }
}