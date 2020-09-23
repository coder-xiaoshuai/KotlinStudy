package com.example.kotlinstudy.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question", indices = [androidx.room.Index("questionId", unique = true)])
class TableQuestion {
    @PrimaryKey
    var questionId: Int = 0
    var content: String = ""
    var contentImageUrl: String = ""
    var questionType: Int = 0
    var questionTypeName: String = ""
    var questionLevel: Int = 0
    var questionLevelName: String = ""
    var answers: List<String>? = null
    var correctAnswer: String = ""
    var answerAnalysis: String = ""
}