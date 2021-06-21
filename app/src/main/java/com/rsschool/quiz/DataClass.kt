package com.rsschool.quiz

data class DataClass(
    val questions: ArrayList<String> = arrayListOf("Сколько дней в неделе", "Сколько месецев в году"),
    val answer11: String = "1",
    val answer12: String = "1",
    val answer13: String = "1",
    val answer14: String = "1",
    val answer15: String = "1",
    val theme1: String = "Theme.Quiz.First",


    var answer1: Int = -1,
    var answer2: Int = -1,
    var answer3: Int = -1,
    var answer4: Int = -1,
    var answer5: Int = -1

)