
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author: Christian Marquardt 
 * Date:1/19/2018 
 * Overview: Lab1 Netbeans 8.2
 * Reading in a txt file into the console then duplicating the console output
 * into another txt file called outputB.txt while also writing into a text document 
 * called outputA.txt where you write in the original input.txt document... 
 * For some reason I could not get the correct location when I put the
 * Input.txt and the output text docs. when I put them in a folder
 * So I dragged them out of it
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Charset lit = Charset.forName("US-ASCII");
        Path base = Paths.get("input.txt");
        Path output1 = Paths.get("outputA.txt");
        Path output2 = Paths.get("outputB.txt");
        try (BufferedReader reader = Files.newBufferedReader(base, lit)) {
            String line = null;
            String second = "";
            String third = "";
            while ((line = reader.readLine()) != null) {
                second = second + line + "\n";
                String weOutHere = line.replace(" ", System.lineSeparator());
                third = third + weOutHere + "\n";
                System.out.println(weOutHere);
            }
            try (BufferedWriter dang = Files.newBufferedWriter(output1, lit)) {
                dang.write(second, 0, second.length());

            }
            try (BufferedWriter dang = Files.newBufferedWriter(output2, lit)) {
                dang.write(third, 0, third.length());

            } catch (IOException x) {
                System.err.format("You is doing it wrong", x);

            }

        }
    }
}
