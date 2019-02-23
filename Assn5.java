public class Main {
  public static void main(String[] args) {
    String number = args[0];
    int sum = Integer.parseInt(args[1]);
    int intNumberLength = number.length();
    if (intNumberLength == 1) { // prevents going into recursive loop
      if (Integer.parseInt(number) == sum) {
        System.out.println(number + "=" + sum);
      }
    } else { // enter recursive loop - once with original string, another with negative
             // string
      build(number, 1, Character.getNumericValue(number.charAt(0)), Character.toString(number.charAt(0)),
          Character.getNumericValue(number.charAt(0)), "", sum, intNumberLength);
      build("-" + number, 2, Character.getNumericValue(number.charAt(0)) * -1,
          "-" + Character.toString(number.charAt(0)), Character.getNumericValue(number.charAt(0)) * -1, "", sum,
          intNumberLength + 1);
    }

  } // end main

  /*
   * Create and evaluate expressions through recursive method.
   * number = original string
   * index = current index in number string
   * runningSum = evaluted sum of current expression
   * expression = string representation of current combination of operators+operands
   * prev = most recent operand
   * prevop = previous operator (+, -, or _/concatenation)
   * sum = target sum for comparison
   * length = length of number string
   */
  public static void build(String number, int index, int runningSum, String expression, int prev, String prevop,
      int sum, int length) {
    // base case
    if (index == length - 1) {
      // evaluate + last operand
      if (runningSum + Character.getNumericValue(number.charAt(index)) == sum) {
        System.out.println(expression + "+" + number.charAt(index) + "=" + sum);
      }
      // evaluate - last operand
      if (runningSum - Character.getNumericValue(number.charAt(index)) == sum) {
        System.out.println(expression + "-" + number.charAt(index) + "=" + sum);
      }
      // evaluate operand concatenated with previous operand
      int newEnd = Integer.parseInt(Integer.toString(prev) + Character.toString(number.charAt(index))); // concatenate previous operand with final digit
      if (prevop.equals("+") && (runningSum - prev + newEnd) == sum) {
        System.out.println(expression + Character.toString(number.charAt(index)) + "=" + sum); // if prevop = +, subtract previous operand and add new final operand
      } else if (prevop.equals("-") && (runningSum + prev - newEnd) == sum) {
        System.out.println(expression + Character.toString(number.charAt(index)) + "=" + sum); // if prevop = -, add previous operand and subtract new final operand
      } else if (prevop.equals("")) { // this means all operators are blank
        expression += Character.toString(number.charAt(index));
        if (Integer.parseInt(expression) == sum) {
          System.out.println(expression + "=" + sum); // evaluate entire expression
        }
      }

    } else {
      // + recursive branch
      build(number, index + 1, runningSum + Character.getNumericValue(number.charAt(index)),
          expression + "+" + number.charAt(index), Character.getNumericValue(number.charAt(index)), "+", sum, length);
      // - recursive branch
      build(number, index + 1, runningSum - Character.getNumericValue(number.charAt(index)),
          expression + "-" + number.charAt(index), Character.getNumericValue(number.charAt(index)), "-", sum, length);
      // concatenation branch, must recalculate runningSum
      if (prevop.equals("+")) { // subtract previous operand, add new operand
        build(number, index + 1, runningSum - prev + Integer.parseInt(Integer.toString(prev) + number.charAt(index)),
            expression + "" + number.charAt(index), Integer.parseInt(Integer.toString(prev) + number.charAt(index)),
            prevop, sum, length);
      } else if (prevop.equals("-")) { // add previous operand, subtract new operand
        build(number, index + 1, runningSum + prev - Integer.parseInt(Integer.toString(prev) + number.charAt(index)),
            expression + "" + number.charAt(index), Integer.parseInt(Integer.toString(prev) + number.charAt(index)),
            prevop, sum, length);
      } else if (prevop.equals("")) { // continue concatenation
        build(number, index + 1,
            Integer.parseInt(Integer.toString(runningSum) + Character.toString(number.charAt(index))),
            expression + "" + number.charAt(index),
            Integer.parseInt(Integer.toString(prev) + Character.toString(number.charAt(index))), prevop, sum, length);
      }
    }
  } // end build
}