import java.awt.dnd.InvalidDnDOperationException;
import java.io.*;
import java.util.Scanner;

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {

    protected int height;

    public AVLTree() {
        super();
        height = -1;
    }

    public AVLTree(BSTNode<T> root) {
        super(root);
        height = -1;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BSTNode<T> node) {
        if (node == null)
            return -1;
        else
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private AVLTree<T> getLeftAVL() {
        AVLTree<T> leftsubtree = new AVLTree<T>(root.left);
        return leftsubtree;
    }

    private AVLTree<T> getRightAVL() {
        AVLTree<T> rightsubtree = new AVLTree<T>(root.right);
        return rightsubtree;
    }

    protected int getBalanceFactor() {
        if (isEmpty())
            return 0;
        else
            return getRightAVL().getHeight() - getLeftAVL().getHeight();
    }

    public void insertAVL(T el) {
        super.insert(el);
        this.balance();
    }

    public void deleteAVL(T el) {
        // Q1
        super.deleteByCopying(el);
        this.balance();
    }

    protected void balance() {
        if (!isEmpty()) {
            getLeftAVL().balance();
            getRightAVL().balance();

            adjustHeight();

            int balanceFactor = getBalanceFactor();

            if (balanceFactor == -2) {
                System.out.println("Balancing node with el: " + root.el);
                if (getLeftAVL().getBalanceFactor() < 0)
                    rotateRight();
                else
                    rotateLeftRight();
            } else if (balanceFactor == 2) {
                System.out.println("Balancing node with el: " + root.el);
                if (getRightAVL().getBalanceFactor() > 0)
                    rotateLeft();
                else
                    rotateRightLeft();
            }
        }
    }

    protected void adjustHeight() {
        if (isEmpty())
            height = -1;
        else
            height = 1 + Math.max(getLeftAVL().getHeight(), getRightAVL().getHeight());
    }

    protected void rotateRight() {
        System.out.println("RIGHT ROTATION");
        // Q1
        BSTNode<T> tempNode = root.right;
        root.right = root.left;
        root.left = root.right.left;
        root.right.left = root.right.right;
        root.right.right = tempNode;

        T val = (T) root.el;
        root.el = root.right.el;
        root.right.el = val;

        getRightAVL().adjustHeight();
        adjustHeight();
    }

    protected void rotateLeft() {
        System.out.println("LEFT ROTATION");
        BSTNode<T> tempNode = root.left;
        root.left = root.right;
        root.right = root.left.right;
        root.left.right = root.left.left;
        root.left.left = tempNode;

        T val = (T) root.el;
        root.el = root.left.el;
        root.left.el = val;

        getLeftAVL().adjustHeight();
        adjustHeight();
    }

    protected void rotateLeftRight() {
        System.out.println("Double Rotation...");
        // Q1
        getLeftAVL().rotateLeft();
        getLeftAVL().adjustHeight();
        this.rotateRight();
        this.adjustHeight();

    }

    protected void rotateRightLeft() {
        System.out.println("Double Rotation...");
        getRightAVL().rotateRight();
        getRightAVL().adjustHeight();
        this.rotateLeft();
        this.adjustHeight();
    }

    // creates a dictionary with only 1 string s.
    public void Dictionary(String s) {
        root = new BSTNode<T>((T) s);
        height = 0;
    }

    // creates an empty dictionary [public Dictionary( )]
    public void Dictionary() {
        root = null;
        height = -1;
    }

    // create a text file having strings, each on a new line. [public Dictionary(File f)]
    public void Dictionary(File f) {
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                this.insertAVL((T) s);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Add new word, This method adds a new word (string) to the existing dictionary
    // [public void addWord(String s)], if the word is already present, it should throw
    // an exception.
    public void addWord(String s) {
        if (this.search((T) s) == null) {
            this.insertAVL((T) s);
        } else {
            throw new InvalidDnDOperationException("Word already exists");
        }
    }

    // Search for word This method searches for a word (string) in the existing dictionary
// [public boolean searchWord(String s)], if the word is found, it should return true,
// otherwise it should return false.
    public boolean searchWord(String s) {
        return this.search((T) s) != null;
    }

    // Delete word This method deletes a word (string) from the existing dictionary
    // [public void deleteWord(String s)], if the word is not present, it should throw an exception.
    public void deleteWord(String s) {
        if (this.search((T) s) == null) {
            throw new InvalidDnDOperationException("Word does not exist");
        } else {
            this.deleteAVL((T) s);
        }
    }

    //Search for similar words This method searches for words that are similar to a given word s.
    // By similar, we mean that the string s differs in exactly 1 letter only with the words in the dictionary.
    // [public String[ ] findSimilar (String s)]
    public String[] findSimilar(String s) {
        String[] similarWords = new String[100];
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String temp = s.substring(0, i) + c + s.substring(i + 1);
                if (search((T) temp) != null) {
                    similarWords[count] = temp;
                    count++;
                }
            }
        }
        return similarWords;
    }

    // print the dictionary in sorted order [public void printDictionary()]
    public void printDictionary() {
        inorder();
    }
}