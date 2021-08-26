package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), InputFragment.inputFragmentListener{

    lateinit var inputFragment: InputFragment
    lateinit var outputFragment: OutputFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputFragment = InputFragment()
        outputFragment = OutputFragment()
        supportFragmentManager.beginTransaction().replace(R.id.inputLayout, inputFragment)
            .replace(R.id.outputLayout, outputFragment)
            .commit()
    }

    override fun onInputSent(input: String) {
        outputFragment.updateInputText(input)
    }

    override fun onOutputSent(input:String, output: String) {
        outputFragment.updateOutputText(output)
        outputFragment.updateInputText(input)
    }
}