// AUTOR: Javier Garcia Santana(El mismisimo coluca)(viva tacoronte,tnf)
// DATE: 16/4/2024
// EMAIL: javier.santana@tprs.stud.vu.lt
// VERSION: 4.0
// COURSE: OOP
// NAME: Expression Simplifier
// COMMENTS: File where the exprSimplifier class is instantiated
//

package expression_simplifier;

public class main {
  public static void main(String[] args) { 
    try {

      String input = "8+9 * 15-(-7/3 +7 * 8)/2+ 3 * 7";
      exprSimplifier simplified_expr = new exprSimplifier(input);
     
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
