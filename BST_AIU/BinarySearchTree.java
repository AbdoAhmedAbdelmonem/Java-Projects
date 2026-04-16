import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    private BSTNode<T> root;
    
    public BinarySearchTree() {
        this.root = null;
    }
    
    public void insert(T data) {
        root = insertRecursive(root, data);
    }
    
    private BSTNode<T> insertRecursive(BSTNode<T> node, T data) {
        if (node == null) {
            return new BSTNode<>(data);
        }
        
        int comparison = data.compareTo(node.getData());
        if (comparison < 0) {
            node.setLeft(insertRecursive(node.getLeft(), data));
        } else if (comparison > 0) {
            node.setRight(insertRecursive(node.getRight(), data));
        }
        
        return node;
    }
    
    public T search(T data) {
        return searchRecursive(root, data);
    }
    
    private T searchRecursive(BSTNode<T> node, T data) {
        if (node == null) return null;
        
        int comparison = data.compareTo(node.getData());
        if (comparison == 0) return node.getData();
        
        return comparison < 0 ? 
            searchRecursive(node.getLeft(), data) : 
            searchRecursive(node.getRight(), data);
    }
    
    public List<T> getInorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }
    
    private void inorderTraversal(BSTNode<T> node, List<T> result) {
        if (node != null) {
            inorderTraversal(node.getLeft(), result);
            result.add(node.getData());
            inorderTraversal(node.getRight(), result);
        }
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public void delete(T data) {
        root = deleteRecursive(root, data);
    }
    
    private BSTNode<T> deleteRecursive(BSTNode<T> node, T data) {
        if (node == null) return null;
        
        int comparison = data.compareTo(node.getData());
        
        if (comparison < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), data));
        } else if (comparison > 0) {
            node.setRight(deleteRecursive(node.getRight(), data));
        } else {
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();
            
            T minValue = findMin(node.getRight());
            BSTNode<T> newNode = new BSTNode<>(minValue);
            newNode.setRight(deleteRecursive(node.getRight(), minValue));
            newNode.setLeft(node.getLeft());
            return newNode;
        }
        return node;
    }
    
    private T findMin(BSTNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getData();
    }
}
