/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programerino1;

import java.io.IOException;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author owner
 */
public class HuffmanCode {

    PriorityQueue<Node> pq = new PriorityQueue<>();
    Hashtable<Character, String> codeTable = new Hashtable<>();
    Hashtable<String, Character> decodeTable = new Hashtable<>();
    Node root;

    public void makeTree() {
        while (pq.size() > 1) {
            Node temp1 = pq.remove();
            Node temp2 = pq.remove();
            Node internal = new Node(null, temp1.frequency + temp2.frequency);
            internal.leftChild = temp1;
            internal.rightChild = temp2;
            pq.add(internal);
        }
        root = pq.remove();
        traverse(root, "");

    }

    public void traverse(Node current, String code) {
        if (current.letters != null) {
            codeTable.put(current.letters, code);
            decodeTable.put(code, current.letters);
        } else {
            traverse(current.leftChild, code + 0);

            traverse(current.rightChild, code + 1);
        }
    }

    public String encode(String message) {
        String m = "";
        for (int i = 0; i < message.length(); i++) {
            m += codeTable.get(message.charAt(i));
        }
        return m;
    }

    public String decode(String message) {
        String secret = "";
        String script = "";
        for (int i = 0; i < message.length(); i++) {
            script += message.charAt(i);
            if (decodeTable.containsKey(script)) {

                secret += decodeTable.get(script);
                script = "";
            }
            
        }
        return secret;
    }
}
