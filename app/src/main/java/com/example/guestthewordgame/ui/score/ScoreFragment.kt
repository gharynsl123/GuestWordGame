package com.example.guestthewordgame.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guestthewordgame.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding as FragmentScoreBinding

    private lateinit var viewModelFactory: ScoreViewModelFactory
    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFactory =
            ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoreViewModel::class.java]

        // Add observer for score
        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }

        // Navigates back to game when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner) { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreGameToGameGame())
                viewModel.onPlayAgainComplete()
            }
        }

        binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }
    }

}