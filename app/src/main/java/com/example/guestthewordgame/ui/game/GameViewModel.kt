package com.example.guestthewordgame.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // The current word
    //Encapsulation The Word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    //Encapsulation The Score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    // Game finished
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

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
        _word.value = ""
        _score.value = 0

        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "GameViewModel destroyed!")
    }

    /** Methods for updating the UI **/
    fun onSkip() {
        if (score.value != 0) {
            _score.value = (score.value)?.minus(1)
            resetList()
        }
    }

    fun onCorrect() {
        if (score.value != 20) {
            _score.value = (score.value)?.plus(1)

            nextWord()
        }
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isNotEmpty()) {
            _word.value = wordList.removeAt(0)
        } else {
            onGameFinish()
        }
    }

    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    companion object {
        const val TAG = "GameViewModel"
    }
}