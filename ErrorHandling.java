// AUTOR: Javier Garcia Santana(El mismisimo coluca)(viva tacoronte, tenerife)
// DATE: 16/4/2024
// EMAIL: javier.santana@tprs.stud.vu.lt
// VERSION: 4.0
// COURSE: OOP
// NAME: Expression Simplifier
// COMMENTS: File where the errorHandling class is declared
//

package expression_simplifier;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorHandling {
  /**
   * Validates the input expression to ensure it contains only valid characters.
   * Throws an IllegalArgumentException if invalid characters are found.
   */
  public static void validateString(String input) {
    String invalidCharRegex = "[^\\d()+\\-*/()]";
    Pattern pattern = Pattern.compile(invalidCharRegex);
    Matcher matcher = pattern.matcher(input);
    if (matcher.find()) {
      throw new IllegalArgumentException("The string contains invalid characters.");
    }
  }

  /**
   * Checks the validity of the expression by ensuring that each number is followed by an operator,
   * and each operator is surrounded by numbers.
   * Throws an IllegalArgumentException if any error is found.
   */
  public static void checkExpressionValidity(String input) {
    boolean lastWasNumber = false;
    boolean lastWasOperator = false;
    boolean lastWasOpeningParenthesis = false;
    boolean lastWasClosingParenthesis = false;

    for (int i = 0; i < input.length(); i++) {
      char currentChar = input.charAt(i);

      if (Character.isDigit(currentChar)) {
        lastWasNumber = true;
        lastWasOperator = false;
        lastWasOpeningParenthesis = false;
        lastWasClosingParenthesis = false;
      } else if (isOperator(currentChar)) {
        if ((lastWasOperator && currentChar != '-') || (lastWasOpeningParenthesis && currentChar != '-') || i == input.length() - 1 || (currentChar == '-' && input.charAt(i + 1) == '-')) {
          throw new IllegalArgumentException("Two operators together at position " + i);
        }
        if (currentChar == '/' && input.charAt(i+1) == '0') {
          throw new IllegalArgumentException("Division by zero not allowed");
        }
        lastWasNumber = false;
        lastWasOperator = true;
        lastWasOpeningParenthesis = false;
        lastWasClosingParenthesis = false;
      } else if (currentChar == '(') {
        if (lastWasNumber || lastWasClosingParenthesis || (i > 0 && input.charAt(i - 1) != '(' && input.charAt(i - 1) != '-' && !isOperator(input.charAt(i - 1)))) {
          throw new IllegalArgumentException("Missing operator before opening parenthesis at position " + i);
        }
        lastWasNumber = false;
        lastWasOperator = false;
        lastWasOpeningParenthesis = true;
        lastWasClosingParenthesis = false;
      } else if (currentChar == ')') {
        if (lastWasOperator || lastWasOpeningParenthesis || i == 0 || input.charAt(i - 1) == '(' || input.charAt(i - 1) == '-' || (i < input.length() - 1 && !isOperator(input.charAt(i + 1)) && input.charAt(i + 1) != ')')) {
          throw new IllegalArgumentException("Missing value around operator at position " + i);
        }
        lastWasNumber = true; // Treat ')' as a number for the purpose of detecting missing operators after it
        lastWasOperator = false;
        lastWasOpeningParenthesis = false;
        lastWasClosingParenthesis = true;
      } else if (currentChar == '-') {
        // If '-' is directly followed by a digit or opening parenthesis, it's a negative sign
        if (i == input.length() - 1 || (Character.isDigit(input.charAt(i + 1)) || input.charAt(i + 1) == '(')) {
          continue;
        }
        if (lastWasNumber || lastWasClosingParenthesis || (i > 0 && input.charAt(i - 1) != '(' && input.charAt(i - 1) != '-' && !isOperator(input.charAt(i - 1)))) {
          throw new IllegalArgumentException("Missing operator after number at position " + i);
        }
        lastWasNumber = false;
        lastWasOperator = true;
        lastWasOpeningParenthesis = false;
        lastWasClosingParenthesis = false;
      } 
    }
  }

  /**
   * Checks the validity of the parentheses by ensuring that they are properly balanced.
   * Throws an IllegalArgumentException if any mismatch is found.
   */
  public static void checkParenthesesValidity(String input) {
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < input.length(); i++) {
      char currentChar = input.charAt(i);
      if (currentChar == '(') {
        stack.push(currentChar);
      } else if (currentChar == ')') {
        if (stack.isEmpty()) {
          throw new IllegalArgumentException("Unmatched closing parenthesis at position " + i);
        }
        stack.pop();
      }
    }
    if (!stack.isEmpty()) {
      throw new IllegalArgumentException("Unmatched opening parenthesis");
    }
 }

  /**
   * Checks if a character is an arithmetic operator (+, -, *, /).
   *
   * @param c The character to be checked
   * @return True if the character is an operator, otherwise false
   */
  public static boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/';
  }
}
