
package expression_simplifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;


public class ExprSimplifier {
  // Private attributes
  private static String input_;
  private String result_;

  // Constructor
  public ExprSimplifier(String input) {
    ExprSimplifier.input_ = input;
    System.out.println("Expression to be simplified");
    System.out.println(getinput());
    validateString();
    removeSpacesInput();
    result_ = simplifyString(getinput());
  }

  // Getter for input_
  public static String getinput() {
    return input_;
  }

  // Setter for input_
  public static void setinput(String input) {
    ExprSimplifier.input_ = input;
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

  public static void removeSpacesInput() {
    String input = getinput();
    String input_no_spaces = new String();
    for (char ch : input.toCharArray()) {
      if (ch != ' ') {
        input_no_spaces += ch;
      }
    }
    setinput(input_no_spaces);
    System.out.println("String without spaces: " + input_no_spaces);
  }

  public boolean isSymbol(char c) {
    if (c == '+' || c == '-' ||c == '*' || c == '/' || c == '(' || c == ')') {
      return true;
    }
    return false;
  }

  public String Calculator(String input, char symbol, int i) {
    System.out.println("Calculator input: " + input + "  i: " + i);

    Stack<Integer> stack = new Stack<>();
    String val_1 = new String();
    String val_2 = new String();
    String aux = new String();
    String result = new String();
    boolean flag = false;
    int j = 0;
    int op_1 = 0;
    int op_2 = 0;
    int lower_bound = 0;
    int upper_bound = 0;

    j = i - 1;
    while (j >= 0 && Character.isDigit(input.charAt(j))) {
      val_1 += input.charAt(j);
      if (j >= 1 && input.charAt(j - 1) == '-') {
        val_1 += '-';
        flag = true;
      }
      --j;

      if (flag) {
        lower_bound = j - 1;
      } else {
        lower_bound = j;
      }
    }
    StringBuilder reversed = new StringBuilder(val_1).reverse();
    System.out.println("First op: " + val_1);
    val_1 = "";
    stack.push(Integer.parseInt(reversed.toString()));

    j = i + 1;
    if (input.charAt(j) == '-') {
      val_2 += '-';
      ++j;
    } 
    while (j < input.length() && Character.isDigit(input.charAt(j))) {
      val_2 += input.charAt(j);
      ++j;
      upper_bound = j;
    }
    stack.push(Integer.parseInt(val_2));
    val_2 = "";
    while (!stack.isEmpty()) {
      op_2 = stack.pop();
      op_1 = stack.pop();
    }
    switch (symbol) {
      case '+':
        stack.push(op_1 + op_2);
        break;
      case '-':
        stack.push(op_1 - op_2);
        break;
      case '*':
        stack.push(op_1 * op_2);
        break;
      case '/':
        stack.push(op_1 / op_2);
        break;
      default:
        break;
    }
    aux = String.valueOf(stack.pop());
    for (int k = 0; k < lower_bound + 1; ++k) {
      result += input.charAt(k);
    }
    result += aux;
    for (int k = upper_bound; k < input.length(); ++k) {
      result += input.charAt(k);
    }
    System.out.println("Operation: " + op_1 + symbol + op_2 + "= " + result);
    return result;
  }

  public String simplifyString(String input) {
    Stack<Integer> stack = new Stack<>();
    String val_1 = new String();
    String val_2 = new String();
    String aux = new String();
    String par_result = new String();
    String result = new String();
    int j = 0;
    int lower_bound = 0;
    int upper_bound = 0;

    for (int i = 0; i < input.length(); ++i) {
      if (input.charAt(i) == '(') {
        int nParentheses = -1;
        System.out.println("recursivity");
        lower_bound = i;
        j = i + 1;
        while (j < input.length() && nParentheses != 0) {
          if (input.charAt(j) == '(') {
            --nParentheses;
          } else if (input.charAt(j) == ')') {
            ++nParentheses;
          }
          if (nParentheses != 0) {
            aux += input.charAt(j);
            ++j;
            upper_bound = j;
          }
        }
  
        aux = simplifyString(aux);
        
        for (int k = 0; k < lower_bound; ++k) {
          par_result += input.charAt(k);
        }
        par_result += aux;
        System.out.println("Upper Bound: " + upper_bound + " of: " + input);
        for (int k = upper_bound + 1; k < input.length(); ++k) {
          par_result += input.charAt(k);
        }
        input = par_result;
        i = lower_bound + aux.length();
      }
    }
    for (int i = 0; i < input.length(); ++i) {
      if (input.charAt(i) == '*') {
        input = Calculator(input, '*', i);
      }
    }
    for (int i = 0; i < input.length(); ++i) {
      if (input.charAt(i) == '/') {
        input = Calculator(input, '/', i);
      }
    }
    for (int i = 0; i < input.length(); ++i) {
      if (input.charAt(i) == '+') {
        input = Calculator(input, '+', i);
      }
    }
    for (int i = 0; i < input.length(); ++i) {
      if (input.charAt(i) == '-') {
        input = Calculator(input, '-', i);
      }
    }

  
    result = input;
    System.out.println("Result= " + result);

    return result;
  }
}