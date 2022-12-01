import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the file name> ");
        String fileName = input.nextLine();
        try {
            File file = new File(fileName);
            Dictionary dictionary = new Dictionary(file);
            System.out.println("Dictionary loaded successfully.");
            System.out.println("Enter the number which corresponds to the operation you want.");
            System.out.println("1. Insert a word\n2. Delete a word\n3. Search for a word\n4. Print the dictionary\n5. Exit");
            int choice = input.nextInt();
            while (choice != 5) {
                switch (choice) {
                    case 1 -> {
                        System.out.println("add new word> ");
                        String word0 = input.next();
                        dictionary.addWord(word0);
                        System.out.println("Word added successfully.");
                    }
                    case 2 -> {
                        System.out.println("remove word> ");
                        String word1 = input.next();
                        dictionary.deleteWord(word1);
                        System.out.println("Word removed successfully.");
                    }
                    case 3 -> {
                        System.out.println("check word> ");
                        String word2 = input.next();
                        if (dictionary.searchWord(word2)) {
                            System.out.println("Word found.");
                        } else {
                            System.out.println("Word not found.");
                        }
                    }
                    case 4 -> dictionary.printDictionary();
                    default -> System.out.println("Invalid choice.");
                }
                System.out.println("\nEnter the number which corresponds to the operation you want.");
                System.out.println("1. Insert a word\n2. Delete a word\n3. Search for a word\n4. Print the dictionary\n5. Exit");
                choice = input.nextInt();
            }
            System.out.println("Height: "+dictionary.getHeight());
            System.out.println("Root: "+dictionary.root.el);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}