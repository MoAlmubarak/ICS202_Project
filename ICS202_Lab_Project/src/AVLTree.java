import java.awt.dnd.InvalidDnDOperationException;

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {

    protected int height;

    public AVLTree() {
        super();
        height = -1;
    }

    public AVLTree(BSTNode<T> root) {
        super(root);
        height = 0;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BSTNode<T> node) {
        if(node == null)
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
        if(isEmpty())
            return 0;
        else
            return getRightAVL().getHeight() - getLeftAVL().getHeight();
    }

    public void insertAVL(T el)  {
        super.insert(el);
        this.balance();
    }

    public void deleteAVL(T el) {
        //Q1
        super.deleteByCopying(el); // Use the BST delete by copying (or by merging)
        this.balance(); // Balance the AVL tree after deleting
    }

    protected void balance()
    {
        if(!isEmpty())
        {
            getLeftAVL().balance();
            getRightAVL().balance();

            adjustHeight();

            int balanceFactor = getBalanceFactor();

            if(balanceFactor == -2) {
//                System.out.println("Balancing node with el: "+root.el);
                if(getLeftAVL().getBalanceFactor() < 0)
                    rotateRight();
                else
                    rotateLeftRight();
            }

            else if(balanceFactor == 2) {
//                System.out.println("Balancing node with el: "+root.el);
                if(getRightAVL().getBalanceFactor() > 0)
                    rotateLeft();
                else
                    rotateRightLeft();
            }
        }
    }

    protected void adjustHeight()
    {
        if(isEmpty())
            height = -1;
        else
            height = 1 + Math.max(getLeftAVL().getHeight(), getRightAVL().getHeight());
    }

    protected void rotateRight() throws UnsupportedOperationException {
//        System.out.println("RIGHT ROTATION");
        //Q1

        if (this.isEmpty()) throw new UnsupportedOperationException("AVLTree is empty"); // If the AVLTree is empty throw an exception

        BSTNode<T> tempNode = root.right; // Save the right node to be used later
        root.right = root.left; // Flip the right and left nodes
        root.left = root.right.left; // Transfer the right-left node to the left
        root.right.left = root.right.right; // Transfer the right-right node to the right-left
        root.right.right = tempNode; // Reassign the temp value to the right-right node

        T val = (T) root.el; // Save the value of the root node to be swapped later.
        root.el = root.right.el; // Save the root node's value
        root.right.el = val; // Assign the right node's value with the

        getRightAVL().adjustHeight(); // Recalculate/Adjust the height of the right AVL tree
        adjustHeight(); // Adjust the height of the root node
    }

    protected void rotateLeft() {
//        System.out.println("LEFT ROTATION");
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

    protected void rotateLeftRight()
    {
//        System.out.println("Double Rotation...");
        //Q1

        getLeftAVL().rotateLeft(); // Rotate left the left AVL tree
        getLeftAVL().adjustHeight(); // Adjust the height of previously rotated left AVL tree
        this.rotateRight(); // Rotate right the root AVL tree
        this.adjustHeight(); // Adjust the height of the root tree
    }

    protected void rotateRightLeft()
    {
//        System.out.println("Double Rotation...");
        getRightAVL().rotateRight();
        getRightAVL().adjustHeight();
        this.rotateLeft();
        this.adjustHeight();
    }
}
