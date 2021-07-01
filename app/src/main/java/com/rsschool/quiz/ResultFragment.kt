package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment(R.layout.fragment_result) {

    private val answersQuestion = arrayOf(
        arrayOf("один", "два", "четыре", "семь", "девять", "3"),
        arrayOf("пять", "двенадцать", "десять", "шесть", "двадцать", "1"),
        arrayOf("десять", "двадцать", "двадцать четыре", "пятнадцать", "тридцать два", "2"),
        arrayOf("десять", " двадцать", "тридцать", "шестьдесят", " сто", "3"),
        arrayOf("шестьдесят", "десять", "тридцать", "семь", "сто", "0")
    )

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val answers: IntArray =
            (arguments?.getIntArray(ANSWERS) ?: arrayOf(0, 0, 0, 0, 0)) as IntArray
        val questions = arguments?.getStringArray(QUESTIONS) ?: arrayOf("", "", "", "", "")
        val result = sumResult(answers) * 100 / 5

        binding.result.text = "Ваш результат $result %"
        binding.exit.setOnClickListener { activity?.finishAffinity() }
        binding.reset.setOnClickListener { onStartQuizFragment() }

        binding.send.setOnClickListener {
            val resultSend = """    Ваш результат $result %
Вопрос 1. ${questions[0]}
    Ваш ответ: ${answersQuestion[0][answers[0]]}
Вопрос 2. ${questions[1]}
    Ваш ответ: ${answersQuestion[1][answers[1]]}
Вопрос 3. ${questions[2]}
    Ваш ответ: ${answersQuestion[2][answers[2]]}
Вопрос 4. ${questions[3]}
    Ваш ответ: ${answersQuestion[3][answers[3]]}
Вопрос 5. ${questions[4]}
    Ваш ответ: ${answersQuestion[4][answers[4]]}"""

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, resultSend)
            startActivity(intent)
        }
    }

    private fun sumResult(answers: IntArray): Int {
        var result = 0
        if (answers[0] == answersQuestion[0][5].toInt()) ++result
        if (answers[1] == answersQuestion[1][5].toInt()) ++result
        if (answers[2] == answersQuestion[2][5].toInt()) ++result
        if (answers[3] == answersQuestion[3][5].toInt()) ++result
        if (answers[4] == answersQuestion[4][5].toInt()) ++result
        return result
    }

    companion object {

        @JvmStatic
        fun newInstance(questions: Array<String>, answers: IntArray): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putIntArray(ANSWERS, answers)
            args.putStringArray(QUESTIONS, questions)
            fragment.arguments = args
            return fragment
        }

        private const val ANSWERS = "answers"
        private const val QUESTIONS = "questions"
    }

    private fun onStartQuizFragment() {
        val fragment: Fragment = QuizFragment()
        val activity: AppCompatActivity = context as AppCompatActivity
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }
}