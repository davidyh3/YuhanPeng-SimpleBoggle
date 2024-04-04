package com.example.simpleboggle

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.simpleboggle.databinding.FragmentUpperBinding
import com.example.simpleboggle.ui.ActivityCallback
import com.example.simpleboggle.ui.TopFragmentListener
import com.example.simpleboggle.utils.CharacterGenerator

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TopFragment : Fragment() ,TopFragmentListener{
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentUpperBinding
    private lateinit var activityCallback: ActivityCallback
    private lateinit var characterGenerator: CharacterGenerator



    private var pressed=mutableSetOf<String>()
    private var lastrow:Int=-1
    private var lastcol:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        characterGenerator= CharacterGenerator()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpperBinding.inflate(inflater,container,false);
        binding.clearButton.setOnClickListener {
            clearResultText()
        }
        randomizeCharacters()
        bindButtonGroup()
        return binding.root
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        activityCallback=activity as ActivityCallback

    }

    private fun bindButtonGroup(){
        for(i in 0 until  4)
        {
            val view = binding.buttonContainer.getChildAt(i) as TableRow
            for(j in 0 until 4)
            {
                var button=view.get(j) as Button
                button.setOnClickListener {
                    var arr=getButtonIndex(button)
                    if(validClick(arr))
                    {
                        var str=binding.resultText.text.toString()
                        str+=button.text.toString()
//                        button.setBackgroundColor(getResources().getColor(R.color.gray))
                        button.isEnabled=false
                        button.isClickable=false
                        binding.resultText.setText(str)
                        lastrow=arr[0]
                        lastcol=arr[1]
                        pressed.add(arr[0].toString()+"_"+arr[1].toString())
                    }else
                    {
                        Toast.makeText(context, "your selection is invalid", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.submitButton.setOnClickListener {
            activityCallback.checkWord(binding.resultText.text.toString())
        }
    }
    override fun randomizeCharacters(){
        for(i in 0 until  4)
        {
            val view = binding.buttonContainer.getChildAt(i) as TableRow
            for(j in 0 until 4)
            {
                var button=view.get(j) as Button
                button.setText(characterGenerator.generateRandomChar().toString())
            }
        }
    }

    override fun resetButtonsAvailability() {
        for(i in 0 until  4)
        {
            val view = binding.buttonContainer.getChildAt(i) as TableRow
            for(j in 0 until 4)
            {
                var button=view.get(j) as Button
                button.isClickable=true
                button.isEnabled=true
            }
        }
        pressed.clear()
        lastrow=-1;
        lastcol=-1
    }

    private fun getButtonIndex(tgt_button: Button):Array<Int>
    {
        for(i in 0 until  4)
        {
            val view = binding.buttonContainer.getChildAt(i) as TableRow
            for(j in 0 until 4)
            {
                var button=view.get(j) as Button
                if(button.equals(tgt_button))
                {
                    return arrayOf(i,j);
                }

            }
        }
        return Array(2){-1};
    }

    private fun validClick(currCord:Array<Int>):Boolean{
        if(lastcol==-1&&lastrow==-1)
            return true
        var coordstr=currCord[0].toString()+"_"+currCord[1].toString()
        if(!(currCord[0]==lastrow && currCord[1]==lastcol) &&
            (Math.abs(currCord[0]-lastrow)<=1 && Math.abs(currCord[1]-lastcol)<=1)&&!pressed.contains(coordstr))
            return true
        return false
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun clearResultText() {
        binding.resultText.setText("")
        pressed.clear()
        lastrow=-1;
        lastcol=-1
        resetButtonsAvailability()

    }
}