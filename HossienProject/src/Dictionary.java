import java.awt.dnd.InvalidDnDOperationException;
import java.io.*;
import java.util.Scanner;

public class Dictionary extends AVLTree<String> {

    // creates a dictionary with only 1 string s.
    public Dictionary(String s) {
        super(new BSTNode<>(s));
        height = 0;
    }

    // creates an empty dictionary [public Dictionary( )]
    public Dictionary() {
        super();
        height = -1;
    }

    // create a text file having strings, each on a new line.
    public Dictionary(File f) {
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                this.insertAVL(s);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Add new word, This method adds a new word (string) to the existing dictionary
    // if the word is already in the dictionary, it should throw an exception.
    public void addWord(String s) {
        if (this.search(s) == null) {
            this.insertAVL(s);
        } else {
            throw new InvalidDnDOperationException("Word already exists");
        }
    }

    // Search for word, This method searches for a word in the existing dictionary
    // if the word is found, it should return true, otherwise it should return false.
    public boolean searchWord(String s) {
        return this.search(s) != null;
    }

    // Delete word This method deletes a word (string) from the existing dictionary, if the word is not in the dictionary,
    // it should throw an exception.
    public void deleteWord(String s) throws InvalidDnDOperationException {
        if (this.search(s) == null) {
            throw new InvalidDnDOperationException("Word does not exist");
        } else {
            this.deleteAVL(s);
        }
    }

    // print the dictionary in sorted order.
    public void printDictionary() {
        inorder();
    }
}
