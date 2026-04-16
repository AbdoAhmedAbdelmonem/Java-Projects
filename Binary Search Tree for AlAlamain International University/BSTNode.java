// Node class for Binary Search Tree
public class BSTNode<T extends Comparable<T>> {
    private T data;
    private BSTNode<T> left;
    private BSTNode<T> right;
    
    public BSTNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    // Getters and Setters
    public T getData() { 
        return data; 
    }
    
    public BSTNode<T> getLeft() { 
        return left; 
    }
    
    public BSTNode<T> getRight() { 
        return right; 
    }
    
    public void setLeft(BSTNode<T> left) { 
        this.left = left; 
    }
    
    public void setRight(BSTNode<T> right) { 
        this.right = right; 
    }
}
