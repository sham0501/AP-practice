package Main;

import Main.Users.Daneshjoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Processor {
    Cli cli;
    Command command;

    public Processor(Cli cli, Command command) {
        this.cli = cli;
        this.command = command;
        process();
    }

    public void process() {
        if (command.getCommand().equals("log")) {
            logIn();
        } else if (command.getCommand().equals("sign")) {
            signUp();
        } else if (command.getCommand().equals("go") && command.getArgument().equals(" 1")) {
            showDoroosOfCurrentUser();
        } else if (command.getCommand().equals("go") && command.getArgument().equals(" 2")) {
            showDaneshkadeha();
        } else if (command.getCommand().equals("go") && (command.getArgument().equals(" riazi") ||
                command.getArgument().equals(" zabanha") || command.getArgument().equals(" fizik") ||
                command.getArgument().equals(" maaref"))) {
            showDoroosOfDaneshkade(command.getArgument());
        } else if (command.getCommand().equals("td")) {
            takeDars(command.getArgument());
        } else if (command.getCommand().equals("rd")) {
            removeDars(command.getArgument());
        } else if (command.getCommand().equals("rdd")) {
            removeDarsFromDaneshkade(command.getArgument());
        } else if (command.getCommand().equals("add")) {
            addDarsToDaneshkade();
        } else if (command.getCommand().equals("iz")) {
            increaseZarfiat(command.getArgument());
        } else if (command.getCommand().equals("sdd")) {
            showDaneshjooOfDars(command.getArgument());
        } else if (command.getCommand().equals("od")) {
            omitDaneshjooFromDars(command.getArgument());
        } else if (command.getCommand().equals("ad")) {
            addDaneshjooFromDars(command.getArgument());
        } else if (command.getCommand().equals("back")) {
            back();
        } else {
            System.out.println("wrong command");
        }
    }

    public void logIn() {
        System.out.println("Log In");
        System.out.print("username: ");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        for (int i = 0; i < cli.users.size(); i++) {
            if (cli.users.get(i).getUsername().equalsIgnoreCase(username)) {
                System.out.print("password: ");
                if (!sc.hasNextInt()) {
                    logIn();
                }
                long password = sc.nextLong();
                if (cli.users.get(i).getPassword() == password) {
                    cli.currentUser = cli.users.get(i);
                    if (!cli.currentUser.getUsername().equalsIgnoreCase("admin")) {
                        System.out.println("welcome " + username);
                        System.out.println("type \"go\" + \"number of your choice\" : ");
                        System.out.println("1- list doroos akhz shode");
                        System.out.println("2- list daneshkadeha va doroos erae shode");
                        cli.permissions.put("go 1", true);
                        cli.permissions.put("go 2", true);
                    } else {
                        cli.permissions.put("go riazi", true);
                        cli.permissions.put("go zabanha", true);
                        cli.permissions.put("go fizik", true);
                        cli.permissions.put("go maaref", true);
                        showDaneshkadeha();
                    }
                    return;
                }
                System.out.println("wrong password");
                cli.currentUser = null;
                return;
            }
        }
        System.out.println("first you have to sign up");
        cli.currentUser = null;
    }

    public void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sign Up");
        System.out.print("username: ");
        String username = sc.nextLine();
        for (int i = 0; i < cli.users.size(); i++) {
            if (cli.users.get(i).getUsername().equalsIgnoreCase(username)) {
                System.out.println("you already signed up");
                return;
            }
        }
        System.out.print("password: ");
        if (!sc.hasNextInt()) {
            System.out.println("please type integer");
            signUp();
        }
        long password = sc.nextLong();
        cli.users.add(new Daneshjoo(username, password));
        cli.currentUser = cli.users.get(cli.users.size() - 1);
        cli.users.get(cli.users.size() - 1).doroosAkhzShode = new ArrayList<>();
        System.out.println("welcome " + username);
        System.out.println("type \"go\" + \"one of these numbers\" :");
        System.out.println("1- list Doroos akhz shode");
        System.out.println("2- list Daneshkadeha va Doroos erae shode");
        cli.permissions.put("go 1", true);
        cli.permissions.put("go 2", true);
    }

    public void showDoroosOfCurrentUser() {
        System.out.println("code/name/ostad/zarfiat/vahed/tarikh emtehan/saat emtehan/rooz class/saat class");
        if (cli.permissions.get("go 1")) {
            cli.setDefaultPermissions();
            for (int i = 0; i < cli.currentUser.doroosAkhzShode.size(); i++) {
                Dars dars = cli.currentUser.doroosAkhzShode.get(i);
                System.out.println(dars.getCode() + "\t\t" + dars.getName() + "\t\t" + dars.getOstad() + "\t\t" +
                        dars.getZarfiatPorShode() + "/" + dars.getZarfiatKol() + "\t\t" + dars.getVahed() + "\t\t1403/" +
                        dars.getMahEmtehan() + "/" + dars.getRoozEmtehan() + "\t\t" + dars.getZamanEmtehan() +
                        "\t\t" + dars.getRoozClass() + "\t\t" + dars.getZamanClass());
            }
            System.out.println();
            if (!cli.currentUser.doroosAkhzShode.isEmpty()) {
                System.out.println("if you want to remove a Dars type \"rd\" + \"code of Dars\"");
            } else {
                System.out.println("empty");
            }
            cli.permissions.put("rd", true);
        } else {
            System.out.println("wrong command");
        }
    }

    public void removeDars(String code) {
        if (cli.permissions.get("rd")) {
            for (int i = 0; i < cli.currentUser.doroosAkhzShode.size(); i++) {
                if ((" " + cli.currentUser.doroosAkhzShode.get(i).getCode()).equals(code)) {
                    Dars wantRemove = cli.currentUser.doroosAkhzShode.get(i);
                    wantRemove.setZarfiatPorShode(wantRemove.getZarfiatPorShode() - 1);
                    System.out.println(wantRemove.getName() + " was successfully removed");
                    cli.currentUser.doroosAkhzShode.remove(i);
                    return;
                }
            }
        } else {
            System.out.println("wrong command");
        }
    }

    public void showDaneshkadeha() {
        if (cli.permissions.get("go 2") || cli.permissions.get("go riazi") || cli.permissions.get("go zabanha") ||
                cli.permissions.get("go fizik") || cli.permissions.get("go maaref")) {
            cli.setDefaultPermissions();
            System.out.println();
            System.out.println("Daneshkadeha:");
            for (int i = 0; i < cli.daneshkadesha.size(); i++) {
                System.out.println("-" + cli.daneshkadesha.get(i).getName());
            }
            System.out.println("type \"go\" + \"name of Daneshkade\" to show Doroos");
            cli.permissions.put("go riazi", true);
            cli.permissions.put("go zabanha", true);
            cli.permissions.put("go fizik", true);
            cli.permissions.put("go maaref", true);
        } else {
            System.out.println("wrong command");
        }
    }

    public void showDoroosOfDaneshkade(String name) {
        if (cli.permissions.get("go riazi") || cli.permissions.get("go zabanha") ||
                cli.permissions.get("go fizik") || cli.permissions.get("go maaref")) {
            cli.setDefaultPermissions();
            cli.backToDars = name;
            System.out.println("Doroos" + name + ":");
            System.out.println("code/name/ostad/zarfiat/vahed/tarikh emtehan/saat emtehan/rooz class/saat class");
            for (int i = 0; i < cli.doroos.size(); i++) {
                if ((" " + cli.doroos.get(i).getDaneshkade()).equals(name)) {
                    Dars dars = cli.doroos.get(i);
                    System.out.println(dars.getCode() + "\t\t" + dars.getName() + "\t\t" + dars.getOstad() + "\t\t" +
                            dars.getZarfiatPorShode() + "/" + dars.getZarfiatKol() + "\t\t" + dars.getVahed() +
                            "\t\t1403/" + dars.getMahEmtehan() + "/" + dars.getRoozEmtehan() + "\t\t" +
                            dars.getZamanEmtehan() + "\t\t" + dars.getRoozClass() + "\t\t" + dars.getZamanClass());
                }
            }
            if (!cli.currentUser.getUsername().equalsIgnoreCase("admin")) {
                System.out.println("if you want to take a Dars type \"td\" + \"code of Dars\"");
                cli.permissions.put("td", true);
            } else {
                System.out.println("choose one and type \"command\" + \"code of Dars\" :");
                System.out.println("-rdd: remove Dars from Daneshkade");
                System.out.println("-add: add Dars to Daneshkade");
                System.out.println("-iz: increase zarfiat");
                System.out.println("-sdd: show Daneshjoo of Dars");
                cli.permissions.put("rdd", true);
                cli.permissions.put("add", true);
                cli.permissions.put("iz", true);
                cli.permissions.put("sdd", true);
            }
        } else {
            System.out.println("wrong command");
        }
    }

    public void takeDars(String code) {
        if (cli.permissions.get("td")) {
            Dars wantTake = cli.getDarsWithCode(code);
            if (wantTake == null) {
                return;
            }
            if (cli.isTakenDars(wantTake)) {
                System.out.println("Dars already was taken");
                return;
            }
            if (wantTake.getZarfiatPorShode() == wantTake.getZarfiatKol()) {
                System.out.println("can't take Dars because it's full");
                return;
            }
            if (cli.numberOfCurrentUserVahed() + wantTake.getVahed() > 20) {
                System.out.println("can't take more than 20 vahed");
                return;
            }
            if (!wantTake.isTakhasosi() &&
                    cli.numberOfOmoomiDarsOfCurrentUserVahed() + wantTake.getVahed() > 5) {
                System.out.println("can't take more than 5 vahed of omoomi Dars");
                return;
            }
            if (tadakhol(wantTake)) {
                return;
            }
            System.out.println(wantTake.getName() + " was taken successfully");
            wantTake.setZarfiatPorShode(wantTake.getZarfiatPorShode() + 1);
            cli.currentUser.doroosAkhzShode.add(wantTake);
        } else {
            System.out.println("wrong command");
        }
    }

    public void removeDarsFromDaneshkade(String code) {
        if (cli.permissions.get("rdd")) {
            for (int i = 0; i < cli.doroos.size(); i++) {
                if ((" " + cli.doroos.get(i).getCode()).equals(code)) {
                    System.out.println(cli.doroos.get(i).getName() + " was successfully removed from Daneshkade");
                    cli.doroos.remove(i);
                    break;
                }
            }
            for (int i = 0; i < cli.daneshkadesha.size(); i++) {
                for (int j = 0; j < cli.daneshkadesha.get(i).doroosEraeShode.size(); j++) {
                    if ((" " + cli.daneshkadesha.get(i).doroosEraeShode.get(j).getCode()).equals(code)) {
                        cli.daneshkadesha.get(i).doroosEraeShode.remove(j);
                        break;
                    }
                }
            }
            for (int i = 0; i < cli.users.size(); i++) {
                if (cli.users.get(i).doroosAkhzShode != null) {
                    for (int j = 0; j < cli.users.get(i).doroosAkhzShode.size(); j++) {
                        if ((" " + cli.users.get(i).doroosAkhzShode.get(j).getCode()).equals(code)) {
                            cli.users.get(i).doroosAkhzShode.remove(j);
                            return;
                        }
                    }
                }
            }
        } else {
            System.out.println("wrong command");
        }
    }

    public void addDarsToDaneshkade() {
        if (cli.permissions.get("add")) {
            File file = new File("NewDars.txt");
            Scanner sc;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (sc.hasNextLine()) {
                cli.doroos.add(new Dars(sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt(), sc.nextInt(),
                        sc.nextInt(), sc.nextInt(), sc.next(), sc.next(), sc.next(), sc.nextBoolean(), sc.next()));
                String garbage = sc.nextLine();
                Dars newDars = cli.doroos.get(cli.doroos.size() - 1);
                for (int i = 0; i < cli.daneshkadesha.size(); i++) {
                    if (cli.daneshkadesha.get(i).getName().equals(newDars.getDaneshkade())) {
                        cli.daneshkadesha.get(i).doroosEraeShode.add(newDars);
                        break;
                    }
                }
                System.out.print(newDars.getName() + ",");
            }
            System.out.println(" was added successfully");
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.flush();
                printWriter.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("wrong command");
        }
    }

    public void increaseZarfiat(String code) {
        if (cli.permissions.get("iz")) {
            System.out.print("number of increase: ");
            Scanner sc = new Scanner(System.in);
            if (!sc.hasNextInt()) {
                if (sc.next().equals("back")){
                    return;
                }
                System.out.println("please type integer");
                increaseZarfiat(code);
            }
                int increaseZarfiat = sc.nextInt();
                for (int i = 0; i < cli.doroos.size(); i++) {
                    if ((" " + cli.doroos.get(i).getCode()).equals(code)) {
                        System.out.println(cli.doroos.get(i).getName() + " was successfully increase " + increaseZarfiat);
                        cli.doroos.get(i).setZarfiatKol(cli.doroos.get(i).getZarfiatKol() + increaseZarfiat);
                        return;
                    }
                }
        } else {
            System.out.println("wrong command");
        }
    }

    public void showDaneshjooOfDars(String code) {
        if (cli.permissions.get("sdd")) {
            cli.setDefaultPermissions();
            cli.codeForOmitOrAdd = cli.getDarsWithCode(code).getCode();
            System.out.println("Dars " + cli.getDarsWithCode(code).getName() + ":");
            q:
            for (int i = 0; i < cli.users.size(); i++) {
                if (cli.users.get(i).doroosAkhzShode != null) {
                    for (int j = 0; j < cli.users.get(i).doroosAkhzShode.size(); j++) {
                        if ((" " + cli.users.get(i).doroosAkhzShode.get(j).getCode()).equals(code)) {
                            System.out.println("-" + cli.users.get(i).getUsername());
                            break q;
                        }
                    }
                }
            }
            System.out.println("if you want to omit a Daneshjoo type \"od\" + \"username\"");
            System.out.println("or want to add a Daneshjoo type \"ad\" + \"username\"");
            cli.permissions.put("od", true);
            cli.permissions.put("ad", true);
        } else {
            System.out.println("wrong command");
        }
    }

    public void omitDaneshjooFromDars(String username) {
        if (cli.permissions.get("od")) {
            for (int i = 0; i < cli.doroos.size(); i++) {
                if (cli.doroos.get(i).getCode() == cli.codeForOmitOrAdd) {
                    cli.doroos.get(i).setZarfiatPorShode(cli.doroos.get(i).getZarfiatPorShode() - 1);
                    break;
                }
            }
            for (int i = 0; i < cli.users.size(); i++) {
                if ((" " + cli.users.get(i).getUsername()).equals(username)) {
                    for (int j = 0; j < cli.users.get(i).doroosAkhzShode.size(); j++) {
                        if (cli.users.get(i).doroosAkhzShode.get(j).getCode() == cli.codeForOmitOrAdd) {
                            System.out.println(username + " was omitted successfully from " +
                                    cli.getDarsWithCode(cli.codeForOmitOrAdd).getName());
                            cli.users.get(i).doroosAkhzShode.remove(j);
                            return;
                        }
                    }
                }
            }
        } else {
            System.out.println("wrong command");
        }
    }

    public void addDaneshjooFromDars(String username) {
        int code = 0;
        if (cli.permissions.get("ad")) {
            for (int i = 0; i < cli.doroos.size(); i++) {
                if (cli.doroos.get(i).getCode() == cli.codeForOmitOrAdd) {
                    cli.doroos.get(i).setZarfiatPorShode(cli.doroos.get(i).getZarfiatPorShode() + 1);
                    code = cli.doroos.get(i).getCode();
                    break;
                }
            }
            for (int i = 0; i < cli.users.size(); i++) {
                if ((" " + cli.users.get(i).getUsername()).equals(username)) {
                    cli.users.get(i).doroosAkhzShode.add(cli.getDarsWithCode(code));
                    System.out.println(username + " was successfully added");
                    return;
                }
            }
        } else {
            System.out.println("wrong command");
        }
    }

    public void back() {
        if (cli.permissions.get("rd") || cli.permissions.get("go riazi")) {
            cli.setDefaultPermissions();
            System.out.println("welcome " + cli.currentUser.getUsername());
            System.out.println("type \"go\" + \"number of your choice\" : ");
            System.out.println("1- list doroos akhz shode");
            System.out.println("2- list daneshkadeha va doroos erae shode");
            cli.permissions.put("go 1", true);
            cli.permissions.put("go 2", true);
        } else if (cli.permissions.get("td") || cli.permissions.get("rdd")) {
            cli.setDefaultPermissions();
            System.out.println("Daneshkadeha:");
            for (int i = 0; i < cli.daneshkadesha.size(); i++) {
                System.out.println("-" + cli.daneshkadesha.get(i).getName());
            }
            System.out.println("type \"go\" + \"name of Daneshkade\" to show Doroos");
            cli.permissions.put("go riazi", true);
            cli.permissions.put("go zabanha", true);
            cli.permissions.put("go fizik", true);
            cli.permissions.put("go maaref", true);
        } else if (cli.permissions.get("od")) {
            cli.setDefaultPermissions();
            System.out.println("Doroos" + cli.backToDars + ":");
            for (int i = 0; i < cli.doroos.size(); i++) {
                if ((" " + cli.doroos.get(i).getDaneshkade()).equals(cli.backToDars)) {
                    Dars dars = cli.doroos.get(i);
                    System.out.println(dars.getCode() + "   " + dars.getName() + "\t" + dars.getOstad() + "\t" +
                            dars.getZarfiatPorShode() + "/" + dars.getZarfiatKol() + "\t\t " + dars.getVahed() +
                            "\t\t  1403/" + dars.getMahEmtehan() + "/" + dars.getRoozEmtehan() + "\t\t\t  " +
                            dars.getZamanEmtehan() + "\t\t\t" + dars.getRoozClass() + "\t\t" + dars.getZamanClass());
                }
            }
            if (!cli.currentUser.getUsername().equalsIgnoreCase("admin")) {
                System.out.println("if you want to take a Dars type \"td\" + \"code of Dars\"");
                cli.permissions.put("td", true);
            } else {
                System.out.println("choose one and type \"command\" + \"code of Dars\" :");
                System.out.println("-rdd: remove Dars from Daneshkade");
                System.out.println("-add: add Dars to Daneshkade");
                System.out.println("-iz: increase zarfiat");
                System.out.println("-sdd: show Daneshjoo of Dars");
                cli.permissions.put("rdd", true);
                cli.permissions.put("add", true);
                cli.permissions.put("iz", true);
                cli.permissions.put("sdd", true);
            }
        }
    }

    public boolean tadakhol(Dars wantTake) {
        boolean is = false;
        for (int i = 0; i < cli.currentUser.doroosAkhzShode.size(); i++) {
            String rooz = cli.currentUser.doroosAkhzShode.get(i).getRoozClass();
            if (rooz.contains("shanbeh") && wantTake.getRoozClass().contains("shanbeh")) {
                is = true;
                break;
            } else if (rooz.contains("yekshanbe") && wantTake.getRoozClass().contains("yekshanbe")) {

            } else if (rooz.contains("doshanbe") && wantTake.getRoozClass().contains("doshanbe")) {
                is = true;
                break;
            } else if (rooz.contains("seshanbe") && wantTake.getRoozClass().contains("seshanbe")) {
                is = true;
                break;
            } else if (rooz.contains("charshanbe") && wantTake.getRoozClass().contains("charshanbe")) {
                is = true;
                break;
            }
        }
        if (is) {
            for (int i = 0; i < cli.currentUser.doroosAkhzShode.size(); i++) {
                String zaman = cli.currentUser.doroosAkhzShode.get(i).getZamanClass();
                int saatStart1 = (zaman.charAt(0) - 48) * 10 + zaman.charAt(1) - 48;
                int daghigheStart1 = (zaman.charAt(3) - 48) * 10 + zaman.charAt(4) - 48;
                int saatStart2 = (wantTake.getZamanClass().charAt(0) - 48) * 10 + wantTake.getZamanClass().charAt(1) - 48;
                int daghigheStart2 = (wantTake.getZamanClass().charAt(3) - 48) * 10 + wantTake.getZamanClass().charAt(4) - 48;
                int saatEnd1 = (zaman.charAt(6) - 48) * 10 + zaman.charAt(7) - 48;
                int daghigheEnd1 = (zaman.charAt(9) - 48) * 10 + zaman.charAt(10) - 48;
                int saatEnd2 = (wantTake.getZamanClass().charAt(6) - 48) * 10 + wantTake.getZamanClass().charAt(7) - 48;
                int daghigheEnd2 = (wantTake.getZamanClass().charAt(9) - 48) * 10 + wantTake.getZamanClass().charAt(10) - 48;
                if ((saatStart1 < saatEnd2) || (saatStart1 == saatEnd2 && daghigheStart1 < daghigheEnd2)) {
                    if ((saatStart2 < saatEnd1) || (saatStart2 == saatEnd1 && daghigheStart2 < daghigheEnd1)) {
                        System.out.println("tadakhol class");
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < cli.currentUser.doroosAkhzShode.size(); i++) {
            if (cli.currentUser.doroosAkhzShode.get(i).getRoozEmtehan() == wantTake.getRoozEmtehan()) {
                if (cli.currentUser.doroosAkhzShode.get(i).getMahEmtehan() == wantTake.getMahEmtehan()) {
                    if (cli.currentUser.doroosAkhzShode.get(i).getZamanEmtehan().equals(wantTake.getZamanEmtehan())) {
                        System.out.println("tadakhol emtehan");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}