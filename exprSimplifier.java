package expression_simplifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;


public class exprSimplifier {
  // Private attributes
  private static String input_;
  private int result_;

  // Constructor
  public exprSimplifier(String input) {
    exprSimplifier.input_ = input;
    System.out.println("Expression to be simplified");
    System.out.println(getinput());
    validateString();
    result_ = simplifyString(getinput());
  }

  // Getter for input_
  public static String getinput() {
    return input_;
  }

  // Setter for input_
  public void setinput(String input) {
    exprSimplifier.input_ = input;
  }

  public static void validateString() throws IllegalArgumentException {
    // Regular expression to match characters that are not digits or operators
    String invalidCharRegex = "[^\\d()+\\-*/()\\s]";
    
    // Compile the pattern
    Pattern pattern = Pattern.compile(invalidCharRegex);
    
    String input = getinput();
    // Match the input string
    Matcher matcher = pattern.matcher(input);
    
    // If any invalid character is found, throw an exception
    if (matcher.find()) {
        throw new IllegalArgumentException("The string contains invalid characters.");
    }
  }

  public int simplifyString(String input) {
    String input_no_spaces = new String();
    Stack<Integer> stack = new Stack<>();
    String op1 = new String();
    String op2 = new String();
    int result = new Int();

    for (char ch : input.toCharArray()) {
      if (ch != ' ') {
        input_no_spaces += ch;
      }
    }
    System.out.println("String without spaces: " + input_no_spaces.toString());

    for (int i = 0; i < input_no_spaces.length(); ++i) {
      if (input_no_spaces.charAt(i) != '+' | input_no_spaces.charAt(i) != '-' | input_no_spaces.charAt(i) != '*' | input_no_spaces.charAt(i) != '/' | input_no_spaces.charAt(i) != '(' | input_no_spaces.charAt(i)!= ')') {
        op1 += input_no_spaces.charAt(i);

      } else if (input_no_spaces.charAt(i) == '(') {
        String priority_operation = new String();
        for (int j = 0; input_no_spaces.charAt(i) != ')'; ++j) {
          priority_operation += input_no_spaces.charAt(i);
        }
      }
    }

    return result;
  }
}