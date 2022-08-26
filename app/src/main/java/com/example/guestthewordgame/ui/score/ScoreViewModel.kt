package com.example.guestthewordgame.ui.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {
    // The final score
    var score = finalScore
}