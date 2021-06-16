package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.databinding.FragmentQuizBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val questions = arrayOf(
        arrayOf("Вопрос 1", "ответ 1", "ответ 2", "ответ 3", "ответ 4", "ответ 5"),
        arrayOf("Вопрос 2", "ответ a", "ответ b", "ответ c", "ответ d", "ответ e"),
        arrayOf("Вопрос 3", "ответ A", "ответ B", "ответ C", "ответ D", "ответ F"),
        arrayOf("Вопрос 4", " 1", " 2", " 3", " 4", " 5"),
        arrayOf("Вопрос 5", "100", "200", "300", "400", "500")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)
            viewpager.adapter = MyAdapter()
            viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

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

    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
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

            if (position == 0) holder.binding.toolbar.navigationIcon = null

        }
    }
}