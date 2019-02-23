#include <iostream>
#include <string> 
#include <sstream>
using namespace std;

// function declaration
void build(string, int, int, string, int, string, int, int);

int main(int argc, char *argv[]) {
  string number = argv[1];
  int sum = stoi(argv[2]);
  int intNumberLength = number.length();
  if (intNumberLength == 1) // prevents going into recursive loop
  {
    if (stoi(number) == sum)
    {
      cout << number << "=" << sum;
    }
  } else { // enter recursive loop - once with original string, another with negative string
      build(number, 1, number.at(0) - '0', string(1, number.at(0)), number.at(0) - '0', "", sum, intNumberLength);
      build("-" + number, 2, (number.at(0) - '0') * -1, "-" + string(1, number.at(0)), (number.at(0) - '0') * -1, "", sum, intNumberLength + 1);
  }
  return 0;
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
void build(string number, int index, int runningSum, string expression, int prev, string prevop, int sum, int length) {
  // base case
  if (index == length - 1)
  {
    // evaluate + last operand
    if (runningSum + (number.at(index) - '0') == sum)
    {
      cout << expression << "+" << number.at(index) << "=" << sum << "\n";
    }
    // evaluate - last operand
    if (runningSum - (number.at(index) - '0') == sum)
    {
      cout << expression << "-" << number.at(index) << "=" << sum << "\n";
    }
    // evaluate operand concatenated with previous operand
    int newEnd = stoi(to_string(prev) + number.at(index)); // concatenate previous operand with final digit
    if (prevop.compare("+") == 0 && ((runningSum - prev + newEnd) == sum))
    {
      cout << expression << number.at(index) << "=" << sum << "\n";
    } // if prevop = +, subtract previous operand and add new final operand
    else if (prevop.compare("-") == 0 && ((runningSum + prev - newEnd) == sum))
    {
      cout << expression << number.at(index) << "=" << sum << "\n";
    } // if prevop = -, add previous operand and subtract new final operand
    else if (prevop.compare("") == 0)
    {
      expression += number.at(index);
      if (stoi(expression) == sum) // evaluate entire expression
      {
        cout << expression << "=" << sum << "\n";
      }
    }
  }
  else {
    // + recursive branch
    build(number, index + 1, runningSum + (number.at(index) - '0'), expression + "+" + number.at(index), number.at(index) - '0', "+", sum, length);
    // - recursive branch
    build(number, index + 1, runningSum - (number.at(index) - '0'), expression + "-" + number.at(index), number.at(index) - '0', "-", sum, length);
    // concatenation branch, must recalculate runningSum
    if (prevop.compare("+") == 0) { // subtract previous operand, add new operand
      build(number, index + 1, runningSum - prev + (stoi(to_string(prev) + number.at(index))), expression + "" + number.at(index), (prev * 10) + (number.at(index) - '0'), prevop, sum, length);
    }
    else if (prevop.compare("-") == 0) // add previous operand, subtract new operand
    {
      build(number, index + 1, runningSum + prev - (stoi(to_string(prev) + number.at(index))), expression + "" + number.at(index), (prev * 10) + (number.at(index) - '0'), prevop, sum, length);
    }
    else if (prevop.compare("") == 0) // continue concatenation
    {
      build(number, index + 1, stoi(to_string(prev) + number.at(index)), expression + "" + number.at(index), stoi(to_string(prev) + number.at(index)), prevop, sum, length);
    }
  }
} // end build