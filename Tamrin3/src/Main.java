public class Main {
    public static void main(String[] args) {
        MyMap<String, Integer> grades = new MyMap<>();
        MyMap<String, Integer> grades2 = new MyMap<>();

        grades.put("parsa", 20);
        grades.put("mandool", 7);
        grades.put("seyed", 16);


        grades.put("parsa", 19);
        System.out.println(grades.size());
        System.out.println(grades.remove("parsa"));
        System.out.println(grades.size());
        System.out.println(grades.indexOf("seyed"));
        System.out.println(grades.get("parsa"));

    }
}
