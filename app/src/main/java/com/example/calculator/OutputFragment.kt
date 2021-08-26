package com.example.calculator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculator.databinding.FragmentInputBinding
import com.example.calculator.databinding.FragmentOutputBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.RuntimeException

class OutputFragment : Fragment() {
    private var _binding: FragmentOutputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOutputBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    fun updateInputText(input:String){
        binding.inputView.text = input
        //binding.outputView.text = ""
    }
    fun updateOutputText(output:String){
        binding.outputView.text = output
        //binding.inputView.text = ""
    }
    fun clearTexts(){
        binding.outputView.text = ""
        binding.inputView.text = ""
    }

}