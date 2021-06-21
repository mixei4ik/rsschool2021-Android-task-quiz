package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.FragmentQuiz2Binding
import com.rsschool.quiz.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private var _binding: FragmentQuiz2Binding? = null
    private val binding get() = _binding!!

    val answersQuestion = arrayOf(
        arrayOf("1", " 2", "4", "7", "9", "R.style.Theme_Quiz_First", "3"),
        arrayOf("5", "12", "10", "6", "20", "R.style.Theme_Quiz_Second", "1"),
        arrayOf("10", "12", "24", "15", "32", "R.style.Theme_Quiz_Third", "2"),
        arrayOf("10", " 20", " 30", "60", " 100", "R.style.Theme_Quiz_Fourth", "3"),
        arrayOf("60", "10", "30", "7", "100", "R.style.Theme_Quiz_Fifth", "0")
    )
    val questions = arrayOf(
        "Сколько дней в неделе?", "Сколько месецев в году?",
        "Сколько часов в сутках?", "Сколько секунд в минуте?", "Сколько минут в часе?"
    )

    val theme = arrayOf(R.style.Theme_Quiz_First, R.style.Theme_Quiz_Second,
        R.style.Theme_Quiz_Third, R.style.Theme_Quiz_Fourth, R.style.Theme_Quiz_Fifth)


    var answers = mutableListOf(-1, -1, -1, -1, -1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuiz2Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewpager.adapter = MyAdapter(viewpager)
            viewpager.isUserInputEnabled = false
            viewpager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
    }

    inner class MyViewHolder(val binding: FragmentQuizBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class MyAdapter(private val viewPager: ViewPager2) :
        RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyViewHolder(FragmentQuizBinding.inflate(layoutInflater, parent, false))

        override fun getItemCount() = questions.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.toolbar.title = "Вопрос ${position + 1}"
            holder.binding.question.text = questions[position]
            holder.binding.optionOne.text = answersQuestion[position][0]
            holder.binding.optionTwo.text = answersQuestion[position][1]
            holder.binding.optionThree.text = answersQuestion[position][2]
            holder.binding.optionFour.text = answersQuestion[position][3]
            holder.binding.optionFive.text = answersQuestion[position][4]
            holder.binding.fragmentQuiz.context?.theme?.applyStyle(theme[position], true)

            if (position == 0) {
                holder.binding.toolbar.navigationIcon = null
                holder.binding.previousButton.isEnabled = false
            }

            if (position == questions.size - 1) holder.binding.nextButton.text = "Submit"

            holder.binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                holder.binding.nextButton.isEnabled = true
                when (checkedId) {
                    holder.binding.optionOne.id -> {
                        answers[position] = 0
                    }
                    holder.binding.optionTwo.id -> {
                        answers[position] = 1
                    }
                    holder.binding.optionThree.id -> {
                        answers[position] = 2
                    }
                    holder.binding.optionFour.id -> {
                        answers[position] = 3
                    }
                    holder.binding.optionFive.id -> {
                        answers[position] = 4
                    }
                }
            }

            holder.binding.nextButton.setOnClickListener {
                if (position == questions.size - 1) {
                    onStartResultFragment()
                } else {
                    viewPager.currentItem = position + 1
                }
            }

            holder.binding.previousButton.setOnClickListener {
                viewPager.currentItem = position - 1
            }

            holder.binding.toolbar.setNavigationOnClickListener {
                viewPager.currentItem = position - 1
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onStartResultFragment() {

        val fragment: Fragment =
            ResultFragment.newInstance(answers = answers.toIntArray(), questions = questions)
        val activity: AppCompatActivity = context as AppCompatActivity
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()

    }
}