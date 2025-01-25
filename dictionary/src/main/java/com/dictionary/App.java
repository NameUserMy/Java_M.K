package com.dictionary;

import java.io.PrintStream;
import java.util.*;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        Dictionary dic = new Dictionary();
        Scanner sc = new Scanner(System.in);
        String[] menuItem = {
                "1 - Знайти за англ. словом",
                "2 - Знайти за укр. словом",
                "3 - Додати запис",
                "0 - Вийти"
        };

        for (int i = 0; i < menuItem.length; i++) {
            System.out.println("+---------------------------+");
            System.out.println("  " + menuItem[i]);
        }

        int choice;
        do {
            System.out.print("$> ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Знайти за англ. словом $> ");
                    sc.reset().nextLine();
                    String engWordSearch = sc.nextLine();
                    System.out.printf("%s%s", dic.searchWord(engWordSearch), "\n");
                    break;
                case 2:
                    System.out.print("Знайти за укр. словом $> ");
                    sc.reset().nextLine();
                    String uaWordSearch = sc.nextLine();
                    System.out.printf("%s%s", dic.searchWord(uaWordSearch), "\n");
                    break;
                case 3:
                    System.out.print("Додати англ. слово $> ");
                    sc.reset().nextLine();
                    String engWord = sc.nextLine();
                    System.out.print("Додати  укр. слово $> ");
                    String uaWord = sc.nextLine();
                    System.out.printf("%s%s", dic.addTranslation(engWord, uaWord), "\n");
                    break;
                default:
                    choice = 0;
                    return;
            }
        } while (choice != 0);
    }
}
