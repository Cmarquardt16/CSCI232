/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programerino1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Authors: Christian Marquardt, Michael Muff, Will Dean 
 * Date: 1/29/2018
 * Overview: To Be Determined
 */
public class Programerino1 {

    public static void main(String[] args) throws IOException {
        HuffmanCode done = new HuffmanCode();
        Charset lit = Charset.forName("US-ASCII");
        Path base = Paths.get("Input/input.txt");
        Path output1 = Paths.get("Output/output.txt");

        try (BufferedReader reader = Files.newBufferedReader(base, lit)) {
            String line = null;
            String second = "";

            while ((line = reader.readLine()) != null) {
                second = second + line + "\n";
                System.out.println(line);
            }
            Hashtable<Character, Integer> turnip = new Hashtable<Character, Integer>();

            for (int i = 0; i < second.length(); i++) {
                char c = second.charAt(i);
                Integer val = turnip.get(c);
                if (val != null) {
                    turnip.put(c, new Integer(val + 1));

                } else {
                    turnip.put(c, 1);
                }

            }
            
            System.out.println("\n" + "Frequency Table: " + turnip + "\n");
            
            for (char c: turnip.keySet())
            {
                Node temp = new Node(c, turnip.get(c));
                done.pq.add(temp);
            }
            
            done.makeTree();
             System.out.print("Code Table: " + done.codeTable + "\n");
             System.out.print("\n" + done.encode(second));
             String encoded = done.encode(second);
             
                try (BufferedWriter dang = Files.newBufferedWriter(output1, lit)) {
                    dang.write(done.decode(encoded));
                    dang.close();

                }
            
        }
    }
}
