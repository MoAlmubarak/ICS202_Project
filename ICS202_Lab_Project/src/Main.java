import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\hp-pc\\Desktop\\ICS202 Lab\\ICS202_Project\\Doc\\Try.txt");
        AVLTree<String> t1 = new AVLTree<String>();
        t1.Dictionary(file);
        System.out.println(t1.searchWord("aarhus"));
        // delete a word from the dictionary
         t1.deleteWord("aarhus");
         System.out.println(t1.searchWord("aarhus"));
        // add new words to the dictionary
//        t1.printDictionary();


//        // read the text file.
//        try {
//            AVLTree<String> t3 = new AVLTree<String>();
//            String tmp;
//            File file = new File("C:\\Users\\hp-pc\\Desktop\\ICS202 Lab\\ICS202_Project\\Doc\\mydictionary.txt");
//            Scanner input2 = new Scanner(file);
//            while (input2.hasNext()) {
//                tmp = input2.next();
//                if (!t3.isInTree(tmp))
//                    t3.insertAVL(tmp);
//            }
//            // Printing inorder
//            t3.inorder();
//            System.out.println();
//
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
    }
}