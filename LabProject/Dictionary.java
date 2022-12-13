package LabProject;

import java.awt.dnd.InvalidDnDOperationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Dictionary extends AVLTree<String> {

    // creates a dictionary with only one string
    public Dictionary(String s) {
        super(new BSTNode<String>(s));
    }

    // creates an empty dictionary
    public Dictionary() {
        super();
    }

    // create dictionary from a text file having strings, each on a new line
    public Dictionary(File f) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNextLine())
            this.insertAVL(fileScanner.nextLine());
        fileScanner.close();
    }

    // Add a new word. This method adds a new word (string) to the existing dictionary
    // If the word is already in the dictionary, it should throw an exception
    public void addWord(String s) throws WordAlreadyExistsException {
        if (this.search(s) == null)
            this.insertAVL(s);
        else
            throw new WordAlreadyExistsException("Word already exists.");
    }

    // Search for word. This method searches for a word in the existing dictionary
    // If the word is found, it returns true, otherwise it returns false
    public boolean searchWord(String s) {
        return this.search(s) != null;
    }

    // Delete word. This method deletes a word (string) from the existing dictionary.
    // If the word is not in the dictionary, it should throw an exception.
    public void deleteWord(String s) throws WordNotFoundException {
        if (this.search(s) == null)
            throw new WordNotFoundException("Word does not exist.");
        else
            this.deleteAVL(s);
    }

    // print the dictionary in sorted order.
    public void printDictionary() {
        if (this.isEmpty())
            System.out.println("The dictionary is empty.");

        else {
            System.out.println();
            this.inorder();
            System.out.println();
        }
    }

    // method for finding the similar words.
    public String findSimilar (String s) {
        Stack<String> similarWordsStack = new Stack<String>();
        Stack<String> flippedStack = new Stack<String>(); // Flipping the stack to preserve the original word order.
        this.findSimilar(s, this.root, similarWordsStack);

        while (!similarWordsStack.isEmpty())
            flippedStack.push(similarWordsStack.pop());

        String formattedString = flippedStack.pop();
        while (!flippedStack.isEmpty())
            formattedString += ", " + flippedStack.pop();
        formattedString += ".";

        return formattedString;
    }

    // helper method that finds similar words in the dictionary, and stores them in the given stack.
    private void findSimilar (String s, BSTNode<String> node, Stack stack) {
        if (node == null)
            return;

        // search in the left subtree.
        findSimilar (s, node.left, stack);

        if (node.el.length() == s.length()) {
            int differentLetter = 0;

            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) != node.el.charAt(i))
                    differentLetter++;

            if (differentLetter == 1)
                stack.push(node.el);
        }

        /* To search for similar words which differ by 1 letter, in case the length of the current node's word
           is greater than the given word by 1 */
        else if ((node.el.length() + 1) == s.length()) {
            int differentLetter = 0;
            for (int i = 0; i < (s.length() - 1); i++) {
                if (s.charAt(i) != node.el.charAt(i)) {
                    differentLetter++;

                    for (int j = i; j < (s.length() - 1); j++) {
                        if (s.charAt(j + 1) != node.el.charAt(j)) {
                            differentLetter++;
                            break;
                        }
                    }
                    break;
                }
            }
            if (differentLetter == 1 || differentLetter == 0)
                stack.push(node.el);
        }

        /* To search for similar words which differ by 1 letter, in case the length of the current node's word
           is less than the given word by 1 */
        else if (node.el.length() == (s.length() + 1)) {
            int differentLetter = 0;
            for (int i = 0; i < (s.length()); i++) {
                if (s.charAt(i) != node.el.charAt(i)) {
                    differentLetter++;

                    for (int j = i; j < (s.length()); j++) {
                        if (s.charAt(j) != node.el.charAt(j+1)){
                            differentLetter++;
                            break;
                        }
                    }
                    break;
                }
            }

            if (differentLetter == 1 || differentLetter == 0)
                stack.push(node.el);
        }

        // search in the right subtree.
        findSimilar (s, node.right, stack);
    }

    // save the dictionary in a new text file, using in-order traversing of the dictionary
    public void exportDictionary(String fileName) throws FileNotFoundException {
        PrintWriter file = new PrintWriter(fileName);
        exportDictionaryAuxiliary(this.root, file);
        file.close();
        }

    // recursive helper method for the above method (traversing the dictionary in-order)
    private void exportDictionaryAuxiliary(BSTNode<String> p, PrintWriter file) {
        if (p != null) {
            exportDictionaryAuxiliary(p.left, file);
            file.print(p.el + "\n");
            exportDictionaryAuxiliary(p.right, file);
        }
    }
}

class WordAlreadyExistsException extends InvalidDnDOperationException {
    public WordAlreadyExistsException(String message) {
        super(message);
    }
}

class WordNotFoundException extends InvalidDnDOperationException {
    public WordNotFoundException(String message) {
        super(message);
    }
}
