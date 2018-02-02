/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programerino1;

/**
 *
 * @author owner
 */
public class Node implements Comparable<Node>{
    public int frequency;           // data of how many times the letter occurs
    public Character letters;            //data for the letters
    public Node leftChild;      // this Node's left child
    public Node rightChild;     // this Node's right child
    
    public Node(Character _letters, int _frequency) 
    {
        letters = _letters;
        frequency = _frequency;
    }

    @Override
    public int compareTo(Node o) {
     return frequency-o.frequency;
    }
    //To test if the Priority Queue was working
 public String toString()
 {
     return letters + " = " + frequency;
 }
 
}
