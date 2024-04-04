package com.example.simpleboggle

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simpleboggle.databinding.FragmentLowerBinding
import com.example.simpleboggle.ui.ActivityCallback
import com.example.simpleboggle.ui.BottomFragmentListener

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BottomFragment : Fragment() , BottomFragmentListener{
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentLowerBinding
    private lateinit var activityCallback: ActivityCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLowerBinding.inflate(inflater,container,false);
        binding.newGameButton.setOnClickListener {
            newGame()
        }
        val view = binding.root
        return view
//        return inflater.inflate(R.layout.fragment_lower, container, false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        activityCallback=activity as ActivityCallback
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun newGame(){
        binding.scoreText.setText("0");
        activityCallback.resetGame()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun updateScoreText(score:Int) {
        binding!!.scoreText.setText(score.toString())
    }
}