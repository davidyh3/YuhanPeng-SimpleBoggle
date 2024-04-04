package com.example.simpleboggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.simpleboggle.databinding.ActivityMainBinding
import com.example.simpleboggle.ui.ActivityCallback
import com.example.simpleboggle.ui.BottomFragmentListener
import com.example.simpleboggle.ui.TopFragmentListener
import com.example.simpleboggle.utils.DictionaryManager
import com.example.simpleboggle.utils.ScoreEvaluator
import com.example.simpleboggle.utils.WordInspector

class MainActivity : AppCompatActivity(), ActivityCallback {

    private var score:Int=0
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomListener: BottomFragmentListener
    private lateinit var topListener: TopFragmentListener
    private lateinit var dictManager: DictionaryManager
    private var usedWords:MutableSet<String> = mutableSetOf<String>()
    private lateinit var dictionary: HashSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        dictManager = DictionaryManager()
        dictionary = dictManager.readFileFromAssetManager("words.txt",this)
    }

    private fun initializeFragmentListeners(){
        bottomListener = supportFragmentManager.findFragmentById(R.id.fragment_bottom) as BottomFragmentListener
        topListener = supportFragmentManager.findFragmentById(R.id.fragment_top) as TopFragmentListener
    }

    override fun transmitScore(score: Int) {
        initializeFragmentListeners()
        this.score=score
        Log.d(this.localClassName,score.toString())
        bottomListener.updateScoreText(score)
        topListener.clearResultText()
    }

    override fun resetGame() {
        initializeFragmentListeners()
        score=0;
        topListener.clearResultText()
        topListener.randomizeCharacters()
        topListener.resetButtonsAvailability()
        usedWords.clear()
    }

    override fun checkWord(word: String) {
        initializeFragmentListeners()
        var checker= WordInspector()
        var res=checker.examineWord(word, usedWords )
        var points=0
        var success=res.get(200)
        if(success!=null)
        {
            Toast.makeText(this, success, Toast.LENGTH_SHORT).show()
            points= ScoreEvaluator().evaluateWordPoints(dictionary,word)
        }else
        {
            Toast.makeText(this, res.get(400), Toast.LENGTH_SHORT).show()
            points=-10
        }
        if(points>0)
            usedWords.add(word)
        score+=points
        bottomListener.updateScoreText(score)
    }
}