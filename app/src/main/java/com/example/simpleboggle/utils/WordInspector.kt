package com.example.simpleboggle.utils

private val VOWELS = hashSetOf<Char>('A','E','I','O','U')
private val NOT_MEET_WORD_LENGTH_MESSAGE = "Error, word must be at least 4 chars long"
private val NOT_ENOUGH_VOWELS = "Error, word must contain minimum of 2 vowels"
private val REPEATED_WORD = "Error, you have entered this word before"
private val CORRECT_WORD_FORMAT = "Word's format is correct"
private val ERROR_CODE = 400
private val SUCCESS_CODE = 200

class WordInspector {

    fun examineWord(word:String, answeredWords:MutableSet<String>): HashMap<Int, String>{
        if (!isLengthSufficient(word,4)){
            return hashMapOf(ERROR_CODE to NOT_MEET_WORD_LENGTH_MESSAGE)
        }
        if (!hasEnoughVowels(word,2)){
            return hashMapOf(ERROR_CODE to NOT_ENOUGH_VOWELS)
        }
        if (isWordPreviouslySubmitted(answeredWords,word)){
            return hashMapOf(ERROR_CODE to REPEATED_WORD)
        }
        return hashMapOf(SUCCESS_CODE to CORRECT_WORD_FORMAT)
    }

    fun hasEnoughVowels(word: String, charNumRequirement:Int): Boolean{
        var count = 0
        for (i in word){
            if(VOWELS.contains(i)){
                count += 1
            }
        }
        return count >= charNumRequirement
    }

    fun isLengthSufficient(word:String, numOfCharRequirement:Int): Boolean{
        return word.length >= numOfCharRequirement
    }

    fun isWordPreviouslySubmitted(previousWords: MutableSet<String>, currentWord: String):Boolean{
        if (previousWords.contains(currentWord)){
            return true
        }
        for (word in previousWords){
            if (isAnagram(word,currentWord)){
                return true
            }
        }
        return false
    }

    fun isAnagram(firstWord: String, secondWord:String):Boolean{
        val oldWordHashSet = hashSetOf<Char>()
        for (i in firstWord){
            oldWordHashSet.add(i)
        }
        val newWordHashSet = hashSetOf<Char>()
        for (j in secondWord){
            newWordHashSet.add(j)
        }
        if (oldWordHashSet.size != newWordHashSet.size){
            return false
        }
        for(c in newWordHashSet){
            if (!oldWordHashSet.contains(c)){
                return false
            }
        }
        return true
    }
}