/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3345.hw4;
import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
/**
 *
 * @author ASUS
 */


class Node {
    int key, height;
    Node left, right;
    Book value;
    Node(int d) {
        key = d;
        height = 1;
    }
    Node (Book book){
        key = book.getID();
        height = 1;
        value = book;
    }
}

   
class Book{
    int id;
    String title;
    String author;
    int getID(){
        return id;
    }
    void setID(int id){
        this.id = id;
    }
    void setAuthor(String author){
        this.author = author;
    }
    void setTitle(String title){
        this.title = title;
    }
}
 
public class Hw4 {
 
    Node root;
 
    // Get tree height
    int height(Node N) {
        if (N == null)
            return 0;
 
        return N.height;
    }
 
    // Get maximum of 2 ints
    int max(int a, int b) {
        return (a > b) ? a : b;
    }
 
    // Rotate right in left left case
    Node rightRotate(Node y) {
        Node x = y.left;
        Node z = x.right;
 
        // Rotate
        x.right = y;
        y.left = z;
 
        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }
 
    // Rotate left in right right case
    Node leftRotate(Node x) {
        Node y = x.right;
        Node z = y.left;
 
        // Rotate
        y.left = x;
        x.right = z;
 
        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
 
        return y;
    }
 
    // Get Balance funciton
    int getBalance(Node N) {
        if (N == null)
            return 0;
 
        return height(N.left) - height(N.right);
    }
 
    Node insert(Node node, Book book){ 
        if (node == null)
            return (new Node(book));
        return insert(node, book.getID());
    }
    Node insert(Node node, int key) { // helper method
        
        if (node == null)
            return (new Node(key));

        
        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Duplicate keys not allowed
            return node;
 
        // Update heights
        node.height = 1 + max(height(node.left),
                              height(node.right));
 
        // check for balance
        int balance = getBalance(node);
 
        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && key < node.left.key){
            System.out.print("imbalance detected at inserting ISBN " + key + " fixed in right rotation\n");
            return rightRotate(node);
        }
        // Right Right Case
        if (balance < -1 && key > node.right.key){
            System.out.print("imbalance detected at inserting ISBN " + key + " fixed in left rotation\n");
            return leftRotate(node);
        }
        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            System.out.print("imbalance detected at inserting ISBN " + key + " fixed in RightLeft rotation\n");
            return rightRotate(node);
        }
 
        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            System.out.print("imbalance detected at inserting ISBN " + key + " fixed in LeftRight rotation\n");
            return leftRotate(node);
        }
 
        return node;
    }
 
    // Just a method to print the ISBN in ascending order to check if the tree has AVL properties 
    void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(" " + node.key);
            inOrder(node.right);
        }
    }
 
    public static void main(String[] args) {
        Hw4 tree = new Hw4();
        
        Scanner input = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String filename = input.nextLine();
        //String filename = "C:\\Users\\ASUS\\Desktop\\test.txt";
        // this is my test text file
        String line = null;
        
        try{
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String [] parts;
            while((line = bufferedReader.readLine()) != null){
                Book book = new Book();
                parts = line.split(" ");
                book.setID(Integer.parseInt(parts[0]));
                book.setTitle(parts[1]);
                book.setAuthor(parts[2]);
                tree.root = tree.insert(tree.root, book);
            }
            
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to open file");
        }
        catch(IOException ex){
            System.out.println("Error reading file");
        }
        System.out.println("Inorder traversal" +
                        " of constructed tree is : ");
        
        // Print out the tree in ascending order
        tree.inOrder(tree.root);
    }
}

