
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;


/*
* Authors: Christian Marquardt
* Date: 1/26/2018
* Overview: Using NetBeans 8.2 reading in a input file into the console
* to create a binary tree where we insert, find, delete, calculate the 
* min and max and finally display the final product which is the tree
*/
//////////////////////////////////////////////////////////////
class Node {

    public int iData;           // data item (key)
    public double dData;        // data item
    public Node leftChild;      // this Node's left child
    public Node rightChild;     // this Node's right child

    public void displayNode() { // display ourself
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
    }
} // end Class Node
////////////////////////////////////////////////////////////////

class Tree {

    private Node root;                 // first Node of Tree

    public Tree() {                    // constructor
        root = null;                   // no nodes in tree yet
    }

    public Node find(int key) {      // find node with given key
        Node current = root;         // (assumes non-empty tree)
        while (current.iData != key) {          // while no match
            if (key < current.iData) {          // go left?
                current = current.leftChild;
            } else {                              // or go right?
                current = current.rightChild;
            }
            if (current == null) // if no child
            {                                   // didn't find it
                return null;
            }
        }
        return current;                         // found it
    }  //end find()

    public void insert(int id, double dd) {
        Node newNode = new Node();    // make new Node
        newNode.iData = id;           // insert data
        newNode.dData = dd;
        newNode.leftChild = null;
        newNode.rightChild = null;
        if (root == null) {            // no node in root
            root = newNode;
        } else {                        // root occupied
            Node current = root;      // start at root  
            Node parent;
            while (true) {            // exits internally			
                parent = current;
                if (id < current.iData) {              // go left?
                    current = current.leftChild;
                    if (current == null) {             // if the end of the line        
                        parent.leftChild = newNode;   // insert on left
                        return;
                    }
                } //end if go left
                else {                                // or go right?
                    current = current.rightChild;
                    if (current == null) // if the end of the line
                    {                                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    } // end insert()

    public boolean delete(int key) {             // delete node with given key
        Node current = root;		             // (assumes non-empty list)
        Node parent = root;
        boolean isLeftChild = true;

        while (current.iData != key) {           // search for Node
            parent = current;
            if (key < current.iData) {           // go left?
                isLeftChild = true;
                current = current.leftChild;
            } else {                               // or go right?
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) {                // end of the line,                             
                return false;                    // didn't find it
            }
        }
        //found the node to delete

        //if no children, simply delete it
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {              // if root,
                root = null;                    // tree is empty
            } else if (isLeftChild) {
                parent.leftChild = null;        // disconnect
            } // from parent
            else {
                parent.rightChild = null;
            }
        } //if no right child, replace with left subtree
        else if (current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        } //if no left child, replace with right subtree
        else if (current.leftChild == null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        } else { // two children, so replace with inorder successor
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }

            //connect successor to current's left child
            successor.leftChild = current.leftChild;
        } // end else two children
        // (successor cannot have a left child)
        return true;              // success
    }// end delete()

    //returns node with next-highest value after delNode
    //goes right child, then right child's left descendants
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;        // go to the right child
        while (current != null) {                 // until no more
            successorParent = successor;          // left children
            successor = current;
            current = current.leftChild;
        }

        if (successor != delNode.rightChild) {    // if successor not right child,
            //make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal: ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
            default:
                System.out.print("Invalid traversal type\n");
                break;
        }
        System.out.println();
    }

    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }

    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }

    public void displayTree() {
        Stack<Node> globalStack = new Stack<Node>();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                ".................................................................");
        while (isRowEmpty == false) {
            Stack<Node> localStack = new Stack<Node>();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }

            while (globalStack.isEmpty() == false) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null
                            || temp.rightChild != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }

                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(' ');
                }
            } // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            } // end while isRowEmpty is false
            System.out.println(
                    ".................................................................");
        }
    }// end displayTree()

    public Node findMax() {
        Node current = root; //Starts at the beginning
        //If there isnt a Node to be found
        if (current == null) {
            System.out.print("There are no nodes in the tree");
        }
        //Right child will alaways be the smallest so it will go all the way to the bottom of the tree
        while (current.rightChild != null) {
            current = current.rightChild;
        }
        return current; //returns the Node
    }

    public Node findMin() {
        Node current = root; //starts at the beginning
        //If there isnt a Node to be found
        if (current == null) {
            System.out.print("There are no nodes in the tree");
        }
        //Left chil will alaways be the smallest so it will go all the way to the bottom of the tree
        while (current.leftChild != null) {
            current = current.leftChild;
        }
        return current; //returns the Node
    }
} // end class Tree

////////////////////////////////////////////////////////////////
class TreeApp {

    public static void main(String[] args) throws IOException {

        Tree theTree = new Tree(); // new instance
        Charset lit = Charset.forName("US-ASCII"); //encoding the input file
        Path base = Paths.get("input/input.txt"); //reading in the text
        try (BufferedReader reader = Files.newBufferedReader(base, lit)) {
            String line = null; //setting to null as a starter
                //shows if there is something in the input file to read
            while ((line = reader.readLine()) != null) {
                String[] words; //creating a string array
                words = line.split(" "); //everything that was put into array was split
                String yeet = words[0]; //starts originally with the first word
                //switch statement with the input file reading it into the console
                switch (yeet) { 

                    case "insert": //inserting nodes
                        //separates the commas and takes the integers instead and storing into an array
                        String swag[] = words[1].split(",");
                        //creating a for loop with the array above to insert them in to the tree as Nodes
                        for (int i = 0; i < swag.length; i++) {
                            int num = Integer.parseInt(swag[i]);
                            theTree.insert(num, num + .9);
                        }
                        System.out.print("Inserting: " + words[1]);
                        System.out.println("\n");
                        break;
                    case "find": //Finds a node 
                        int flum = Integer.parseInt(words[1]); //Parsing whats after find
                        Node found = theTree.find(flum); //Setting a Node to the Parsed words[1] to see if it is in the binary tree
                        //If the Node has been found within the Binary Tree
                        if (found != null) {
                            System.out.print("Found: ");
                            found.displayNode(); //calling the method with the parameters (int, double)
                            System.out.println("\n");
                            //If the Node was not found
                        } else {
                            System.out.print("Could not find ");
                            System.out.print(flum + "\n");
                        }

                        break;
                    case "delete": //deltes the node
                        int num = Integer.parseInt(words[1]);
                        boolean didDelete = theTree.delete(num); //creating a true false statement is the node can be deleted
                        //If the Node was found and could be deleted
                        if (didDelete) {
                            System.out.print("Deleted: " + num + '\n');
                            //If there was no Node to be found
                        } else {
                            System.out.print("Could not delete value: " + num + '\n');
                        }

                        break;
                    case "traverse": //Depending on the number it will traverse in 3 different ways
                        theTree.traverse(Integer.parseInt(words[1]));
                        break;
                    case "min": //finds the min
                         System.out.println();
                        Node minny = theTree.findMin(); //creating a Node that is the min
                        if (minny != null) {
                            System.out.print("Min: ");
                            minny.displayNode(); //calling the method for (int, double)
                            System.out.println("\n");
                        }               
                        break;
                    case "max": //finds the max
                        Node maxxy = theTree.findMax(); //creating a Node that is the max
                        if (maxxy != null) {
                            System.out.print("Max: ");
                            maxxy.displayNode(); //calling the method (int, double)
                            System.out.print("\n");
                        }               
                        break;
                    case "show": //displays the entire tree
                        theTree.displayTree(); //calling the method
                        break;

                } // end switch
            } // end while
        }
    }// end main()

    private static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    private static int getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }

    private static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }
}// end TreeApp class
////////////////////////////////////////////////////////////////
