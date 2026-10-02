package Main;

import Main.Users.Admin;
import Main.Users.Daneshjoo;
import Main.Users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cli {
    private Command command;
    private Processor processor;
    ArrayList<User> users;
    ArrayList<Daneshkade> daneshkadesha;
    ArrayList<Dars> doroos;
    User currentUser;
    Map<String, Boolean> permissions;
    int codeForOmitOrAdd;
    String backToDars;

    public Cli() {
        System.out.println("type your command:\n-log in\n-sign up");
        System.out.println("everywhere you can type these command: log in, sign up, back (back to previous page)");
        users = new ArrayList<>();
        doroos = new ArrayList<>();
        daneshkadesha = new ArrayList<>();
        permissions = new HashMap<>();
        setDefaultPermissions();
        Scanner sc = new Scanner(System.in);
        getFiles();
        while (sc.hasNextLine()) {
            this.command = new Command(sc.next(), sc.nextLine());
            System.out.println();
            this.processor = new Processor(this, this.command);
            setFiles();
        }
    }

    public void setDefaultPermissions() {
        permissions.put("go 1", false);
        permissions.put("go 2", false);
        permissions.put("go riazi", false);
        permissions.put("go zabanha", false);
        permissions.put("go fizik", false);
        permissions.put("go maaref", false);
        permissions.put("td", false);
        permissions.put("rd", false);
        permissions.put("rdd", false);
        permissions.put("add", false);
        permissions.put("iz", false);
        permissions.put("sdd", false);
        permissions.put("od", false);
        permissions.put("ad", false);
    }

    public void getFiles() {
        getDoroos();
        getDaneshkadeha();
        getUsers();
        getCurrentUser();
    }

    public void getDaneshkadeha() {
        File file = new File("Daneshkadeha.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            daneshkadesha.add(new Daneshkade(sc.next()));
            daneshkadesha.get(daneshkadesha.size() - 1).doroosEraeShode = new ArrayList<>();
            while (sc.hasNextInt()) {
                daneshkadesha.get(daneshkadesha.size() - 1).doroosEraeShode.add(getDarsWithCode(sc.nextInt()));
            }
            String garbage = sc.nextLine();
        }
    }

    public void getDoroos() {
        File file = new File("Doroos.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            doroos.add(new Dars(sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt(), sc.nextInt(),
                    sc.nextInt(), sc.nextInt(), sc.next(), sc.next(), sc.next(), sc.nextBoolean(), sc.next()));
            String garbage = sc.nextLine();
        }
    }

    public void getUsers() {
        File file = new File("Users.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            String username = sc.next();
            long password = sc.nextLong();
            if (!username.equals("admin")) {
                users.add(new Daneshjoo(username, password));
                users.get(users.size() - 1).doroosAkhzShode = new ArrayList<>();
                while (sc.hasNextInt()) {
                    users.get(users.size() - 1).doroosAkhzShode.add(getDarsWithCode(sc.nextInt()));
                }
            } else {
                users.add(new Admin(username, password));
            }
            String garbage = sc.nextLine();
        }
    }

    public void getCurrentUser() {
        File file = new File("CurrentUser.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (sc.hasNextLine()) {
            String username = sc.next();
            long password = sc.nextLong();
            if (!username.equals("admin")) {
                currentUser = new Daneshjoo(username, password);
            } else {
                currentUser = new Admin(username, password);
            }
        }
    }

    public void setFiles() {
        setDoroos();
        setDaneshkadeha();
        setUsers();
        setCurrentUser();
    }

    public void setDaneshkadeha() {
        File file = new File("Daneshkadeha.txt");
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < daneshkadesha.size(); i++) {
            printWriter.print(daneshkadesha.get(i).getName());
            for (int j = 0; j < daneshkadesha.get(i).doroosEraeShode.size(); j++) {
                printWriter.print(" " + daneshkadesha.get(i).doroosEraeShode.get(j).getCode());
            }
            printWriter.println();
        }
        printWriter.flush();
        printWriter.close();
    }

    public void setDoroos() {
        File file = new File("Doroos.txt");
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < doroos.size(); i++) {
            Dars dars = doroos.get(i);
            printWriter.println(dars.getCode() + "\t" + dars.getName() + "\t" + dars.getOstad() + "\t" +
                    dars.getZarfiatPorShode() + "\t" + dars.getZarfiatKol() + "\t" + dars.getVahed() + "\t" +
                    dars.getMahEmtehan() + "\t" + dars.getRoozEmtehan() + "\t" + dars.getZamanEmtehan() +
                    "\t" + dars.getRoozClass() + "\t" + dars.getZamanClass() + "\t" + dars.isTakhasosi() +
                    "\t" + dars.getDaneshkade());
        }
        printWriter.flush();
        printWriter.close();
    }

    public void setUsers() {
        File file = new File("Users.txt");
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < users.size(); i++) {
            printWriter.print(users.get(i).getUsername() + " " + users.get(i).getPassword());
            if (users.get(i).doroosAkhzShode != null && !users.get(i).doroosAkhzShode.isEmpty()) {
                for (int j = 0; j < users.get(i).doroosAkhzShode.size(); j++) {
                    printWriter.print(" " + users.get(i).doroosAkhzShode.get(j).getCode());
                }
            }
            printWriter.println();
        }
        printWriter.flush();
        printWriter.close();
    }

    public void setCurrentUser() {
        File file = new File("CurrentUser.txt");
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (currentUser != null) {
            printWriter.println(currentUser.getUsername() + " " + currentUser.getPassword());
        }
        printWriter.flush();
        printWriter.close();
    }

    public Dars getDarsWithCode(int code) {
        for (int i = 0; i < doroos.size(); i++) {
            if (doroos.get(i).getCode() == code) {
                return doroos.get(i);
            }
        }
        return null;
    }

    public Dars getDarsWithCode(String code) {
        for (int i = 0; i < doroos.size(); i++) {
            if ((" " + doroos.get(i).getCode()).equals(code)) {
                return doroos.get(i);
            }
        }
        return null;
    }

    public int numberOfCurrentUserVahed() {
        int number = 0;
        for (int i = 0; i < currentUser.doroosAkhzShode.size(); i++) {
            number += currentUser.doroosAkhzShode.get(i).getVahed();
        }
        return number;
    }

    public int numberOfOmoomiDarsOfCurrentUserVahed() {
        int number = 0;
        for (int i = 0; i < currentUser.doroosAkhzShode.size(); i++) {
            if (!currentUser.doroosAkhzShode.get(i).isTakhasosi()) {
                number += currentUser.doroosAkhzShode.get(i).getVahed();
            }
        }
        return number;
    }

    public boolean isTakenDars(Dars dars) {
        for (int i = 0; i < currentUser.doroosAkhzShode.size(); i++) {
            if (currentUser.doroosAkhzShode.get(i).getCode() == dars.getCode()) {
                return true;
            }
        }
        return false;
    }
}