package LabProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner userInput;

    public static void main(String[] args) {
        userInput = new Scanner(System.in);
        System.out.println(
                        "1. Create a dictionary and initialize it with a text file\n" +
                        "2. Create a dictionary and initialize it with a given single word\n" +
                        "3. Create an empty dictionary\n" +
                        "To quit, enter any other key.");
        System.out.print("\nEnter your choice> ");

        String choice = userInput.next();

        switch (choice) {
            case "1" -> fileDictionaryTest();
            case "2" -> oneWordDictionaryTest();
            case "3" -> emptyDictionaryTest();
            default -> userInput.close(); // No need to keep the scanner object alive anymore
        }
    }

    public static void fileDictionaryTest() {
        try {
            userInput = new Scanner(System.in);
            System.out.print("\nEnter the file name> ");
            Dictionary testDictionary = new Dictionary(new File(userInput.nextLine()));
            System.out.println("Dictionary loaded successfully.");

            dictionaryManipulationTest(testDictionary);
        }

        catch (FileNotFoundException e) {
            System.out.println("******Exception: file not found and/or inaccessible.******");
            fileDictionaryTest();
        }

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("******Exception: unknown error.******");
        }
    }

    public static void oneWordDictionaryTest() {
        userInput = new Scanner(System.in);
        System.out.print("\nEnter a word for the dictionary to be initialized with> ");
        Dictionary testDictionary = new Dictionary(userInput.next());
        System.out.println("Dictionary created and initialized successfully.");

        dictionaryManipulationTest(testDictionary);
    }

    public static void emptyDictionaryTest() {
            Dictionary testDictionary = new Dictionary();
            System.out.println("\nEmpty dictionary created successfully.");

            dictionaryManipulationTest(testDictionary);
    }

    public static void dictionaryManipulationTest(Dictionary dictionary) {
        try {
            userInput = new Scanner(System.in);
            System.out.println("\n" +
                    "1. Insert a word\n" +
                    "2. Delete a word\n" +
                    "3. Search for a word\n" +
                    "4. Search for similar words\n" +
                    "5. Print the dictionary\n" +
                    "6. Save the dictionary to a new text file\n" +
                    "7. Exit");
            System.out.print("\nEnter the number which corresponds to the operation you want> ");
            int choice = userInput.nextInt();

            while (choice != 7) {
                switch (choice) {
                    case 1 -> {
                        System.out.print("add new word> ");
                        dictionary.addWord(userInput.next());
                        System.out.println("Word added successfully.");
                    }

                    case 2 -> {
                        System.out.print("remove word> ");
                        dictionary.deleteWord(userInput.next());
                        System.out.println("Word removed successfully.");
                    }

                    case 3 -> {
                        System.out.print("check word> ");

                        if (dictionary.searchWord(userInput.next()))
                            System.out.println("Word found.");

                        else
                            System.out.println("Word not found.");
                    }
                    case 4 -> {
                        System.out.print("search for similar words> ");
                        System.out.println(dictionary.findSimilar(userInput.next()));
                    }

                    case 5 ->
                            dictionary.printDictionary();

                    case 6 -> {
                        System.out.print("Save Updated Dictionary (Y/N)> ");
                        String saveChoice = userInput.next();

                        while (!saveChoice.equals("Y") && !saveChoice.equals("N")) {
                            System.out.println("Invalid saving choice - Please either enter Y or N!");
                            System.out.print("Save Updated Dictionary (Y/N)> ");
                            saveChoice = userInput.next();
                        }

                        if (saveChoice.equals("Y")) {
                            userInput = new Scanner(System.in); // Re-instantiate scanner to avoid errors, usually this is needed before using nextLine on a global object.
                            System.out.print("Enter filename> ");
                            dictionary.exportDictionary(userInput.nextLine());
                            System.out.println("Dictionary saved successfully.");
                        }
                    }
                    default -> System.out.println("Invalid choice.");
                }

                System.out.println("\n" +
                        "1. Insert a word\n" +
                        "2. Delete a word\n" +
                        "3. Search for a word\n" +
                        "4. Search for similar words\n" +
                        "5. Print the dictionary\n" +
                        "6. Save the dictionary to a new text file\n" +
                        "7. Exit");
                System.out.print("\nEnter the number which corresponds to the operation you want> ");

                choice = userInput.nextInt();
                if (choice == 7) userInput.close(); // No need to keep the scanner object alive anymore
            }
        }

        catch (WordAlreadyExistsException e) {
            System.out.println("******Exception: word already exists.******");
            dictionaryManipulationTest(dictionary);
        }

        catch (WordNotFoundException e) {
            System.out.println("******Exception: word not found in dictionary.******");
            dictionaryManipulationTest(dictionary);
        }

        catch (InputMismatchException e) { // For when the user choice is not an integer value
            System.out.println("******Exception: your choice is not an integer value.******");
            dictionaryManipulationTest(dictionary);
        }

        catch (FileNotFoundException e) {
            System.out.println("******Exception: file not found and/or inaccessible.******");
            dictionaryManipulationTest(dictionary);
        }

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("******Exception: unknown error.******");
        }
    }
}