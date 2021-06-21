package com.rsschool.quiz

import android.content.Intent
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.DataClass


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val questions = arrayOf(
        arrayOf("Сколько дней в неделе", "1", " 2", "4", "7", "9", "Theme.Quiz.First"),
        arrayOf("Сколько месецев в году", "5", "12", "10", "6", "20", "Theme.Quiz.Second"),
        arrayOf("Сколько часов в сутках", "10", "12", "24", "15", "32", "Theme.Quiz.Third"),
        arrayOf("Сколько секунд в минуте", "10", " 20", " 30", "60", " 100", "Theme.Quiz.Fourth"),
        arrayOf("Сколько минут в часе", "60", "10", "30", "7", "100", "Theme.Quiz.Fifth")
    )

    var answers = mutableListOf(-1, -1, -1, -1, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)
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

    inner class MyAdapter(private val viewPager: ViewPager2) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyViewHolder(FragmentQuizBinding.inflate(layoutInflater, parent, false))

        override fun getItemCount() = questions.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.toolbar.title = "Вопрос ${position + 1}"
            holder.binding.question.text = questions[position][0]
            holder.binding.optionOne.text = questions[position][1]
            holder.binding.optionTwo.text = questions[position][2]
            holder.binding.optionThree.text = questions[position][3]
            holder.binding.optionFour.text = questions[position][4]
            holder.binding.optionFive.text = questions[position][5]
//            holder.binding.context?.setTheme(questions[position][6])
//            holder.binding.context?.theme?.applyStyle(questions[position][6], true)


            if (position == 0) {
                holder.binding.toolbar.navigationIcon = null
                holder.binding.previousButton.isEnabled = false
            }

            if (position == questions.size - 1) holder.binding.nextButton.text = "Submit"

            holder.binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                holder.binding.nextButton.isEnabled = true
                when (checkedId) {
                    holder.binding.optionOne.id -> {
                        answers[position] = 1
                    }
                    holder.binding.optionTwo.id -> {
                        answers[position] = 2
                    }
                    holder.binding.optionThree.id -> {
                        answers[position] = 3
                    }
                    holder.binding.optionFour.id -> {
                        answers[position] = 4
                    }
                    holder.binding.optionFive.id -> {
                        answers[position] = 5
                    }
                }
            }

            holder.binding.nextButton.setOnClickListener {
                if (position == questions.size - 1) {
                    val bundle = bundleOf("answers" to  answers.toIntArray())

                    ResultFragment().arguments = bundle
                    onGoResultFragment()
                } else viewPager.currentItem = position + 1

            }

            holder.binding.previousButton.setOnClickListener {
                viewPager.currentItem = position - 1
            }

            holder.binding.toolbar.setNavigationOnClickListener {
                viewPager.currentItem = position - 1
            }
        }

    }

    private fun onGoResultFragment() {



        val fragment: Fragment = ResultFragment()
//        val activity: AppCompatActivity = context as AppCompatActivity
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()

    }
}