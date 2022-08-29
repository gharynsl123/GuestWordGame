package com.example.guestthewordgame.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.guestthewordgame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    //Associate GameViewModel
    private lateinit var viewModel: GameViewModel

    //Make ViewBindings
    private var _binding: FragmentGameBinding? = null
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

        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }

        viewModel.word.observe(viewLifecycleOwner) { newWord ->
            binding.wordText.text = newWord
        }

        // Observer for the Game finished event
        viewModel.eventGameFinish.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) gameFinished()
        }

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
        binding.wordText.text = viewModel.word.value

    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.value.toString()
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameGameToScoreGame()
        action.score = viewModel.score.value ?: 0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishComplete()
    }

}