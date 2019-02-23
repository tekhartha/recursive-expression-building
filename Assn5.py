# -*- coding: utf-8 -*-
import sys

number = sys.argv[1]
sum1 = int(sys.argv[2])
intNumberLength = len(number)
index = 0
runningSum = 0
expression = ""
prev = 0
prevop = ""

'''
* Create and evaluate expressions through recursive method.
   * number = original string
   * index = current index in number string
   * runningSum = evaluted sum of current expression
   * expression = string representation of current combination of operators+operands
   * prev = most recent operand
   * prevop = previous operator (+, -, or _/concatenation)
   * sum = target sum for comparison
   * length = length of number string
'''

def recursion(number,index,runningSum,expression,prev,prevop,sum1,length):
    if (index == length -1): # base case
        if (runningSum + int(number[index]) == sum1): # evaluate + last operand
            print(expression + "+" + str(number[index]) + "=" + str(sum1))
        if (runningSum - int(number[index]) == sum1): # evaluate - last operand
            print(expression + "-" + str(number[index]) + "=" + str(sum1))
		# evaluate operand concatenated with previous operand
        newEnd = int(str(prev) + str(number[index])) # concatenate previous operand with final digit
        if (prevop == "+" and (runningSum - prev + newEnd == sum1)): # if prevop = +, subtract previous operand and add new final operand
            print(expression + str(number[index]) + "=" + str(sum1))
        elif (prevop == "-" and (runningSum + prev - newEnd == sum1)): # if prevop = -, add previous operand and subtract new final operand
            print(expression+str(number[index]) + "=" + str(sum1))
        elif prevop == "": 
            expression += number[index]
            if int(expression) == sum1: # evaluate entire expression
                print(expression + "=" + str(sum1))
    else:
		# + recursive branch
        recursion(number, index +1, runningSum + int(number[index]), expression + "+" + str(number[index]),int(number[index]),"+",sum1,length)
		# - recursive branch
        recursion(number, index +1, runningSum - int(number[index]), expression + "-" + str(number[index]),int(number[index]),"-",sum1,length)
		# concatenation branch, must recalculate runningSum
        if prevop == "+": # subtract previous operand, add new operand
            recursion(number, index+1, runningSum - prev+ (int(str(prev) + number[index])),expression + ""+ number[index],(int(str(prev) + number[index])),prevop,sum1,length)
        elif prevop == "-": # add previous operand, subtract new operand
            recursion(number, index+1, runningSum+prev-(int(str(prev) + number[index])),expression + ""+number[index],(int(str(prev) + number[index])),prevop,sum1,length)
        elif prevop == "": # continue concatenation
            recursion(number,index+1, int(str(runningSum)+number[index]),expression +""+number[index],int(str(prev)+number[index]),prevop,sum1,length)

if(intNumberLength == 1): # prevents going into recursive loop
	if(int(number) == sum1):
		print(number + "=" + str(sum1))
else: # enter recursive loop - once with original string, another with negative string
    recursion(number,1,int(number[0]),number[0],int(number[0]),"",sum1,intNumberLength)
    recursion("-" + number, 2, int(number[0]) *-1, "-" + number[0], int(number[0])*-1,"",sum1,intNumberLength+1)



