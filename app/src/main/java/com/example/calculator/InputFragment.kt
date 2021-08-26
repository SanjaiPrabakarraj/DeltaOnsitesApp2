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

class InputFragment : Fragment() {
    var listener: inputFragmentListener? = null
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    var lastNumeric: Boolean = false
    var stateError: Boolean = false
    var lastDot: Boolean = false
    var inputString = ""
    var outputString = ""

    interface inputFragmentListener{
        fun onInputSent(input: String)
        fun onOutputSent(input: String)
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
            listener!!.onInputSent(inputString)
        }
        binding.int1Button.setOnClickListener {
            onDigit("1")
            listener!!.onInputSent(inputString)
        }
        binding.int2Button.setOnClickListener {
            onDigit("2")
            listener!!.onInputSent(inputString)
        }
        binding.int3Button.setOnClickListener {
            onDigit("3")
            listener!!.onInputSent(inputString)
        }
        binding.int4Button.setOnClickListener {
            onDigit("4")
            listener!!.onInputSent(inputString)
        }
        binding.int5Button.setOnClickListener {
            onDigit("5")
            listener!!.onInputSent(inputString)
        }
        binding.int6Button.setOnClickListener {
            onDigit("6")
            listener!!.onInputSent(inputString)
        }
        binding.int7Button.setOnClickListener {
            onDigit("7")
            listener!!.onInputSent(inputString)
        }
        binding.int8Button.setOnClickListener {
            onDigit("8")
            listener!!.onInputSent(inputString)
        }
        binding.int9Button.setOnClickListener {
            onDigit("9")
            listener!!.onInputSent(inputString)
        }
        binding.equalButton.setOnClickListener {
            onEqual()
            listener!!.onOutputSent(outputString)
        }
        binding.divideButton.setOnClickListener {
            onOperator("/")
            listener!!.onInputSent(inputString)
        }
        binding.addButton.setOnClickListener {
            onOperator("+")
            listener!!.onInputSent(inputString)
        }
        binding.subtractButton.setOnClickListener {
            onOperator("-")
            listener!!.onInputSent(inputString)
        }
        binding.multiplyButton.setOnClickListener {
            onOperator("*")
            listener!!.onInputSent(inputString)
        }
        binding.clearInputButton.setOnClickListener {
            onClear()
            listener!!.onInputSent(inputString)
            listener!!.onOutputSent(outputString)
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
        if (inputString == outputString){
            inputString = ""
        }
        if (stateError) {
            inputString = text
            stateError = false
        } else {
            inputString += text
        }
        lastNumeric = true
        outputString = ""
    }

    fun onDecimalPoint() {
        if (lastNumeric && !stateError && !lastDot) {
            inputString += "."
            lastNumeric = false
            lastDot = true
        }
        outputString = ""
    }

    fun onOperator(text: String) {
        if (lastNumeric && !stateError) {
            inputString += text
            lastNumeric = false
            lastDot = false
        }
        outputString = ""
    }

    fun onClear() {
        inputString = ""
        outputString = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual() {
        if (lastNumeric && !stateError) {
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
        }
    }

}