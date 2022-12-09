import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter the string> ");
//        String string = input.nextLine();
//        Dictionary t1 = new Dictionary();
//        System.out.println("Dictionary created successfully.");
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the file name> ");
            String fileName = input.nextLine();
            File file = new File(fileName);
            Dictionary t1 = new Dictionary(file);
            System.out.println("Dictionary loaded successfully.");
            System.out.println("Enter the number which corresponds to the operation you want.");
            System.out.println("1. Insert a word\n2. Delete a word\n3. Search for a word\n4. Print the dictionary\n5. Exit");
            int choice = input.nextInt();
            while (choice != 5) {
                switch (choice) {
                    case 1 -> {
                        System.out.println("add new word> ");
                        String word0 = input.next();
                        t1.addWord(word0);
                        System.out.println("Word added successfully.");
                    }
                    case 2 -> {
                        System.out.println("remove word> ");
                        String word1 = input.next();
                        t1.deleteWord(word1);
                        System.out.println("Word removed successfully.");
                    }
                    case 3 -> {
                        System.out.println("check word> ");
                        String word2 = input.next();
                        if (t1.searchWord(word2)) {
                            System.out.println("Word found.");
                        } else {
                            System.out.println("Word not found.");
                        }
                    }
                    case 4 -> t1.printDictionary();
                    default -> System.out.println("Invalid choice.");
                }
                System.out.println("\nEnter the number which corresponds to the operation you want.");
                System.out.println("1. Insert a word\n2. Delete a word\n3. Search for a word\n4. Print the dictionary\n5. Exit");
                choice = input.nextInt();
            }
            System.out.println("Height: "+t1.getHeight());
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("******Error: file not found.******");
        }

        catch (WordAlreadyExistsException e) {
            e.printStackTrace();
            System.out.println("******Error: word already exists.******");
        }

        catch (WordNotFoundException e) {
            e.printStackTrace();
            System.out.println("******Error: word not found in dictionary.******");
        }

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("******Unknown error.******");
        }
    }
}