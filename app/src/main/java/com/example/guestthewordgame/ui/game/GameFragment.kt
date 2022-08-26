package com.example.guestthewordgame.ui.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.guestthewordgame.R
import com.example.guestthewordgame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    //Associate GameViewModel
    private lateinit var viewModel: GameViewModel

    //Make ViewBindings
    private var _binding : FragmentGameBinding? = null
    private val binding get() = _binding as FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.apply {
            correctButton.setOnClickListener { onCorrect() }
            skipButton.setOnClickListener { onSkip() }
            endGameButton.setOnClickListener { onEndGame() }
            updateScoreText()
            updateWordText()
        }

    }

    /** Methods for buttons presses **/

    private fun onSkip() {
        viewModel.onSkip()
        updateWordText()
        updateScoreText()
    }
    private fun onCorrect() {
        viewModel.onCorrect()
        updateScoreText()
        updateWordText()
    }

    private fun onEndGame() {
        gameFinished()
    }

    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = viewModel.word

    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.toString()
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameGameToScoreGame()
        action.score = viewModel.score
        NavHostFragment.findNavController(this).navigate(action)
    }

}