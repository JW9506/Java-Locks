package com.quickstart._avltree;

public class Main {
  public static void main(String[] args) {
    AVLTree tree = new AVLTree();

    // Example Insertions
    tree.insert(10);
    tree.insert(20);
    tree.insert(30);
    tree.insert(40);
    tree.insert(50);
    tree.insert(25);

    // Print the tree
    tree.printTree();
  }
}


class AVLTree {
  private Node root;

  // Node class for AVL Tree
  static class Node {
    int key, height;
    Node left, right;

    Node(int d) {
      key = d;
      height = 1;
    }
  }

  // Get height of the tree
  int height(Node N) {
    if (N == null)
      return 0;
    return N.height;
  }

  // A utility function to right rotate subtree rooted with y
  Node rightRotate(Node y) {
    Node x = y.left;
    Node T2 = x.right;

    // Perform rotation
    x.right = y;
    y.left = T2;

    // Update heights
    y.height = Math.max(height(y.left), height(y.right)) + 1;
    x.height = Math.max(height(x.left), height(x.right)) + 1;

    // Return new root
    return x;
  }

  // A utility function to left rotate subtree rooted with x
  Node leftRotate(Node x) {
    Node y = x.right;
    Node T2 = y.left;

    // Perform rotation
    y.left = x;
    x.right = T2;

    // Update heights
    x.height = Math.max(height(x.left), height(x.right)) + 1;
    y.height = Math.max(height(y.left), height(y.right)) + 1;

    // Return new root
    return y;
  }

  // Get Balance factor of node N
  int getBalance(Node N) {
    if (N == null)
      return 0;
    return height(N.left) - height(N.right);
  }

  Node insert(Node node, int key) {

    /* 1. Perform the normal BST insertion */
    if (node == null)
      return (new Node(key));

    if (key < node.key)
      node.left = insert(node.left, key);
    else if (key > node.key)
      node.right = insert(node.right, key);
    else // Duplicate keys not allowed
      return node;

    /* 2. Update height of this ancestor node */
    node.height = 1 + Math.max(height(node.left), height(node.right));

    /*
     * 3. Get the balance factor of this ancestor node to check whether this node became unbalanced
     */
    int balance = getBalance(node);

    // If this node becomes unbalanced, then there are 4 cases

    // Left Left Case
    if (balance > 1 && key < node.left.key)
      return rightRotate(node);

    // Right Right Case
    if (balance < -1 && key > node.right.key)
      return leftRotate(node);

    // Left Right Case
    if (balance > 1 && key > node.left.key) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    // Right Left Case
    if (balance < -1 && key < node.right.key) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    /* return the (unchanged) node pointer */
    return node;
  }

  void insert(int key) {
    root = insert(root, key);
  }

  // A utility function to print preorder traversal of the tree.
  void preOrder(Node node) {
    if (node != null) {
      System.out.print(node.key + " ");
      preOrder(node.left);
      preOrder(node.right);
    }
  }

  void printTree() {
    preOrder(root);
  }
}

