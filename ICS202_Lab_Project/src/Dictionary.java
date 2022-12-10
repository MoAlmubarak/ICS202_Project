
import java.awt.dnd.InvalidDnDOperationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Dictionary extends AVLTree<String> {

    // creates a dictionary with only 1 string s.
    public Dictionary(String s) {
        super(new BSTNode<String>(s));
    }

    // creates an empty dictionary [public Dictionary( )]
    public Dictionary() {
        super();
    }

    // create a text file having strings, each on a new line.
    public Dictionary(File f) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNextLine())
            this.insertAVL(fileScanner.nextLine());
        fileScanner.close();
    }

    // Add new word, This method adds a new word (string) to the existing dictionary
    // if the word is already in the dictionary, it should throw an exception.
    public void addWord(String s) throws InvalidDnDOperationException {
        if (this.search(s) == null)
            this.insertAVL(s);
        else
            throw new WordAlreadyExistsException("Word already exists");
    }

    // Search for word, This method searches for a word in the existing dictionary
    // if the word is found, it should return true, otherwise it should return false.
    public boolean searchWord(String s) {
        return this.search(s) != null;
    }

    // Delete word This method deletes a word (string) from the existing dictionary, if the word is not in the dictionary,
    // it should throw an exception.
    public void deleteWord(String s) throws InvalidDnDOperationException {
        if (this.search(s) == null)
            throw new WordNotFoundException("Word does not exist");
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

    // helper method for finding the similar words.
    public void findSimilar (String s) {
        this.findSimilar (s, this.root);
    }

    // method finds the similar words in the dictionary, and prints them.
    private void findSimilar (String s, BSTNode<String> node) {
        if (node == null)
            return;
        // search in the left subtree.
        findSimilar (s, node.left);

        if (node.el.length() == s.length()) {
            int differentLetter = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != node.el.charAt(i))
                    differentLetter++;
            }
            if (differentLetter == 1)
                System.out.println(node.el);
        }
        // To search for similar words which differ by 1 letter,
        // in case the length of the input word greater then the similar word by 1
        else if ((node.el.length()+1) == s.length()) {
            int differentLetter = 0;
            for (int i = 0; i < (s.length()-1); i++) {
                if (s.charAt(i) != node.el.charAt(i)) {
                    differentLetter++;
                    for(int j = i; j < (s.length()-1); j++) {
                        if (s.charAt(j+1) != node.el.charAt(j)){
                            differentLetter++;
                            break;
                        }
                    }
                    break;
                }
            }
            if (differentLetter == 1 || differentLetter == 0)
                System.out.println(node.el);
        }
        // To search for similar words which differ by 1 letter, in case the length of the input word
        // less then the similar word by 1
        else if (node.el.length() == (s.length()+1)) {
            int differentLetter = 0;
            for (int i = 0; i < (s.length()); i++) {
                if (s.charAt(i) != node.el.charAt(i)) {
                    differentLetter++;
                    for(int j = i; j < (s.length()); j++) {
                        if (s.charAt(j) != node.el.charAt(j+1)){
                            differentLetter++;
                            break;
                        }
                    }
                    break;
                }
            }
            if (differentLetter == 1 || differentLetter == 0)
                System.out.println(node.el);
        }
        // search in the right subtree.
        findSimilar (s, node.right);
    }

    // save the dictionary in a new text file, using in-order traversing of the dictionary
    public void exportDictionary(String fileName) throws FileNotFoundException {
        PrintWriter file = new PrintWriter(fileName);
        exportDictionaryAuxiliary(this.root, file);
        file.close();
    }

    // recursive helper method for the above method (traversing the dictionary in-order)
    public void exportDictionaryAuxiliary(BSTNode<String> p, PrintWriter file) {
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
