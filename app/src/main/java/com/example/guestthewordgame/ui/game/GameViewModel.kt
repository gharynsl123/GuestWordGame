package com.example.guestthewordgame.ui.game

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // The current word
    var word = ""

    // The current score
    var score = 0

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.random().toCharArray().shuffle()
    }

    init {
        Log.i(TAG, "GameViewModel created!")
        resetList()
        nextWord()
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "GameViewModel destroyed!")
    }

    /** Methods for updating the UI **/
    fun onSkip() {
        score--
        nextWord()
    }
    fun onCorrect() {
        if (score != 20){
            score++
        }
        nextWord()
    }

    /**
     * Moves to the next word in the list.
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (!wordList.isEmpty()) {
            word = wordList.removeAt(0)
        }
    }

    companion object {
        const val TAG = "GameViewModel"
    }
}