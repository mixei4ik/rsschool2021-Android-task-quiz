package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rsschool.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment(R.layout.fragment_result) {

//    private var exitButton: Button? = null

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        exitButton = view.findViewById(R.id.exit)

//        exitButton?.setOnClickListener { activity?.finish() }
//    }


    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val answers = arguments?.getIntArray("answers")


        binding.exit.setOnClickListener { activity?.finish() }
        binding.reset.setOnClickListener {  }

        binding.send.setOnClickListener {
            val resultSend = answers.toString()

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, resultSend)
            startActivity(intent)

        }


    }
}