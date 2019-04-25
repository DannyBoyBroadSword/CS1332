import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 *
 * @author Andrew Boughan Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection you "
                + "are trying to add is null");
        } else {
            Iterator<T> dataIterator = data.iterator();
            while (dataIterator.hasNext()) {
                T buffer = (T) dataIterator.next();
                if (buffer == null) {
                    throw new IllegalArgumentException("Cannot add "
                        + "null object from collection to tree");
                } else {
                    this.add(buffer);
                }
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to be added
     * @throws IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data you are "
                + "trying to add is null");
        } else {
            root = addNode(data, this.getRoot());

        }
    }

    /**
     * private helper method for add(T data)
     *
     * @param data the data to be added
     * @param node the node data should be added too
     * @return a node as part of the addNode helperMethod.
     */
    private BSTNode<T> addNode(T data, BSTNode<T> node) {
        if (node == null) {
            this.size += 1;
            return new BSTNode<T>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addNode(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addNode(data, node.getRight()));
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf (no children). In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data you are "
                + "trying to add is null");
        } else {
            if (this.contains(data)) {
                size--;
                BSTNode<T> dummy = new BSTNode<T>(null);
                root = getRemove(this.getRoot(), data, dummy);
                return dummy.getData();
            } else {
                throw new java.util.NoSuchElementException("Element "
                    + "in tree not found");
            }
        }
    }

    /**
     * Private method for remove
     *
     * @param root  the current node being pointed too.
     * @param data  the data to remove from the tree.
     * @param dummy temp node
     * @return node(s) for recursive rebuilding of tree
     */
    private BSTNode<T> getRemove(BSTNode<T> root, T data, BSTNode<T> dummy) {
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
                BSTNode<T> dummyTemp = new BSTNode<>(null);
                root.setLeft(findPred(root.getLeft(), dummyTemp));
                root.setData(dummyTemp.getData());
            }
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(getRemove(root.getLeft(), data, dummy));
        }
        if (data.compareTo(root.getData()) > 0) {
            root.setRight(getRemove(root.getRight(), data, dummy));
        }
        return root;
    }

    /**
     * private method for private method getRemove
     *
     * @param node  the current node being pointed too.
     * @param dummyTemp dummy node
     * @return data representing the predecessor
     */
    private BSTNode<T> findPred(BSTNode<T> node, BSTNode<T> dummyTemp) {
        if (node.getRight() == null) {
            dummyTemp.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(findPred(node.getRight(), dummyTemp));
        }
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
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
            throw new IllegalArgumentException("Data you are trying to get is "
                + "null");
        } else {
            BSTNode<T> result = getSearch(this.getRoot(), data);
            if (result == null) {
                throw new java.util.NoSuchElementException("Element in tree Not"
                    + "Found");
            } else {
                return result.getData();
            }
        }
    }

    /**
     * private method for search.
     *
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param root the node the search function is currently pointing too
     * @param data the data currently being searched for.
     * @return node in search
     */
    private BSTNode<T> getSearch(BSTNode<T> root, T data) {
        if (root == null || root.getData().compareTo(data) == 0) {
            return root;
        }
        if (root.getData().compareTo(data) > 0) {
            return getSearch(root.getLeft(), data);
        }
        return getSearch(root.getRight(), data);
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data you are trying to get is "
                + "null");
        } else {
            BSTNode<T> result = getSearch(this.getRoot(), data);
            return result != null;
        }

    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> result = new ArrayList<T>();
        return preOrderList(this.getRoot(), result);
    }

    /**
     * private method for preOrder
     * Should run in O(n).
     *
     * @param root   the current node being pointed too.
     * @param result the list to be returned to the public method (Being Built)
     * @return a preOrder traversal of the tree
     */
    private List<T> preOrderList(BSTNode<T> root, List<T> result) {
        if (root == null) {
            return result;
        }
        result.add(root.getData());
        preOrderList(root.getLeft(), result);
        preOrderList(root.getRight(), result);

        return result;
    }


    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> result = new ArrayList<T>();
        return inOrderList(this.getRoot(), result);
    }

    /**
     * Private method for inOrder
     * Should run in O(n).
     *
     * @param root   the current node being pointed too.
     * @param result the list to be returned to the public method (Being Built)
     * @return a inOrder traversal of the tree
     */
    private List<T> inOrderList(BSTNode<T> root, List<T> result) {
        if (root == null) {
            return result;
        }
        inOrderList(root.getLeft(), result);
        result.add(root.getData());
        inOrderList(root.getRight(), result);

        return result;
    }


    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> result = new ArrayList<T>();
        return postOrderList(this.getRoot(), result);
    }

    /**
     * Private method for postOrder
     * Should run in O(n).
     *
     * @param root   the current node being pointed too.
     * @param result the list to be returned to the public method (Being Built)
     * @return a postorder traversal of the tree
     */
    private List<T> postOrderList(BSTNode<T> root, List<T> result) {
        if (root == null) {
            return result;
        }
        postOrderList(root.getLeft(), result);
        postOrderList(root.getRight(), result);
        result.add(root.getData());

        return result;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     * <p>
     * Should run in O(n). This does not need to be done recursively.
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> result = new ArrayList<T>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(this.getRoot());
        while (q.peek() != null) {
            BSTNode<T> pointer = q.poll();
            result.add(pointer.getData());
            if (pointer.getLeft() != null) {
                q.add(pointer.getLeft());
            }
            if (pointer.getRight() != null) {
                q.add(pointer.getRight());
            }
        }
        return result;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     * <p>
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     * <p>
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     * <p>
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     *
     * @param <T>      the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(
        BSTNode<T> treeRoot) {
        return isBSThelper(treeRoot, null, null);
    }

    /**
     * Private helper method for isBST
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     * <p>
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     * <p>
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     * <p>
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     *
     * @param <T>      the generic typing
     * @param treeRoot the root of the binary tree to check
     * @param low      Low value.
     * @param high     high value.
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBSThelper(
        BSTNode<T> treeRoot, T low, T high) {
        if (treeRoot == null) {
            return true;
        }
        if (((low != null) && treeRoot.getData().compareTo(low)
            <= 0)
            || ((high != null) && (treeRoot.getData().compareTo(high) >= 0))) {
            return false;
        }

        return isBSThelper(treeRoot.getLeft(), low, treeRoot.getData())
            && isBSThelper(treeRoot.getRight(), treeRoot.getData(), high);

    }

    /**
     * Clears the tree.
     * <p>
     * Should run in O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     * <p>
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return getHeight(this.getRoot());
    }

    /**
     * private method for height
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     * <p>
     * Should be calculated in O(n).
     *
     * @param node the current node getHeight is pointed to.
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int getHeight(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int leftDepth = getHeight(node.getLeft());
            int rightDepth = getHeight(node.getRight());

            if (leftDepth > rightDepth) {
                return (leftDepth + 1);
            } else {
                return (rightDepth + 1);
            }
        }
    }

    /**
     * Returns the size of the BST.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the BST.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}