package com.example.simpleboggle.utils

class ScoreEvaluator {

    fun evaluateWordPoints(dic:HashSet<String>, word: String): Int{
        if (!isWordInList(dic,word.lowercase())){
            return -10
        }else{
            return computeWordValue(word)
        }
    }

    private fun computeWordValue(word: String):Int{
        var doubleScore = 1
        var totalValue = 0
        val doubleScoreConsonants = mutableSetOf<Char>('S','Z','P','X','Q')
        val vowels = mutableSetOf<Char>('A','E','I','O','U')
        for (i in word){
            if (vowels.contains(i)) {
                totalValue += 5
            }else{
                totalValue += 1
                if (doubleScoreConsonants.contains(i)){
                    doubleScore = 2
                }
            }
        }
        return totalValue * doubleScore
    }

    private fun isWordInList(dic: HashSet<String>, word: String):Boolean{
        return dic.contains(word)
    }
}