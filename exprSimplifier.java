// AUTOR: Javier Garcia Santana(El mismisimo coluca)(viva tacoronte,tnf)
// DATE: 16/4/2024
// EMAIL: javier.santana@tprs.stud.vu.lt
// VERSION: 4.0
// COURSE: OOP
// NAME: Expression Simplifier
// COMMENTS: File where the exprSimplifier class is declared
//

package expression_simplifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

public class ExprSimplifier {
  private String input;
  private String result;

  /**
   * Constructor for ExprSimplifier class.
   * Initializes the input expression, removes spaces, validates the input,
   * and simplifies the expression.
   *
   * @param input The expression to be simplified
   */
  public ExprSimplifier(String input) {
    this.input = input.replaceAll("\\s", "");
    validateString();
    result = simplifyString(this.input);
  }

  /**
   * Validates the input expression to ensure it contains only valid characters.
   * Throws an IllegalArgumentException if invalid characters are found.
   */
  private void validateString() {
    String invalidCharRegex = "[^\\d()+\\-*/()]";
    Pattern pattern = Pattern.compile(invalidCharRegex);
    Matcher matcher = pattern.matcher(input);
    if (matcher.find()) {
      throw new IllegalArgumentException("The string contains invalid characters.");
    }
  }

  /**
   * Checks if a character is an arithmetic operator (+, -, *, /).
   *
   * @param c The character to be checked
   * @return True if the character is an operator, otherwise false
   */
  private boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/';
  }

  /**
   * Evaluates an arithmetic operation in the input expression.
   *
   * @param input  The input expression
   * @param symbol The operator symbol (+, -, *, /)
   * @param index  The index of the operator in the expression
   * @return The input expression with the operation result
   */
  private String evaluateOperation(String input, char symbol, int index) {
    System.out.println("Calculator input: " + input + "  i: " + index);

    Stack<Integer> stack = new Stack<>();
    String val_1 = new String();
    String val_2 = new String();
    boolean flag = false;
    int j = index - 1;
    int op_1, op_2 = 0;
    int lower_bound = 0;
    int upper_bound = 0;
    
    // Extract operand 1
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
    val_1 = "";
    op_1 = (Integer.parseInt(reversed.toString()));

    j = index + 1;
    if (input.charAt(j) == '-') {
      val_2 += '-';
      ++j;
    } 

    // Extract operand 2
    while (j < input.length() && Character.isDigit(input.charAt(j))) {
      val_2 += input.charAt(j);
      ++j;
      upper_bound = j;
    }
    op_2 = (Integer.parseInt(val_2));
    val_2 = "";

    // Perform the operation
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
    // Replace the operation in the input expression with the result
    String result = stack.pop().toString();
    result = input.substring(0, lower_bound + 1) + result + input.substring(upper_bound);
 
    System.out.println("Operation: " + op_1 + symbol + op_2 + "= " + result);
    return result;
  }

  /**
   * Simplifies the input expression recursively.
   * Handles parentheses and arithmetic operations.
   *
   * @param input The input expression to be simplified
   * @return The simplified expression
   */
  private String simplifyString(String input) {
    String result = input;
    for (int i = 0; i < result.length(); i++) {
      char c = result.charAt(i);
      if (c == '(') {
        int nParentheses = 1;
        int j = i + 1;
        while (j < result.length() && nParentheses != 0) {
          if (result.charAt(j) == '(') {
            nParentheses++;
          } else if (result.charAt(j) == ')') {
            nParentheses--;
          }
          j++;
        }
        System.out.println("Substring= " + result.substring(i + 1, j - 1));

        String simplifiedExpression = simplifyString(result.substring(i + 1, j - 1));
        result = result.substring(0, i) + simplifiedExpression + result.substring(j);
        System.out.println("Result= " + result);
      }
    }

    for (char op : new char[]{'*', '/', '+', '-'}) {
      for (int i = 0; i < result.length(); i++) {
        if (result.charAt(i) == op) {
          result = simplifyString(evaluateOperation(result, op, i));
          break;
        }
      }
    }
    return result;
  }

  /**
   * Returns the result of the expression simplification.
   *
   * @return The simplified expression.
   */
  public String getResult() {
    return result;
  }
}