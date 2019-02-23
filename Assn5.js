var number = process.argv[2];
var sum = parseInt(process.argv[3]);
var intNumberLength = number.length;
var index = 0;
var runningSum = 0;
var expression = "";
var prev = 0;
var prevop = "";
var length = 0;
var intNumberLength = number.length;


if(intNumberLength == 1) // prevents going into recursive loop
{
	if(parseInt(number) == sum)
	{
		console.log(number + " = " + sum);
	}
}
else // enter recursive loop - once with original string, another with negative string
{
	build(number, 1, parseInt(number.charAt(0)), number.charAt(0),parseInt(number.charAt(0)), "", sum, intNumberLength);
	build("-" + number,2,parseInt(number.charAt(0)) *-1, "-" + number.charAt(0), parseInt(number.charAt(0)) *-1, "",sum, intNumberLength +1);
}

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

function build(number,index,runningSum,expression,prev,prevop,sum,length)
{	
	// base case
	if(index == length-1)
	{
		// evaluate + last operand
		if(runningSum + parseInt(number.charAt(index)) == sum)
		{
			console.log(expression + "+" + number.charAt(index)+ "=" + sum);
		}
		// evaluate - last operand
		if(runningSum - parseInt(number.charAt(index)) == sum)
		{
			console.log(expression + "-" + number.charAt(index) + "=" + sum);
		}
		// evaluate operand concatenated with previous operand
		var newEnd = parseInt(prev.toString() + number.charAt(index)); // concatenate previous operand with final digit
		if(prevop == "+" && (runningSum - prev + newEnd) == sum)
		{
			console.log(expression + number.charAt(index) + "=" + sum);
		} // if prevop = +, subtract previous operand and add new final operand
		else if(prevop == "-" && (runningSum + prev - newEnd) == sum)
		{
			console.log(expression + number.charAt(index) + "=" + sum);
		} // if prevop = -, add previous operand and subtract new final operand
		else if(prevop == "")
		{
			expression += number.charAt(index);
			if(parseInt(expression == sum)) // evaluate entire expression
			{
				console.log(expression + "=" + sum);
			}
		}
		
	}
	else
	{
		// + recursive branch
		build(number, index + 1, runningSum + parseInt(number.charAt(index)), expression + "+" + number.charAt(index), parseInt(number.charAt(index)), "+",sum, length);
		// - recursive branch
		build(number, index + 1, runningSum - parseInt(number.charAt(index)), expression + "-" +number.charAt(index), parseInt(number.charAt(index)), "-", sum, length);
		// concatenation branch, must recalculate runningSum
		if(prevop == "+") // subtract previous operand, add new operand
		{
			build(number, index + 1, runningSum - prev + parseInt(prev.toString() + number.charAt(index)),expression + "" + number.charAt(index),parseInt(prev.toString() + number.charAt(index)),prevop,sum,length);
		}
		else if(prevop == "-") // add previous operand, subtract new operand
		{
			build(number, index + 1, runningSum + prev - parseInt(prev.toString() + number.charAt(index)),expression + "" + number.charAt(index),parseInt(prev.toString() + number.charAt(index)),prevop,sum,length);
		}
		else if(prevop == "") // continue concatenation
		{
			build(number, index +1, parseInt(runningSum.toString() + number.charAt(index)), expression + "" +number.charAt(index),parseInt(prev.toString() + number.charAt(index)),prevop,sum,length);
		}
	}
	
} // end build