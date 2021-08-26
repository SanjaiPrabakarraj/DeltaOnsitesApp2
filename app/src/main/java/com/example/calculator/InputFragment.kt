package com.example.calculator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import com.example.calculator.databinding.FragmentInputBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.RuntimeException
import java.util.*

class InputFragment : Fragment() {
    var listener: inputFragmentListener? = null
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    var newOp = true
    var lastEqual = false
    var inputString = ""
    var outputString = ""
    var intermediateString = ""
    var operation = "+"
    var prevOp = "+"

    interface inputFragmentListener{
        fun onInputSent(input: String)
        fun onOutputSent(input: String, output: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.int0Button.setOnClickListener {
            onDigit("0")
            //listener!!.onInputSent(inputString)
        }
        binding.int1Button.setOnClickListener {
            onDigit("1")
            //listener!!.onInputSent(inputString)
        }
        binding.int2Button.setOnClickListener {
            onDigit("2")
            //listener!!.onInputSent(inputString)
        }
        binding.int3Button.setOnClickListener {
            onDigit("3")
            //listener!!.onInputSent(inputString)
        }
        binding.int4Button.setOnClickListener {
            onDigit("4")
            //listener!!.onInputSent(inputString)
        }
        binding.int5Button.setOnClickListener {
            onDigit("5")
            //listener!!.onInputSent(inputString)
        }
        binding.int6Button.setOnClickListener {
            onDigit("6")
            //listener!!.onInputSent(inputString)
        }
        binding.int7Button.setOnClickListener {
            onDigit("7")
            //listener!!.onInputSent(inputString)
        }
        binding.int8Button.setOnClickListener {
            onDigit("8")
            //listener!!.onInputSent(inputString)
        }
        binding.int9Button.setOnClickListener {
            onDigit("9")
            //listener!!.onInputSent(inputString)
        }
        binding.equalButton.setOnClickListener {
            onEqual()
            listener!!.onOutputSent(outputString, intermediateString)
            intermediateString = ""
        }
        binding.divideButton.setOnClickListener {
            prevOp = operation
            operation = "/"
            onOperator()
            //listener!!.onInputSent(inputString)
        }
        binding.addButton.setOnClickListener {
            prevOp = operation
            operation = "+"
            onOperator()
            //listener!!.onInputSent(inputString)
        }
        binding.subtractButton.setOnClickListener {
            prevOp = operation
            operation = "-"
            onOperator()
            //listener!!.onInputSent(inputString)
        }
        binding.multiplyButton.setOnClickListener {
            prevOp = operation
            operation = "*"
            onOperator()
            //listener!!.onInputSent(inputString)
        }
        binding.clearInputButton.setOnClickListener {
            onClear()
            //listener!!.onInputSent(inputString)
            listener!!.onOutputSent(inputString, outputString)
        }
        binding.decimalButton.setOnClickListener {
            onDecimalPoint()
            listener!!.onInputSent(inputString)
        }
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is inputFragmentListener){
            listener = context
        }
        else {
            throw RuntimeException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun onDigit(text:String) {
        if (lastEqual){
            inputString = ""
            outputString = ""
        }
        if (stateError) {
            inputString = text
            stateError = false
        } else{
            inputString += text
        }
        if (newOp){
            outputString += text
        }
        lastNumeric = true
        intermediateString += text
        listener!!.onOutputSent(inputString, intermediateString)
        lastEqual = false
        binding.decimalButton.isEnabled = true
    }

    fun onDecimalPoint() {
        if (lastNumeric && !stateError && !lastDot) {
            inputString += "."
            intermediateString += "."
            lastNumeric = false
            lastDot = true
            listener!!.onOutputSent(inputString, intermediateString)
        }
    }

    fun onOperator() {
        if (lastNumeric && !stateError) {
            if(newOp){
                if (lastEqual) {
                    inputString = outputString
                    intermediateString += inputString
                }
                outputString = inputString
                inputString = ""
                lastNumeric = false
                lastDot = false
                newOp = false
                intermediateString += operation
                prevOp = operation
            } else {
                var temp = operation
                operation = prevOp
                onEqual()
                operation = temp
                inputString = outputString
                intermediateString = outputString
                outputString = inputString
                inputString = ""
                lastNumeric = false
                lastDot = false
                newOp = false
                intermediateString += operation
                prevOp = operation
            }
        }
        listener!!.onOutputSent(inputString, intermediateString)
        lastEqual = false
    }

    fun onClear() {
        inputString = ""
        outputString = ""
        intermediateString = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual() {
        if (lastNumeric && !stateError) {
            var result = 0.0
            if (lastEqual){
                intermediateString = "Ans" + operation + inputString
            }
            when(operation){
                "+" -> {result = outputString.toDouble() + inputString.toDouble()}
                "-" -> {result = outputString.toDouble() - inputString.toDouble()}
                "*" -> {result = outputString.toDouble() * inputString.toDouble()}
                "/" -> {result = outputString.toDouble() / inputString.toDouble()}
            }
            outputString = result.toString()
            //inputString = result.toString()
            newOp = true
            intermediateString += "=" + outputString
            lastEqual = true
            binding.decimalButton.isEnabled = false
            /*
            val expression = ExpressionBuilder(inputString).build()
            try {
                val result = expression.evaluate()
                outputString = result.toString()
                inputString = outputString
                lastDot = true
            } catch (ex: ArithmeticException) {
                outputString = "Error"
                stateError = true
                lastNumeric = false
            }

             */
        }
    }

    fun Prec(ch: Char): Int {
        when (ch) {
            '+', '-' -> return 1
            '*', '/' -> return 2
            '^' -> return 3
        }
        return -1
    }

    fun infixToPostfix(exp: String): String {
        // initializing empty String for result
        var result: String = ""

        // initializing empty stack
        val stack: Stack<Char> = Stack()
        for (i in 0 until exp.length) {
            val c = exp[i]

            // If the scanned character is an
            // operand, add it to output.
            if (Character.isLetterOrDigit(c)) result += c else if (c == '(') stack.push(c) else if (c == ')') {
                while (!stack.isEmpty() &&
                    stack.peek() !== '('
                ) result += stack.pop()
                stack.pop()
            } else  // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(c)
                    <= Prec(stack.peek())
                ) {
                    result += stack.pop()
                }
                stack.push(c)
            }
        }

        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() === '(') return "Invalid Expression"
            result += stack.pop()
        }
        return result
    }

    fun evaluatePostfix(exp: String): Int {
        //create a stack
        val stack: Stack<Int> = Stack()

        // Scan all characters one by one
        for (i in 0 until exp.length) {
            val c = exp[i]

            // If the scanned character is an operand (number here),
            // push it to the stack.
            if (Character.isDigit(c)) stack.push(c - '0') else {
                val val1: Int = stack.pop()
                val val2: Int = stack.pop()
                when (c) {
                    '+' -> stack.push(val2 + val1)
                    '-' -> stack.push(val2 - val1)
                    '/' -> stack.push(val2 / val1)
                    '*' -> stack.push(val2 * val1)
                }
            }
        }
        return stack.pop()
    }

    fun operatorEvent(view: View){

    }

}