// AUTOR: Javier Garcia Santana(El mismisimo coluca)(viva tacoronte,tenerife)
// DATE: 16/4/2024
// EMAIL: javier.santana@tprs.stud.vu.lt
// VERSION: 4.0
// COURSE: OOP
// NAME: main
// COMMENTS: File where the exprSimplifier class is instantiated
// COMPILE: javac expression_simplifier/*.java
// RUN: java expression_simplifier.main

package expression_simplifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class main {
  public static void main(String[] args) { 
    try {

      String filePath = "expression_simplifier/input.txt";
      String outputFilePath = "expression_simplifier/output.txt";
      try {
        // Step 1: Read the contents of the text file
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder fileContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            fileContent.append(line).append("\n");
        }
        reader.close();
        
        // Step 2: Process the content to generate the result string
        String input = fileContent.toString().trim(); // Assuming the content is the input expression
        ExprSimplifier simplified_expr = new ExprSimplifier(input);
        String result = simplified_expr.getResult();

        // Step 3: Print both the content of the text file and the result string
        System.out.println("\nContent of the text file:");
        System.out.println(fileContent.toString());
        System.out.println("Result:");
        System.out.println(result);

        // Step 4: Write the result string to an output text file
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        writer.write("Content of the text file:\n");
        writer.write(fileContent.toString());
        writer.write("\nResult:\n");
        writer.write(result);
        writer.close();
        
        System.out.println("\nResult has been written to " + outputFilePath);
      } catch (IOException e) {
            e.printStackTrace();
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
