import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Andrew Boughan Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection is null! "
                + "Cannot create AVL of Null collection!!!!!");
        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Item you are trying to "
                    + "AVL add is null. You can't do that!!");
            }
            add(item);
        }


    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @param data the data to be added
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't put null data in "
                + "the AVL tree");
        }
        root = add(data, root);
    }


    /**
     * Private helper method that uses BST add strategy to add to AVl then
     * update the trees shape property so that it fits the rules of AVLS
     *
     * @param data    the data to be added
     * @param pointer the node to which the data should be added to
     *                recursively dumps to root.
     * @return the recursive mending of the tree after AVL shape constraining
     */
    private AVLNode<T> add(T data, AVLNode<T> pointer) {
        if (pointer == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.equals(pointer.getData())) {
            return pointer;
        } else if (data.compareTo(pointer.getData()) > 0) {
            pointer.setRight(add(data, pointer.getRight()));
        } else {
            pointer.setLeft(add(data, pointer.getLeft()));
        }
        return checkAVL(data, pointer);
    }

    /**
     * updates and rotates the avl at the given node
     *
     * @param data    we need this to keep data when we add remove etc.
     * @param pointer the node we are currently performing update and rotate on
     * @return the node the recurses back to root.
     */
    private AVLNode<T> checkAVL(T data, AVLNode<T> pointer) {
        pointer.setHeight(heightHelper(pointer));
        pointer.setBalanceFactor(balance(pointer));
        if (pointer.getBalanceFactor() > 1) {
            if (pointer.getLeft().getRight() != null
                && data.compareTo(pointer.getLeft().getData()) > 0) {
                pointer.setLeft(leftRotate(pointer.getLeft()));
            }
            return rightRotate(pointer);
        } else if (pointer.getBalanceFactor() < -1) {
            if (pointer.getRight().getLeft() != null
                && data.compareTo(pointer.getRight().getData()) < 0) {
                pointer.setRight(rightRotate(pointer.getRight()));
            }
            return leftRotate(pointer);
        } else {
            return pointer;
        }
    }

    /**
     * Updates shape property by performing rotations and updating heights
     * and Balance factors
     *
     * @param a the first node
     * @param b the second node
     */
    private void updateAVL(AVLNode<T> a, AVLNode<T> b) {
        a.setHeight(heightHelper(a));
        a.setBalanceFactor(balance(a));
        b.setHeight(heightHelper(b));
        b.setBalanceFactor(balance(b));
    }

    /**
     * performs right rotation and update on pointer
     *
     * @param pointer the node being updated
     * @return node that recurses back and assigned to root
     */
    private AVLNode<T> rightRotate(AVLNode<T> pointer) {
        AVLNode<T> b = pointer.getLeft();
        pointer.setLeft(b.getRight());
        b.setRight(pointer);
        updateAVL(pointer, b);
        return b;
    }

    /**
     * performs left rotation and update on pointer
     *
     * @param pointer the node being updated
     * @return node that recurses back and assigned to root
     */
    private AVLNode<T> leftRotate(AVLNode<T> pointer) {
        AVLNode<T> b = pointer.getRight();
        pointer.setRight(b.getLeft());
        b.setLeft(pointer);
        updateAVL(pointer, b);
        return b;
    }


    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     * <p>
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data you are "
                + "trying to remove is null");
        } else {
            if (this.contains(data)) {
                size--;
                AVLNode<T> dummy = new AVLNode<>(null);
                root = getRemove(this.getRoot(), data, dummy);
                return dummy.getData();
            } else {
                throw new java.util.NoSuchElementException("Element "
                    + "in tree not found");
            }
        }
    }

    /**
     * COPIED from my BST Homework. REUSE!! Private method for remove.
     *
     * @param root  the root where we start to find our node to remove
     * @param data  the data we are trying to remove
     * @param dummy a temp AVLNode
     * @return node(s) for recursive rebuilding of tree.
     */
    private AVLNode<T> getRemove(AVLNode<T> root, T data, AVLNode<T> dummy) {
        if (root == null) {
            return null;
        } else if (data.equals(root.getData())) {
            dummy.setData(root.getData());
            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            }
            if (root.getLeft() == null && root.getRight() != null) {
                return root.getRight();
            }
            if (root.getRight() == null && root.getLeft() != null) {
                return root.getLeft();
            }
            if (root.getLeft() != null && root.getRight() != null) {
                AVLNode<T> dummyTemp = new AVLNode<>(null);
                root.setRight(findSuc(root.getRight(), dummyTemp));
                root.setData(dummyTemp.getData());
            }
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(getRemove(root.getLeft(), data, dummy));
        }
        if (data.compareTo(root.getData()) > 0) {
            root.setRight(getRemove(root.getRight(), data, dummy));
        }
        return checkAVL(data, root);
    }

    /**
     * private method for private method getRemove
     * COPIED FROM MY BST HOMEWORK
     *
     * @param node      the current node being pointed too.
     * @param dummyTemp dummy node
     * @return data representing the predecessor
     */
    private AVLNode<T> findSuc(AVLNode<T> node, AVLNode<T> dummyTemp) {
        if (node.getLeft() == null) {
            dummyTemp.setData(node.getData());
            if (node.getRight() != null) {
                return node.getRight();
            }
            return null;
        } else {
            node.setLeft(findSuc(node.getLeft(), dummyTemp));
        }
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be "
                + "searched for in the tree and thus cannot be returned");
        }
        if (get(data, root) == null) {
            throw new java.util.NoSuchElementException("Data could "
                + "not be found");
        }
        return get(data, root).getData();
    }

    /**
     * Similar to contains but we return a pointer in recursion and in the
     * public method we call the getData mehtod to complete the get Stub
     * requirements.
     *
     * @param data    the data we are looking for in tree (BST)
     * @param pointer the current node being interrogated
     * @return null if not in tree, the node if in the tree, recursion pointers
     */
    private AVLNode<T> get(T data, AVLNode<T> pointer) {
        if (pointer == null) {
            return null;
        } else if (data.equals(pointer.getData())) {
            return pointer;
        } else if (data.compareTo(pointer.getData()) > 0) {
            return get(data, pointer.getRight());
        } else {
            return get(data, pointer.getLeft());
        }
    }


    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be "
                + "searched for in the tree!");
        }
        //we will start at the root (just like BST hw)
        return containsHelper(data, root);
    }

    /**
     * private helper method to recursively check for value.
     * We employ same method we used in BST homework because AVL's are BSTs
     *
     * @param data    the data value we are interrogating the tree for
     * @param pointer the current node we are at in terms of traversing
     * @return a boolean value seeing if we hit null (not in the tree) or true
     * (its in the tree!)
     */
    private boolean containsHelper(T data, AVLNode<T> pointer) {
        if (pointer == null) {
            return false;
        } else if (data.equals(pointer.getData())) {
            return true;
        } else if (data.compareTo(pointer.getData()) > 0) {
            return containsHelper(data, pointer.getRight());
        } else {
            return containsHelper(data, pointer.getLeft());
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     * <p>
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     * <p>
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     * <p>
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> output = new ArrayList<>();
        deepestBranchesHelper(root, output);
        return output;
    }

    /**
     * Copy and rehash of preOrder traversal for finding deepest branches.
     * We choose which branch to go of the child whose height is
     * non-null/the most high.
     * @param root the node to start our deepestBranch traversal.
     * @param output the outputted list.
     */
    private void deepestBranchesHelper(AVLNode<T> root, List<T> output) {
        if (root == null) {
            return;
        }
        output.add(root.getData());
        int leftHeight = -1;
        if (root.getLeft() != null) {
            leftHeight = root.getLeft().getHeight();
        }
        int rightHeight = -1;
        if (root.getRight() != null) {
            rightHeight = root.getRight().getHeight();
        }
        if (leftHeight > rightHeight) {
            deepestBranchesHelper(root.getLeft(), output);
        } else if (leftHeight < rightHeight) {
            deepestBranchesHelper(root.getRight(), output);
        } else {
            deepestBranchesHelper(root.getLeft(), output);
            deepestBranchesHelper(root.getRight(), output);
        }

    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     * <p>
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     *                                            or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null && data2 == null) {
            throw new java.lang.IllegalArgumentException("Both data1 "
                + "and data2 are null");
        }
        if (data1 == null) {
            throw new java.lang.IllegalArgumentException("data 1 is null");
        }
        if (data2 == null) {
            throw new java.lang.IllegalArgumentException("data 2 is null");
        }
        if (data1.compareTo(data2) > 0) {
            throw new java.lang.IllegalArgumentException("data 1 is larger "
                + "than data 2 therfore does not follow bound restrictions");
        }

        List<T> output = new ArrayList<>();
        sortedInBetweenHelper(root, data1, data2, output);
        return output;
    }

    /**
     * Private helper method for SortedInBetween In order traversal where
     * we decide which branch to go down depending on current node's data
     *
     * @param root the node to start our interrogation.
     * @param data1 the lower threwshold
     * @param data2 the higher threshold
     * @param output output from list.
     */
    private void sortedInBetweenHelper(AVLNode<T> root,
                                       T data1, T data2, List<T> output) {
        if (root == null) {
            return;
        }
        if (root.getData().compareTo(data1) <= 0) {
            sortedInBetweenHelper(root.getRight(), data1, data2, output);
        } else if (root.getData().compareTo(data2) >= 0) {
            sortedInBetweenHelper(root.getLeft(), data1, data2, output);
        } else {
            sortedInBetweenHelper(root.getLeft(), data1, data2, output);
            output.add(root.getData());
            sortedInBetweenHelper(root.getRight(), data1, data2, output);
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else if (root.getLeft() != null && root.getRight() != null) {
            return 1 + Math.max(root.getLeft().getHeight(),
                root.getRight().getHeight());
        } else if (root.getLeft() != null) {
            return 1 + root.getLeft().getHeight();
        } else if (root.getRight() != null) {
            return 1 + root.getRight().getHeight();
        } else {
            return 0;
        }
    }

    /**
     * @param node the node we want to return the height on.
     * @return returns the height at the given node by recursively
     * traversing down from node counting each level down
     */
    private int heightHelper(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(node.getLeft()),
            heightHelper(node.getRight()));
    }

    /**
     * Returns the size of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Private helper method to return the balance of a given node.
     *
     * @param node the node we want to find the balance factor for.
     * @return the balance factor at the node int > 0 indicates left heavy
     * int < 0 indicates right heavy
     */
    private int balance(AVLNode<T> node) {
        if (node.getLeft() != null && node.getRight() != null) {
            return node.getLeft().getHeight() - node.getRight().getHeight();
        } else if (node.getLeft() != null) {
            return node.getLeft().getHeight() + 1;
        } else if (node.getRight() != null) {
            return -1 - node.getRight().getHeight();
        } else {
            return 0;
        }
    }

    /**
     * Returns the root of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}